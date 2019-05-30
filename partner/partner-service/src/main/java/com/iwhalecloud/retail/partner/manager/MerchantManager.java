package com.iwhalecloud.retail.partner.manager;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.goods2b.dto.MerchantTagRelDTO;
import com.iwhalecloud.retail.goods2b.dto.req.MerchantTagRelListReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.MerchantTagRelService;
import com.iwhalecloud.retail.partner.common.ParInvoiceConst;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.MerchantDetailDTO;
import com.iwhalecloud.retail.partner.dto.req.*;
import com.iwhalecloud.retail.partner.dto.resp.MerchantLigthResp;
import com.iwhalecloud.retail.partner.dto.resp.MerchantPageResp;
import com.iwhalecloud.retail.partner.entity.Invoice;
import com.iwhalecloud.retail.partner.entity.Merchant;
import com.iwhalecloud.retail.partner.entity.MerchantAccount;
import com.iwhalecloud.retail.partner.mapper.MerchantMapper;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.request.UserListReq;
import com.iwhalecloud.retail.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MerchantManager {
    @Resource
    private MerchantMapper merchantMapper;

    @Reference
    private MerchantTagRelService merchantTagRelService;

    @Reference
    private UserService userService;

    @Autowired
    private InvoiceManager invoiceManager;

    @Autowired
    private MerchantAccountManager merchantAccountManager;


    /**
     * 根据条件 查询商家条数
     * @param req
     * @return
     */
    public Integer countMerchant(MerchantCountReq req) {
        QueryWrapper<Merchant> queryWrapper = new QueryWrapper<Merchant>();
        Boolean hasParam = false;
        if (!StringUtils.isEmpty(req.getStatus())) {
            hasParam = true;
            queryWrapper.eq(Merchant.FieldNames.status.getTableFieldName(), req.getStatus());
        }
        if (!CollectionUtils.isEmpty(req.getMerchantTypeList())) {
            hasParam = true;
            queryWrapper.in(Merchant.FieldNames.merchantType.getTableFieldName(), req.getMerchantTypeList());
        }
        return merchantMapper.selectCount(queryWrapper);
    }

    /**
     * 根据条件（精确）查找单个 商家
     * @param req
     * @return
     */
    @Cacheable(value = PartnerConst.CACHE_NAME_PAR_MERCHANT, key = "#req.merchantId", condition = "#req.merchantId != null")
    public Merchant getMerchant(MerchantGetReq req) {
        QueryWrapper<Merchant> queryWrapper = new QueryWrapper<Merchant>();
        Boolean hasParam = false;
        if (!StringUtils.isEmpty(req.getMerchantId())) {
            hasParam = true;
            queryWrapper.eq(Merchant.FieldNames.merchantId.getTableFieldName(), req.getMerchantId());
        }
        if (!StringUtils.isEmpty(req.getMerchantCode())) {
            hasParam = true;
            queryWrapper.eq(Merchant.FieldNames.merchantCode.getTableFieldName(), req.getMerchantCode());
        }
//        if (!StringUtils.isEmpty(req.getUserId())) {
//            hasParam = true;
//            queryWrapper.eq(Merchant.FieldNames.userId.getTableFieldName(), req.getUserId());
//        }
        if (!hasParam) {
            return null;
        }
        queryWrapper.last(" limit 1 "); // 限定查询条数(避免没参数的查出整表）
        List<Merchant> merchantList = merchantMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(merchantList)) {
            return null;
        }
        return merchantList.get(0);
    }

    /**
     *  更新商家信息
     *  直接清楚掉缓存中对应的数据
     * @param merchant
     * @return
     */
    @CacheEvict(value = PartnerConst.CACHE_NAME_PAR_MERCHANT, key = "#merchant.merchantId")
    public int updateMerchant(Merchant merchant){
        return merchantMapper.updateById(merchant);
    }

    /**
     * 商家信息列表
     * @param req
     * @return
     */
    public List<MerchantDTO> listMerchant(MerchantListReq req){
        QueryWrapper<Merchant> queryWrapper = new QueryWrapper<Merchant>();
        Boolean hasParam = false;
        if(!StringUtils.isEmpty(req.getAssignedFlg())){
            hasParam = true;
            queryWrapper.eq(Merchant.FieldNames.assignedFlg.getTableFieldName(), req.getAssignedFlg());
        }
        if(!StringUtils.isEmpty(req.getMerchantCode())){
            hasParam = true;
            queryWrapper.like(Merchant.FieldNames.merchantCode.getTableFieldName(), req.getMerchantCode());
        }
        if(!StringUtils.isEmpty(req.getMerchantName())){
            hasParam = true;
            queryWrapper.like(Merchant.FieldNames.merchantName.getTableFieldName(), req.getMerchantName());
        }
        if(!StringUtils.isEmpty(req.getMerchantType())){
            hasParam = true;
            queryWrapper.eq(Merchant.FieldNames.merchantType.getTableFieldName(), req.getMerchantType());
        }
        if(!StringUtils.isEmpty(req.getLanId())){
            hasParam = true;
            queryWrapper.eq(Merchant.FieldNames.lanId.getTableFieldName(), req.getLanId());
        }
        if(!StringUtils.isEmpty(req.getCity())){
            hasParam = true;
            queryWrapper.eq(Merchant.FieldNames.city.getTableFieldName(), req.getCity());
        }
        if(!CollectionUtils.isEmpty(req.getMerchantIdList())){
            hasParam = true;
            queryWrapper.in(Merchant.FieldNames.merchantId.getTableFieldName(), req.getMerchantIdList());
        }
        if(!CollectionUtils.isEmpty(req.getMerchantCodeList())){
            hasParam = true;
            queryWrapper.in(Merchant.FieldNames.merchantCode.getTableFieldName(), req.getMerchantCodeList());
        }
        if(!CollectionUtils.isEmpty(req.getBusinessEntityCodeList())){
            hasParam = true;
            queryWrapper.in(Merchant.FieldNames.businessEntityCode.getTableFieldName(), req.getBusinessEntityCodeList());
        }
        if(!CollectionUtils.isEmpty(req.getCityList())){
            hasParam = true;
            queryWrapper.in(Merchant.FieldNames.city.getTableFieldName(), req.getCityList());
        }
        if(!CollectionUtils.isEmpty(req.getParCrmOrgIdList())){
            hasParam = true;
            queryWrapper.in(Merchant.FieldNames.parCrmOrgId.getTableFieldName(), req.getParCrmOrgIdList());
        }
        queryWrapper.last(" limit 40000 "); // 限定查询条数(避免没参数的查出整表）

        // 设置查询字段(优化 不查全表字段  避免耗时超长）
        queryWrapper.select(
                Merchant.FieldNames.merchantId.getTableFieldName(),
                Merchant.FieldNames.merchantCode.getTableFieldName(),
                Merchant.FieldNames.merchantName.getTableFieldName(),
                Merchant.FieldNames.merchantType.getTableFieldName(),
                Merchant.FieldNames.status.getTableFieldName(),
                Merchant.FieldNames.userId.getTableFieldName(),
                Merchant.FieldNames.lanId.getTableFieldName(),
                Merchant.FieldNames.city.getTableFieldName(),
                Merchant.FieldNames.shopCode.getTableFieldName(),
                Merchant.FieldNames.shopName.getTableFieldName(),
                Merchant.FieldNames.businessEntityCode.getTableFieldName(),
                Merchant.FieldNames.businessEntityName.getTableFieldName(),
                Merchant.FieldNames.customerCode.getTableFieldName(),
                Merchant.FieldNames.parCrmOrgId.getTableFieldName(),
                Merchant.FieldNames.parCrmOrgCode.getTableFieldName(),
                Merchant.FieldNames.linkman.getTableFieldName(),
                Merchant.FieldNames.phoneNo.getTableFieldName(),
                Merchant.FieldNames.assignedFlg.getFieldName()
        );

        List<MerchantDTO> merchantDTOList = Lists.newArrayList();
        if (!hasParam) {
            return merchantDTOList;
        }
        List<Merchant> merchantList = merchantMapper.selectList(queryWrapper);
        if(!CollectionUtils.isEmpty(merchantList)) {
            for (Merchant merchant : merchantList) {
                MerchantDTO merchantDTO = new MerchantDTO();
                BeanUtils.copyProperties(merchant, merchantDTO);
                merchantDTOList.add(merchantDTO);
            }
        }

        // tagId不空的情况  过滤
        if (!StringUtils.isEmpty(req.getTagId())) {
            // 有同一个tagId的 商家ID集合
            List<String> merchantIdList = getMerchantIdListByTag(req.getTagId());
//            return merchantDTOList.stream().filter(item -> merchantIdList.contains(item.getMerchantId())).collect(Collectors.toList());
            merchantDTOList = merchantDTOList.stream().filter(item -> merchantIdList.contains(item.getMerchantId())).collect(Collectors.toList());
        }

        // loginName不空的情况  过滤
        if (!StringUtils.isEmpty(req.getLoginName())) {
            // 有同一个tagId的 商家ID集合
            UserListReq userListReq = new UserListReq();
            userListReq.setLoginName(req.getLoginName());
            List<String> merchantIdList = getMerchantIdListByLoginName(userListReq);
//            return merchantDTOList.stream().filter(item -> merchantIdList.contains(item.getMerchantId())).collect(Collectors.toList());
            merchantDTOList = merchantDTOList.stream().filter(item -> merchantIdList.contains(item.getMerchantId())).collect(Collectors.toList());
        }

        return merchantDTOList;
    }

    /**
     * 商家信息列表分页
     * @param req
     * @return
     */
    public Page<MerchantPageResp> pageMerchant(MerchantPageReq req){


        // 是否需要先获取userId集合（后面判空用） 查询条件有涉及到通过user_id字段关联的其他表字段且有值时 为true
        Boolean isNeedGetUserIdList = false;
        List<String> userIdList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(req.getUserIdList())) {
            isNeedGetUserIdList = true;
            userIdList = req.getUserIdList();
        }

        // 是否需要先获取merchantId集合（后面判空用） 查询条件有涉及到通过merchant_id字段关联的其他表字段且有值时 为true
        Boolean isNeedGetMerchantIdList = false;
        List<String> merchantIdList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(req.getMerchantIdList())) {
            isNeedGetMerchantIdList = true;
            merchantIdList = req.getMerchantIdList();
        }

        // 非商家表字段  基本上是  先转换成  merchant_id  集合 再作为条件  进行查询

        // 系统账号和系统状态 转换成 merchant_id集合
        if (!StringUtils.isEmpty(req.getLoginName())
                || Objects.nonNull(req.getUserStatus())) {
            UserListReq userListReq = new UserListReq();
            userListReq.setLoginName(req.getLoginName());
            userListReq.setStatusCd(req.getUserStatus());
            List<String> resultList= getMerchantIdListByLoginName(userListReq);
            if (isNeedGetMerchantIdList) {
                // 取交集
                merchantIdList.retainAll(resultList);
            } else {
                merchantIdList = resultList;
            }
            isNeedGetMerchantIdList = true; // 这个赋值要放到最后面
        }

        // 营业执照失效期开始时间、营业执照失效期 结束时间 转换成 merchant_id
        if (Objects.nonNull(req.getEndExpireDate())
                || Objects.nonNull(req.getStartExpireDate())) {
            InvoiceListReq invoiceListReq = new InvoiceListReq();
            BeanUtils.copyProperties(req, invoiceListReq);
            invoiceListReq.setInvoiceType(ParInvoiceConst.InvoiceType.SPECIAL_VAT_INVOICE.getCode());

            List<String> resultList= getMerchantIdListByInvoice(invoiceListReq);
            if (isNeedGetMerchantIdList) {
                // 取交集
                merchantIdList.retainAll(resultList);
            } else {
                merchantIdList = resultList;
            }

            isNeedGetMerchantIdList = true; // 这个赋值要放到最后面

        }

        // 分组标签 转换成 merchant_id
        if (!StringUtils.isEmpty(req.getTagId())) {
            List<String> resultList= getMerchantIdListByTag(req.getTagId());
            if (isNeedGetMerchantIdList) {
                // 取交集
                merchantIdList.retainAll(resultList);
            } else {
                merchantIdList = resultList;
            }
            isNeedGetMerchantIdList = true; // 这个赋值要放到最后面
        }

        // 专票状态
        String vatInvoiceStatus = req.getVatInvoiceStatus();
        // 营业执照到期日
        Date busiLicenceExpDate = req.getBusiLicenceExpDate();
        if (!StringUtils.isEmpty(vatInvoiceStatus) || !StringUtils.isEmpty(busiLicenceExpDate)) {
            InvoiceListReq invoiceListReq = new InvoiceListReq();
            invoiceListReq.setBusiLicenceExpDate(busiLicenceExpDate);
            invoiceListReq.setInvoiceType(ParInvoiceConst.InvoiceType.SPECIAL_VAT_INVOICE.getCode());
            invoiceListReq.setVatInvoiceStatus(vatInvoiceStatus);
            List<Invoice> invoiceList = invoiceManager.listInvoice(invoiceListReq);
            for (Invoice invoice: invoiceList) {
                merchantIdList.add(invoice.getMerchantId());
            }
            // 这个赋值要放到最后面
            isNeedGetMerchantIdList = true;
        }

        if (CollectionUtils.isEmpty(userIdList)
                && isNeedGetUserIdList) {
            // 筛选后 集合为空
            // 添加一个空值 ID （使查询结果为空的）
            userIdList.add("");
        }

        if (CollectionUtils.isEmpty(merchantIdList)
                && isNeedGetMerchantIdList) {
            // 筛选后 集合为空
            // 添加一个空值 ID （使查询结果为空的）
            merchantIdList.add("");
        }

        // 回塞值
        if (!CollectionUtils.isEmpty(userIdList)) {
            req.setUserIdList(userIdList);
        }
        if (!CollectionUtils.isEmpty(merchantIdList)) {
            req.setMerchantIdList(merchantIdList);
        }

        Page<MerchantPageResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        Page<MerchantPageResp> merchantPageRespPage = merchantMapper.pageMerchant(page, req);

        return merchantPageRespPage;
    }

    /**
     * 供应商类型的商家（详细）信息列表分页
     * @param req
     * @return
     */
    public Page<Merchant> pageRetailMerchant(RetailMerchantPageReq req){

        // 条件过滤
        QueryWrapper<Merchant> queryWrapper = new QueryWrapper<>();

        // 商家类型 写死
        queryWrapper.eq(Merchant.FieldNames.merchantType.getTableFieldName(), PartnerConst.MerchantTypeEnum.PARTNER.getType());

        // 条件是：eq(等于的） 的字段
        if (!StringUtils.isEmpty(req.getStatus())) { // 商家状态
            queryWrapper.eq(Merchant.FieldNames.status.getTableFieldName(), req.getStatus());
        }
        if (!StringUtils.isEmpty(req.getLanId())) { // 地市
            queryWrapper.eq(Merchant.FieldNames.lanId.getTableFieldName(), req.getLanId());
        }
        if (!StringUtils.isEmpty(req.getCity())) { // 市县
            queryWrapper.eq(Merchant.FieldNames.city.getTableFieldName(), req.getCity());
        }
        if (!StringUtils.isEmpty(req.getSubBureau())) { // 分局/县部门
            queryWrapper.eq(Merchant.FieldNames.subBureau.getTableFieldName(), req.getSubBureau());
        }
        if (!StringUtils.isEmpty(req.getMarketCenter())) { // 营销中心/支局
            queryWrapper.eq(Merchant.FieldNames.marketCenter.getTableFieldName(), req.getMarketCenter());
        }
        if (!StringUtils.isEmpty(req.getChannelType())) { // 渠道大类
            queryWrapper.eq(Merchant.FieldNames.channelType.getTableFieldName(), req.getChannelType());
        }
        if (!StringUtils.isEmpty(req.getChannelMediType())) { // 渠道小类
            queryWrapper.eq(Merchant.FieldNames.channelMediType.getTableFieldName(), req.getChannelMediType());
        }
        if (!StringUtils.isEmpty(req.getChannelSubType())) { // 渠道子类
            queryWrapper.eq(Merchant.FieldNames.channelSubType.getTableFieldName(), req.getChannelSubType());
        }

        // 条件是in
        if (!CollectionUtils.isEmpty(req.getLanIdList())) {
            queryWrapper.in(Merchant.FieldNames.lanId.getTableFieldName(), req.getLanIdList());
        }
        if (!CollectionUtils.isEmpty(req.getCityList())) {
            queryWrapper.in(Merchant.FieldNames.city.getTableFieldName(), req.getCityList());
        }

        // 条件是：like(模糊查询的）  的字段
        if (!StringUtils.isEmpty(req.getMerchantCode())) { // 商家编码
            queryWrapper.like(Merchant.FieldNames.merchantCode.getTableFieldName(), req.getMerchantCode());
        }
        if (!StringUtils.isEmpty(req.getMerchantName())) { // 商家名称
            queryWrapper.like(Merchant.FieldNames.merchantName.getTableFieldName(), req.getMerchantName());
        }
        if (!StringUtils.isEmpty(req.getBusinessEntityName())) { // 商家所属经营主体
            queryWrapper.like(Merchant.FieldNames.businessEntityName.getTableFieldName(), req.getBusinessEntityName());
        }
        if (!StringUtils.isEmpty(req.getShopCode())) { // 销售点编码
            queryWrapper.like(Merchant.FieldNames.shopCode.getTableFieldName(), req.getShopCode());
        }
        if (!StringUtils.isEmpty(req.getShopName())) { // 销售点名称
            queryWrapper.like(Merchant.FieldNames.shopName.getTableFieldName(), req.getShopName());
        }

        // 是否需要先获取userId集合（后面判空用） 查询条件有涉及到通过user_id字段关联的其他表字段且有值时 为true
        Boolean isNeedGetUserIdList = false;
        List<String> userIdList = Lists.newArrayList();

        // 是否需要先获取merchantId集合（后面判空用） 查询条件有涉及到通过merchant_id字段关联的其他表字段且有值时 为true
        Boolean isNeedGetMerchantIdList = false;
        List<String> merchantIdList = Lists.newArrayList();

        // 非商家表字段  基本上是  先转换成   merchant_id  集合 再作为条件  进行查询

        // 系统账号和系统状态 转换成 merchant_id集合
        if (!StringUtils.isEmpty(req.getLoginName())
                || Objects.nonNull(req.getUserStatus())) {
            UserListReq userListReq = new UserListReq();
            userListReq.setLoginName(req.getLoginName());
            userListReq.setStatusCd(req.getUserStatus());
            List<String> resultList= getMerchantIdListByLoginName(userListReq);
            if (isNeedGetMerchantIdList) {
                // 取交集
                merchantIdList.retainAll(resultList);
            } else {
                merchantIdList = resultList;
            }
            isNeedGetMerchantIdList = true; // 这个赋值要放到最后面
        }

        // 营业执照失效期 区间 转换成 merchant_id
        // 营业执照失效期、营业执照失效期开始时间、营业执照失效期 结束时间 转换成 merchant_id
        if (Objects.nonNull(req.getEndExpireDate())
                || Objects.nonNull(req.getStartExpireDate())) {
            InvoiceListReq invoiceListReq = new InvoiceListReq();
            BeanUtils.copyProperties(req, invoiceListReq);
            invoiceListReq.setInvoiceType(ParInvoiceConst.InvoiceType.SPECIAL_VAT_INVOICE.getCode());

            List<String> resultList= getMerchantIdListByInvoice(invoiceListReq);
            if (isNeedGetMerchantIdList) {
                // 取交集
                merchantIdList.retainAll(resultList);
            } else {
                merchantIdList = resultList;
            }

            isNeedGetMerchantIdList = true; // 这个赋值要放到最后面

        }

        // 分组标签 转换成 merchant_id
        if (!StringUtils.isEmpty(req.getTagId())) {
            List<String> resultList= getMerchantIdListByTag(req.getTagId());
            if (isNeedGetMerchantIdList) {
                // 取交集
                merchantIdList.retainAll(resultList);
            } else {
                merchantIdList = resultList;
            }

            isNeedGetMerchantIdList = true; // 这个赋值要放到最后面
        }


        if (CollectionUtils.isEmpty(userIdList)
                && isNeedGetUserIdList) {
            // 筛选后 集合为空
            // 添加一个空值 ID （使查询结果为空的）
            userIdList.add("");
        }

        if (CollectionUtils.isEmpty(merchantIdList)
                && isNeedGetMerchantIdList) {
            // 筛选后 集合为空
            // 添加一个空值 ID （使查询结果为空的）
            merchantIdList.add("");
        }

        // 条件是：in(包含的）  的字段
        if (!CollectionUtils.isEmpty(userIdList)) {
            // user_id
            queryWrapper.in(Merchant.FieldNames.userId.getTableFieldName(), userIdList);
        }
        if (!CollectionUtils.isEmpty(merchantIdList)) {
            // merchant_id
            queryWrapper.in(Merchant.FieldNames.merchantId.getTableFieldName(), merchantIdList);
        }


        Page<Merchant> resultPage =  new Page<Merchant>(req.getPageNo(), req.getPageSize());
        resultPage =  (Page)merchantMapper.selectPage(resultPage, queryWrapper);
        return resultPage;
    }


    /**
     * 供应商类型的商家（详细）信息列表分页
     * @param req
     * @return
     */
    public Page<Merchant> pageSupplierMerchant(SupplyMerchantPageReq req){

        // 条件过滤
        QueryWrapper<Merchant> queryWrapper = new QueryWrapper<>();

        // 商家类型 写死 地包和省包
        queryWrapper.in(Merchant.FieldNames.merchantType.getTableFieldName(), PartnerConst.MerchantTypeEnum.SUPPLIER_GROUND.getType(), PartnerConst.MerchantTypeEnum.SUPPLIER_PROVINCE.getType());

        // 条件是：eq(等于的） 的字段
        if (!StringUtils.isEmpty(req.getStatus())) { // 商家状态
            queryWrapper.eq(Merchant.FieldNames.status.getTableFieldName(), req.getStatus());
        }
        if (!StringUtils.isEmpty(req.getMerchantType())) { // 商家类型
            queryWrapper.eq(Merchant.FieldNames.merchantType.getTableFieldName(), req.getMerchantType());
        }

        // 条件是：like(模糊查询的）  的字段
        if (!StringUtils.isEmpty(req.getMerchantCode())) { // 商家编码
            queryWrapper.like(Merchant.FieldNames.merchantCode.getTableFieldName(), req.getMerchantCode());
        }
        if (!StringUtils.isEmpty(req.getMerchantName())) { // 商家名称
            queryWrapper.like(Merchant.FieldNames.merchantName.getTableFieldName(), req.getMerchantName());
        }
        if (!StringUtils.isEmpty(req.getBusinessEntityName())) { // 商家所属经营主体
            queryWrapper.like(Merchant.FieldNames.businessEntityName.getTableFieldName(), req.getBusinessEntityName());
        }

        // 是否需要先获取userId集合（后面判空用） 查询条件有涉及到通过user_id字段关联的其他表字段且有值时 为true
        Boolean isNeedGetUserIdList = false;
        List<String> userIdList = Lists.newArrayList();

        // 是否需要先获取merchantId集合（后面判空用） 查询条件有涉及到通过merchant_id字段关联的其他表字段且有值时 为true
        Boolean isNeedGetMerchantIdList = false;
        List<String> merchantIdList = Lists.newArrayList();

        // 非商家表字段  基本上是  先转换成  user_id 或  merchant_id  集合 再作为条件  进行查询

        // 系统账号和系统状态 转换成 merchant_id集合
        if (!StringUtils.isEmpty(req.getLoginName())
                || Objects.nonNull(req.getUserStatus())) {

            UserListReq userListReq = new UserListReq();
            userListReq.setLoginName(req.getLoginName());
            userListReq.setStatusCd(req.getUserStatus());
            List<String> resultList= getMerchantIdListByLoginName(userListReq);
            if (isNeedGetMerchantIdList) {
                // 取交集
                merchantIdList.retainAll(resultList);
            } else {
                merchantIdList = resultList;
            }
            isNeedGetMerchantIdList = true; // 这个赋值要放到最后面
        }

        // 营业执照号、税号、公司账号、营业执照失效期、营业执照失效期开始时间、营业执照失效期 结束时间 转换成 merchant_id
        if (!StringUtils.isEmpty(req.getBusiLicenceCode()) || !StringUtils.isEmpty(req.getTaxCode())
                || !StringUtils.isEmpty(req.getRegisterBankAcct())
                || Objects.nonNull(req.getEndExpireDate()) || Objects.nonNull(req.getStartExpireDate())) {

            InvoiceListReq invoiceListReq = new InvoiceListReq();
            BeanUtils.copyProperties(req, invoiceListReq);
            invoiceListReq.setInvoiceType(ParInvoiceConst.InvoiceType.SPECIAL_VAT_INVOICE.getCode());

            List<String> resultList= getMerchantIdListByInvoice(invoiceListReq);
            if (isNeedGetMerchantIdList) {
                // 取交集
                merchantIdList.retainAll(resultList);
            } else {
                merchantIdList = resultList;
            }

            isNeedGetMerchantIdList = true; // 这个赋值要放到最后面

        }

        // 收款账号 转换成 merchant_id
        if (!StringUtils.isEmpty(req.getAccount())) {
            MerchantAccountListReq merchantAccountListReq = new MerchantAccountListReq();
            merchantAccountListReq.setAccount(req.getAccount());
            List<String> resultList= getMerchantIdListByAccount(merchantAccountListReq);
            if (isNeedGetMerchantIdList) {
                // 取交集
                merchantIdList.retainAll(resultList);
            } else {
                merchantIdList = resultList;
            }

            isNeedGetMerchantIdList = true; // 这个赋值要放到最后面
        }


        if (CollectionUtils.isEmpty(userIdList)
                && isNeedGetUserIdList) {
            // 筛选后 集合为空
            // 添加一个空值 ID （使查询结果为空的）
            userIdList.add("");
        }

        if (CollectionUtils.isEmpty(merchantIdList)
                && isNeedGetMerchantIdList) {
            // 筛选后 集合为空
            // 添加一个空值 ID （使查询结果为空的）
            merchantIdList.add("");
        }

        // 条件是：in(包含的）  的字段
        if (!CollectionUtils.isEmpty(userIdList)) {
            // user_id
            queryWrapper.in(Merchant.FieldNames.userId.getTableFieldName(), userIdList);
        }
        if (!CollectionUtils.isEmpty(merchantIdList)) {
            // merchant_id
            queryWrapper.in(Merchant.FieldNames.merchantId.getTableFieldName(), merchantIdList);
        }


        Page<Merchant> resultPage =  new Page<Merchant>(req.getPageNo(), req.getPageSize());
        resultPage =  (Page)merchantMapper.selectPage(resultPage, queryWrapper);
        return resultPage;
    }

    /**
     * 厂家类型的商家（详细）信息列表分页
     * @param req
     * @return
     */
    public Page<Merchant> pageFactoryMerchant(FactoryMerchantPageReq req){

        // 条件过滤
        QueryWrapper<Merchant> queryWrapper = new QueryWrapper<>();

        // 条件是：eq(等于的） 的字段

        // 商家类型 写死
        queryWrapper.eq(Merchant.FieldNames.merchantType.getTableFieldName(), PartnerConst.MerchantTypeEnum.MANUFACTURER.getType());

        // 条件是：like(模糊查询的）  的字段
        if(!StringUtils.isEmpty(req.getMerchantCode())){ // 商家编码
            queryWrapper.like(Merchant.FieldNames.merchantCode.getTableFieldName(), req.getMerchantCode());
        }
        if(!StringUtils.isEmpty(req.getMerchantName())){ // 商家名称
            queryWrapper.like(Merchant.FieldNames.merchantName.getTableFieldName(), req.getMerchantName());
        }

        // 是否需要先获取userId集合（后面判空用） 查询条件有非par_merchant表字段且有值时 为true
        Boolean isNeedGetMerchantIdList = false;
        List<String> merchantIdList = Lists.newArrayList();

        // 非商家表字段  基本上是  先转换成  user_id 或  merchant_id  集合 再作为条件  进行查询

        // 系统账号和系统状态 转换成 merchant_id集合
        if (!StringUtils.isEmpty(req.getLoginName())) {
            UserListReq userListReq = new UserListReq();
            userListReq.setLoginName(req.getLoginName());
            List<String> resultList= getMerchantIdListByLoginName(userListReq);
            if (isNeedGetMerchantIdList) {
                // 取交集
                merchantIdList.retainAll(resultList);
            } else {
                merchantIdList = resultList;
            }
            isNeedGetMerchantIdList = true; // 这个赋值要放到最后面
        }

        // 营业执照失效期、营业执照失效期开始时间、营业执照失效期 结束时间 转换成 merchant_id
        if (Objects.nonNull(req.getEndExpireDate())
                || Objects.nonNull(req.getStartExpireDate())) {
            InvoiceListReq invoiceListReq = new InvoiceListReq();
            BeanUtils.copyProperties(req, invoiceListReq);
            invoiceListReq.setInvoiceType(ParInvoiceConst.InvoiceType.SPECIAL_VAT_INVOICE.getCode());

            List<String> resultList= getMerchantIdListByInvoice(invoiceListReq);
            if (isNeedGetMerchantIdList) {
                // 取交集
                merchantIdList.retainAll(resultList);
            } else {
                merchantIdList = resultList;
            }

            isNeedGetMerchantIdList = true; // 这个赋值要放到最后面

        }

        if (CollectionUtils.isEmpty(merchantIdList)
                && isNeedGetMerchantIdList) {
            // 筛选后 集合为空
            // 添加一个空值 ID （使查询结果为空的, 因为下面的校验是 空集合 跳过作为查询条件）
            merchantIdList.add("");
        }

        // 条件是：in(包含的）  的字段
        if(!CollectionUtils.isEmpty(merchantIdList)){
            // user_id
            queryWrapper.in(Merchant.FieldNames.merchantId.getTableFieldName(), merchantIdList);
        }

        Page<Merchant> resultPage =  new Page<Merchant>(req.getPageNo(), req.getPageSize());
        resultPage =  (Page)merchantMapper.selectPage(resultPage, queryWrapper);
        return resultPage;
    }

    /**
     * 根据 loginName 获取 模糊查询对应的 userId 集合
     * @param req
     * @return
     */
    private List<String> getUserIdListByLoginName(UserListReq req) {
        List<String> userIdList = Lists.newArrayList();
        List<UserDTO> userDTOList = userService.getUserList(req);
        if (!CollectionUtils.isEmpty(userDTOList)) {
            userDTOList.forEach(userDTO -> {
                userIdList.add(userDTO.getUserId());
            });
        }
        return userIdList;
    }

    /**
     * 根据 loginName 获取 模糊查询对应的 merchant_id 集合
     * @param req
     * @return
     */
    private List<String> getMerchantIdListByLoginName(UserListReq req) {
        List<String> merchantIdList = Lists.newArrayList();
        List<UserDTO> userDTOList = userService.getUserList(req);
        if (!CollectionUtils.isEmpty(userDTOList)) {
            userDTOList.forEach(userDTO -> {
                merchantIdList.add(userDTO.getRelCode());
            });
        }
        return merchantIdList;
    }

    /**
     * 根据 par_invoice表字段 模糊查询对应的 merchantId 集合
     * @param req
     * @return
     */
    private List<String> getMerchantIdListByInvoice(InvoiceListReq req) {
        List<String> merchantIdList = Lists.newArrayList();
        List<Invoice> invoiceList = invoiceManager.listInvoice(req);
        if (!CollectionUtils.isEmpty(invoiceList)) {
            invoiceList.forEach(invoice -> {
                merchantIdList.add(invoice.getMerchantId());
            });
        }
        return merchantIdList;
    }

    /**
     * 根据 par_merchant_account表字段 模糊查询对应的 merchantId 集合
     * @param req
     * @return
     */
    private List<String> getMerchantIdListByAccount(MerchantAccountListReq req) {
        List<String> merchantIdList = Lists.newArrayList();
        List<MerchantAccount> merchantAccountList = merchantAccountManager.listMerchantAccount(req);
        if (!CollectionUtils.isEmpty(merchantAccountList)) {
            merchantAccountList.forEach(merchantAccount -> {
                merchantIdList.add(merchantAccount.getMerchantId());
            });
        }
        return merchantIdList;
    }

    /**
     * 根据 prod_merchant_tag_rel表字段 模糊查询对应的 merchantId 集合
     * @param tagId
     * @return
     */
//    private List<String> getMerchantIdListByTag(MerchantTagRelListReq req) {
    public List<String> getMerchantIdListByTag(String tagId) {
        MerchantTagRelListReq merchantTagRelListReq = new MerchantTagRelListReq();
        merchantTagRelListReq.setTagId(tagId);
        List<String> merchantIdList = Lists.newArrayList();
        List<MerchantTagRelDTO> merchantTagRelDTOList = merchantTagRelService.listMerchantTagRel(merchantTagRelListReq).getResultData();
        if (!CollectionUtils.isEmpty(merchantTagRelDTOList)) {
            merchantTagRelDTOList.forEach(merchantTagRelDTO -> {
                merchantIdList.add(merchantTagRelDTO.getMerchantId());
            });
        }
        return merchantIdList;
    }


    /**
     * 添加商家
     * @param merchant 商家主体
     * @return
     */
    public int insertMerchant(Merchant merchant){
        return merchantMapper.insert(merchant);
    }

    /**
     * 根据商家编码更新商家信息
     * 调用这个更新要清除全部的缓存，因为没有根据code 做缓存
     * @param merchantDetailDTO
     * @return
     */
    @CacheEvict(value = PartnerConst.CACHE_NAME_PAR_MERCHANT, allEntries = true)
    public int updateMerchantByCode(MerchantDetailDTO merchantDetailDTO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(Merchant.FieldNames.merchantCode.getTableFieldName(), merchantDetailDTO.getMerchantCode());
        Merchant merchant = new Merchant();
        BeanUtils.copyProperties(merchantDetailDTO, merchant);
        merchant.setLastUpdateDate(new Date());
        return merchantMapper.update(merchant,queryWrapper);
    }

    /**
     * 根据地区集合查询商家
     * @param merchantListReq
     * @return
     */
    public List<Merchant> listMerchantByLanCity(MerchantListLanCityReq merchantListReq){
        QueryWrapper<Merchant> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(Merchant.FieldNames.city.getTableFieldName(),merchantListReq.getCityList());
        queryWrapper.or();
        queryWrapper.in(Merchant.FieldNames.lanId.getTableFieldName(),merchantListReq.getLanList());
        //只查询出商家的id字段
        queryWrapper.select(Merchant.FieldNames.merchantId.getTableFieldName());
        return merchantMapper.selectList(queryWrapper);
    }

    /**
     * 商家信息列表（只取部分必要字段）
     * @param req
     * @return
     */
    public List<MerchantLigthResp> listMerchantForOrder(MerchantLigthReq req){
        return merchantMapper.listMerchantForOrder(req);
    }

    /**
     * 商家信息列表（只取部分必要字段）
     * @param req
     * @return
     */
    public MerchantLigthResp getMerchantForOrder(MerchantGetReq req){
        return merchantMapper.getMerchantForOrder(req);
    }
}
