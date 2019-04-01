package com.iwhalecloud.retail.partner.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductBaseGetResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.BrandService;
import com.iwhalecloud.retail.goods2b.service.dubbo.MerchantTagRelService;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductBaseService;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.MerchantRulesDTO;
import com.iwhalecloud.retail.partner.dto.MerchantRulesDetailDTO;
import com.iwhalecloud.retail.partner.dto.req.*;
import com.iwhalecloud.retail.partner.dto.resp.MerchantRulesDetailPageResp;
import com.iwhalecloud.retail.partner.dto.resp.TransferPermissionGetResp;
import com.iwhalecloud.retail.partner.entity.MerchantRules;
import com.iwhalecloud.retail.partner.manager.MerchantManager;
import com.iwhalecloud.retail.partner.manager.MerchantRulesManager;
import com.iwhalecloud.retail.partner.service.MerchantRulesService;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.system.dto.CommonRegionDTO;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.request.CommonRegionListReq;
import com.iwhalecloud.retail.system.dto.request.UserGetReq;
import com.iwhalecloud.retail.system.service.CommonRegionService;
import com.iwhalecloud.retail.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private MerchantService merchantService;

    @Reference
    private MerchantTagRelService merchantTagRelService;

    @Reference
    private UserService userService;

    @Autowired
    private MerchantManager merchantManager;

    /**
     * 添加一个 商家 权限规则
     *
     * @param req
     * @return
     */
    @Override
    @Transactional
    public ResultVO<Integer> saveMerchantRules(MerchantRulesSaveReq req) {
        log.info("MerchantRulesServiceImpl.saveMerchantRules(), 入参ManufacturerSaveReq={} ", req);
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
     * 获取一个 商家 权限规则
     *
     * @param merchantRulesId
     * @return
     */
    @Override
    public ResultVO<MerchantRulesDTO> getMerchantRulesById(String merchantRulesId) {
        log.info("MerchantRulesServiceImpl.getMerchantRulesById(), 入参merchantRulesId={} ", merchantRulesId);
        MerchantRulesDTO merchantRulesDTO = merchantRulesManager.getMerchantRulesById(merchantRulesId);
        log.info("MerchantRulesServiceImpl.getMerchantRulesById(), 出参manufacturerDTO={} ", merchantRulesDTO);
        return ResultVO.success(merchantRulesDTO);
    }


    /**
     * 更新 商家 权限规则
     *
     * @param req
     * @return
     */
//    @Override
//    public ResultVO<Integer> updateMerchantRules(MerchantRulesUpdateReq req) {
//
//        int resultInt = merchantRulesManager.updateMerchantRules(req);
//        log.info("Method: MerchantRulesServiceImpl.updateMerchantRules()");
//        log.info("InputParameter: MerchantRulesUpdateReq = {}", JSON.toJSONString(req));
//        log.info("OutputParameter: update effect rows = {} ", JSON.toJSONString(resultInt));
//
//        if (resultInt <= 0) {
//            return ResultVO.error("更新商家权限记录失败");
//        }
//        return ResultVO.success(resultInt);
//    }

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
        if (result <= 0) {
            return ResultVO.error("删除商家经营权限规则信息失败");
        }
        return ResultVO.success("删除商家权限规则信息 条数：" + result, result);
    }


    /**
     * 商家 权限规则信息列表
     *
     * @param req
     * @returns
     */
    @Override
    public ResultVO<List<MerchantRulesDTO>> listMerchantRules(MerchantRulesListReq req) {
        log.info("MerchantRulesServiceImpl.listMerchantRules(), input: MerchantRulesListReq={} ", req);
        List<MerchantRulesDTO> list = merchantRulesManager.listMerchantRules(req);
        log.info("MerchantRulesServiceImpl.listMerchantRules(), output: list={} ", list);
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

        log.info("MerchantRulesServiceImpl.listMerchantRulesDetail(), output: resultList={} ", resultList);
        return ResultVO.success(resultList);
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
        if(StringUtils.isNotEmpty(req.getLoginName())){
            UserGetReq userGetReq = new UserGetReq();
            userGetReq.setLoginName(req.getLoginName());
            UserDTO userDTO = userService.getUser(userGetReq);
            req.setUserId(userDTO.getUserId());
            req.setRelCode(userDTO.getRelCode());
        }
        // 分组标签 转换成 merchant_id
        if (!StringUtils.isEmpty(req.getTagId())) {
//            MerchantTagRelListReq merchantTagRelListReq = new MerchantTagRelListReq();
//            merchantTagRelListReq.setTagId(req.getTagId());
            List<String> resultMerchantList= merchantManager.getMerchantIdListByTag(req.getTagId());
            req.setTagMerchantList(resultMerchantList);
        }

        Page<MerchantRulesDetailPageResp> merchantRulesDetailPageRespPage = merchantRulesManager.pageMerchantRules(req);
        List<MerchantRulesDetailPageResp> ruleList = merchantRulesDetailPageRespPage.getRecords();
        if(ruleList != null && ruleList.size() > 0){
            for (int i = 0; i < ruleList.size(); i++) {
                MerchantRulesDetailPageResp rule = ruleList.get(i);
                rule.setLanName(commonRegionService.getCommonRegionById(rule.getLanId()).getResultData().getRegionName());
                rule.setCityName(commonRegionService.getCommonRegionById(rule.getCity()).getResultData().getRegionName());
                if(StringUtils.equals(rule.getRuleType(), PartnerConst.MerchantRuleTypeEnum.BUSINESS.getType())){
                    //经营权限
                    if (StringUtils.equals(rule.getTargetType(), PartnerConst.MerchantBusinessTargetTypeEnum.MODEL.getType())) {
                        //机型权限
                        ProductGetByIdReq productGetByIdReq = new ProductGetByIdReq();
                        productGetByIdReq.setProductId(rule.getTargetId());
                        ProductResp productResp = productService.getProduct(productGetByIdReq).getResultData();
                        rule.setTargetName(productResp.getUnitName());
                        rule.setTargetCode(productResp.getSn());
                    } else if(StringUtils.equals(rule.getTargetType(), PartnerConst.MerchantBusinessTargetTypeEnum.REGION.getType())){
                        //区域权限
                        rule.setTargetName(commonRegionService.getCommonRegionById(rule.getTargetId()).getResultData().getRegionName());
                        rule.setTargetCode(rule.getTargetId());
                    } else if (StringUtils.equals(rule.getTargetType(), PartnerConst.MerchantBusinessTargetTypeEnum.MERCHANT.getType())){
                        //商家权限
                        MerchantDTO merchantDTO = merchantService.getMerchantById(rule.getMerchantId()).getResultData();
                        rule.setTargetName(merchantDTO.getMerchantName());
                        rule.setTargetCode(merchantDTO.getMerchantCode());
                    }
                } else if(StringUtils.equals(rule.getRuleType(), PartnerConst.MerchantRuleTypeEnum.GREEN_CHANNEL.getType())){
                    //绿色通道权限
                    if(StringUtils.equals(rule.getTargetType(), PartnerConst.MerchantGreenChannelTargetTypeEnum.PRODUCT.getType())){
                        //产品权限
                        ProductBaseGetByIdReq productBaseGetByIdReq = new ProductBaseGetByIdReq();
                        productBaseGetByIdReq.setProductBaseId(rule.getTargetId());
                        ProductBaseGetResp productBaseGetResp = productBaseService.getProductBase(productBaseGetByIdReq).getResultData();
                        rule.setTargetName(productBaseGetResp.getProductName());
                    } else if(StringUtils.equals(rule.getTargetType(), PartnerConst.MerchantGreenChannelTargetTypeEnum.MODEL.getType())){
                        //机型权限
                        ProductGetByIdReq productGetByIdReq = new ProductGetByIdReq();
                        productGetByIdReq.setProductId(rule.getTargetId());
                        ProductResp productResp = productService.getProduct(productGetByIdReq).getResultData();
                        rule.setTargetName(productResp.getUnitName());
                        rule.setTargetCode(productResp.getSn());
                    }
                } else if(StringUtils.equals(rule.getRuleType(), PartnerConst.MerchantRuleTypeEnum.TRANSFER.getType())){
                    //调拨权限
                    if(StringUtils.equals(rule.getTargetType(), PartnerConst.MerchantTransferTargetTypeEnum.MODEL.getType())){
                        //机型权限
                        ProductGetByIdReq productGetByIdReq = new ProductGetByIdReq();
                        productGetByIdReq.setProductId(rule.getTargetId());
                        ProductResp productResp = productService.getProduct(productGetByIdReq).getResultData();
                        rule.setTargetName(productResp.getUnitName());
                        rule.setTargetCode(productResp.getSn());
                    } else if(StringUtils.equals(rule.getTargetType(), PartnerConst.MerchantTransferTargetTypeEnum.REGION.getType())){
                        //区域权限
                        rule.setTargetName(commonRegionService.getCommonRegionById(rule.getTargetId()).getResultData().getRegionName());
                        rule.setTargetCode(rule.getTargetId());
                    } else if(StringUtils.equals(rule.getTargetType(), PartnerConst.MerchantTransferTargetTypeEnum.MERCHANT.getType())){
                        //商家权限
                        MerchantDTO merchantDTO = merchantService.getMerchantById(rule.getMerchantId()).getResultData();
                        rule.setTargetName(merchantDTO.getMerchantName());
                        rule.setTargetCode(merchantDTO.getMerchantCode());
                    }
                }
            }
            merchantRulesDetailPageRespPage.setRecords(ruleList);
        }

        return ResultVO.success(merchantRulesDetailPageRespPage);
    }

    /**
     * 获取权限详情
     *
     * @param req
     * @param merchantRulesDTOList
     * @return
     */
    private List getDetailList(MerchantRulesDetailListReq req, List<MerchantRulesDTO> merchantRulesDTOList) {
        if (CollectionUtils.isEmpty(merchantRulesDTOList)) {
            return Lists.newArrayList();
        }

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
                BrandQueryReq brandQueryReq = new BrandQueryReq();
                brandQueryReq.setBrandIdList(targetIdList);
//                detailList = brandService.listBrandFileUrl(targetIdList).getResultData();
                detailList = brandService.listBrandFileUrl(brandQueryReq).getResultData();
                fieldName = "brandId";

            } else if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantBusinessTargetTypeEnum.REGION.getType())) {
                // 区域
                fieldName = "regionId";
//                detailList = regionsService.getRegionList(targetIdList).getResultData();
                CommonRegionListReq commonRegionListReq = new CommonRegionListReq();
                commonRegionListReq.setRegionIdList(targetIdList);
                detailList = commonRegionService.listCommonRegion(commonRegionListReq).getResultData();

            } else if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantBusinessTargetTypeEnum.MODEL.getType())) {
                // 机型 productId
                ProductsPageReq productsPageReq = new ProductsPageReq();
                productsPageReq.setUnitName(req.getUnitName());
                productsPageReq.setSn(req.getSn());
                productsPageReq.setUnitType(req.getUnitType());
                productsPageReq.setPageNo(1);
                productsPageReq.setPageSize(1000); // 写死 大一点
                productsPageReq.setProductIdList(targetIdList);
                // 品牌
                if (!StringUtils.isEmpty(req.getBrandId())) {
                    productsPageReq.setBrandIdList(Lists.newArrayList(req.getBrandId()));
                }

                detailList = productService.selectPageProductAdmin(productsPageReq).getResultData().getRecords();
                fieldName = "productId";

            } else if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantBusinessTargetTypeEnum.MERCHANT.getType())) {
                // 商家
                MerchantListReq merchantListReq = new MerchantListReq();
                merchantListReq.setMerchantType(req.getMerchantType());
                merchantListReq.setMerchantName(req.getMerchantName());
                merchantListReq.setMerchantCode(req.getMerchantCode());
                merchantListReq.setLanId(req.getLanId());
                merchantListReq.setCity(req.getCity());
                merchantListReq.setTagId(req.getTagId());
                merchantListReq.setMerchantIdList(targetIdList);
                detailList = merchantService.listMerchant(merchantListReq).getResultData();
                fieldName = "merchantId";

            }
        } else if (StringUtils.equals(req.getRuleType(), PartnerConst.MerchantRuleTypeEnum.GREEN_CHANNEL.getType())) {
            // 绿色通道权限
            ProductsPageReq productsPageReq = new ProductsPageReq();
            productsPageReq.setUnitName(req.getUnitName());
            productsPageReq.setProductName(req.getProductName());
            productsPageReq.setSn(req.getSn());
            productsPageReq.setUnitType(req.getUnitType());
            productsPageReq.setPageNo(1);
            productsPageReq.setPageSize(1000); // 写死 大一点
            // 品牌
            if (!StringUtils.isEmpty(req.getBrandId())) {
                productsPageReq.setBrandIdList(Lists.newArrayList(req.getBrandId()));
            }

            if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantGreenChannelTargetTypeEnum.PRODUCT.getType())) {
                // 产品 productBaseId
                productsPageReq.setProductBaseIdList(targetIdList);
                fieldName = "productBaseId";

            } else if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantGreenChannelTargetTypeEnum.MODEL.getType())) {
                // 机型 productId
                productsPageReq.setProductIdList(targetIdList);
                fieldName = "productId";
            }

            detailList = productService.selectPageProductAdmin(productsPageReq).getResultData().getRecords();
        } else if (StringUtils.equals(req.getRuleType(), PartnerConst.MerchantRuleTypeEnum.TRANSFER.getType())) {
            // 调拨权限
            if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantTransferTargetTypeEnum.MODEL.getType())) {
                // 机型 productId
                ProductsPageReq productsPageReq = new ProductsPageReq();
                productsPageReq.setUnitName(req.getUnitName());
                productsPageReq.setSn(req.getSn());
                productsPageReq.setUnitType(req.getUnitType());
                productsPageReq.setPageNo(1);
                productsPageReq.setPageSize(1000); // 写死 大一点
                productsPageReq.setProductIdList(targetIdList);
                // 品牌
                if (!StringUtils.isEmpty(req.getBrandId())) {
                    productsPageReq.setBrandIdList(Lists.newArrayList(req.getBrandId()));
                }

                detailList = productService.selectPageProductAdmin(productsPageReq).getResultData().getRecords();
                fieldName = "productId";

            } else if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantTransferTargetTypeEnum.REGION.getType())) {
                // 区域
//                detailList = regionsService.getRegionList(targetIdList).getResultData();
                CommonRegionListReq commonRegionListReq = new CommonRegionListReq();
                commonRegionListReq.setRegionIdList(targetIdList);
                detailList = commonRegionService.listCommonRegion(commonRegionListReq).getResultData();
                fieldName = "regionId";

            } else if (StringUtils.equals(req.getTargetType(), PartnerConst.MerchantTransferTargetTypeEnum.MERCHANT.getType())) {
                // 商家
                MerchantListReq merchantListReq = new MerchantListReq();
                merchantListReq.setMerchantType(req.getMerchantType());
                merchantListReq.setMerchantName(req.getMerchantName());
                merchantListReq.setMerchantCode(req.getMerchantCode());
                merchantListReq.setLanId(req.getLanId());
                merchantListReq.setCity(req.getCity());
                merchantListReq.setTagId(req.getTagId());
                merchantListReq.setMerchantIdList(targetIdList);
                detailList = merchantService.listMerchant(merchantListReq).getResultData();
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
//        return resultList;
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
     * 四种情况
     * 1、只添加对象权限时，可选对象是设置的对象列表；
     * 2、只添加区域权限时，可选对象是设置的区域下的所有对象；
     * 3、同时添加了区域权限：长沙和株洲市，对象权限：长沙市对象1，那么可选对象是长沙市对象1和株洲市下的所有对象
     * 4、同时添加了区域权限：长沙和株洲市，对象权限：浏阳市对象1，那么可选对象是浏阳市对象1和长沙和株洲市的所有对象
     * 5、同时添加了区域权限：长沙和株洲市，对象权限：长沙市对象1、衡阳市对象2，那么可选对象是长沙市对象1、衡阳市对象2和株洲市的所有对象
     */
    @Override
    public ResultVO<List<String>> getRegionAndMerchantPermission(MerchantRulesCommonReq req) {
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

        for (MerchantDTO merchantDTO : merchantDTOS) {
            String merchantId = merchantDTO.getMerchantId();
            String city = merchantDTO.getCity();
            String lanId = merchantDTO.getLanId();
            if (!merchantList.contains(merchantId) && !regionList.contains(city) && !regionList.contains(lanId)) {
                return ResultVO.error(merchantDTO.getMerchantName() + ": " + merchantDTO.getMerchantId() + "不符合权限规则");
            }
        }
        return ResultVO.success();
    }
}