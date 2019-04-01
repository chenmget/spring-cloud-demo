package com.iwhalecloud.retail.partner.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.req.*;
import com.iwhalecloud.retail.partner.dto.resp.InvoiceAddResp;
import com.iwhalecloud.retail.partner.dto.resp.InvoicePageResp;

import java.util.List;

public interface InvoiceService {

    /**
     * 新增商家发票
     * @param req
     * @return
     */
    ResultVO<InvoiceAddResp> createParInvoice(InvoiceAddReq req);

    /**
     * 查询商家发票列表
     * @param req
     * @return
     */
    ResultVO<Page<InvoicePageResp>> pageInvoiceByMerchantId(InvoicePageReq req);

    /**
     * 修改商家发票
     * @param req
     * @return
     */
//    ResultVO<Integer> updateBusinessEntity(BusinessEntityUpdateReq req);

    /**
     * 查询发票详情
     * @param invoiceId
     * @return
     */
    ResultVO<InvoicePageResp> queryParInvoiceInfo(String invoiceId);

//    int auditingParInvoiceFinish(String invoiceId);

    /**
     * 批量查询发票信息
     * @param req
     * @return
     */
    ResultVO<List<InvoicePageResp>> queryInvoiceByMerchantIds(QueryInvoiceByMerchantIdsReq req);

}