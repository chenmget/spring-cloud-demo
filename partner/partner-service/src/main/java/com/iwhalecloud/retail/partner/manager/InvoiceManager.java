package com.iwhalecloud.retail.partner.manager;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.partner.common.ParInvoiceConst;
import com.iwhalecloud.retail.partner.dto.req.InvoiceListReq;
import com.iwhalecloud.retail.partner.dto.req.InvoicePageReq;
import com.iwhalecloud.retail.partner.dto.resp.InvoicePageResp;
import com.iwhalecloud.retail.partner.entity.Invoice;
import com.iwhalecloud.retail.partner.mapper.InvoiceMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Component
public class InvoiceManager {
    
    @Resource
    private InvoiceMapper invoiceMapper;

    /**
     * 查询商家发票列表
     * @param req
     * @return
     */
    public Page<InvoicePageResp> pageInvoiceByMerchantId(InvoicePageReq req) {
        Page<InvoicePageResp> page = new Page<>(req.getPageNo(),req.getPageSize());
        Page<InvoicePageResp> parInvoiceListResp = invoiceMapper.pageInvoiceByMerchantId(page, req);
        return parInvoiceListResp;
    }

    /**
     * 新增/修改商家发票
     * @param invoice
     * @return
     */
    public int createParInvoice(Invoice invoice){
        if(StringUtils.isEmpty(invoice.getInvoiceId())) {
            invoice.setVatInvoiceStatus(ParInvoiceConst.VatInvoiceStatus.AUDITING.getCode());
            return invoiceMapper.insert(invoice);
        }else{
            return invoiceMapper.updateById(invoice);
        }
    }

    /**
     * 修改发票状态
     */
    public int updateInvoiceStatus(String invoiceId,String vatInvoiceStatus){
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(invoiceId);
        invoice.setVatInvoiceStatus(vatInvoiceStatus);
        return invoiceMapper.updateById(invoice);
    }

    /**
     * 查询发票详情
     * @param invoiceId
     * @return
     */
    public InvoicePageResp queryParInvoiceInfo(String invoiceId){
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(invoiceId);
        invoice = invoiceMapper.selectById(invoice);
        InvoicePageResp invoicePageResp = new InvoicePageResp();
        BeanUtils.copyProperties(invoice, invoicePageResp);
        return invoicePageResp;
    }

    /**
     * 查询发票列表
     * @param req
     * @return
     */
    public List<Invoice> listInvoice(InvoiceListReq req) {
        QueryWrapper<Invoice> queryWrapper = new QueryWrapper<Invoice>();
        Boolean hasParam = false;
        if(!StringUtils.isEmpty(req.getTaxCode())){
            hasParam = true;
            queryWrapper.like(Invoice.FieldNames.taxCode.getTableFieldName(), req.getTaxCode());
        }
        if(!StringUtils.isEmpty(req.getBusiLicenceCode())){
            hasParam = true;
            queryWrapper.like(Invoice.FieldNames.busiLicenceCode.getTableFieldName(), req.getBusiLicenceCode());
        }
        if(!StringUtils.isEmpty(req.getRegisterBankAcct())){
            hasParam = true;
            queryWrapper.like(Invoice.FieldNames.registerBankAcct.getTableFieldName(), req.getRegisterBankAcct());
        }
        if(Objects.nonNull(req.getBusiLicenceExpDate())){
            hasParam = true;
            queryWrapper.le(Invoice.FieldNames.busiLicenceExpDate.getTableFieldName(), req.getBusiLicenceExpDate());
        }

        if(!StringUtils.isEmpty(req.getMerchantId())){
            hasParam = true;
            queryWrapper.eq(Invoice.FieldNames.merchantId.getTableFieldName(), req.getMerchantId());
        }
        if(!StringUtils.isEmpty(req.getInvoiceType())){
            hasParam = true;
            queryWrapper.eq(Invoice.FieldNames.invoiceType.getTableFieldName(), req.getInvoiceType());
        }
        if(!StringUtils.isEmpty(req.getVatInvoiceStatus())){
            hasParam = true;
            queryWrapper.eq(Invoice.FieldNames.vatInvoiceStatus.getTableFieldName(), req.getVatInvoiceStatus());
        }

        List<Invoice> invoiceList = Lists.newArrayList();
        if (!hasParam) {
            return invoiceList;
        }
        invoiceList = invoiceMapper.selectList(queryWrapper);
        return invoiceList;
    }
    
}
