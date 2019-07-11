package com.iwhalecloud.retail.partner.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.ProductConst;
import com.iwhalecloud.retail.goods2b.dto.ProductDTO;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductBaseGetResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductPageResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.BrandService;
import com.iwhalecloud.retail.goods2b.service.dubbo.MerchantTagRelService;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductBaseService;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.*;
import com.iwhalecloud.retail.partner.dto.req.*;
import com.iwhalecloud.retail.partner.dto.resp.FactoryMerchantImeiResp;
import com.iwhalecloud.retail.partner.dto.resp.MerchantImeiRulesResp;
import com.iwhalecloud.retail.partner.dto.resp.MerchantRulesDetailPageResp;
import com.iwhalecloud.retail.partner.dto.resp.TransferPermissionGetResp;
import com.iwhalecloud.retail.partner.entity.Merchant;
import com.iwhalecloud.retail.partner.entity.MerchantRules;
import com.iwhalecloud.retail.partner.manager.MerchantManager;
import com.iwhalecloud.retail.partner.manager.MerchantRulesManager;
import com.iwhalecloud.retail.partner.service.MerchantRulesService;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.system.common.SysOrgConst;
import com.iwhalecloud.retail.partner.service.PermissionApplyItemService;
import com.iwhalecloud.retail.partner.service.PermissionApplyService;
import com.iwhalecloud.retail.system.dto.CommonRegionDTO;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.request.*;
import com.iwhalecloud.retail.system.dto.response.OrganizationListResp;
import com.iwhalecloud.retail.system.service.CommonOrgService;
import com.iwhalecloud.retail.system.service.CommonRegionService;
import com.iwhalecloud.retail.system.service.OrganizationService;
import com.iwhalecloud.retail.system.service.UserService;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.dto.req.ProcessStartReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MerchantRulesServiceImpl implements MerchantRulesService {

    @Autowired
    private MerchantRulesManager merchantRulesManager;

    @Reference
    private ProductService productService;

    @Reference
    private ProductBaseService productBaseService;

    @Reference
    private BrandService brandService;

    @Reference
    private CommonRegionService commonRegionService;

    @Reference
    private CommonOrgService commonOrgService;

    @Reference
    private OrganizationService organizationService;

    @Reference
    private MerchantService merchantService;

    @Reference
    private MerchantTagRelService merchantTagRelService;

    @Reference
    private UserService userService;

    @Autowired
    private MerchantManager merchantManager;

    @Autowired
    private PermissionApplyItemService permissionApplyItemService;

    @Autowired
    private PermissionApplyService permissionApplyService;

    /**
     * 添加一个 商家 权限规则
     *
     * @param req
     * @return
     */
    @Override
//    @Transactional
    public ResultVO<Integer> saveMerchantRules(MerchantRulesSaveReq req) {
        log.info("MerchantRulesServiceImpl.saveMerchantRules(), 入参ManufacturerSaveReq={} ", req);

        // 过滤已经存在的权限
        req.setTargetIdList(filterTargetIdList(req.getTargetIdList(), req));

        if (CollectionUtils.isEmpty(req.getTargetIdList())) {
            return ResultVO.success();
        }

        int resultInt = 0;
        if (!CollectionUtils.isEmpty(req.getTargetIdList())) {
            // 批量插入
            List<MerchantRules> merchantRulesList = Lists.newArrayList();
            for (String targetId : req.getTargetIdList()) {
                MerchantRules merchantRules = new MerchantRules();
                BeanUtils.copyProperties(req, merchantRules);
                merchantRules.setTargetId(targetId);
                merchantRulesList.add(merchantRules);
//                resultInt =  resultInt + merchantRulesManager.insert(merchantRules);
            }
            if (merchantRulesManager.saveBatch(merchantRulesList)) {
                // 保存成功
                resultInt = 1;
            }
        }
        log.info("MerchantRulesServiceImpl.saveMerchantRules(), 出参resultInt={} ", resultInt);
        if (resultInt <= 0) {
            return ResultVO.error("新增商家权限信息失败，请确认参数");
        }
        return ResultVO.success(resultInt);
    }

    /**
     * 过滤targetIdList中已经存在的 权限
     * @param targetIdList
     * @param req
     * @return
     */
    private List<String> filterTargetIdList(List<String> targetIdList, MerchantRulesSaveReq req) {

        if (CollectionUtils.isEmpty(targetIdList)) {
            return targetIdList;
        }

        // 先获取已经有权限  过滤targetIdList 里面包含 已有的权限
        MerchantRulesListReq listReq = new MerchantRulesListReq();
        // 一定要包含下面三个条件  才能精确到某一类权限
        listReq.setMerchantId(req.getMerchantId());
        listReq.setRuleType(req.getRuleType());
        listReq.setTargetType(req.getTargetType());
        List<MerchantRulesDTO> merchantRulesDTOList = merchantRulesManager.listMerchantRules(listReq);
        if (!CollectionUtils.isEmpty(merchantRulesDTOList)) {
            // 比较
            List<String> idList = merchantRulesDTOList.stream().map(MerchantRulesDTO::getTargetId).collect(Collectors.toList());
            targetIdList.removeAll(idList);
        }

        return targetIdList;
    }


    private void merchantAssigned(String merchantId) {
        ProductGetReq productGetReq = new ProductGetReq();
        productGetReq.setStatus(ProductConst.StatusType.EFFECTIVE.getCode());
        ResultVO<Page<ProductDTO>> resultVO = productService.selectProduct(productGetReq);
        if (resultVO.isSuccess() && null != resultVO.getResultData()) {
            List<ProductDTO> productDTOs = resultVO.getResultData().getRecords();
            List<String> productIds = new ArrayList<>();
            if (!CollectionUtils.isEmpty(productDTOs)) {
                for (ProductDTO productDTO : productDTOs) {
                    productIds.add(productDTO.getProductId());
                }
                MerchantRulesSaveReq merchantRulesSaveReq = new MerchantRulesSaveReq();
                merchantRulesSaveReq.setMerchantId(merchantId);
                merchantRulesSaveReq.setRuleType(PartnerConst.MerchantRuleTypeEnum.BUSINESS.getType());
                merchantRulesSaveReq.setTargetType(PartnerConst.MerchantBusinessTargetTypeEnum.MODEL.getType());
                int listSize = productIds.size();
                int toIndex = 300;
                log.info("MerchantRulesServiceImpl.merchantAssigned(),商家id={} 添加产品权限数={} ", merchantId, listSize);
                for (int i = 0; i < listSize; i += 300) {
                    if (i + 300 > listSize) {
                        toIndex = listSize - i;
                    }
                    List newlist = productIds.subList(i, i + toIndex);
                    merchantRulesSaveReq.setTargetIdList(newlist);
                    this.saveMerchantRules(merchantRulesSaveReq);
                    Merchant merchant = new Merchant();
                    merchant.setMerchantId(merchantId);
                    merchant.setAssignedFlg(PartnerConst.AssignedFlgEnum.YES.getType());
                    merchantManager.updateMerchant(merchant);
                    log.info("MerchantRulesServiceImpl.merchantAssigned(),商家id={} 添加产品权限第={}次，总共={}次 ", merchantId, i, listSize / 500 + 1);
                }
            }
        }
    }

    /**
     * 删除 商家 权限规则
     *
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> deleteMerchantRules(MerchantRulesDeleteReq req) {
        log.info("MerchantRulesServiceImpl.deleteMerchantRules(), input: MerchantRulesDeleteReq={} ", req);
        int result = merchantRulesManager.deleteMerchantRules(req);
        log.info("MerchantRulesServiceImpl.deleteMerchantRules(), output: delete effect row num = {} ", result);
        if (result < 0) {
            return ResultVO.error("删除商家经营权限规则信息失败");
        }
        return ResultVO.success("删除商家权限规则信息 条数：" + result, result);
    }



    @Override
    public ResultVO<List<MerchantRulesDTO>> listMerchantRules(MerchantRulesDetailListReq req) {
        log.info("MerchantRulesServiceImpl.listMerchantRules(), input: MerchantRulesDetailListReq={} ", req);
        MerchantRulesListReq merchantRulesListReq = new MerchantRulesListReq();
        BeanUtils.copyProperties(req, merchantRulesListReq);
        List<MerchantRulesDTO> list = merchantRulesManager.listMerchantRules(merchantRulesListReq);
        return ResultVO.success(list);
    }

    /**
     * 商家 权限规则详情 信息 列表查询
     *
     * @param req
     * @return
     */
    @Override
    public ResultVO<List<MerchantRulesDetailDTO>> listMerchantRulesDetail(MerchantRulesDetailListReq req) {
        log.info("MerchantRulesServiceImpl.listMerchantRulesDetail(), input: MerchantRulesDetailListReq={} ", req);
        MerchantRulesListReq merchantRulesListReq = new MerchantRulesListReq();
        BeanUtils.copyProperties(req, merchantRulesListReq);
        List<MerchantRulesDTO> list = merchantRulesManager.listMerchantRules(merchantRulesListReq);

        List<MerchantRulesDetailDTO> resultList = getDetailList(req, list);

        log.info("MerchantRulesServiceImpl.listMerchantRulesDetail(), output: resultList.size={} ", resultList.size());
        return ResultVO.success(resultList);
    }

    @Override
    public ResultVO<Page<MerchantRulesDetailDTO>> pageMerchantRulesDetail(MerchantRulesDetailListReq req) {
        log.info("MerchantRulesServiceImpl.pageMerchantRulesDetail(), input: MerchantRulesDetailListReq={} ", req);
        MerchantRulesListReq merchantRulesListReq = new MerchantRulesListReq();
        BeanUtils.copyProperties(req, merchantRulesListReq);

        // 修改后逻辑  分页在  具体详情表
        List<MerchantRulesDTO> list = merchantRulesManager.listMerchantRules(merchantRulesListReq);
        Page<MerchantRulesDetailDTO> merchantRulesDetailDTOPage = getDetailPageList(req, list);

        log.info("MerchantRulesServiceImpl.pageMerchantRulesDetail(), output: resultList.size={} ", JSON.toJSONString(merchantRulesDetailDTOPage.getRecords().size()));
        return ResultVO.success(merchantRulesDetailDTOPage);
    }

    /**
     * 商家 权限规则详情 信息 分页查询
     *
     * @param req
     * @return
     */
    @Override
    public ResultVO<Page<MerchantRulesDetailPageResp>> pageMerchantRules(MerchantRulesDetailPageReq req) {
        log.info("MerchantRulesServiceImpl.pageMerchantRules(), input: MerchantRulesDetailPageReq={} ", req);
        if (StringUtils.isNotEmpty(req.getLoginName())) {
            UserGetReq userGetReq = new UserGetReq();
            userGetReq.setLoginName(req.getLoginName());
            UserDTO userDTO = userService.getUser(userGetReq);
            req.setUserId(userDTO.getUserId());
            req.setRelCode(userDTO.getRelCode());
        }
        // 分组标签 转换成 merchant_id
        if (!StringUtils.isEmpty(req.getTagId())) {
            List<String> resultMerchantList = merchantManager.getMerchantIdListByTag(req.getTagId());
            req.setTagMerchantList(resultMerchantList);
        }
        Page<MerchantRulesDetailPageResp> merchantRulesDetailPageRespPage = merchantRulesManager.pageMerchantRules(req);
        List<MerchantRulesDetailPageResp> ruleList = merchantRulesDetailPageRespPage.getRecords();
        if (ruleList != null && ruleList.size() > 0) {
            for (int i = 0; i < ruleList.size(); i++) {
                MerchantRulesDetailPageResp rule = ruleList.get(i);
                rule.setLanName(getRegionNameByRegionId(rule.getLanId()));
                rule.setCityName(getRegionNameByRegionId(rule.getCity()));
                if (StringUtils.equals(rule.getRuleType(), PartnerConst.MerchantRuleTypeEnum.BUSINESS.getType())) {
                    //经营权限
                    if (StringUtils.equals(rule.getTargetType(), PartnerConst.MerchantBusinessTargetTypeEnum.MODEL.getType())) {
                        //机型权限
                        ProductGetByIdReq productGetByIdReq = new ProductGetByIdReq();
                        productGetByIdReq.setProductId(rule.getTargetId());
                        ProductResp productResp = productService.getProduct(productGetByIdReq).getResultData();
                        if (Objects.nonNull(productResp)) {
                            rule.setTargetName(productResp.getUnitName());
                            rule.setTargetCode(productResp.getSn());
                        }
                    } else if (StringUtils.equals(rule.getTargetType(), PartnerConst.MerchantBusinessTargetTypeEnum.REGION.getType())) {
                        //区域权限
                        rule.setTargetName(getRegionNameByRegionId(rule.getTargetId()));
                        rule.setTargetCode(rule.getTargetId());
                    } else if (StringUtils.equals(rule.getTargetType(), PartnerConst.MerchantBusinessTargetTypeEnum.MERCHANT.getType())) {
                        //商家权限
                        MerchantDTO merchantDTO = merchantService.getMerchantById(rule.getTargetId()).getResultData();
                        if (Objects.nonNull(merchantDTO)) {
                            rule.setTargetName(merchantDTO.getMerchantName());
                            rule.setTargetCode(merchantDTO.getMerchantCode());
                        }
                    }
                } else if (StringUtils.equals(rule.getRuleType(), PartnerConst.MerchantRuleTypeEnum.GREEN_CHANNEL.getType())) {
                    //绿色通道权限
                    if (StringUtils.equals(rule.getTargetType(), PartnerConst.MerchantGreenChannelTargetTypeEnum.PRODUCT.getType())) {
                        //产品权限
                        ProductBaseGetByIdReq productBaseGetByIdReq = new ProductBaseGetByIdReq();
                        productBaseGetByIdReq.setProductBaseId(rule.getTargetId());
                        ProductBaseGetResp productBaseGetResp = productBaseService.getProductBase(productBaseGetByIdReq).getResultData();
                        if (Objects.nonNull(productBaseGetResp)) {
                            rule.setTargetName(productBaseGetResp.getProductName());
                        }
                    } else if (StringUtils.equals(rule.getTargetType(), PartnerConst.MerchantGreenChannelTargetTypeEnum.MODEL.getType())) {
                        //机型权限
                        ProductGetByIdReq productGetByIdReq = new ProductGetByIdReq();
                        productGetByIdReq.setProductId(rule.getTargetId());
                        ProductResp productResp = productService.getProduct(productGetByIdReq).getResultData();
                        if (Objects.nonNull(productResp)) {
                            rule.setTargetName(productResp.getUnitName());
                            rule.setTargetCode(productResp.getSn());
                        }
                    }
                } else if (StringUtils.equals(rule.getRuleType(), PartnerConst.MerchantRuleTypeEnum.TRANSFER.getType())) {
                    //调拨权限
                    if (StringUtils.equals(rule.getTargetType(), PartnerConst.MerchantTransferTargetTypeEnum.MODEL.getType())) {
                        //机型权限
                        ProductGetByIdReq productGetByIdReq = new ProductGetByIdReq();
                        productGetByIdReq.setProductId(rule.getTargetId());
                        ProductResp productResp = productService.getProduct(productGetByIdReq).getResultData();
                        if (Objects.nonNull(productResp)) {
                            rule.setTargetName(productResp.getUnitName());
                            rule.setTargetCode(productResp.getSn());
                        }
                    } else if (StringUtils.equals(rule.getTargetType(), PartnerConst.MerchantTransferTargetTypeEnum.REGION.getType())) {
                        //区域权限
                        rule.setTargetName(getRegionNameByRegionId(rule.getTargetId()));
                        rule.setTargetCode(rule.getTargetId());
                    } else if (StringUtils.equals(rule.getTargetType(), PartnerConst.MerchantTransferTargetTypeEnum.MERCHANT.getType())) {
                        //商家权限
                        MerchantDTO merchantDTO = merchantService.getMerchantById(rule.getTargetId()).getResultData();
                        if (Objects.nonNull(merchantDTO)) {
                            rule.setTargetName(merchantDTO.getMerchantName());
                            rule.setTargetCode(merchantDTO.getMerchantCode());
                        }
                    }
                }
            }
            merchantRulesDetailPageRespPage.setRecords(ruleList);
        }

        log.info("MerchantRulesServiceImpl.pageMerchantRules(), output: list.size={} ", JSON.toJSONString(ruleList.size()));

        return ResultVO.success(merchantRulesDetailPageRespPage);
    }

    /**
     * 根据regionId获取 regionName
     *
     * @param regionId
     * @return
     */
    private String getRegionNameByRegionId(String regionId) {
        if (StringUtils.isEmpty(regionId)) {
            return "";
        }
        CommonRegionDTO commonRegionDTO = commonRegionService.getCommonRegionById(regionId).getResultData();
        if (commonRegionDTO != null) {
            return commonRegionDTO.getRegionName();
        }
        return "";
    }

    /**
     * 获取权限详情
     *
     * @param req
     * @param merchantRulesDTOList
     * @return
     */
    private List getDetailList(MerchantRulesDetailListReq req, List<MerchantRulesDTO> merchantRulesDTOList) {
        List<MerchantRulesDetailDTO> resultList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(merchantRulesDTOList)) {
            return resultList;
        }
        String fieldName = "";
        List<String> targetIdList = Lists.newArrayList();
        for (MerchantRulesDTO merchantRulesDTO : merchantRulesDTOList) {
            targetIdList.add(merchantRulesDTO.getTargetId());
            MerchantRulesDetailDTO merchantRulesDetailDTO = new MerchantRulesDetailDTO();
            BeanUtils.copyProperties(merchantRulesDTO, merchantRulesDetailDTO);
            resultList.add(merchantRulesDetailDTO);
        }
        List detailList = Lists.newArrayList();
        if (StringUtils.equals(req.getRuleType(), PartnerConst.MerchantRuleTypeEnum.BUSINESS.getType())) {
            // 经营权限
            if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantBusinessTargetTypeEnum.BRAND.getType())) {
                // 品牌
                detailList = getBrandList(req, targetIdList);
                fieldName = "brandId";

            } else if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantBusinessTargetTypeEnum.REGION.getType())) {
                // 区域
//                detailList = getCommonRegionList(req, targetIdList);
//                fieldName = "regionId";

                // zhong.wenlong 2019.06.13 改查 sys_common_org表
                detailList = getCommonOrgList(req, targetIdList);
                fieldName = "orgId";

            } else if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantBusinessTargetTypeEnum.MODEL.getType())) {
                // 机型 productId
                detailList = getProductOrModelList(req, targetIdList);
                fieldName = "productId";

            } else if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantBusinessTargetTypeEnum.MERCHANT.getType())) {
                // 商家
                detailList = getMerchantList(req, targetIdList);
                fieldName = "merchantId";

            }
        } else if (StringUtils.equals(req.getRuleType(), PartnerConst.MerchantRuleTypeEnum.GREEN_CHANNEL.getType())) {
            // 绿色通道权限
            if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantGreenChannelTargetTypeEnum.PRODUCT.getType())) {
                // 产品 productBaseId
                fieldName = "productBaseId";
            } else if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantGreenChannelTargetTypeEnum.MODEL.getType())) {
                // 机型 productId
                fieldName = "productId";
            }
            detailList = getProductOrModelList(req, targetIdList);

        } else if (StringUtils.equals(req.getRuleType(), PartnerConst.MerchantRuleTypeEnum.TRANSFER.getType())) {
            // 调拨权限
            if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantTransferTargetTypeEnum.MODEL.getType())) {
                // 机型 productId
                detailList = getProductOrModelList(req, targetIdList);
                fieldName = "productId";

            } else if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantTransferTargetTypeEnum.REGION.getType())) {
                // 区域
                detailList = getCommonRegionList(req, targetIdList);
                fieldName = "regionId";

            } else if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantTransferTargetTypeEnum.MERCHANT.getType())) {
                // 商家
                detailList = getMerchantList(req, targetIdList);
                fieldName = "merchantId";
            }
        }

        // 回塞详情数据
        for (MerchantRulesDetailDTO merchantRulesDetailDTO : resultList) {
            for (Object object : detailList) {
                String id = getFieldValueByFieldName(fieldName, object);
                if (StringUtils.equals(merchantRulesDetailDTO.getTargetId(), id)) {
                    merchantRulesDetailDTO.setTargetData(object);
                    break;
                }
            }
        }

        // 去除没有详情的数据
        return resultList.stream().filter(item -> item.getTargetData() != null).collect(Collectors.toList());
    }

    /**
     * 获取权限详情(分页）
     *
     * @param req
     * @param merchantRulesDTOList
     * @return
     */
    private Page<MerchantRulesDetailDTO> getDetailPageList(MerchantRulesDetailListReq req, List<MerchantRulesDTO> merchantRulesDTOList) {

        Page<MerchantRulesDetailDTO> respPage = new Page<MerchantRulesDetailDTO>();
        if (CollectionUtils.isEmpty(merchantRulesDTOList)) {
            return respPage;
        }

        // 保存具体的分页信息
        Page detailPage = new Page();

        List<MerchantRulesDetailDTO> resultList = Lists.newArrayList();
        String fieldName = "";
        List<String> targetIdList = Lists.newArrayList();
        for (MerchantRulesDTO merchantRulesDTO : merchantRulesDTOList) {
            targetIdList.add(merchantRulesDTO.getTargetId());
            MerchantRulesDetailDTO merchantRulesDetailDTO = new MerchantRulesDetailDTO();
            BeanUtils.copyProperties(merchantRulesDTO, merchantRulesDetailDTO);
            resultList.add(merchantRulesDetailDTO);
        }
        List detailList = Lists.newArrayList();
        if (StringUtils.equals(req.getRuleType(), PartnerConst.MerchantRuleTypeEnum.BUSINESS.getType())) {
            // 经营权限
            if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantBusinessTargetTypeEnum.BRAND.getType())) {
                // 品牌
                detailPage = getBrandPage(req, targetIdList);
                fieldName = "brandId";

            } else if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantBusinessTargetTypeEnum.REGION.getType())) {
                // 区域
//                detailPage = getCommonRegionPage(req, targetIdList);
//                fieldName = "regionId";

                // zhong.wenlong 2019.06.13 改查 sys_common_org表
                detailPage = getCommonOrgPage(req, targetIdList);
                fieldName = "orgId";

            } else if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantBusinessTargetTypeEnum.MODEL.getType())) {
                // 机型 productId
                detailPage = getProductOrModelPage(req, targetIdList);
                fieldName = "productId";

            } else if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantBusinessTargetTypeEnum.MERCHANT.getType())) {
                // 商家
                detailPage = getMerchantPage(req, targetIdList);
                fieldName = "merchantId";
            }
        } else if (StringUtils.equals(req.getRuleType(), PartnerConst.MerchantRuleTypeEnum.GREEN_CHANNEL.getType())) {
            // 绿色通道权限
            if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantGreenChannelTargetTypeEnum.PRODUCT.getType())) {
                // 产品 productBaseId
                fieldName = "productBaseId";
            } else if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantGreenChannelTargetTypeEnum.MODEL.getType())) {
                // 机型 productId
                fieldName = "productId";
            }
            detailPage = getProductOrModelPage(req, targetIdList);
        } else if (StringUtils.equals(req.getRuleType(), PartnerConst.MerchantRuleTypeEnum.TRANSFER.getType())) {
            // 调拨权限
            if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantTransferTargetTypeEnum.MODEL.getType())) {
                // 机型 productId
                detailPage = getProductOrModelPage(req, targetIdList);
                fieldName = "productId";

            } else if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantTransferTargetTypeEnum.REGION.getType())) {
                // 区域
                detailPage = getCommonRegionPage(req, targetIdList);
                fieldName = "regionId";

            } else if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantTransferTargetTypeEnum.MERCHANT.getType())) {
                // 商家
                detailPage = getMerchantPage(req, targetIdList);
                fieldName = "merchantId";
            }
        }

        // 取分页里面的列表数据
        detailList = detailPage.getRecords();

        // 回塞详情数据
        for (MerchantRulesDetailDTO merchantRulesDetailDTO : resultList) {
            for (Object object : detailList) {
                String id = getFieldValueByFieldName(fieldName, object);
                if (StringUtils.equals(merchantRulesDetailDTO.getTargetId(), id)) {
                    merchantRulesDetailDTO.setTargetData(object);
                    break;
                }
            }
        }

        // 主要是复制 分页信息
        BeanUtils.copyProperties(detailPage, respPage);
        // 设置分页 结果列表（并去除没有详情的数据）
        respPage.setRecords(resultList.stream().filter(item -> item.getTargetData() != null).collect(Collectors.toList()));
        return respPage;
    }

    /**
     * 根据条件获取 品牌列表
     *
     * @param req
     * @param targetIdList
     * @return
     */
    private List getBrandList(MerchantRulesDetailListReq req, List<String> targetIdList) {
        BrandQueryReq brandQueryReq = new BrandQueryReq();
        brandQueryReq.setBrandIdList(targetIdList);
        return brandService.listBrandFileUrl(brandQueryReq).getResultData();
    }

    /**
     * 根据条件获取 品牌列表 分页
     *
     * @param req
     * @param targetIdList
     * @return
     */
    private Page getBrandPage(MerchantRulesDetailListReq req, List<String> targetIdList) {
        BrandPageReq brandPageReq = new BrandPageReq();
        brandPageReq.setBrandIdList(targetIdList);
        brandPageReq.setPageNo(req.getPageNo());
        brandPageReq.setPageSize(req.getPageSize());
        return brandService.pageBrandFileUrl(brandPageReq).getResultData();
    }

    /**
     * 根据条件获取 组织列表
     *
     * @param req
     * @param targetIdList
     * @return
     */
    private List getCommonOrgList(MerchantRulesDetailListReq req, List<String> targetIdList) {
        CommonOrgListReq commonOrgListReq = new CommonOrgListReq();
        commonOrgListReq.setOrgIdList(targetIdList);
        return commonOrgService.listCommonOrg(commonOrgListReq).getResultData();
    }

    /**
     * 根据条件获取 组织列表 分页
     *
     * @param req
     * @param targetIdList
     * @return
     */
    private Page getCommonOrgPage(MerchantRulesDetailListReq req, List<String> targetIdList) {
        CommonOrgPageReq commonOrgPageReq = new CommonOrgPageReq();
        commonOrgPageReq.setOrgIdList(targetIdList);
        commonOrgPageReq.setPageNo(req.getPageNo());
        commonOrgPageReq.setPageSize(req.getPageSize());
        return commonOrgService.pageCommonOrg(commonOrgPageReq).getResultData();
    }


    /**
     * 根据条件获取 产品或机型 列表
     *
     * @param req
     * @param targetIdList
     * @return
     */
    private List getProductOrModelList(MerchantRulesDetailListReq req, List<String> targetIdList) {
        ProductsPageReq productsPageReq = new ProductsPageReq();
        productsPageReq.setUnitName(req.getUnitName());
        productsPageReq.setProductName(req.getProductName());
        productsPageReq.setSn(req.getSn());
        productsPageReq.setUnitType(req.getUnitType());
        productsPageReq.setPageNo(1);
        productsPageReq.setPageSize(1000); // 写死 大一点
        productsPageReq.setCatId(req.getCatId());
        productsPageReq.setTypeId(req.getTypeId());
        // 品牌
        if (!StringUtils.isEmpty(req.getBrandId())) {
            productsPageReq.setBrandIdList(Lists.newArrayList(req.getBrandId()));
        }

        if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantGreenChannelTargetTypeEnum.PRODUCT.getType())) {
            // 产品 productBaseId
            productsPageReq.setProductBaseIdList(targetIdList);
        } else if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantGreenChannelTargetTypeEnum.MODEL.getType())) {
            // 机型 productId
            productsPageReq.setProductIdList(targetIdList);
        }
        return productService.selectPageProductAdminAll(productsPageReq).getResultData().getRecords();
    }

    /**
     * 根据条件获取 产品或机型列表 分页
     *
     * @param req
     * @param targetIdList
     * @return
     */
    private Page getProductOrModelPage(MerchantRulesDetailListReq req, List<String> targetIdList) {
        ProductsPageReq productsPageReq = new ProductsPageReq();
        productsPageReq.setUnitName(req.getUnitName());
        productsPageReq.setProductName(req.getProductName());
        productsPageReq.setSn(req.getSn());
        productsPageReq.setUnitType(req.getUnitType());
        productsPageReq.setPageNo(req.getPageNo());
        productsPageReq.setPageSize(req.getPageSize());
        productsPageReq.setCatId(req.getCatId());
        productsPageReq.setTypeId(req.getTypeId());
        // 品牌
        if (!StringUtils.isEmpty(req.getBrandId())) {
            productsPageReq.setBrandIdList(Lists.newArrayList(req.getBrandId()));
        }

        if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantGreenChannelTargetTypeEnum.PRODUCT.getType())) {
            // 产品 productBaseId
            productsPageReq.setProductBaseIdList(targetIdList);
        } else if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantGreenChannelTargetTypeEnum.MODEL.getType())) {
            // 机型 productId
            productsPageReq.setProductIdList(targetIdList);
        }
        return productService.selectPageProductAdminAll(productsPageReq).getResultData();

    }

    /**
     * 根据条件获取 区域列表
     *
     * @param req
     * @param targetIdList
     * @return
     */
    private List getCommonRegionList(MerchantRulesDetailListReq req, List<String> targetIdList) {
        CommonRegionListReq commonRegionListReq = new CommonRegionListReq();
        commonRegionListReq.setRegionIdList(targetIdList);
        return commonRegionService.listCommonRegion(commonRegionListReq).getResultData();
    }

    /**
     * 根据条件获取 区域列表 分页
     *
     * @param req
     * @param targetIdList
     * @return
     */
    private Page getCommonRegionPage(MerchantRulesDetailListReq req, List<String> targetIdList) {
        CommonRegionPageReq commonRegionPageReq = new CommonRegionPageReq();
        commonRegionPageReq.setRegionIdList(targetIdList);
        commonRegionPageReq.setPageNo(req.getPageNo());
        commonRegionPageReq.setPageSize(req.getPageSize());
        return commonRegionService.pageCommonRegion(commonRegionPageReq).getResultData();
    }

    /**
     * 根据条件获取 商家列表
     *
     * @param req
     * @param targetIdList
     * @return
     */
    private List getMerchantList(MerchantRulesDetailListReq req, List<String> targetIdList) {
        MerchantListReq merchantListReq = new MerchantListReq();
        merchantListReq.setMerchantType(req.getMerchantType());
        merchantListReq.setMerchantName(req.getMerchantName());
        merchantListReq.setMerchantCode(req.getMerchantCode());
        merchantListReq.setLanId(req.getLanId());
        merchantListReq.setCity(req.getCity());
        merchantListReq.setTagId(req.getTagId());
        merchantListReq.setLoginName(req.getLoginName());
        merchantListReq.setMerchantIdList(targetIdList);
        return merchantService.listMerchant(merchantListReq).getResultData();
    }

    /**
     * 根据条件获取 商家列表 分页
     *
     * @param req
     * @param targetIdList
     * @return
     */
    private Page getMerchantPage(MerchantRulesDetailListReq req, List<String> targetIdList) {
        MerchantPageReq merchantPageReq = new MerchantPageReq();
        merchantPageReq.setMerchantType(req.getMerchantType());
        merchantPageReq.setMerchantName(req.getMerchantName());
        merchantPageReq.setMerchantCode(req.getMerchantCode());
        merchantPageReq.setLanId(req.getLanId());
        merchantPageReq.setCity(req.getCity());
        merchantPageReq.setTagId(req.getTagId());
        merchantPageReq.setLoginName(req.getLoginName());
        merchantPageReq.setMerchantIdList(targetIdList);
        merchantPageReq.setPageNo(req.getPageNo());
        merchantPageReq.setPageSize(req.getPageSize());
        return merchantService.pageMerchant(merchantPageReq).getResultData();
    }

    /**
     * 根据属性名获取属性值
     *
     * @param fieldName
     * @param object
     * @return
     */
    private String getFieldValueByFieldName(String fieldName, Object object) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            //设置对象的访问权限，保证对private的属性的访问
            field.setAccessible(true);
            return (String) field.get(object);
        } catch (Exception e) {
            log.info("MerchantRulesServiceImpl.getFieldValueByFieldName(), fieldName={} , object={}", fieldName, JSON.toJSONString(object));
            return null;
        }
    }


    /**
     * 获取调拨权限
     *
     * @param merchantId
     * @return
     */
    @Override
    public ResultVO<TransferPermissionGetResp> getTransferPermission(String merchantId) {
        if (StringUtils.isEmpty(merchantId)) {
            return ResultVO.success(new TransferPermissionGetResp());
        }

        MerchantRulesListReq req = new MerchantRulesListReq();
        req.setMerchantId(merchantId);
        // 调拨权限
        req.setRuleType(PartnerConst.MerchantRuleTypeEnum.TRANSFER.getType());
        List<MerchantRulesDTO> list = merchantRulesManager.listMerchantRules(req);
        StringBuilder regionIdsStr = new StringBuilder("");
        StringBuilder productIdsStr = new StringBuilder("");
        StringBuilder merchantIdsStr = new StringBuilder("");

        for (MerchantRulesDTO merchantRulesDTO : list) {
            if (StringUtils.equals(merchantRulesDTO.getTargetType(),
                    PartnerConst.MerchantTransferTargetTypeEnum.REGION.getType())) {
                // 区域类型
                regionIdsStr.append(merchantRulesDTO.getTargetId() + ",");
            } else if (StringUtils.equals(merchantRulesDTO.getTargetType(),
                    PartnerConst.MerchantTransferTargetTypeEnum.MODEL.getType())) {
                // 机型类型
                productIdsStr.append(merchantRulesDTO.getTargetId() + ",");
            } else if (StringUtils.equals(merchantRulesDTO.getTargetType(),
                    PartnerConst.MerchantTransferTargetTypeEnum.MERCHANT.getType())) {
                // 商家类型
                merchantIdsStr.append(merchantRulesDTO.getTargetId() + ",");

            }
        }
        TransferPermissionGetResp resp = new TransferPermissionGetResp();
        if (StringUtils.isBlank(regionIdsStr.toString())) {
            resp.setRegionIdList(null);
        } else {
            resp.setRegionIdList(Lists.newArrayList(regionIdsStr.toString().split(",")));
        }
        if (StringUtils.isBlank(productIdsStr.toString())) {
            resp.setProductIdList(null);
        } else {
            resp.setProductIdList(Lists.newArrayList(productIdsStr.toString().split(",")));
        }
        if (StringUtils.isBlank(merchantIdsStr.toString())) {
            resp.setMerchantIdList(null);
        } else {
            resp.setMerchantIdList(Lists.newArrayList(merchantIdsStr.toString().split(",")));
        }

        return ResultVO.success(resp);
    }

    @Override
    public ResultVO<List<String>> getBusinessModelPermission(String merchantId) {
        if (StringUtils.isEmpty(merchantId)) {
            return ResultVO.success(Lists.newArrayList());
        }

        MerchantRulesListReq req = new MerchantRulesListReq();
        req.setMerchantId(merchantId);
        // 经营权限 --  机型类型
        req.setRuleType(PartnerConst.MerchantRuleTypeEnum.BUSINESS.getType());
        req.setTargetType(PartnerConst.MerchantBusinessTargetTypeEnum.MODEL.getType());

        List<MerchantRulesDTO> list = merchantRulesManager.listMerchantRules(req);
//        String regionIdsStr = "", productIdsStr = "", merchantIdsStr = "";
        StringBuilder modelIdsStr = new StringBuilder("");

        for (MerchantRulesDTO merchantRulesDTO : list) {
            // 机型类型
            modelIdsStr.append(merchantRulesDTO.getTargetId() + ",");
        }

        if (StringUtils.isBlank(modelIdsStr.toString())) {
            return ResultVO.success(null);
        } else {
            List<String> idList = Lists.newArrayList(modelIdsStr.toString().split(","));
            return ResultVO.success(idList);
        }

    }


    /**
     * 获取绿色通道权限--机型
     *
     * @param merchantId
     * @return
     */
    @Override
    public ResultVO<List<String>> getGreenChannelPermission(String merchantId) {
        MerchantRulesListReq req = new MerchantRulesListReq();
        req.setMerchantId(merchantId);
        // 绿色通道
        req.setRuleType(PartnerConst.MerchantRuleTypeEnum.GREEN_CHANNEL.getType());
        List<MerchantRulesDTO> greenChannelRulelist = merchantRulesManager.listMerchantRules(req);
        if (CollectionUtils.isEmpty(greenChannelRulelist)) {
            return ResultVO.error("没有该记录，请确认");
        }

        String productType = PartnerConst.MerchantGreenChannelTargetTypeEnum.MODEL.getType();
        // 筛选对象类型为机型的
        List<MerchantRulesDTO> productTypeRule = greenChannelRulelist.stream().filter(t -> productType.equals(t.getTargetType())).collect(Collectors.toList());
        // 把产品的productId筛选出来
        List<String> productIdList = productTypeRule.stream().map(MerchantRulesDTO::getTargetId).collect(Collectors.toList());

        return ResultVO.success(productIdList);
    }



    /**
     * 通过查询对象获取targetId的集合
     *
     * @param req
     * @return
     */
    @Override
    public ResultVO<List<String>> getCommonPermission(MerchantRulesCommonReq req) {
        if (StringUtils.isEmpty(req.getMerchantId())) {
            return ResultVO.success(Lists.newArrayList());
        }

        MerchantRulesListReq merchantRulesReq = new MerchantRulesListReq();
        BeanUtils.copyProperties(req, merchantRulesReq);
        List<MerchantRulesDTO> list = merchantRulesManager.listMerchantRules(merchantRulesReq);

        // 把品牌的productId筛选出来
        List<String> brandIdList = list.stream().map(MerchantRulesDTO::getTargetId).collect(Collectors.toList());
        return ResultVO.success(brandIdList);
    }

    /**
     * 四种情况
     * 1、只添加机型权限时，可选机型是设置的机型列表；
     * 2、只添加品牌权限时，可选产品是设置的品牌下的所有机型；
     * 3、同时添加了品牌权限：小米和华为，机型权限：小米6，那么可选产品是小米6和华为的所有机型
     * 4、同时添加了品牌权限：小米和华为，机型权限：苹果6，那么可选产品是苹果6和华为、小米的所有机型
     * 5、同时添加了品牌权限：小米和华为，机型权限：小米6、苹果6，那么可选产品是苹果6、小米6和华为的所有机型
     */
    @Override
    public ResultVO<List<String>> getProductAndBrandPermission(String merchantId) {
        List<String> productIdList = null;
        List<String> mixedId = null;

        // 经营权限--机型
        MerchantRulesCommonReq merchantRulesReq = new MerchantRulesCommonReq();
        merchantRulesReq.setMerchantId(merchantId);
        merchantRulesReq.setRuleType(PartnerConst.MerchantRuleTypeEnum.BUSINESS.getType());
        merchantRulesReq.setTargetType(PartnerConst.MerchantBusinessTargetTypeEnum.MODEL.getType());
        ResultVO<List<String>> productResultVO = this.getCommonPermission(merchantRulesReq);
        log.info("MerchantRulesServiceImpl.getProductAndBrandPermission product req={}, resp={}", merchantRulesReq, JSON.toJSONString(productResultVO));
        if (productResultVO.isSuccess() && !CollectionUtils.isEmpty(productResultVO.getResultData())) {
            productIdList = productResultVO.getResultData();
            mixedId = productIdList;
        }

        // 经营权限--品牌
        merchantRulesReq.setTargetType(PartnerConst.MerchantBusinessTargetTypeEnum.BRAND.getType());
        ResultVO<List<String>> brandResultVO = this.getCommonPermission(merchantRulesReq);
        log.info("MerchantRulesServiceImpl.getCommonPermission brand req={}, resp={}", merchantRulesReq, JSON.toJSONString(brandResultVO));
        if (brandResultVO.isSuccess() && !CollectionUtils.isEmpty(brandResultVO.getResultData())) {
            List<String> brandProductIdList = brandResultVO.getResultData();
            ProductAndBrandGetReq productReq = new ProductAndBrandGetReq();
            productReq.setBrandIdList(brandProductIdList);
            productReq.setProductIdList(productIdList);
            ResultVO<List<ProductResp>> productByBrandIdsVO = productService.getProductByProductIdsAndBrandIds(productReq);
            log.info("MerchantRulesServiceImpl.getProductAndBrandPermission productService.getProductByProductIdsAndBrandIds req={}, resp={}", JSON.toJSONString(productReq), JSON.toJSONString(productByBrandIdsVO));
            // 第三种情况 品牌设置和机型设置权限都不为空，且有交集
            if (productByBrandIdsVO.isSuccess() && !CollectionUtils.isEmpty(productByBrandIdsVO.getResultData()) && !CollectionUtils.isEmpty(productIdList)) {
                List<ProductResp> productByBrandList = productByBrandIdsVO.getResultData();
                List<String> brandProductIdListTemp = brandProductIdList;
                // 筛选出设置品牌权限且关联的机型也设置了权限的品牌ID
                productByBrandList = productByBrandList.stream().filter(t -> brandProductIdListTemp.contains(t.getBrandId())).collect(Collectors.toList());
                List<String> relatedBrandId = productByBrandList.stream().map(ProductResp::getBrandId).collect(Collectors.toList());
                log.info("relatedBrandId={}", JSON.toJSONString(relatedBrandId));

                ResultVO<List<ProductResp>> productByProductIdsVO = null;
                // 如果品牌关联的机型不为空，去除此品牌关联的机型ID
                brandProductIdList.removeAll(relatedBrandId);
                if (!CollectionUtils.isEmpty(brandProductIdList)) {
                    ProductAndBrandGetReq brandGetReq = new ProductAndBrandGetReq();
                    brandGetReq.setBrandIdList(brandProductIdList);
                    productByProductIdsVO = productService.getProductByProductIdsAndBrandIds(brandGetReq);
                    log.info("MerchantRulesServiceImpl.getProductAndBrandPermission productService.getProductByProductIdsAndBrandIds req={}, resp={}", brandGetReq, JSON.toJSONString(productByProductIdsVO));
                }

                if (null != productByProductIdsVO && productByProductIdsVO.isSuccess() && !CollectionUtils.isEmpty(productByProductIdsVO.getResultData())) {
                    List<ProductResp> productResps = productByProductIdsVO.getResultData();
                    List<String> notRelatedProductId = productResps.stream().map(ProductResp::getProductId).collect(Collectors.toList());
                    productIdList.addAll(notRelatedProductId);
                    mixedId = productIdList;
                }
                log.info("mixedId={}", JSON.toJSONString(mixedId));
            } else if (productByBrandIdsVO.isSuccess() && CollectionUtils.isEmpty(productByBrandIdsVO.getResultData())) {
                // 设置权限的品牌（不为空）和设置权限的机型没有关联，获取所有品牌关联的机型和设置权限的机型（交集为空）
                ProductAndBrandGetReq noRelatedBrandReq = new ProductAndBrandGetReq();
                noRelatedBrandReq.setBrandIdList(brandProductIdList);
                ResultVO<List<ProductResp>> noRelatedBrandVO = productService.getProductByProductIdsAndBrandIds(noRelatedBrandReq);
                log.info("MerchantRulesServiceImpl.getProductAndBrandPermission productService.getProductByProductIdsAndBrandIds noRelatedBrandReq req={}, resp={}", JSON.toJSONString(noRelatedBrandReq), JSON.toJSONString(noRelatedBrandVO));
                if (noRelatedBrandVO.isSuccess() && !CollectionUtils.isEmpty(noRelatedBrandVO.getResultData())) {
                    List<ProductResp> noRelatedBrandProducts = noRelatedBrandVO.getResultData();
                    mixedId = noRelatedBrandProducts.stream().map(ProductResp::getProductId).collect(Collectors.toList());
                    mixedId.addAll(productIdList);
                }
            } else if (productByBrandIdsVO.isSuccess() && !CollectionUtils.isEmpty(productByBrandIdsVO.getResultData())) {
                // 设置权限的品牌（不为空）和设置权限的机型没有关联，获取所有品牌关联的机型,设置的机型权限为空
                ProductAndBrandGetReq noRelatedBrandReq = new ProductAndBrandGetReq();
                noRelatedBrandReq.setBrandIdList(brandProductIdList);
                ResultVO<List<ProductResp>> noRelatedBrandVO = productService.getProductByProductIdsAndBrandIds(noRelatedBrandReq);
                log.info("MerchantRulesServiceImpl.getProductAndBrandPermission productService.getProductByProductIdsAndBrandIds noRelatedBrandReq req={}, resp={}", JSON.toJSONString(noRelatedBrandReq), JSON.toJSONString(noRelatedBrandVO));
                if (noRelatedBrandVO.isSuccess() && !CollectionUtils.isEmpty(noRelatedBrandVO.getResultData())) {
                    List<ProductResp> noRelatedBrandProducts = noRelatedBrandVO.getResultData();
                    mixedId = noRelatedBrandProducts.stream().map(ProductResp::getProductId).collect(Collectors.toList());
                }
            }
        }

        if (CollectionUtils.isEmpty(mixedId)) {
            // 既没有有机型权限，又没有品牌权限，不能查看，为null会看所有
            mixedId = new ArrayList<>(1);
            String nullProductId = "null";
            mixedId.add(nullProductId);
        }

        return ResultVO.success(mixedId);
    }

    /**
     * 通过merchantId查询 调拨权限的 商家区域和对象权限集合
     * 旧逻辑
     * 四种情况
     * 1、只添加对象权限时，可选对象是设置的对象列表；
     * 2、只添加区域权限时，可选对象是设置的区域下的所有对象；
     * 3、同时添加了区域权限：长沙和株洲市，对象权限：长沙市对象1，那么可选对象是长沙市对象1和株洲市下的所有对象
     * 4、同时添加了区域权限：长沙和株洲市，对象权限：浏阳市对象1，那么可选对象是浏阳市对象1和长沙和株洲市的所有对象
     * 5、同时添加了区域权限：长沙和株洲市，对象权限：长沙市对象1、衡阳市对象2，那么可选对象是长沙市对象1、衡阳市对象2和株洲市的所有对象
     */
    @Override
    public ResultVO<List<String>> getTransferRegionAndMerchantPermission(MerchantRulesCommonReq req) {
        List<String> merchantIdList = null;
        List<String> mixedId = null;

        // 经营权限--对象
        String merchantId = req.getMerchantId();
        String ruleType = req.getRuleType();
        MerchantRulesCommonReq merchantRulesReq = new MerchantRulesCommonReq();
        merchantRulesReq.setMerchantId(merchantId);
        merchantRulesReq.setRuleType(ruleType);
        merchantRulesReq.setTargetType(PartnerConst.MerchantBusinessTargetTypeEnum.MERCHANT.getType());
        ResultVO<List<String>> merchantResultVO = this.getCommonPermission(merchantRulesReq);
        log.info("MerchantRulesServiceImpl.getCommonPermission merchant req={}, resp={}", merchantRulesReq, JSON.toJSONString(merchantResultVO));
        if (merchantResultVO.isSuccess() && !CollectionUtils.isEmpty(merchantResultVO.getResultData())) {
            merchantIdList = merchantResultVO.getResultData();
            mixedId = merchantIdList;
        }

        // 经营权限--区域
        merchantRulesReq.setTargetType(PartnerConst.MerchantBusinessTargetTypeEnum.REGION.getType());
        ResultVO<List<String>> regionResultVO = this.getCommonPermission(merchantRulesReq);
        log.info("MerchantRulesServiceImpl.getCommonPermission region req={}, resp={}", merchantRulesReq, JSON.toJSONString(regionResultVO));
        if (regionResultVO.isSuccess() && !CollectionUtils.isEmpty(regionResultVO.getResultData())) {
            List<String> parentRegionIdList = regionResultVO.getResultData();
            // 把本地网转换为它下面的regionId
            List<String> childRegionIdList = getRegionIdListByLanIdOrRegionIdList(parentRegionIdList);
            MerchantListReq regionAndMerchantIdListReq = new MerchantListReq();
            regionAndMerchantIdListReq.setCityList(childRegionIdList);
            regionAndMerchantIdListReq.setMerchantIdList(merchantIdList);
            ResultVO<List<MerchantDTO>> merchantVO = merchantService.listMerchant(regionAndMerchantIdListReq);
            log.info("MerchantRulesServiceImpl.getRegionAndMerchantPermission merchantService.listMerchant req={}, resp={}", JSON.toJSONString(regionAndMerchantIdListReq), JSON.toJSONString(merchantVO));
            if (merchantVO.isSuccess() && !CollectionUtils.isEmpty(merchantVO.getResultData()) && !CollectionUtils.isEmpty(merchantIdList)) {
                // 有对象权限，有区域权限，有交集
                List<MerchantDTO> regionByMerchantList = merchantVO.getResultData();
                List<String> regionIdListTemp = childRegionIdList;
                // 筛选出设置区域权限且关联的商家也设置了权限的区域ID
                regionByMerchantList = regionByMerchantList.stream().filter(t -> regionIdListTemp.contains(t.getCity())).collect(Collectors.toList());
                List<String> relatedRegionId = regionByMerchantList.stream().map(MerchantDTO::getCity).collect(Collectors.toList());
                List<String> relatedParentRegionId = getParentRegionIdListByRegionIdList(relatedRegionId);
                // 如果区域下的对象不为空，去除此区域内的商家ID,选的区域权限不是本地网但是有对应对象也去除
                parentRegionIdList.removeAll(relatedParentRegionId);
                if (!CollectionUtils.isEmpty(parentRegionIdList)) {
                    // 去除了重回的区域id
                    List<String> noRepeatRegionIdList = getRegionIdListByLanIdOrRegionIdList(parentRegionIdList);
                    MerchantListReq regionListReq = new MerchantListReq();
                    regionListReq.setCityList(noRepeatRegionIdList);
                    ResultVO<List<MerchantDTO>> regionMerchantVO = merchantService.listMerchant(regionListReq);
                    log.info("MerchantRulesServiceImpl.getRegionAndMerchantPermission merchantService.listMerchant req={}, resp={}", JSON.toJSONString(regionListReq), JSON.toJSONString(regionMerchantVO));

                    List<MerchantDTO> merchantResps = regionMerchantVO.getResultData();
                    List<String> notRelatedMerchantId = merchantResps.stream().map(MerchantDTO::getMerchantId).collect(Collectors.toList());
                    merchantIdList.addAll(notRelatedMerchantId);
                }
                mixedId = merchantIdList;
            } else if (merchantVO.isSuccess() && CollectionUtils.isEmpty(merchantVO.getResultData()) && !CollectionUtils.isEmpty(merchantIdList)) {
                // 有对象权限，有区域权限，但是没有交集
                MerchantListReq regionIdListReq = new MerchantListReq();
                regionIdListReq.setCityList(childRegionIdList);
                ResultVO<List<MerchantDTO>> regionIdMerchantVO = merchantService.listMerchant(regionIdListReq);
                log.info("MerchantRulesServiceImpl.getRegionAndMerchantPermission merchantService.listMerchant regionIdListReq={}, resp={}", JSON.toJSONString(regionIdListReq), JSON.toJSONString(regionIdMerchantVO));
                if (regionIdMerchantVO.isSuccess() && !CollectionUtils.isEmpty(regionIdMerchantVO.getResultData())) {
                    List<MerchantDTO> notRelateMerchants = regionIdMerchantVO.getResultData();
                    List<String> notRelateMerchantIds = notRelateMerchants.stream().map(MerchantDTO::getMerchantId).collect(Collectors.toList());
                    notRelateMerchantIds.addAll(merchantIdList);
                    mixedId = notRelateMerchantIds;
                }
            } else if (merchantVO.isSuccess() && !CollectionUtils.isEmpty(merchantVO.getResultData()) && CollectionUtils.isEmpty(merchantIdList)) {
                // 没有对象权限,有区域权限
                MerchantListReq regionIdListReq = new MerchantListReq();
                regionIdListReq.setCityList(childRegionIdList);
                ResultVO<List<MerchantDTO>> regionIdMerchantVO = merchantService.listMerchant(regionIdListReq);
                log.info("MerchantRulesServiceImpl.getRegionAndMerchantPermission merchantService.listMerchant regionIdListReq={}, resp={}", JSON.toJSONString(regionIdListReq), JSON.toJSONString(regionIdMerchantVO));
                if (regionIdMerchantVO.isSuccess() && !CollectionUtils.isEmpty(regionIdMerchantVO.getResultData())) {
                    List<MerchantDTO> notRelateMerchants = regionIdMerchantVO.getResultData();
                    List<String> notRelateMerchantIds = notRelateMerchants.stream().map(MerchantDTO::getMerchantId).collect(Collectors.toList());
                    mixedId = notRelateMerchantIds;
                }
            }
        }

        if (CollectionUtils.isEmpty(mixedId)) {
            // 既没有商家权限，又没有区域权限，不能查看，为null会看所有
            mixedId = new ArrayList<>(1);
            String nullProductId = "null";
            mixedId.add(nullProductId);
        }

        return ResultVO.success(mixedId);
    }

    /**
     *  通过merchantId查询 经营权限的   商家区域和对象权限集合
     *
     * 新逻辑：经营权限--区域  改为 sys_common_org表主键
     * 四种情况
     * 1、只添加对象权限时，可选对象是设置的对象列表；
     * 2、只添加区域权限时，可选对象是设置的区域下的所有对象；
     * 3、同时添加了区域权限：长沙和株洲市，对象权限：长沙市对象1，那么可选对象是长沙市对象1和株洲市下的所有对象
     * 4、同时添加了区域权限：长沙和株洲市，对象权限：浏阳市对象1，那么可选对象是浏阳市对象1和长沙和株洲市的所有对象
     * 5、同时添加了区域权限：长沙和株洲市，对象权限：长沙市对象1、衡阳市对象2，那么可选对象是长沙市对象1、衡阳市对象2和株洲市的所有对象
     */
    @Override
    public ResultVO<List<String>> getBusinessRegionAndMerchantPermission(MerchantRulesCommonReq req) {
        List<String> merchantIdList = null;
        List<String> mixedId = null;

        // 经营权限--对象
        String merchantId = req.getMerchantId();
        String ruleType = req.getRuleType();
        MerchantRulesCommonReq merchantRulesReq = new MerchantRulesCommonReq();
        merchantRulesReq.setMerchantId(merchantId);
        merchantRulesReq.setRuleType(ruleType);
        merchantRulesReq.setTargetType(PartnerConst.MerchantBusinessTargetTypeEnum.MERCHANT.getType());
        ResultVO<List<String>> merchantResultVO = this.getCommonPermission(merchantRulesReq);
        log.info("MerchantRulesServiceImpl.getCommonPermission merchant req={}, resp={}", merchantRulesReq, JSON.toJSONString(merchantResultVO));
        // 商家对应的有权限的  商家ID集合
        merchantIdList = merchantResultVO.getResultData();

        // 经营权限--区域
        merchantRulesReq.setTargetType(PartnerConst.MerchantBusinessTargetTypeEnum.REGION.getType());
        ResultVO<List<String>> regionResultVO = this.getCommonPermission(merchantRulesReq);
        log.info("MerchantRulesServiceImpl.getCommonPermission region req={}, resp={}", merchantRulesReq, JSON.toJSONString(regionResultVO));
        // 商家有权限的 组织ID集合
        List<String> orgIdList = regionResultVO.getResultData();

        // 逻辑：
        if (!CollectionUtils.isEmpty(merchantIdList) && CollectionUtils.isEmpty(orgIdList)) {
            // 有对象权限, 没有组织区域权限
            mixedId = merchantIdList;
            log.info("MerchantRulesServiceImpl.getCommonPermission() 有对象权限, 没有有组织区域权限 merchantIdList={}, orgIdList={}", JSON.toJSONString(merchantIdList), JSON.toJSONString(orgIdList));

        } else if (CollectionUtils.isEmpty(merchantIdList) && !CollectionUtils.isEmpty(orgIdList)) {
            // 没有对象权限, 有组织区域权限
            log.info("MerchantRulesServiceImpl.getCommonPermission() 没有对象权限, 有组织区域权限 merchantIdList={}, orgIdList={}", JSON.toJSONString(merchantIdList), JSON.toJSONString(orgIdList));

            // 找出orgId集合下的所有 下属的第6级 组织ID集合
            OrganizationChildListReq childListReq =  new OrganizationChildListReq();
            childListReq.setOrgIdList(orgIdList);
            childListReq.setOrgLevel(SysOrgConst.ORG_LEVEL.LEVEL_6.getCode());
            List<OrganizationListResp> allChildOrgList = organizationService.listOrganizationChild(childListReq).getResultData();
            List<String> allChildOrgIdList = allChildOrgList.stream().map(OrganizationListResp::getOrgId).collect(Collectors.toList());

            // 根据orgid集合找商家信息
            if (!CollectionUtils.isEmpty(allChildOrgIdList)) {
                MerchantListReq merchantListReq = new MerchantListReq();
                merchantListReq.setParCrmOrgIdList(merchantIdList);
                List<MerchantDTO> allMerchantList = merchantService.listMerchant(merchantListReq).getResultData();
                List<String> allMerchantIdList = allMerchantList.stream().map(MerchantDTO::getMerchantId).collect(Collectors.toList());
                mixedId = allMerchantIdList;
            }

        } else if (!CollectionUtils.isEmpty(merchantIdList) && !CollectionUtils.isEmpty(orgIdList)) {
            // 有对象权限, 有组织区域权限
            log.info("MerchantRulesServiceImpl.getCommonPermission() 有对象权限, 有组织区域权限 merchantIdList={}, orgIdList={}", JSON.toJSONString(merchantIdList), JSON.toJSONString(orgIdList));

            // 找出有权限的商家信息里面的  组织信息 集合
            MerchantListReq merchantListReq = new MerchantListReq();
            merchantListReq.setMerchantIdList(merchantIdList);
            List<MerchantDTO> merchantDTOList = merchantService.listMerchant(merchantListReq).getResultData();
            List<String> merchantOrgIdList = merchantDTOList.stream().map(MerchantDTO::getParCrmOrgId).collect(Collectors.toList());
            List<OrganizationListResp> merchantOrgList = Lists.newArrayList();
            if (!CollectionUtils.isEmpty(merchantOrgIdList)) {
                // 取出商家 组织信息 集合
                OrganizationListReq organizationListReq = new OrganizationListReq();
                organizationListReq.setOrgIdList(merchantOrgIdList);
                merchantOrgList = organizationService.listOrganization(organizationListReq).getResultData();
            }
            List<String> relateOrgIdList =  Lists.newArrayList();
            if (!CollectionUtils.isEmpty(merchantOrgList)) {
                // 筛选出设置组织区域权限且关联的商家也设置了权限的 组织区域ID
                for (OrganizationListResp org : merchantOrgList) {
                    for (String orgId : orgIdList) {
                        if (StringUtils.contains(org.getPathCode(), orgId)) {
                            relateOrgIdList.add(orgId);
                        }
                    }
                }
            }

            // 组织ID集合里面 去除掉有关联的 orgId
            orgIdList.removeAll(relateOrgIdList);

            // 找出筛选后的orgId集合下的所有 下属的第6级 组织ID集合
            OrganizationChildListReq childListReq =  new OrganizationChildListReq();
            childListReq.setOrgIdList(orgIdList);
            childListReq.setOrgLevel(SysOrgConst.ORG_LEVEL.LEVEL_6.getCode());
            List<OrganizationListResp> allChildOrgList = organizationService.listOrganizationChild(childListReq).getResultData();
            List<String> allChildOrgIdList = allChildOrgList.stream().map(OrganizationListResp::getOrgId).collect(Collectors.toList());
            // 根据orgid集合找商家信息
            if (!CollectionUtils.isEmpty(allChildOrgIdList)) {
                MerchantListReq merchantListReq1 = new MerchantListReq();
                merchantListReq1.setParCrmOrgIdList(merchantIdList);
                List<MerchantDTO> allMerchantList = merchantService.listMerchant(merchantListReq1).getResultData();
                List<String> allMerchantIdList = allMerchantList.stream().map(MerchantDTO::getMerchantId).collect(Collectors.toList());
                merchantIdList.addAll(allMerchantIdList);
            }

            mixedId = merchantIdList;
        }

        if (CollectionUtils.isEmpty(mixedId)) {
            // 既没有商家权限，又没有区域权限，不能查看，为null会看所有
            mixedId = new ArrayList<>(1);
            String nullProductId = "null";
            mixedId.add(nullProductId);
        }

        return ResultVO.success(mixedId);
    }


    /**
     * 根据一个（可能含有本地网ID）区域ID集合 取出所有的市县区域ID集合
     * 主要是要把 是本地网ID的  要取出本地网下面的所有 市县区域ID
     *
     * @param regionIdList
     * @return
     */
    private List<String> getRegionIdListByLanIdOrRegionIdList(List<String> regionIdList) {
        if (CollectionUtils.isEmpty(regionIdList)) {
            return regionIdList;
        }
        // 取出本地网的 regionId 集合
        List<CommonRegionDTO> parCommonRegionDTOList = commonRegionService.listCommonRegion(new CommonRegionListReq()).getResultData();
        List<String> lanIdList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(parCommonRegionDTOList)) {
            parCommonRegionDTOList.forEach(commonRegionDTO -> {
                lanIdList.add(commonRegionDTO.getRegionId());
            });
        }

        // 装载原数据集合
        HashSet<String> resultHashSet = new HashSet<>(regionIdList);

        for (String regionId : regionIdList) {
            if (lanIdList.contains(regionId)) {
                // 是本地网  取出下面 的市县
                CommonRegionListReq regionListReq = new CommonRegionListReq();
                regionListReq.setParRegionId(regionId);
                List<CommonRegionDTO> commonRegionDTOList = commonRegionService.listCommonRegion(regionListReq).getResultData();
                log.info("MerchantRulesServiceImpl.getRegionIdListByLanIdOrRegionIdList, req={} ", JSON.toJSONString(regionListReq), JSON.toJSONString(commonRegionDTOList));
                if (!CollectionUtils.isEmpty(commonRegionDTOList)) {
                    commonRegionDTOList.forEach(commonRegionDTO -> {
                        resultHashSet.add(commonRegionDTO.getRegionId());
                    });
                }
            }
        }

        return Lists.newArrayList(resultHashSet);

    }

    /**
     * 根据一个（可能含有本地网ID）区域ID集合 取出所有本地网ID
     *
     * @param regionIdList
     * @return
     */
    private List<String> getParentRegionIdListByRegionIdList(List<String> regionIdList) {
        if (CollectionUtils.isEmpty(regionIdList)) {
            return regionIdList;
        }

        // 装载原数据集合
        HashSet<String> resultHashSet = new HashSet<>(regionIdList);

        for (String regionId : regionIdList) {
            // 是本地网  取出下面 的市县
            CommonRegionListReq regionListReq = new CommonRegionListReq();
            List<String> regionList = Lists.newArrayList(regionId);
            regionListReq.setRegionIdList(regionList);
            List<CommonRegionDTO> commonRegionDTOList = commonRegionService.listCommonRegion(regionListReq).getResultData();
            log.info("MerchantRulesServiceImpl.getParentRegionIdListByRegionIdList, req={} ", JSON.toJSONString(regionListReq), JSON.toJSONString(commonRegionDTOList));
            if (!CollectionUtils.isEmpty(commonRegionDTOList)) {
                commonRegionDTOList.forEach(commonRegionDTO -> {
                    resultHashSet.add(commonRegionDTO.getParRegionId());
                    resultHashSet.add(regionId);
                });
            }
        }
        return Lists.newArrayList(resultHashSet);

    }

    /**
     * 批量添加 商家 权限规则
     *
     * @param req
     * @return
     */
    @Override
    @Transactional
    public ResultVO<Integer> saveExcelMerchantRules(List<MerchantRuleSaveReq> req) {
        log.info("MerchantRulesServiceImpl.saveExcelMerchantRules(), 入参List<MerchantRuleSaveReq>={} ", req);
        List<MerchantRules> merchantRulesList = Lists.newArrayList();
        int resultInt = 0;
        for (int i = 0; i < req.size(); i++) {
            MerchantRules merchantRules = new MerchantRules();
            BeanUtils.copyProperties(req.get(i), merchantRules);
            merchantRulesList.add(merchantRules);
        }
        if (merchantRulesManager.saveBatch(merchantRulesList)) {
            // 保存成功
            resultInt = 1;
        }
        log.info("MerchantRulesServiceImpl.saveExcelMerchantRules(), 出参resultInt={} ", resultInt);
        if (resultInt <= 0) {
            return ResultVO.error("批量添加商家权限规则，请确认参数");
        }
        return ResultVO.success(resultInt);
    }

    @Override
    public ResultVO checkMerchantRules(MerchantRulesCheckReq req) {
        log.info("MerchantRulesServiceImpl.checkMerchantRules req={} ", JSON.toJSONString(req));
        String sourceMerchantId = req.getSourceMerchantId();
        List<String> targetMerchantIds = req.getTargetMerchantIds();
        if (StringUtils.isEmpty(sourceMerchantId) || CollectionUtils.isEmpty(targetMerchantIds)) {
            return ResultVO.error("参数为空");
        }
        MerchantListReq merchantListReq = new MerchantListReq();
        merchantListReq.setMerchantIdList(targetMerchantIds);

        // 查询所有目标商家的商家信息
        ResultVO<List<MerchantDTO>> resultVO = merchantService.listMerchant(merchantListReq);
        List<MerchantDTO> merchantDTOS = resultVO.getResultData();
        if (CollectionUtils.isEmpty(merchantDTOS)) {
            return ResultVO.error("找不到商家信息");
        }

        // 查询商家的权限规则信息
        MerchantRuleGetReq merchantRuleGetReq = new MerchantRuleGetReq();
        merchantRuleGetReq.setMerchantId(sourceMerchantId);
        // 规则类型为经营权限
        merchantRuleGetReq.setRuleType(PartnerConst.MerchantRuleTypeEnum.BUSINESS.getType());
        List<String> targetTypeList = new ArrayList<>();
        // 对象类型为区域或商家
        targetTypeList.add(PartnerConst.MerchantBusinessTargetTypeEnum.REGION.getType());
        targetTypeList.add(PartnerConst.MerchantBusinessTargetTypeEnum.MERCHANT.getType());
        List<MerchantRulesDTO> merchantRulesDTOS = merchantRulesManager.queryMerchantRuleByCondition(merchantRuleGetReq);

        // 根据对象类型分组
        List<String> regionList = new ArrayList<>();
        List<String> merchantList = new ArrayList<>();
        merchantRulesDTOS.forEach(x -> {
            String targetType = x.getTargetType();
            if (PartnerConst.MerchantBusinessTargetTypeEnum.REGION.getType().equals(targetType)) {
                // 对象类型为区域时，对象id为区域id
                regionList.add(x.getTargetId());
            } else if (PartnerConst.MerchantBusinessTargetTypeEnum.MERCHANT.getType().equals(targetType)) {
                // 对象类型为商家时，对象id为商家id
                merchantList.add(x.getTargetId());
            }
        });

        // 原先的逻辑 判断： 目标商家 是否在有权限的商家集合里面 或者 目标商家的 lanId或regionId 是否再有权限的 区域ID集合
//        for (MerchantDTO merchantDTO : merchantDTOS) {
//            String merchantId = merchantDTO.getMerchantId();
//            String city = merchantDTO.getCity();
//            String lanId = merchantDTO.getLanId();
//            if (!merchantList.contains(merchantId) && !regionList.contains(city) && !regionList.contains(lanId)) {
//                return ResultVO.error(merchantDTO.getMerchantName() + ": " + merchantDTO.getMerchantId() + "不符合权限规则");
//            }
//        }

        // 新逻辑 判断： 1、目标商家 是否在有权限的商家集合里面
        // 或者 2、目标商家的 对应的组织信息里面的 pathCode 字段 是否包含 有权限的组织区域ID集合里面的其中一个
        List<String> merchantOrgIdList = merchantDTOS.stream().map(MerchantDTO::getParCrmOrgId).collect(Collectors.toList());
        List<OrganizationListResp> merchantOrgList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(merchantOrgIdList)) {
            // 取出目标商家 组织信息 集合
            OrganizationListReq organizationListReq = new OrganizationListReq();
            organizationListReq.setOrgIdList(merchantOrgIdList);
            merchantOrgList = organizationService.listOrganization(organizationListReq).getResultData();
        }
        for (MerchantDTO merchantDTO : merchantDTOS) {
            String merchantId = merchantDTO.getMerchantId();
            if (!merchantList.contains(merchantId) && !checkOrgPermission(merchantOrgIdList, merchantOrgList, merchantDTO.getParCrmOrgId())) {
                return ResultVO.error(merchantDTO.getMerchantName() + ": " + merchantDTO.getMerchantId() + "不符合权限规则");
            }
        }
        return ResultVO.success();
    }


    /**
     *
     * 校验 商家经营权限-区域权限
     * 逻辑： 目标商家的 对应的组织信息里面的 pathCode 字段 是否包含 有权限的组织区域ID集合里面的其中一个
     * @param sourceOrgIdList  有权限的 组织区域ID集合
     * @param targetOrgList 目标商家集合对应的  组织信息集合(避免每次去数据库 取数据）
     * @param targetOrgId 需要校验的 目标商家的 组织区域ID
     * @return
     */
    private boolean checkOrgPermission(List<String> sourceOrgIdList, List<OrganizationListResp> targetOrgList, String targetOrgId) {
        if (CollectionUtils.isEmpty(sourceOrgIdList) || CollectionUtils.isEmpty(targetOrgList) || Objects.isNull(targetOrgId)) {
            return false;
        }
        // 找到 targetOrgId 对应的 org信息对象
        OrganizationListResp targetOrg = null;
        for (OrganizationListResp org : targetOrgList) {
            if (StringUtils.equals(org.getOrgId(), targetOrgId)) {
                targetOrg = org;
                break;
            }
        }
        if (Objects.isNull(targetOrg)) {
            log.info("MerchantRulesServiceImpl.checkOrgPermission() 没找到targetOrgId={} 对应的组织信息", JSON.toJSONString(targetOrgId));
            return false;
        }

        // 目标 组织区域对象的pathCode 是否包含 有权限组织区域ID
        for (String sourceOrgId : sourceOrgIdList) {
            if (StringUtils.contains(targetOrg.getPathCode(), sourceOrgId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ResultVO<Boolean> checkProdListRule(String merchantId, String productBaseId) {
        List<String> productIds = new ArrayList<>();
        ProductGetReq productGetReq = new ProductGetReq();
        productGetReq.setProductBaseId(productBaseId);
        ResultVO<Page<ProductDTO>> pageResultVO = productService.selectProduct(productGetReq);
        log.info("MerchantRulesServiceImpl.checkProdListRule.selectProduct, pageResultVO.getResultData().getRecords()={} ", pageResultVO.getResultData().getRecords());
        if (pageResultVO.isSuccess() && null != pageResultVO.getResultData()) {
            List<ProductDTO> productDTOList = pageResultVO.getResultData().getRecords();
            if (!CollectionUtils.isEmpty(productDTOList)) {
                for (ProductDTO productDTO : productDTOList) {
                    productIds.add(productDTO.getProductId());
                }
            }
        }

        MerchantRulesCommonReq req = new MerchantRulesCommonReq();
        req.setMerchantId(merchantId);
        req.setRuleType(PartnerConst.MerchantRuleTypeEnum.BUSINESS.getType());
        req.setTargetType(PartnerConst.MerchantBusinessTargetTypeEnum.MODEL.getType());
        ResultVO<List<String>> resultVO = this.getCommonPermission(req);
        List<String> targetids = new ArrayList<>();
        if (resultVO.isSuccess() && null != resultVO.getResultData()) {
            targetids = resultVO.getResultData();
        }

        log.info("MerchantRulesServiceImpl.checkProdListRule, productIds={} ,targetids={}", productIds, targetids);
        if (!CollectionUtils.isEmpty(productIds) && !CollectionUtils.isEmpty(targetids)) {
            productIds.retainAll(targetids);
            log.info("MerchantRulesServiceImpl.checkProdListRule2, productIds={}", productIds);
            if (!CollectionUtils.isEmpty(productIds)) {
                return ResultVO.success(true);
            }
        }
        return ResultVO.success(false);
    }



    @Override
    public ResultVO<List<MerchantImeiRulesResp>> listFactoryMerchantImeiRuleByMerchant(String merchantId) {
        //获取厂商所有规格产品
        ProductRebateReq productRebateReq = new ProductRebateReq();
        productRebateReq.setManufacturerId(merchantId);
        ResultVO<List<ProductResp>> productResultVO = productService.getProductForRebate(productRebateReq);
        log.info("MerchantRulesServiceImpl.listFactoryMerchantImeiRuleByMerchant.getProductForRebate req={} resp={}", JSON.toJSON(productRebateReq), JSON.toJSON(productResultVO));
        if (!productResultVO.isSuccess() || null == productResultVO.getResultData()) {
            return ResultVO.success(new ArrayList<>());
        }
        List<ProductResp> products = productResultVO.getResultData();
        //获取厂商有串码录入的权限
        List<MerchantRulesDTO> rules = listMerchantImeiRules(merchantId);
        //获取厂商在审核中的权限
        PermissionApplyItemListReq permissionApplyItemListReq = new PermissionApplyItemListReq();
        permissionApplyItemListReq.setMerchantId(merchantId);
        permissionApplyItemListReq.setOperationType(PartnerConst.PermissionApplyItemOperationTypeEnum.DELETE.getType());
        permissionApplyItemListReq.setRuleType(PartnerConst.MerchantRuleTypeEnum.IMIE.getType());
        permissionApplyItemListReq.setTargetType(PartnerConst.MerchantImeiTargetTypeEnum.MODEL.getType());
        permissionApplyItemListReq.setStatusCd(PartnerConst.TelecomCommonState.VALID.getCode());
        ResultVO<List<PermissionApplyItemDTO>> applyResult = permissionApplyItemService.listPermissionApplyItem(permissionApplyItemListReq);
        log.info("MerchantRulesServiceImpl.listFactoryMerchantImeiRuleByMerchant.listPermissionApplyItem req={} resp={}", JSON.toJSON(permissionApplyItemListReq), JSON.toJSON(applyResult));
        List<PermissionApplyItemDTO> applyList = applyResult.getResultData();
        //厂商拥有的串码录入权限，组装成map，减少便利次数
        Map<String, String> ruleMap = new HashMap<>();
        for (MerchantRulesDTO dto : rules) {
            ruleMap.put(dto.getTargetId(), dto.getMerchantRuleId());
        }
        //审核中的取消权限，组装成map，减少便利次数
        Map<String, String> applyMap = new HashMap<>();
        for (PermissionApplyItemDTO dto : applyList) {
            applyMap.put(dto.getTargetId(), "1");
        }
        List<MerchantImeiRulesResp> resList = new ArrayList<>();
        //遍历是否有权限
        for (ProductResp product : products) {
            MerchantImeiRulesResp merchantImeiRulesResp = new MerchantImeiRulesResp();
            BeanUtils.copyProperties(product, merchantImeiRulesResp);
            String productId = product.getProductId();
            if (ruleMap.get(productId) == null) {
                merchantImeiRulesResp.setStatusCd(PartnerConst.PermissionApplyStatusEnum.NOT_PASS.getCode());
            } else {
                //注入权限的关联id
                merchantImeiRulesResp.setMerchantRuleId(ruleMap.get(productId));
                merchantImeiRulesResp.setStatusCd(PartnerConst.PermissionApplyStatusEnum.PASS.getCode());
            }
            if (applyMap.get(productId) != null) {
                merchantImeiRulesResp.setStatusCd(PartnerConst.PermissionApplyStatusEnum.AUDITING.getCode());
            }
            resList.add(merchantImeiRulesResp);
        }
        return ResultVO.success(resList);
    }

    /**
     * 获取商家串码录入权限
     * @param merchantId
     * @return
     */
    private List<MerchantRulesDTO> listMerchantImeiRules(String merchantId) {
        MerchantRulesDetailListReq merchantRulesDetailListReq = new MerchantRulesDetailListReq();
        merchantRulesDetailListReq.setMerchantId(merchantId);
        merchantRulesDetailListReq.setRuleType(PartnerConst.MerchantRuleTypeEnum.IMIE.getType());
        merchantRulesDetailListReq.setTargetType(PartnerConst.MerchantImeiTargetTypeEnum.MODEL.getType());
        ResultVO<List<MerchantRulesDTO>> rulePageResultVO = listMerchantRules(merchantRulesDetailListReq);
        return rulePageResultVO.getResultData();
    }

    @Override
    public ResultVO<FactoryMerchantImeiResp> listFactoryMerchantDelImeiRuleByApplyId(String applyId) {
        FactoryMerchantImeiResp factoryMerchantImeiResp = new FactoryMerchantImeiResp();
        //根据applyId获取申请详情
        ResultVO<PermissionApplyDTO> permissionApplyResult = permissionApplyService.getPermissionApplyById(applyId);
        log.info("MerchantRulesServiceImpl.listFactoryMerchantDelImeiRuleByApplyId.getPermissionApplyById req={} resp={}", applyId, JSON.toJSON(permissionApplyResult));
        if (!permissionApplyResult.isSuccess() || permissionApplyResult.getResultData() == null) {
            return ResultVO.error("未找到相关申请单");
        }
        PermissionApplyDTO permissionApplyDTO = permissionApplyResult.getResultData();
        String merchantId = permissionApplyDTO.getMerchantId();
        //根据merchantId获取厂商详情
        ResultVO<MerchantDTO> merchantResult = merchantService.getMerchantById(merchantId);
        log.info("MerchantRulesServiceImpl.listFactoryMerchantDelImeiRuleByApplyId.getMerchantById req={} resp={}", merchantId, JSON.toJSON(merchantResult));
        if (!merchantResult.isSuccess() || merchantResult.getResultData() == null) {
            return ResultVO.error("未找到相关厂商信息");
        }
        factoryMerchantImeiResp.setMerchantName(merchantResult.getResultData().getMerchantName());
        //根据applyId获取申请明细列表
        PermissionApplyItemListReq req = new PermissionApplyItemListReq();
        req.setApplyId(applyId);
        ResultVO<List<PermissionApplyItemDTO>> resultVO = permissionApplyItemService.listPermissionApplyItem(req);
        log.info("MerchantRulesServiceImpl.listFactoryMerchantDelImeiRuleByApplyId.listPermissionApplyItem req={} resp={}", req, JSON.toJSON(resultVO));
        List<PermissionApplyItemDTO> itemList = resultVO.getResultData();
        List<String> productIdList = itemList.stream().map(t -> t.getTargetId()).collect(Collectors.toList());
        //根据申请详情中的targetId获取产品的机型
        List<MerchantImeiRulesResp> productList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(productIdList)) {
//            ProductListGetByIdsReq productListGetByIdsReq = new ProductListGetByIdsReq();
//            productListGetByIdsReq.setProductIdList(productIdList);
//            ResultVO<List<ProductDTO>> productResult = productService.getProductListByIds(productListGetByIdsReq);
            ProductRebateReq productRebateReq = new ProductRebateReq();
            productRebateReq.setManufacturerId(merchantId);
            ResultVO<List<ProductResp>> productResult = productService.getProductForRebate(productRebateReq);
            log.info("MerchantRulesServiceImpl.listFactoryMerchantDelImeiRuleByApplyId.getProductListByIds req={} resp={}", JSON.toJSON(productRebateReq), JSON.toJSON(productResult));
            if (!productResult.isSuccess()) {
                return ResultVO.error("获取产品详情失败");
            }
            List<ProductResp> productDtoList = productResult.getResultData();
            for (ProductResp product : productDtoList) {
                MerchantImeiRulesResp obj = new MerchantImeiRulesResp();
                BeanUtils.copyProperties(product, obj);
                productList.add(obj);
            }
        }
        factoryMerchantImeiResp.setProductList(productList);
        return ResultVO.success(factoryMerchantImeiResp);
    }

    @Override
    @Transactional(rollbackForClassName = "Exception")
    public ResultVO addFactoryMerchantImeiRule(MerchantRulesUpdateReq req) {
        req.setRuleType(PartnerConst.MerchantRuleTypeEnum.IMIE.getType());
        req.setTargetType(PartnerConst.MerchantImeiTargetTypeEnum.MODEL.getType());
        //需要新增的串码权限
        List<String> targetIds = req.getTargetIdList();
        String merchantId = req.getMerchantId();
        //获取商家目前所有的权限
        ResultVO<List<String>> vo = getImeiPermission(merchantId);
        List<String> ruleIds = vo.getResultData();
        if (!CollectionUtils.isEmpty(targetIds)) {
            for (String targetId : targetIds) {
                //判断商家是否原来就有该权限，有就忽略，没有就新加
                if (!ruleIds.contains(targetId)) {
                    MerchantRules merchantRules = new MerchantRules();
                    merchantRules.setMerchantId(merchantId);
                    merchantRules.setRuleType(PartnerConst.MerchantRuleTypeEnum.IMIE.getType());
                    merchantRules.setTargetId(targetId);
                    merchantRules.setTargetType(PartnerConst.MerchantImeiTargetTypeEnum.MODEL.getType());
                    merchantRulesManager.insert(merchantRules);
                }
            }
        }
        return ResultVO.success();
    }

    @Override
    public ResultVO<List<String>> getImeiPermission(String merchantId) {
        MerchantRulesListReq req = new MerchantRulesListReq();
        req.setMerchantId(merchantId);
        req.setRuleType(PartnerConst.MerchantRuleTypeEnum.IMIE.getType());
        req.setTargetType(PartnerConst.MerchantImeiTargetTypeEnum.MODEL.getType());
        List<MerchantRulesDTO> imeiRulelist = merchantRulesManager.listMerchantRules(req);
        // 把产品的productId筛选出来
        List<String> productIdList = imeiRulelist.stream().map(MerchantRulesDTO::getTargetId).collect(Collectors.toList());
        return ResultVO.success(productIdList);
    }

    @Override
    @Transactional(rollbackForClassName = "Exception")
    public ResultVO delFactoryMerchantImeiRule(MerchantRulesUpdateReq req) {
        List<String> targetIds = req.getTargetIdList();
        if (CollectionUtils.isEmpty(targetIds)) {
            //没有需要删除的产品id
            return ResultVO.error("请选择要取消录入权限的产品");
        }
        String merchantId = req.getMerchantId();
        //获取厂商信息，拿到厂商的lanId
        ResultVO<MerchantDTO> merchantResultVO = merchantService.getMerchantById(merchantId);
        MerchantDTO merchantDTO = merchantResultVO.getResultData();
        if (!merchantResultVO.isSuccess() || merchantDTO == null) {
            return ResultVO.error("厂商信息不存在");
        }
        //生成一条权限取消的记录
        PermissionApplySaveDTO permissionApplySaveDTO = new PermissionApplySaveDTO();
        permissionApplySaveDTO.setMerchantId(merchantId);
        permissionApplySaveDTO.setUserId(req.getUserId());
        permissionApplySaveDTO.setName(req.getUserName());
        permissionApplySaveDTO.setLanId(merchantDTO.getLanId());
        permissionApplySaveDTO.setRegionId(merchantDTO.getLanId() + "01");
        permissionApplySaveDTO.setRuleType(PartnerConst.MerchantRuleTypeEnum.IMIE.getType());
        permissionApplySaveDTO.setTargetType(PartnerConst.MerchantImeiTargetTypeEnum.MODEL.getType());
        //组装permissionApply
        PermissionApplySaveReq permissionApplySaveReq = new PermissionApplySaveReq();
        permissionApplySaveReq.setApplyType(PartnerConst.PermissionApplyTypeEnum.PERMISSION_APPLY.getCode());
        permissionApplySaveReq.setMerchantId(merchantId);
        permissionApplySaveReq.setStatusCd(PartnerConst.PermissionApplyStatusEnum.AUDITING.getCode());
        permissionApplySaveDTO.setPermissionApplySaveReq(permissionApplySaveReq);
        //获取厂商有的权限
        List<MerchantRulesDTO> rules = listMerchantImeiRules(merchantId);
        Map<String, String> ruleMap = new HashMap<>();
        for (MerchantRulesDTO dto : rules) {
            ruleMap.put(dto.getTargetId(), dto.getMerchantRuleId());
        }
        //组装permissionApplyItem
        List<PermissionApplyItemSaveReq> itemList = new ArrayList<>();
        for (String targetId : targetIds) {
            PermissionApplyItemSaveReq permissionApplyItemSaveReq = new PermissionApplyItemSaveReq();
            permissionApplyItemSaveReq.setOperationType(PartnerConst.PermissionApplyItemOperationTypeEnum.DELETE.getType());
            permissionApplyItemSaveReq.setRuleType(PartnerConst.MerchantRuleTypeEnum.IMIE.getType());
            permissionApplyItemSaveReq.setTargetType(PartnerConst.MerchantImeiTargetTypeEnum.MODEL.getType());
            permissionApplyItemSaveReq.setTargetId(targetId);
            permissionApplyItemSaveReq.setStatusCd(PartnerConst.TelecomCommonState.VALID.getCode());
            permissionApplyItemSaveReq.setMerchantRuleId(ruleMap.get(targetId));
            itemList.add(permissionApplyItemSaveReq);
        }
        permissionApplySaveDTO.setItemList(itemList);
        // 1、新增申请单信息
        ResultVO<String> resultVO = permissionApplyService.addPermissionApply(permissionApplySaveDTO);
        if (!resultVO.isSuccess()) {
            return resultVO;
        }
        String applyId = resultVO.getResultData();
        ResultVO<String> applyItemResult = permissionApplyService.addPermissionApplyItem(permissionApplySaveDTO, applyId);
        if (!applyItemResult.isSuccess()) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return applyItemResult;
        }
        return ResultVO.success();
    }

    @Override
    public ResultVO<String> checkMerchantImeiRule(String merchantId, String productId) {
        //默认返回成功
        return ResultVO.success();

        //获取厂家串码录入权限
//        MerchantRulesListReq req = new MerchantRulesListReq();
//        req.setMerchantId(merchantId);
//        req.setRuleType(PartnerConst.MerchantRuleTypeEnum.IMIE.getType());
//        req.setTargetType(PartnerConst.MerchantImeiTargetTypeEnum.MODEL.getType());
//        req.setTargetId(productId);
//        List<MerchantRulesDTO> imeiRulelist = merchantRulesManager.listMerchantRules(req);
//        if (imeiRulelist.size() > 0) {
//            return ResultVO.success();
//        }
//        return ResultVO.error("您暂时没有该产品的串码录入权限");
    }

}