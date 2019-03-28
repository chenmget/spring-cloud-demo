package com.iwhalecloud.retail.partner.service.impl;

import com.iwhalecloud.retail.partner.PartnerServiceApplication;
import com.iwhalecloud.retail.partner.dto.req.InvoicePageReq;
import com.iwhalecloud.retail.partner.service.InvoiceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = PartnerServiceApplication.class)
@RunWith(SpringRunner.class)
public class PartnerInvoiceTest {
    
    @Autowired
    private InvoiceService invoiceService;
    
    @Test
    public void listPartnerInvoice(){
        InvoicePageReq invoicePageReq = new InvoicePageReq();
        invoicePageReq.setPageNo(1);
        invoicePageReq.setPageSize(10);
        invoicePageReq.setMerchantId("1");
        invoicePageReq.setInvoiceType("10");
        invoiceService.pageInvoiceByMerchantId(invoicePageReq);
    }
    @Test
    public void createParInvoice(){
        
    }
}
