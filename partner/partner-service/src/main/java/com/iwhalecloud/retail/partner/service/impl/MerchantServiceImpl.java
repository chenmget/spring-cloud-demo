package com.iwhalecloud.retail.partner.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.MerchantTagRelDTO;
import com.iwhalecloud.retail.goods2b.dto.req.MerchantTagRelListReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.MerchantTagRelService;
import com.iwhalecloud.retail.partner.common.ParInvoiceConst;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.MerchantDetailDTO;
import com.iwhalecloud.retail.partner.dto.req.*;
import com.iwhalecloud.retail.partner.dto.resp.*;
import com.iwhalecloud.retail.partner.entity.BusinessEntity;
import com.iwhalecloud.retail.partner.entity.Invoice;
import com.iwhalecloud.retail.partner.entity.Merchant;
import com.iwhalecloud.retail.partner.entity.MerchantAccount;
import com.iwhalecloud.retail.partner.manager.BusinessEntityManager;
import com.iwhalecloud.retail.partner.manager.InvoiceManager;
import com.iwhalecloud.retail.partner.manager.MerchantAccountManager;
import com.iwhalecloud.retail.partner.manager.MerchantManager;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.CommonRegionDTO;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.request.CommonRegionListReq;
import com.iwhalecloud.retail.system.dto.request.UserEditReq;
import com.iwhalecloud.retail.system.dto.request.UserGetReq;
import com.iwhalecloud.retail.system.dto.request.UserListReq;
import com.iwhalecloud.retail.system.service.CommonRegionService;
import com.iwhalecloud.retail.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service(timeout = 20000)
@Component("merchantService")
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantManager merchantManager;

    @Reference
    private UserService userService;

    @Reference
    private CommonRegionService commonRegionService;

    @Reference
    private MerchantTagRelService merchantTagRelService;

    @Autowired
    private InvoiceManager invoiceManager;

    @Autowired
    private MerchantAccountManager merchantAccountManager;

    @Autowired
    private BusinessEntityManager businessEntityManager;

    /**
     * 根据条件 查询商家条数
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> countMerchant(MerchantCountReq req) {
        log.info("MerchantServiceImpl.countMerchant(), input: MerchantCountReq={} ", JSON.toJSONString(req));
        ResultVO<Integer> resultVO = ResultVO.success(merchantManager.countMerchant(req));
        log.info("MerchantServiceImpl.countMerchant(), output: resultVO={} ", JSON.toJSONString(resultVO));
        return resultVO;
    }


    /**
     * 根据商家id 获取一个 商家 概要信息（字段不够用的话 用getMerchantDetail（）取）
     *
     * @param merchantId
     * @return
     */
    @Override
    public ResultVO<MerchantDTO> getMerchantById(String merchantId) {
        log.info("MerchantServiceImpl.getMerchantById(), input: merchantId={} ", merchantId);
        MerchantGetReq req = new MerchantGetReq();
        req.setMerchantId(merchantId);
        Merchant merchant = merchantManager.getMerchant(req);
        MerchantDTO merchantDTO = new MerchantDTO();
        if (merchant == null) {
            merchantDTO = null;
        } else {
            BeanUtils.copyProperties(merchant, merchantDTO);
            // 取本地网名称  市县名称
            merchantDTO.setLanName(getRegionNameByRegionId(merchantDTO.getLanId()));
            merchantDTO.setCityName(getRegionNameByRegionId(merchantDTO.getCity()));
        }
        log.info("MerchantServiceImpl.getMerchantById(), output: merchantDTO={} ", JSON.toJSONString(merchantDTO));
        return ResultVO.success(merchantDTO);
    }

    /**
     * 根据商家编码 获取一个 商家 概要信息（字段不够用的话 用getMerchantDetail（）取）
     *
     * @param merchantCode
     * @return
     */
    @Override
    public ResultVO<MerchantDTO> getMerchantByCode(String merchantCode) {
        log.info("MerchantServiceImpl.getMerchantByCode(), input: merchantCode={} ", merchantCode);
        MerchantGetReq req = new MerchantGetReq();
        req.setMerchantCode(merchantCode);
        Merchant merchant = merchantManager.getMerchant(req);
        MerchantDTO merchantDTO = new MerchantDTO();
        if (merchant == null) {
            merchantDTO = null;
        } else {
            BeanUtils.copyProperties(merchant, merchantDTO);
            // 取本地网名称  市县名称
            merchantDTO.setLanName(getRegionNameByRegionId(merchantDTO.getLanId()));
            merchantDTO.setCityName(getRegionNameByRegionId(merchantDTO.getCity()));
        }
        log.info("MerchantServiceImpl.getMerchantByCode(), output: merchantDTO={} ", JSON.toJSONString(merchantDTO));
        return ResultVO.success(merchantDTO);
    }

    /**
     * 获取一个 商家 概要信息（字段不够用的话 用getMerchantDetail（）取）
     *
     * @param req
     * @return
     */
    @Override
    public ResultVO<MerchantDTO> getMerchant(MerchantGetReq req) {
        log.info("MerchantServiceImpl.getMerchant(), input: MerchantGetReq={} ", JSON.toJSONString(req));
        Merchant merchant = merchantManager.getMerchant(req);
        MerchantDTO merchantDTO = new MerchantDTO();
        if (merchant == null) {
            merchantDTO = null;
        } else {
            BeanUtils.copyProperties(merchant, merchantDTO);
            // 取本地网名称  市县名称
            merchantDTO.setLanName(getRegionNameByRegionId(merchantDTO.getLanId()));
            merchantDTO.setCityName(getRegionNameByRegionId(merchantDTO.getCity()));

            // 获取经营主体ID
            if (!StringUtils.isEmpty(merchantDTO.getBusinessEntityCode())) {
                BusinessEntityGetReq businessEntityGetReq = new BusinessEntityGetReq();
                businessEntityGetReq.setBusinessEntityCode(merchantDTO.getBusinessEntityCode());
                BusinessEntity businessEntity = businessEntityManager.getBusinessEntity(businessEntityGetReq);
                if (businessEntity != null) {
                    merchantDTO.setBusinessEntityId(businessEntity.getBusinessEntityId());
                }
            }
        }

        log.info("MerchantServiceImpl.getMerchant(), output: MerchantDTO={} ", JSON.toJSONString(merchantDTO));
        return ResultVO.success(merchantDTO);
    }


    /**
     * 获取一个 商家详情（取全表的）
     *
     * @param req
     * @return
     */
    @Override
    public ResultVO<MerchantDetailDTO> getMerchantDetail(MerchantGetReq req) {
        log.info("MerchantServiceImpl.getMerchantDetail(), input: MerchantGetReq={} ", JSON.toJSONString(req));
        Merchant merchant = merchantManager.getMerchant(req);
        MerchantDetailDTO merchantDetailDTO = new MerchantDetailDTO();
        if (merchant == null) {
            merchantDetailDTO = null;
        } else {
            BeanUtils.copyProperties(merchant, merchantDetailDTO);

            // 取本地网名称  市县名称
            merchantDetailDTO.setLanName(getRegionNameByRegionId(merchantDetailDTO.getLanId()));
            merchantDetailDTO.setCityName(getRegionNameByRegionId(merchantDetailDTO.getCity()));

            // 营业执照号、税号、公司账号、营业执照失效期
            InvoiceListReq invoiceListReq = new InvoiceListReq();
            invoiceListReq.setMerchantId(merchantDetailDTO.getMerchantId());
            invoiceListReq.setInvoiceType(ParInvoiceConst.InvoiceType.SPECIAL_VAT_INVOICE.getCode());
            List<Invoice> invoiceList = invoiceManager.listInvoice(invoiceListReq);
            if (!CollectionUtils.isEmpty(invoiceList)) {
                // 取第一条数据
                BeanUtils.copyProperties(invoiceList.get(0), merchantDetailDTO);
            }

            // “系统账号”、“系统状态”
//            if (!StringUtils.isEmpty(merchantDetailDTO.getUserId())) {
//                UserDTO userDTO = userService.getUserByUserId(merchantDetailDTO.getUserId());
            if (!StringUtils.isEmpty(merchantDetailDTO.getMerchantId())) {
                UserGetReq userGetReq = new UserGetReq();
                userGetReq.setRelCode(merchantDetailDTO.getMerchantId());
                UserDTO userDTO = userService.getUser(userGetReq);
                if (userDTO != null) {
                    merchantDetailDTO.setLoginName(userDTO.getLoginName());
                    merchantDetailDTO.setUserStatus(userDTO.getStatusCd().toString());
                }
            }

        }
        log.info("MerchantServiceImpl.getMerchantDetail(), output: merchantDetailDTO={} ", JSON.toJSONString(merchantDetailDTO));
        return ResultVO.success(merchantDetailDTO);
    }

    /**
     * 更新 商家
     *
     * @param req
     * @return
     */
    @Override
    @Transactional
    public ResultVO<Integer> updateMerchant(MerchantUpdateReq req) {
        log.info("MerchantServiceImpl.updateMerchant() input：MerchantUpdateReq={}", req.toString());
        Merchant merchant = new Merchant();
        BeanUtils.copyProperties(req, merchant);
        int result = merchantManager.updateMerchant(merchant);
        log.info("MerchantServiceImpl.updateMerchant() output：update effect row num={}", result);
        if (result <= 0) {
            return ResultVO.error("更新商家信息失败");
        }
        //如果商家状态值不为空，则执行用户状态更新操作
        if (!StringUtils.isEmpty(req.getStatus())) {
            //判断是否有用户的rel_code和商家的MERCHANT_ID相同,
//            UserGetReq userGetReq = new UserGetReq();
//            userGetReq.setRelCode(merchant.getMerchantId());
//            UserDTO userDTO = userService.getUser(userGetReq);
            // 可能有绑定多个用户
            UserListReq userListReq = new UserListReq();
            userListReq.setRelCode(merchant.getMerchantId());
            List<UserDTO> userDTOList = userService.getUserList(userListReq);
//            if(userDTO != null) {
            if (!CollectionUtils.isEmpty(userDTOList)) {
                // 有绑定用户
                try {
                    updateUserBYMerchantCode(userDTOList, merchant);
                } catch (Exception e) {
                    // 更新商家信息失败
                    throw new RuntimeException("更新商家信息失败");
                }
            }
//            else {
//                // 更新商家信息失败
//                throw new RuntimeException("更新商家信息失败");
//            }
        }
        return ResultVO.success(result);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultVO<Integer> updateMerchantBatch(MerchantUpdateBatchReq req) {
        log.info("MerchantServiceImpl.updateMerchantBatch req={}", req.toString());
        final int[] result = {0};
        List<String> merchantIdList = req.getMerchantIdList();
        if (CollectionUtils.isEmpty(merchantIdList)) {
            return ResultVO.success(result[0]);
        }
        merchantIdList.forEach(item -> {
            Merchant merchant = new Merchant();
            merchant.setMerchantType(req.getMerchantType());
            merchant.setStatus(req.getStatus());
            merchant.setMerchantId(item);
            result[0] += merchantManager.updateMerchant(merchant);
            log.info("MerchantServiceImpl.updateMerchant output：update effect row num={}", result[0]);
            if (result[0] > 0) {
                if (!StringUtils.isEmpty(req.getStatus())) {
                    // 可能有绑定多个用户
                    UserListReq userListReq = new UserListReq();
                    userListReq.setRelCode(merchant.getMerchantId());
                    List<UserDTO> userDTOList = userService.getUserList(userListReq);
                    if (!CollectionUtils.isEmpty(userDTOList)) {
                        // 有绑定用户
                        try {
                            updateUserBYMerchantCode(userDTOList, merchant);
                        } catch (Exception e) {
                            // 更新商家信息失败
                            log.error("更新商家信息失败 error={}", e.getMessage());
                            //手动回滚
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        }
                    }
                }
            }
        });
        return ResultVO.success(result[0]);
    }

    /**
     * 根据商家状态设置用户状态
     *
     * @param req
     * @return
     */
    private void updateUserBYMerchantCode(List<UserDTO> userDTOList, Merchant req) throws Exception {
        log.info("MerchantServiceImpl.updateUserBYMerchantCode() input：userDTOList={} Merchant={}", JSON.toJSONString(userDTOList), JSON.toJSONString(req));

        /*
         * 根据商家状态设置用户状态：
         * 1.如果商家状态值为空，则不执行用户绑定商家操作；
         * 2.如果商家状态值为有效VALID：1000,设置用户状态值为有效USER_STATUS_VALID=1;
         * 3.如果商家状态值为除有效外的其他值，设置用户状态值为禁用USER_STATUS_INVALID=0;
         * */
        if (!StringUtils.isEmpty(req.getStatus())
                && !CollectionUtils.isEmpty(userDTOList)) {

            for (UserDTO userDTO : userDTOList) {
                UserEditReq userEditReq = new UserEditReq();
//                BeanUtils.copyProperties(userDTO, userEditReq);
                userEditReq.setUserId(userDTO.getUserId());

                // 如果商家状态值为有效VALID：1000,设置用户状态值为有效USER_STATUS_VALID=1;
                if (PartnerConst.MerchantStatusEnum.VALID.getType().equals(req.getStatus())) {
                    userEditReq.setStatusCd(SystemConst.USER_STATUS_VALID);
                } else {
                    // 如果商家状态值为除有效外的其他值，设置用户状态值为禁用USER_STATUS_INVALID=0;
                    userEditReq.setStatusCd(SystemConst.USER_STATUS_INVALID);
                }
                ResultVO editUserResultVO = userService.editUser(userEditReq);
                if (!editUserResultVO.isSuccess()) {
                    // 抛出异常 回滚
                    throw new Exception();
                }
            }
        }
    }

    /**
     * 商家 信息列表（只取 部分必要字段）
     *
     * @param req
     * @return
     */
    @Override
    public ResultVO<List<MerchantDTO>> listMerchant(MerchantListReq req) {
        log.info("MerchantServiceImpl.listMerchant(), input: MerchantListReq={} ", JSON.toJSONString(req));

        List<MerchantDTO> list = merchantManager.listMerchant(req);

        if (!CollectionUtils.isEmpty(list) && BooleanUtils.isTrue(req.getNeedOtherTableFields())) {

            /***** 其他表字段  统一获取  避免循环获取  不然导出调用时可能会出现超时问题 *****/

            // 取本地网  市县  ID集合
            HashSet<String> regionIdHashSet = new HashSet<>(); // 去重
            for (MerchantDTO dto : list) {
                regionIdHashSet.add(dto.getLanId());
                regionIdHashSet.add(dto.getCity());
            }

            Map<String, String> regionNamesMap = getRegionNamesMap(regionIdHashSet);

            // 取本地网名称  市县名称
            for (MerchantDTO dto : list) {
                // 取本地网名称  市县名称
                dto.setLanName(regionNamesMap.get(dto.getLanId()));
                dto.setCityName(regionNamesMap.get(dto.getCity()));
            }

//            // 取本地网名称  市县名称
//            for (MerchantDTO merchantDTO : list) {
//                // 取本地网名称  市县名称
//                merchantDTO.setLanName(getRegionNameByRegionId(merchantDTO.getLanId()));
//                merchantDTO.setCityName(getRegionNameByRegionId(merchantDTO.getCity()));
//            }
        }
        log.info("MerchantServiceImpl.listMerchant(), output: list={} ", list);
        return ResultVO.success(list);
    }

    /**
     * 商家 信息列表分页
     *
     * @param pageReq
     * @return
     */
    @Override
    public ResultVO<Page<MerchantPageResp>> pageMerchant(MerchantPageReq pageReq) {
        log.info("MerchantServiceImpl.pageMerchant(), input: MerchantPageReq={} ", JSON.toJSONString(pageReq));
        Page<MerchantPageResp> page = merchantManager.pageMerchant(pageReq);

        /***** 其他表字段  统一获取  避免循环获取  不然导出调用时可能会出现超时问题 *****/

        if (!CollectionUtils.isEmpty(page.getRecords())) {

            // 取本地网  市县  ID集合
            HashSet<String> regionIdHashSet = new HashSet<>(); // 去重
            for (MerchantPageResp merchantDTO : page.getRecords()) {
                regionIdHashSet.add(merchantDTO.getLanId());
                regionIdHashSet.add(merchantDTO.getCity());
            }

            Map<String, String> regionNamesMap = getRegionNamesMap(regionIdHashSet);

            // 取本地网名称  市县名称
            for (MerchantPageResp merchantDTO : page.getRecords()) {
                // 取本地网名称  市县名称
                merchantDTO.setLanName(regionNamesMap.get(merchantDTO.getLanId()));
                merchantDTO.setCityName(regionNamesMap.get(merchantDTO.getCity()));
            }
        }

//        // 取本地网名称  市县名称
//        for (MerchantPageResp merchantDTO : page.getRecords()) {
//            // 取本地网名称  市县名称
//            merchantDTO.setLanName(getRegionNameByRegionId(merchantDTO.getLanId()));
//            merchantDTO.setCityName(getRegionNameByRegionId(merchantDTO.getCity()));
//        }
        log.info("MerchantServiceImpl.pageMerchant() output: list<MerchantPageResp>={} ", JSON.toJSONString(page.getRecords()));
        return ResultVO.success(page);
    }

    /**
     * 商家 绑定 用户
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackForClassName = "Exception")
    public ResultVO<Integer> bindUser(MerchantBindUserReq req) throws Exception {
        log.info("MerchantServiceImpl.bindUser() input：MerchantBindUserReq={}", req.toString());
        //TODO 获取用户信息  校对 用户类型和商家类型

        // 更新商家
        Merchant merchant = new Merchant();
//        merchant.setMerchantId(req.getMerchantId());
//        merchant.setUserId(req.getUserId());
//        merchant.setMerchantType(req.getMerchantType());
        BeanUtils.copyProperties(req, merchant);
        int result = merchantManager.updateMerchant(merchant);
        log.info("MerchantServiceImpl.bindUser() output：更新商家信息影响的行数={}", result);
        if (result <= 0) {
            return ResultVO.error("更新商家信息失败");
        }

        // 用户绑定商家
        UserEditReq userEditReq = new UserEditReq();
        userEditReq.setUserId(req.getUserId());
        userEditReq.setRelCode(req.getMerchantId());

//        if (req.getMerchantType() == PartnerConst.MerchantTypeEnum.MANUFACTURER.getType()) {
//            // 商家
//            userEditReq.setUserFounder(SystemConst.USER_FOUNDER_8);
//        }
//        switch (req.getMerchantType()) {
//            case PartnerConst.MerchantTypeEnum.MANUFACTURER.getType():
//            ;
//            case "11":
//
//        }
//        ResultVO editUserResultVO = userService.editUser(userEditReq);
        ResultVO editUserResultVO = ResultVO.error();
        if (!editUserResultVO.isSuccess()) {
            // 抛出异常 回滚
            throw new Exception();
        }

        return ResultVO.success(result);
    }

    /**
     * 零售商类型的 商家 信息列表分页
     *
     * @param pageReq
     * @return
     */
    @Override
    public ResultVO<Page<RetailMerchantDTO>> pageRetailMerchant(RetailMerchantPageReq pageReq) {
        log.info("MerchantServiceImpl.pageRetailMerchant() input：RetailMerchantPageReq={}", JSON.toJSONString(pageReq));
        Page<Merchant> merchantPage = merchantManager.pageRetailMerchant(pageReq);

        // 零售商列表字段:
        // 店中商编码、店中商名称、店中商经营主体名称、销售点名称、销售点编码、
        // 区域（分公司、市县、分局/县部门、营销中心/支局）、渠道视图状态、渠道大类、渠道小类、渠道子类。
        // 分组标签
        // 营业执照失效期

        // 转换成 对应的 dto
        Page<RetailMerchantDTO> targetPage = new Page<>();
        BeanUtils.copyProperties(merchantPage, targetPage);
        List<RetailMerchantDTO> targetList = Lists.newArrayList();

//        for (Merchant merchant : merchantPage.getRecords()) {
//            RetailMerchantDTO targetDTO = new RetailMerchantDTO();
//            BeanUtils.copyProperties(merchant, targetDTO);
//
//            // 取本地网名称  市县名称
//            targetDTO.setLanName(getRegionNameByRegionId(targetDTO.getLanId()));
//            targetDTO.setCityName(getRegionNameByRegionId(targetDTO.getCity()));
//
//            // 营业执照号、税号、公司账号、营业执照失效期
//            InvoiceListReq invoiceListReq = new InvoiceListReq();
//            invoiceListReq.setMerchantId(targetDTO.getMerchantId());
//            invoiceListReq.setInvoiceType(ParInvoiceConst.InvoiceType.SPECIAL_VAT_INVOICE.getCode());
//            List<Invoice> invoiceList = invoiceManager.listInvoice(invoiceListReq);
//            if (!CollectionUtils.isEmpty(invoiceList)) {
//                // 取第一条数据
//                Invoice invoice = invoiceList.get(0);
//                targetDTO.setBusiLicenceCode(invoice.getBusiLicenceCode());
//                targetDTO.setBusiLicenceExpDate(invoice.getBusiLicenceExpDate());
//                targetDTO.setTaxCode(invoice.getTaxCode());
//                targetDTO.setRegisterBankAcct(invoice.getRegisterBankAcct());
//                // 不要用copyProperties  有可能两个表的数据不一样 覆盖商家名称
////                BeanUtils.copyProperties(invoiceList.get(0), targetDTO);
//            }
//
//            // 标签信息
//            MerchantTagRelListReq tagRelListReq = new MerchantTagRelListReq();
//            tagRelListReq.setMerchantId(targetDTO.getMerchantId());
//            List<MerchantTagRelDTO> tagRelDTOList = merchantTagRelService.listMerchantTagRel(tagRelListReq).getResultData();
//            if (!CollectionUtils.isEmpty(tagRelDTOList)) {
//                // 取第一条数据
//                List<String> tagNameList = Lists.newArrayList();
//                for (MerchantTagRelDTO merchantTagRelDTO : tagRelDTOList) {
//                    tagNameList.add(merchantTagRelDTO.getTagName());
//                }
//                // 用逗号分隔拼接
//                targetDTO.setTagNames(StringUtils.join(tagNameList, ","));
//            }
//            targetList.add(targetDTO);
//        }


        /***** 其他表字段  统一获取  避免循环获取  不然导出调用时可能会出现超时问题 *****/
        if (!CollectionUtils.isEmpty(merchantPage.getRecords())) {

            HashSet<String> regionIdHashSet = new HashSet<>(); // 去重
            HashSet<String> merchantIdHashSet = new HashSet<>(); // 去重
            for (Merchant entity : merchantPage.getRecords()) {

                RetailMerchantDTO targetDTO = new RetailMerchantDTO();
                BeanUtils.copyProperties(entity, targetDTO);
                targetList.add(targetDTO);

                // 取本地网  市县  ID集合
                regionIdHashSet.add(entity.getLanId());
                regionIdHashSet.add(entity.getCity());

                // 取merchantId集合
                merchantIdHashSet.add(entity.getMerchantId());
            }

            Map<String, String> regionNamesMap = getRegionNamesMap(regionIdHashSet);
            Map<String, Invoice> invoiceMap = getInvoiceMap(merchantIdHashSet);
            Map<String, String> tagNamesMap = getTagNamesMap(merchantIdHashSet);

            // 取本地网名称  市县名称
            for (RetailMerchantDTO dto : targetList) {

                // 设置 本地网名称  市县名称
                dto.setLanName(regionNamesMap.get(dto.getLanId()));
                dto.setCityName(regionNamesMap.get(dto.getCity()));

                // 设置 发票里面的相关信息
                Invoice invoice = invoiceMap.get(dto.getMerchantId());
                if (Objects.nonNull(invoice)) {
                    dto.setBusiLicenceCode(invoice.getBusiLicenceCode());
                    dto.setBusiLicenceExpDate(invoice.getBusiLicenceExpDate());
                    dto.setTaxCode(invoice.getTaxCode());
                    dto.setRegisterBankAcct(invoice.getRegisterBankAcct());
                    // 不要用copyProperties  有可能两个表的数据不一样 覆盖商家名称
    //                BeanUtils.copyProperties(invoice, dto);
                }
                // 设置标签
                dto.setTagNames(tagNamesMap.get(dto.getMerchantId()));
            }
        }

        targetPage.setRecords(targetList);

        log.info("MerchantServiceImpl.pageRetailMerchant() output：list<RetailMerchantDTO>={}", JSON.toJSONString(targetPage.getRecords()));
        return ResultVO.success(targetPage);
    }

    /**
     * 供应商类型的 商家 信息列表分页
     *
     * @param pageReq
     * @return
     */
    @Override
    public ResultVO<Page<SupplyMerchantDTO>> pageSupplyMerchant(SupplyMerchantPageReq pageReq) {
        log.info("MerchantServiceImpl.pageSupplyMerchant() input：SupplyMerchantPageReq={}", JSON.toJSONString(pageReq));
        Page<Merchant> merchantPage = merchantManager.pageSupplierMerchant(pageReq);

        // 地包商、国/省包商列表字段：
        // 商家编码、商家名称、经营主体名称、渠道视图状态、
        // 分公司、市县（sys_common_region 表字段）
        // 翼支付收款账号、支付宝收款账号 (par_merchant_account 表字段）
        // 营业执照号、税号、公司账号、营业执照失效期 （par_invoice 表字段）
        // 转换成 对应的 dto
        Page<SupplyMerchantDTO> targetPage = new Page<>();
        BeanUtils.copyProperties(merchantPage, targetPage);
        List<SupplyMerchantDTO> targetList = Lists.newArrayList();

//        for (Merchant merchant : merchantPage.getRecords()) {
//            SupplyMerchantDTO targetDTO = new SupplyMerchantDTO();
//            BeanUtils.copyProperties(merchant, targetDTO);
//
//            // 取本地网名称 市县名称
//            targetDTO.setLanName(getRegionNameByRegionId(targetDTO.getLanId()));
//            targetDTO.setCityName(getRegionNameByRegionId(targetDTO.getCity()));
//
//            // 营业执照号、税号、公司账号、营业执照失效期
//            InvoiceListReq invoiceListReq = new InvoiceListReq();
//            invoiceListReq.setMerchantId(targetDTO.getMerchantId());
//            invoiceListReq.setInvoiceType(ParInvoiceConst.InvoiceType.SPECIAL_VAT_INVOICE.getCode());
//            List<Invoice> invoiceList = invoiceManager.listInvoice(invoiceListReq);
//            if (!CollectionUtils.isEmpty(invoiceList)) {
//                // 取第一条数据
//                Invoice invoice = invoiceList.get(0);
//                targetDTO.setBusiLicenceCode(invoice.getBusiLicenceCode());
//                targetDTO.setBusiLicenceExpDate(invoice.getBusiLicenceExpDate());
//                targetDTO.setTaxCode(invoice.getTaxCode());
//                targetDTO.setRegisterBankAcct(invoice.getRegisterBankAcct());
//                // 不要用copyProperties  有可能两个表的数据不一样 覆盖商家名称
////                BeanUtils.copyProperties(invoiceList.get(0), targetDTO);
//            }
//
//            // 取收款账号
//            MerchantAccountListReq accountListReq = new MerchantAccountListReq();
//            accountListReq.setMerchantId(targetDTO.getMerchantId());
//            accountListReq.setAccountType(PartnerConst.MerchantAccountTypeEnum.BEST_PAY.getType());
//            List<MerchantAccount> accountList = merchantAccountManager.listMerchantAccount(accountListReq);
//            if (!CollectionUtils.isEmpty(accountList)) {
//                // 取第一条数据
//                targetDTO.setAccount(accountList.get(0).getAccount());
//            }
//
//
//            targetList.add(targetDTO);
//        }


        /***** 其他表字段  统一获取  避免循环获取  不然导出调用时可能会出现超时问题 *****/
        if (!CollectionUtils.isEmpty(merchantPage.getRecords())) {
            HashSet<String> regionIdHashSet = new HashSet<>(); // 去重
            HashSet<String> merchantIdHashSet = new HashSet<>(); // 去重
            for (Merchant entity : merchantPage.getRecords()) {

                SupplyMerchantDTO targetDTO = new SupplyMerchantDTO();
                BeanUtils.copyProperties(entity, targetDTO);
                targetList.add(targetDTO);

                // 取本地网  市县  ID集合
                regionIdHashSet.add(entity.getLanId());
                regionIdHashSet.add(entity.getCity());

                // 取merchantId集合
                merchantIdHashSet.add(entity.getMerchantId());
            }

            Map<String, String> regionNamesMap = getRegionNamesMap(regionIdHashSet);
            Map<String, Invoice> invoiceMap = getInvoiceMap(merchantIdHashSet);
            Map<String, String> merchantAccountMap = getMerchantAccountMap(merchantIdHashSet);

            // 取本地网名称  市县名称
            for (SupplyMerchantDTO dto : targetList) {

                // 设置 本地网名称  市县名称
                dto.setLanName(regionNamesMap.get(dto.getLanId()));
                dto.setCityName(regionNamesMap.get(dto.getCity()));

                // 设置 发票里面的相关信息
                Invoice invoice = invoiceMap.get(dto.getMerchantId());
                if (Objects.nonNull(invoice)) {
                    dto.setBusiLicenceCode(invoice.getBusiLicenceCode());
                    dto.setBusiLicenceExpDate(invoice.getBusiLicenceExpDate());
                    dto.setTaxCode(invoice.getTaxCode());
                    dto.setRegisterBankAcct(invoice.getRegisterBankAcct());
                    // 不要用copyProperties  有可能两个表的数据不一样 覆盖商家名称
//                BeanUtils.copyProperties(invoice, dto);
                }

                // 设置 账户名称
                dto.setAccount(merchantAccountMap.get(dto.getMerchantId()));
            }
        }

        targetPage.setRecords(targetList);
        log.info("MerchantServiceImpl.pageSupplyMerchant() output：list<SupplyMerchantDTO>={}", JSON.toJSONString(targetPage.getRecords()));
        return ResultVO.success(targetPage);
    }

    /**
     * 厂商类型的 商家 信息列表分页
     *
     * @param pageReq
     * @return
     */
    @Override
    public ResultVO<Page<FactoryMerchantDTO>> pageFactoryMerchant(FactoryMerchantPageReq pageReq) {
        log.info("MerchantServiceImpl.pageFactoryMerchant() input：FactoryMerchantPageReq={}", JSON.toJSONString(pageReq));
        Page<Merchant> merchantPage = merchantManager.pageFactoryMerchant(pageReq);

        // 厂商列表字段：商家编码、商家名称、分公司、市县、渠道视图状态
        // 转换成 对应的 dto
        Page<FactoryMerchantDTO> targetPage = new Page<>();
        BeanUtils.copyProperties(merchantPage, targetPage);
        List<FactoryMerchantDTO> targetList = Lists.newArrayList();

//        for (Merchant merchant : merchantPage.getRecords()) {
//            FactoryMerchantDTO targetDTO = new FactoryMerchantDTO();
//            BeanUtils.copyProperties(merchant, targetDTO);
//
//            // 取本地网名称  市县名称
//            targetDTO.setLanName(getRegionNameByRegionId(targetDTO.getLanId()));
//            targetDTO.setCityName(getRegionNameByRegionId(targetDTO.getCity()));
//
//            targetList.add(targetDTO);
//        }

        /***** 其他表字段  统一获取  避免循环获取  不然导出调用时可能会出现超时问题 *****/
        if (!CollectionUtils.isEmpty(merchantPage.getRecords())) {

            HashSet<String> regionIdHashSet = new HashSet<>(); // 去重
            for (Merchant entity : merchantPage.getRecords()) {

                FactoryMerchantDTO targetDTO = new FactoryMerchantDTO();
                BeanUtils.copyProperties(entity, targetDTO);
                targetList.add(targetDTO);

                // 取本地网  市县  ID集合
                regionIdHashSet.add(entity.getLanId());
                regionIdHashSet.add(entity.getCity());

            }

            Map<String, String> regionNamesMap = getRegionNamesMap(regionIdHashSet);

            // 取本地网名称  市县名称
            for (FactoryMerchantDTO dto : targetList) {

                // 设置 本地网名称  市县名称
                dto.setLanName(regionNamesMap.get(dto.getLanId()));
                dto.setCityName(regionNamesMap.get(dto.getCity()));

            }
        }

        targetPage.setRecords(targetList);

        log.info("MerchantServiceImpl.pageFactoryMerchant() output：list<FactoryMerchantDTO>={}", JSON.toJSONString(targetPage.getRecords()));
        return ResultVO.success(targetPage);
    }

    /**
     * 根据regionId集合获取所有的  区域名称
     * @param regionIdHashSet
     * @return
     */
    private Map<String, String> getRegionNamesMap(HashSet<String> regionIdHashSet) {
        CommonRegionListReq req = new CommonRegionListReq();
        req.setRegionIdList(Lists.newArrayList(regionIdHashSet));
        List<CommonRegionDTO> dtoList = commonRegionService.listCommonRegion(req).getResultData();
        Map resultMap = new HashMap();
        if (!CollectionUtils.isEmpty(dtoList)) {
            dtoList.forEach(commonRegionDTO -> {
                resultMap.put(commonRegionDTO.getRegionId(), commonRegionDTO.getRegionName());
            });
        }
        return resultMap;
    }

    /**
     * 根据商家ID集合获取所有的 发票信息
     * @param merchantIdHashSet
     * @return
     */
    private Map<String, Invoice> getInvoiceMap(HashSet<String> merchantIdHashSet) {

        // 营业执照号、税号、公司账号、营业执照失效期
        InvoiceListReq invoiceListReq = new InvoiceListReq();
        invoiceListReq.setMerchantIdList(Lists.newArrayList(merchantIdHashSet));
        invoiceListReq.setInvoiceType(ParInvoiceConst.InvoiceType.SPECIAL_VAT_INVOICE.getCode());
        List<Invoice> invoiceList = invoiceManager.listInvoice(invoiceListReq);
        Map<String, Invoice> resultMap = new HashMap();
        if (!CollectionUtils.isEmpty(invoiceList)) {
            invoiceList.forEach(invoice -> {
                resultMap.put(invoice.getMerchantId(), invoice);
            });
        }
        return resultMap;
    }

    /**
     * 根据商家ID集合获取所有的 发票信息
     * @param merchantIdHashSet
     * @return
     */
    private Map<String, String> getMerchantAccountMap(HashSet<String> merchantIdHashSet) {

        // 取收款账号
        MerchantAccountListReq accountListReq = new MerchantAccountListReq();
        accountListReq.setMerchantIdList(Lists.newArrayList(merchantIdHashSet));
        accountListReq.setAccountType(PartnerConst.MerchantAccountTypeEnum.BEST_PAY.getType());
        List<MerchantAccount> merchantAccountList = merchantAccountManager.listMerchantAccount(accountListReq);
        Map<String, String> resultMap = new HashMap();
        if (!CollectionUtils.isEmpty(merchantAccountList)) {
            merchantAccountList.forEach(merchantAccount -> {
                resultMap.put(merchantAccount.getMerchantId(), merchantAccount.getAccount());
            });
        }
        return resultMap;
    }

    /**
     * 根据商家ID集合获取所有的 标签名称
     * @param merchantIdHashSet
     * @return
     */
    private Map<String, String> getTagNamesMap(HashSet<String> merchantIdHashSet) {

        // 标签信息
        MerchantTagRelListReq tagRelListReq = new MerchantTagRelListReq();
        tagRelListReq.setMerchantIdList(Lists.newArrayList(merchantIdHashSet));
        List<MerchantTagRelDTO> merchantTagRelDTOList = merchantTagRelService.listMerchantTagRel(tagRelListReq).getResultData();
        Map<String, String> resultMap = new HashMap();
        if (!CollectionUtils.isEmpty(merchantTagRelDTOList)) {
            merchantTagRelDTOList.forEach(dto -> {
                String str = resultMap.get(dto.getMerchantId());
                if (StringUtils.isEmpty(str)) {
                    resultMap.put(dto.getMerchantId(), dto.getTagName());
                } else {  // 多个标签的  要拼装
                    str = str + "," + dto.getTagName();
                    resultMap.put(dto.getMerchantId(), str);
                }
            });
        }

        return resultMap;
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

    @Override
    public ResultVO<List<String>> listMerchantByLanCity(MerchantListLanCityReq req) {
        log.info("MerchantServiceImpl.listMerchantByLanCity MerchantListReq={}",JSON.toJSON(req));
        List<Merchant> merchants = merchantManager.listMerchantByLanCity(req);
        log.info("MerchantServiceImpl.listMerchantByLanCity merchantManager.listMerchantByLanCity merchants={}",JSON.toJSON(merchants));
        List<String> merchantIds = merchants.stream().distinct().map(Merchant::getMerchantId).collect(Collectors.toList());
        return ResultVO.success(merchantIds);
    }

    /**
     * 根据商家id 获取一个 商家 概要信息（字段不够用的话 用getMerchantDetail（）取）
     *
     * @param merchantId
     * @return
     */
    @Override
    public MerchantDTO getMerchantInfoById(String merchantId) {
        log.info("MerchantServiceImpl.getMerchantById(), input: merchantId={} ", merchantId);
        MerchantGetReq req = new MerchantGetReq();
        req.setMerchantId(merchantId);
        Merchant merchant = merchantManager.getMerchant(req);
        MerchantDTO merchantDTO = new MerchantDTO();
        if (merchant == null) {
            merchantDTO = null;
        } else {
            BeanUtils.copyProperties(merchant, merchantDTO);
        }
        log.info("MerchantServiceImpl.getMerchantById(), output: merchantDTO={} ", JSON.toJSONString(merchantDTO));
        return merchantDTO;
    }

    @Override
    public ResultVO<List<MerchantLigthResp>> listMerchantForOrder(MerchantLigthReq req){
        return ResultVO.success(merchantManager.listMerchantForOrder(req));
    }

    @Override
    public ResultVO<MerchantLigthResp> getMerchantForOrder(MerchantGetReq req){
        return ResultVO.success(merchantManager.getMerchantForOrder(req));
    }
}