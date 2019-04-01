package com.iwhalecloud.retail.partner.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.dto.req.InvoicePageReq;
import com.iwhalecloud.retail.partner.dto.req.QueryInvoiceByMerchantIdsReq;
import com.iwhalecloud.retail.partner.dto.resp.InvoicePageResp;
import com.iwhalecloud.retail.partner.entity.Invoice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: InvoiceMapper
 * @author autoCreate
 */
@Mapper
public interface InvoiceMapper extends BaseMapper<Invoice>{

    /**
     * 查询商家发票列表
     * @param page
     * @param req
     * @return
     */
    Page<InvoicePageResp> pageInvoiceByMerchantId(Page<InvoicePageResp> page, @Param("req") InvoicePageReq req);

    /**
     * 批量查询发票信息列表
     * @param req
     * @return
     */
    List<InvoicePageResp> queryInvoiceByMerchantIds(@Param("req") QueryInvoiceByMerchantIdsReq req);


}