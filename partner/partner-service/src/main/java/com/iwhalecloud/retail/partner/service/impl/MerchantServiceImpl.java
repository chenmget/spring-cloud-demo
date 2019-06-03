package com.iwhalecloud.retail.partner.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.MerchantTagRelDTO;
import com.iwhalecloud.retail.goods2b.dto.req.MerchantTagRelListReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.MerchantTagRelService;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
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
import com.iwhalecloud.retail.system.common.SysUserMessageConst;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.CommonFileDTO;
import com.iwhalecloud.retail.system.dto.CommonRegionDTO;
import com.iwhalecloud.retail.system.dto.OrganizationDTO;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.request.*;
import com.iwhalecloud.retail.system.dto.response.OrganizationListResp;
import com.iwhalecloud.retail.system.service.CommonFileService;
import com.iwhalecloud.retail.system.dto.request.*;
import com.iwhalecloud.retail.system.service.CommonFileService;
import com.iwhalecloud.retail.system.service.CommonRegionService;
import com.iwhalecloud.retail.system.service.OrganizationService;
import com.iwhalecloud.retail.system.service.UserService;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.dto.req.ProcessStartReq;
import com.iwhalecloud.retail.workflow.service.TaskService;
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
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import com.twmacinta.util.MD5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

import static com.iwhalecloud.retail.dto.ResultVO.success;

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
    private OrganizationService organizationService;

    @Reference
    private MerchantTagRelService merchantTagRelService;

    @Reference
    private CommonFileService commonFileService;

    @Autowired
    private InvoiceManager invoiceManager;

    @Autowired
    private MerchantAccountManager merchantAccountManager;

    @Autowired
    private BusinessEntityManager businessEntityManager;

    @Reference
    private CommonFileService commonFileService;

    @Reference
    private TaskService taskService;

    @Reference
    private ProductService productService;

    /**
     * 根据条件 查询商家条数
     *
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
            if (!StringUtils.isEmpty(merchantDetailDTO.getMerchantId())) {
                UserGetReq userGetReq = new UserGetReq();
                userGetReq.setRelCode(merchantDetailDTO.getMerchantId());
                UserDTO userDTO = userService.getUser(userGetReq);
                if (userDTO != null) {
                    merchantDetailDTO.setLoginName(userDTO.getLoginName());
                    merchantDetailDTO.setUserStatus(userDTO.getStatusCd().toString());
                }
            }

            // 取经营单元（三级组织） 、 营销支局（四级组织）
            // 根据parCrmOrgPathCode取3、4级组织ID （如果有）
            String orgIdWithLevel3 = getOrgIdByPathCode(merchantDetailDTO.getParCrmOrgPathCode(), 3);
            String orgIdWithLevel4 = getOrgIdByPathCode(merchantDetailDTO.getParCrmOrgPathCode(), 4);
            merchantDetailDTO.setOrgIdWithLevel3(orgIdWithLevel3);
            merchantDetailDTO.setOrgIdWithLevel4(orgIdWithLevel4);
            if (StringUtils.isNotEmpty(orgIdWithLevel3)) {
                OrganizationDTO dto = organizationService.getOrganization(orgIdWithLevel3).getResultData();
                if (Objects.nonNull(dto)) {
                    merchantDetailDTO.setOrgNameWithLevel3(dto.getOrgName());
                }
            }
            if (StringUtils.isNotEmpty(orgIdWithLevel4)) {
                OrganizationDTO dto = organizationService.getOrganization(orgIdWithLevel4).getResultData();
                if (Objects.nonNull(dto)) {
                    merchantDetailDTO.setOrgNameWithLevel4(dto.getOrgName());
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

        }
        log.info("MerchantServiceImpl.listMerchant(), output: list.size()={} ", list.size());
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

            // 取本地网  市县  ID集合  去重
            HashSet<String> regionIdHashSet = new HashSet<>();
            // 所有商家ID去重
            HashSet<String> merchantIdHashSet = new HashSet<>();

            for (MerchantPageResp merchantDTO : page.getRecords()) {
                // 取本地网  市县  ID集合
                regionIdHashSet.add(merchantDTO.getLanId());
                regionIdHashSet.add(merchantDTO.getCity());

                // 取merchantId集合
                merchantIdHashSet.add(merchantDTO.getMerchantId());
            }

            Map<String, String> regionNamesMap = getRegionNamesMap(regionIdHashSet);
            Map<String, String> loginNamesMap = getLoginNamesMap(merchantIdHashSet);

            // 取本地网名称  市县名称
            for (MerchantPageResp merchantDTO : page.getRecords()) {
                // 取本地网名称  市县名称
                merchantDTO.setLanName(regionNamesMap.get(merchantDTO.getLanId()));
                merchantDTO.setCityName(regionNamesMap.get(merchantDTO.getCity()));

                // 取商家的登录名称
                merchantDTO.setLoginName(loginNamesMap.get(merchantDTO.getMerchantId()));
            }
        }
        log.info("MerchantServiceImpl.pageMerchant() output: list<MerchantPageResp>.siz()={} ", JSON.toJSONString(page.getRecords().size()));
        return ResultVO.success(page);
    }

    /**
     * 商家 绑定 用户
     *
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

        /***** 其他表字段  统一获取  避免循环获取  不然导出调用时可能会出现超时问题 *****/
        if (!CollectionUtils.isEmpty(merchantPage.getRecords())) {

            // 取本地网  市县  ID集合  去重
            HashSet<String> regionIdHashSet = new HashSet<>();
            // 所有商家ID去重
            HashSet<String> merchantIdHashSet = new HashSet<>();
            // 所有组织id去重
            HashSet<String> orgIdHashSet = new HashSet<>();

            for (Merchant entity : merchantPage.getRecords()) {

                RetailMerchantDTO targetDTO = new RetailMerchantDTO();
                BeanUtils.copyProperties(entity, targetDTO);

                // 根据parCrmOrgPathCode取3、4级组织ID （如果有）
                String orgIdWithLevel3 = getOrgIdByPathCode(targetDTO.getParCrmOrgPathCode(), 3);
                String orgIdWithLevel4 = getOrgIdByPathCode(targetDTO.getParCrmOrgPathCode(), 4);
                targetDTO.setOrgIdWithLevel3(orgIdWithLevel3);
                targetDTO.setOrgIdWithLevel4(orgIdWithLevel4);

                targetList.add(targetDTO);

                // 取组织  ID集合
                orgIdHashSet.add(orgIdWithLevel3);
                orgIdHashSet.add(orgIdWithLevel4);

                // 取本地网  市县  ID集合
                regionIdHashSet.add(entity.getLanId());
                regionIdHashSet.add(entity.getCity());

                // 取merchantId集合
                merchantIdHashSet.add(entity.getMerchantId());
            }

            Map<String, String> regionNamesMap = getRegionNamesMap(regionIdHashSet);
            Map<String, Invoice> invoiceMap = getInvoiceMap(merchantIdHashSet);
            Map<String, String> tagNamesMap = getTagNamesMap(merchantIdHashSet);
            Map<String, String> orgNamesMap = getOrgNamesMap(orgIdHashSet);
            Map<String, String> loginNamesMap = getLoginNamesMap(merchantIdHashSet);

            // 设置 对应要翻译的名称
            for (RetailMerchantDTO dto : targetList) {

                // 设置 3\4级组织名称
                dto.setOrgNameWithLevel3(orgNamesMap.get(dto.getOrgIdWithLevel3()));
                dto.setOrgNameWithLevel4(orgNamesMap.get(dto.getOrgIdWithLevel4()));

                // 设置 本地网名称  市县名称
                dto.setLanName(regionNamesMap.get(dto.getLanId()));
                dto.setCityName(regionNamesMap.get(dto.getCity()));

                // 取商家的登录名称
                dto.setLoginName(loginNamesMap.get(dto.getMerchantId()));

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

        log.info("MerchantServiceImpl.pageRetailMerchant() output：list<RetailMerchantDTO>.size()={}", JSON.toJSONString(targetPage.getRecords().size()));
        return ResultVO.success(targetPage);
    }

    /**
     * 根据pathCode 获取对应等级orgId
     *
     * @param pathCode
     * @param level    取第几级 （从0开始)
     */
    private String getOrgIdByPathCode(String pathCode, int level) {
        // pathCode示例：1000000020.843000000000000.843073800000000.843073805020000.843073805021007
        //  比如level= 3  取的值 是 843073805020000
        if (StringUtils.isEmpty(pathCode) || level < 0) {
            return null;
        }
        // 要用 \\.  转义分割符号
        String[] orgIds = pathCode.split("\\.");
        if (orgIds.length < level + 1) {
            // 不够个数
            return null;
        }
//        String orgId = null;
//        orgId = orgIds[level];
//        return orgId;
        return orgIds[level];
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

        /***** 其他表字段  统一获取  避免循环获取  不然导出调用时可能会出现超时问题 *****/
        if (!CollectionUtils.isEmpty(merchantPage.getRecords())) {

            // 取本地网  市县  ID集合  去重
            HashSet<String> regionIdHashSet = new HashSet<>();
            // 所有商家ID去重
            HashSet<String> merchantIdHashSet = new HashSet<>();

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
            Map<String, String> loginNamesMap = getLoginNamesMap(merchantIdHashSet);

            // 取本地网名称  市县名称
            for (SupplyMerchantDTO dto : targetList) {

                // 设置 本地网名称  市县名称
                dto.setLanName(regionNamesMap.get(dto.getLanId()));
                dto.setCityName(regionNamesMap.get(dto.getCity()));

                // 取商家的登录名称
                dto.setLoginName(loginNamesMap.get(dto.getMerchantId()));

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

        /***** 其他表字段  统一获取  避免循环获取  不然导出调用时可能会出现超时问题 *****/
        if (!CollectionUtils.isEmpty(merchantPage.getRecords())) {

            // 取本地网  市县  ID集合  去重
            HashSet<String> regionIdHashSet = new HashSet<>();
            // 所有商家ID去重
            HashSet<String> merchantIdHashSet = new HashSet<>();

            for (Merchant entity : merchantPage.getRecords()) {

                FactoryMerchantDTO targetDTO = new FactoryMerchantDTO();
                BeanUtils.copyProperties(entity, targetDTO);
                targetList.add(targetDTO);

                // 取本地网  市县  ID集合
                regionIdHashSet.add(entity.getLanId());
                regionIdHashSet.add(entity.getCity());

                // 取merchantId集合
                merchantIdHashSet.add(entity.getMerchantId());
            }

            Map<String, String> regionNamesMap = getRegionNamesMap(regionIdHashSet);
            Map<String, String> loginNamesMap = getLoginNamesMap(merchantIdHashSet);

            // 取本地网名称  市县名称
            for (FactoryMerchantDTO dto : targetList) {

                // 设置 本地网名称  市县名称
                dto.setLanName(regionNamesMap.get(dto.getLanId()));
                dto.setCityName(regionNamesMap.get(dto.getCity()));

                // 取商家的登录名称
                dto.setLoginName(loginNamesMap.get(dto.getMerchantId()));

            }
        }

        targetPage.setRecords(targetList);

        log.info("MerchantServiceImpl.pageFactoryMerchant() output：list<FactoryMerchantDTO>={}", JSON.toJSONString(targetPage.getRecords()));
        return ResultVO.success(targetPage);
    }

    /**
     * 根据regionId集合获取所有的  区域名称
     *
     * @param orgIdHashSet
     * @return
     */
    private Map<String, String> getOrgNamesMap(HashSet<String> orgIdHashSet) {
        Map resultMap = new HashMap();
        if (CollectionUtils.isEmpty(orgIdHashSet)) {
            return resultMap;
        }
        OrganizationListReq req = new OrganizationListReq();
        req.setOrgIdList(Lists.newArrayList(orgIdHashSet));
        List<OrganizationListResp> dtoList = organizationService.listOrganization(req).getResultData();
        if (!CollectionUtils.isEmpty(dtoList)) {
            dtoList.forEach(dto -> {
                resultMap.put(dto.getOrgId(), dto.getOrgName());
            });
        }
        return resultMap;
    }

    /**
     * 根据merchantId集合获取所有的  登录名称
     *
     * @param merchantIdHashSet
     * @return
     */
    private Map<String, String> getLoginNamesMap(HashSet<String> merchantIdHashSet) {
        UserListReq req = new UserListReq();
        req.setRelCodeList(Lists.newArrayList(merchantIdHashSet));
        List<UserDTO> dtoList = userService.getUserList(req);
        Map resultMap = new HashMap();
        if (!CollectionUtils.isEmpty(dtoList)) {
            dtoList.forEach(userDTO -> {
                resultMap.put(userDTO.getRelCode(), userDTO.getLoginName());
            });
        }
        return resultMap;
    }

    /**
     * 根据regionId集合获取所有的  区域名称
     *
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
     *
     * @param merchantIdHashSet
     * @return
     */
    private Map<String, Invoice> getInvoiceMap(HashSet<String> merchantIdHashSet) {
        Map<String, Invoice> resultMap = new HashMap();
        if (CollectionUtils.isEmpty(merchantIdHashSet)) {
            return resultMap;
        }
        // 营业执照号、税号、公司账号、营业执照失效期
        InvoiceListReq invoiceListReq = new InvoiceListReq();
        invoiceListReq.setMerchantIdList(Lists.newArrayList(merchantIdHashSet));
        invoiceListReq.setInvoiceType(ParInvoiceConst.InvoiceType.SPECIAL_VAT_INVOICE.getCode());
        List<Invoice> invoiceList = invoiceManager.listInvoice(invoiceListReq);
        if (!CollectionUtils.isEmpty(invoiceList)) {
            invoiceList.forEach(invoice -> {
                resultMap.put(invoice.getMerchantId(), invoice);
            });
        }
        return resultMap;
    }

    /**
     * 根据商家ID集合获取所有的 发票信息
     *
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
     *
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
        log.info("MerchantServiceImpl.listMerchantByLanCity MerchantListReq={}", JSON.toJSON(req));
        List<Merchant> merchants = merchantManager.listMerchantByLanCity(req);
        log.info("MerchantServiceImpl.listMerchantByLanCity merchantManager.listMerchantByLanCity merchants.size()={}", JSON.toJSON(merchants.size()));
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
    public ResultVO<List<MerchantLigthResp>> listMerchantForOrder(MerchantLigthReq req) {
        return ResultVO.success(merchantManager.listMerchantForOrder(req));
    }

    @Override
    public ResultVO<MerchantLigthResp> getMerchantForOrder(MerchantGetReq req) {
        return ResultVO.success(merchantManager.getMerchantForOrder(req));
    }

    @Override
    public List<String> getMerchantIdList(String merchantName) {
        return merchantManager.getMerchantIdList(merchantName);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultVO saveFactoryMerchant(FactoryMerchantSaveReq req) {
        try {
            //新增厂商表记录
            Merchant merchant=this.addMerchant(req);
            String merchantId=merchant.getMerchantId();
            //关联账户信息,生成user信息
            UserDTO user=this.addUser(req,merchantId);
            String userId=user.getUserId();
            String userName=user.getUserName();
            //注册厂商信息并生成审核流程
            UserFactoryMerchantReq userFactoryMerchantReq=new UserFactoryMerchantReq();
            BeanUtils.copyProperties(req, userFactoryMerchantReq);
            userFactoryMerchantReq.setUserId(userId);
            userFactoryMerchantReq.setUserName(userName);
            ResultVO vo=initFactoryMerchant(userFactoryMerchantReq,merchantId);
            if(!vo.isSuccess()){
                //注册厂商信息失败，回滚生成的账户信息
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        } catch (Exception e) {
            return ResultVO.error(e.getMessage());
        }
        return success();
    }

    @Override
    public ResultVO<String> resistManufacturer(ManufacturerResistReq req) {
        return null;
    }

    /**
     *厂商自注册
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO registerFactoryMerchant(UserFactoryMerchantReq req) {
        try {
            Merchant merchant=this.addMerchant(req);
            String merchantId=merchant.getMerchantId();
            initFactoryMerchant(req,merchantId);
            return ResultVO.success();
        } catch (Exception e) {
            return ResultVO.error(e.getMessage());
        }
    }




    /**
     * 注册厂商信息并生成审核流程
     * @param req
     * @param merchantId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<String> initFactoryMerchant(UserFactoryMerchantReq req,String merchantId) {
        try {
            String userId=req.getUserId();
            String userName=req.getUserName();
            //新增厂商附件记录
            this.addEnclosure(req,merchantId);
            //发起审核流程
            String extends1=req.getLanId();
            this.startProcess(PartnerConst.MerchantProcessEnum.PROCESS_3040501.getProcessTitle(), userId, userName,PartnerConst.MerchantProcessEnum.PROCESS_3040501.getProcessId(),
                    merchantId,extends1, WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_3040501.getTaskSubType());
            return ResultVO.success(merchantId);
        } catch (Exception e) {
            return ResultVO.error(e.getMessage());
        }
    }

    /**
     * 新建厂商记录
     * @param req
     * @return
     */
    private  Merchant addMerchant(Object req) {
        Merchant merchant=new Merchant();
        BeanUtils.copyProperties(req, merchant);
        merchant.setStatus(PartnerConst.MerchantStatusEnum.NOT_EFFECT.getType());//默认未生效
        merchant.setMerchantType(PartnerConst.MerchantTypeEnum.MANUFACTURER.getType());//类别为厂商
        merchantManager.insertMerchant(merchant);
        return merchant;
    }


    /**
     * 厂商添加用户信息
     * @param req
     * @return
     * @throws Exception
     */
    private UserDTO addUser(FactoryMerchantSaveReq req,String merchantId) throws RuntimeException {
        UserAddReq userAddReq=new UserAddReq();
        BeanUtils.copyProperties(req, userAddReq);
        userAddReq.setStatusCd(SystemConst.USER_STATUS_INVALID);//默认禁用字段
        userAddReq.setUserFounder(SystemConst.USER_FOUNDER_8);//用户类型为厂商
        userAddReq.setRelCode(merchantId);
        //生成默认密码
        String loginPwd="Ab_123456";
        userAddReq.setLoginPwd(new MD5(loginPwd).asHex());
        ResultVO<UserDTO> vo=userService.addUser(userAddReq);
        if(vo!=null&&vo.getResultData()!=null&&vo.isSuccess()){
            return vo.getResultData();
        }else{
            throw new RuntimeException(vo.getResultMsg());
        }
    }

    /**
     * 新增厂商专票信息
     * @param req
     */
    private  void addParInvoice(UserFactoryMerchantReq req,String merchantId) {
        Invoice invoice=new Invoice();
        BeanUtils.copyProperties(req, invoice);
        invoice.setMerchantId(merchantId);
        invoiceManager.createParInvoice(invoice);
    }

    /**
     * 上传厂商附件信息
     * @param req
     * @return
     */
    private  void addEnclosure(UserFactoryMerchantReq req,String merchantId) {
        //上传营业执照
        addBusinessLicense(req.getBusinessLicense(),merchantId,Boolean.FALSE);
        addBusinessLicense(req.getBusinessLicenseCopy(),merchantId,Boolean.FALSE);
        //上传身份证
        addlegalPersonIdCard(req.getLegalPersonIdCardFont(),merchantId,Boolean.FALSE);
        addlegalPersonIdCard(req.getLegalPersonIdCardBack(),merchantId,Boolean.FALSE);
        //上传授权证明
        addAuthorizationCertificate(req.getAuthorizationCertificate(),merchantId,Boolean.TRUE);
        //上传合同
        addContract(req.getContract(),merchantId,Boolean.FALSE);

    }

    /**
     * 上传合同
     * @param contract 图片地址
     * @param merchantId 关联主键
     * @param rollBack 上传失败是否回滚 true 抛出异常 false处理异常
     */
    private void addContract(String contract, String merchantId,Boolean rollBack) {
        //合同不为空，上传合同
        if(StringUtils.isNotBlank(contract)){
            try {
                CommonFileDTO dto=new CommonFileDTO(SystemConst.FileType.IMG_FILE.getCode(),SystemConst.FileClass.CONTRACT_TEXT.getCode(),merchantId,contract);
                commonFileService.saveCommonFile(dto);
            } catch (Exception e) {
                log.error("MerchantServiceImpl.addlegalPersonIdCard(), input: contract={} ", contract);
                if(rollBack){
                    throw new RuntimeException("上传合同失败");
                }

            }
        }
    }

    /**
     * 上传授权证明
     * @param authorizationCertificate 图片地址
     * @param merchantId 关联主键
     * @param rollBack 上传失败是否回滚 true 抛出异常 false处理异常
     */
    private void addAuthorizationCertificate(String authorizationCertificate, String merchantId,Boolean rollBack) {
        Assert.notNull(authorizationCertificate, "授权证明不允许为空");
        try {
            CommonFileDTO dto=new CommonFileDTO(SystemConst.FileType.IMG_FILE.getCode(),SystemConst.FileClass.AUTHORIZATION_CERTIFICATE.getCode(),merchantId,authorizationCertificate);
            commonFileService.saveCommonFile(dto);
        } catch (Exception e) {
            log.error("MerchantServiceImpl.addAuthorizationCertificate(), input: authorizationCertificate={} ", authorizationCertificate);
            if(rollBack){
                throw new RuntimeException("上传授权失败");
            }
        }
    }

    /**
     * 上传身份证
     * @param legalPersonIdCardFont 图片地址
     * @param merchantId 关联主键
     * @param rollBack 上传失败是否回滚 true 抛出异常 false处理异常
     */
    private void addlegalPersonIdCard(String legalPersonIdCardFont, String merchantId,Boolean rollBack) {
        //身份证不为空，上传身份证
        if(StringUtils.isNotBlank(legalPersonIdCardFont)){
            try {
                CommonFileDTO dto=new CommonFileDTO(SystemConst.FileType.IMG_FILE.getCode(),SystemConst.FileClass.BUSINESS_LICENSE.getCode(),merchantId,legalPersonIdCardFont);
                commonFileService.saveCommonFile(dto);
            } catch (Exception e) {
                log.error("MerchantServiceImpl.addlegalPersonIdCard(), input: legalPersonIdCardFont={} ", legalPersonIdCardFont);
                if(rollBack){
                    throw new RuntimeException("上传身份证失败");
                }
            }
        }
    }

    /**
     * 上传营业执照
     * @param businessLicense 图片地址
     * @param merchantId 关联主键
     * @param rollBack 上传失败是否回滚 true 抛出异常 false处理异常
     */
    private void addBusinessLicense(String businessLicense, String merchantId,Boolean rollBack) {
        //营业执照照片不为空，上传营业执照
        if(StringUtils.isNotBlank(businessLicense)){
            try {
                CommonFileDTO dto=new CommonFileDTO(SystemConst.FileType.IMG_FILE.getCode(),SystemConst.FileClass.BUSINESS_LICENSE.getCode(),merchantId,businessLicense);
                commonFileService.saveCommonFile(dto);
            } catch (Exception e) {
                log.error("MerchantServiceImpl.addBusinessLicense(), input: businessLicense={} ", businessLicense);
                if(rollBack){
                    throw new RuntimeException("上传营业执照失败");
                }
            }
        }
    }

    /**
     * 新增审核流程
     * @param title 流程名称
     * @param userId  流程发起人id
     * @param userName  流程发起人name
     * @param processId 流程processid
     * @param formId
     * @param extends1 如果申请人是商家，该字段信息显示“地市+区县”信息，如果是电信人员则显示“岗位+部门”信息
     * @param taskType 流程类别
     */
    private void startProcess(String title,String userId,String userName,String processId,String formId,String extends1,String taskType){
        ProcessStartReq processStartDTO = new ProcessStartReq();
        processStartDTO.setTitle(title);
        //创建流程者， 参数需要提供
        processStartDTO.setApplyUserId(userId);
        processStartDTO.setApplyUserName(userName);
        processStartDTO.setProcessId(processId);
        processStartDTO.setFormId(formId);
        processStartDTO.setExtends1(extends1);
        processStartDTO.setTaskSubType(taskType);
        try{
            taskService.startProcess(processStartDTO);
        }catch (Exception ex){
            throw new RuntimeException("生成审核流程失败");
        }
    }

    /**
     * 插入附件信息
     * @param req
     * @param merchanId
     */
    private String insertAttachment(SupplierResistReq req,String merchanId) {
        CommonFileDTO commonFileDTO =null;

        //营业执照正本
        String businessLicenseUrl = req.getBusinessLicense();
        if(StringUtils.isNotBlank(businessLicenseUrl)){
            String fileClass = SystemConst.FileClass.BUSINESS_LICENSE.getCode();
            String fileType = SystemConst.FileType.IMG_FILE.getCode();
            commonFileDTO = new CommonFileDTO(fileType,fileClass,merchanId,businessLicenseUrl);
            if(commonFileService.saveCommonFile(commonFileDTO).isSuccess()) ResultVO.error("");

        }
        //营业执照副本
        String businessLicenseCopyUrl = req.getBusinessLicenseCopy();
        if(StringUtils.isNotBlank(businessLicenseCopyUrl)){
            String fileClass = SystemConst.FileClass.BUSINESS_LICENSE.getCode();
            String fileType = SystemConst.FileType.IMG_FILE.getCode();
            commonFileDTO = new CommonFileDTO(fileType,fileClass,merchanId,businessLicenseCopyUrl);
            commonFileService.saveCommonFile(commonFileDTO);
        }
        //法人身份证正面
        String personUrl = req.getLegalPersonIdCardFont();
        if(StringUtils.isNotBlank(personUrl)){
            String fileClass = SystemConst.FileClass.IDENTITY_CARD_PHOTOS.getCode();
            String fileType = SystemConst.FileType.IMG_FILE.getCode();
            commonFileDTO = new CommonFileDTO(fileType,fileClass,merchanId,businessLicenseCopyUrl);
            commonFileService.saveCommonFile(commonFileDTO);
        }
        //法人身份证反面
        String personBackUrl = req.getLegalPersonIdCardBack();
        if(StringUtils.isNotBlank(personBackUrl)){
            String fileClass = SystemConst.FileClass.IDENTITY_CARD_PHOTOS.getCode();
            String fileType = SystemConst.FileType.IMG_FILE.getCode();
            commonFileDTO = new CommonFileDTO(fileType,fileClass,merchanId,personBackUrl);
            commonFileService.saveCommonFile(commonFileDTO);
        }
        //授权证明
        String authorizationUrl = req.getAuthorizationCertificate();
        if(StringUtils.isNotBlank(personBackUrl)){
            String fileClass = SystemConst.FileClass.AUTHORIZATION_CERTIFICATE.getCode();
            String fileType = SystemConst.FileType.IMG_FILE.getCode();
            commonFileDTO = new CommonFileDTO(fileType,fileClass,merchanId,authorizationUrl);
            commonFileService.saveCommonFile(commonFileDTO);
        }

        //合同文件
        String contractUrl = req.getContract();
        commonFileDTO.setFileUrl(contractUrl);
        if(StringUtils.isNotBlank(contractUrl)){
            String fileClass = SystemConst.FileClass.CONTRACT_TEXT.getCode();
            String fileType = SystemConst.FileType.TXT_FILE.getCode();
            commonFileDTO = new CommonFileDTO(fileType,fileClass,merchanId,contractUrl);
            commonFileService.saveCommonFile(commonFileDTO);
        }
        return null;
    }





/*
    private MerchantAccount fillWindPayAccount(SupplierResistReq resistReq, MerchantAccount account) {
        account.setAccount(resistReq.getAccount());
        account.setBankAccount(resistReq.getWindPayCount());
        //支付类型为---翼支付
        account.setAccountType(PartnerConst.MerchantAccountTypeEnum.BEST_PAY.getType());
        return account;
    }*/

/*    private MerchantAccount fillBankAccount(SupplierResistReq resistReq,MerchantAccount account) {
        account.setAccount(resistReq.getAccount());
        account.setBank(resistReq.getBank());
        account.setBankAccount(resistReq.getBankAccount());
        //支付类型为---银行
        account.setAccountType(PartnerConst.MerchantAccountTypeEnum.BANK_ACCOUNT.getType());
        return account;
    }*/

    /**
     * 修改商户信息
     * @param req
     * @return
     */
    @Override
    @Transactional
    public ResultVO editMerchant (MerchantEditReq req) {
        Merchant merchant=new Merchant();
        BeanUtils.copyProperties(req, merchant);
        this.merchantManager.updateMerchant(merchant);
        return ResultVO.success();
    }
    private SupplierResistResp fillSupplierRegistResp(MerchantDetailDTO merchant, UserDTO user, List<MerchantAccount> merchantAccountList, List<CommonFileDTO> commonFileList) {
        SupplierResistResp resistResp = new SupplierResistResp();
        BeanUtils.copyProperties(merchant,resistResp);
        BeanUtils.copyProperties(user,resistResp);
        for(CommonFileDTO commonFileDTO : commonFileList) {
            //身份证
            if (commonFileDTO.getFileClass().equals(SystemConst.FileClass.IDENTITY_CARD_PHOTOS.getCode())) {
                if (resistResp.getLegalPersonIdCardFont() == null) {
                    resistResp.setLegalPersonIdCardFont(commonFileDTO.getFileUrl());
                } else resistResp.setLegalPersonIdCardBack(commonFileDTO.getFileUrl());
            }
            //营业执照
            if (commonFileDTO.getFileClass().equals(SystemConst.FileClass.BUSINESS_LICENSE.getCode())) {
                resistResp.setBusinessLicense(commonFileDTO.getFileUrl());
                if (resistResp.getBusinessLicense() == null) {
                    resistResp.setBusinessLicense(commonFileDTO.getFileUrl());
                } else {
                    resistResp.setBusinessLicenseCopy(commonFileDTO.getFileUrl());
                }
            }
            if (commonFileDTO.getFileClass().equals(SystemConst.FileClass.CONTRACT_TEXT.getCode())) {
                resistResp.setContract(commonFileDTO.getFileUrl());
            }
            if (commonFileDTO.getFileClass().equals(SystemConst.FileClass.AUTHORIZATION_CERTIFICATE.getCode())) {
                resistResp.setAuthorizationCertificate(commonFileDTO.getFileUrl());
            }
        }
        //accout
        for(MerchantAccount account : merchantAccountList){
            resistResp.setAccount(account.getAccount());
            if(account.getAccountType().equals(PartnerConst.MerchantAccountTypeEnum.BEST_PAY.getType())){
                resistResp.setWindPayCount(account.getAccountName());
            }
            if(account.getAccountType().equals(PartnerConst.MerchantAccountTypeEnum.BANK_ACCOUNT.getType())){
                resistResp.setBank(account.getBank());
                resistResp.setBankAccount(account.getBankAccount());
            }
        }
        return resistResp;
    }


    private MerchantAccount fillWindPayAccount(SupplierResistReq resistReq, MerchantAccount account) {
        account.setAccount(resistReq.getAccount());
        account.setBankAccount(resistReq.getWindPayCount());
        //支付类型为---翼支付
        account.setAccountType(PartnerConst.MerchantAccountTypeEnum.BEST_PAY.getType());
        return account;
    }

    private MerchantAccount fillBankAccount(SupplierResistReq resistReq,MerchantAccount account) {
        account.setAccount(resistReq.getAccount());
        account.setBank(resistReq.getBank());
        account.setBankAccount(resistReq.getBankAccount());
        //支付类型为---银行
        account.setAccountType(PartnerConst.MerchantAccountTypeEnum.BANK_ACCOUNT.getType());
        return account;
    }

    /**
     * 填充merchant
     * @param req
     * @return
     */
    private Merchant fillMerchant(SupplierResistReq req) {
        //商户编码，id，名称，地市lanid，联系电话,状态,商户类型
        //code要自动生成兼容手工维护的code，city可以地市+01,状态为待审核
        Merchant merchant = new Merchant();
        BeanUtils.copyProperties(req,merchant);
        merchant.setStatus(PartnerConst.MerchantStatusEnum.NOT_EFFECT.getType());
        if(merchant.getLanId()!=null)
            merchant.setCity(merchant.getLanId() + "01");
        return merchant;
    }


    /**
     * remote add sys_user
     * @param req
     * @param userFounder
     * @param merchantId
     * @return
     */
    public ResultVO<UserDTO> addUser(SupplierResistReq req,int userFounder,String merchantId){
        UserAddReq userAddReq = new UserAddReq();
        BeanUtils.copyProperties(req,userAddReq);
        userAddReq.setLoginPwd(new MD5(SystemConst.DFPASSWD).asHex());
        userAddReq.setUserFounder(userFounder);
        //关联商户信息和系统用户
        userAddReq.setRelCode(merchantId); //关联商户信息和系统用户
        //刚注册用户处于禁用状态
        userAddReq.setStatusCd(SystemConst.USER_STATUS_INVALID);
        return userService.addUser(userAddReq);
    }
    public ResultVO<Merchant> addMerchantInfo(SupplierResistReq req) {
        MerchantAccount account = new MerchantAccount();
        BeanUtils.copyProperties(req,account);
        Merchant merchant = fillMerchant(req);
        String merchantId = merchantManager.addMerchan(merchant);
        account.setMerchantId(merchantId);
        //插入银行收款信息
        account = fillBankAccount(req,account);
        merchantAccountManager.insert(account);
        //插入翼支付收款信息
        account = fillWindPayAccount(req,account);
        merchantAccountManager.insert(account);
        return ResultVO.success(merchant);
    }

    public ResultVO checkBank(SupplierResistReq req) {
        //银行卡号是否正确
        JSONObject obj = JSONObject.parseObject(getCardDetail(req.getBankAccount()));
        String stat = (String) obj.get("stat");
        Boolean flag = (Boolean) obj.get("validated");
        if(!stat.equals("ok") || !flag)
            return ResultVO.error("银行卡无效");
        return ResultVO.success();
    }

    private String getCardDetail(String cardNo) {
        String url = "https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardNo=";
        url+=cardNo;
        url+="&cardBinCheck=true";
        StringBuilder sb = new StringBuilder();
        try {
            URL urlObject = new URL(url);
            URLConnection uc = urlObject.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


}