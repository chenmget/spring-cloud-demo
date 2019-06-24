package com.iwhalecloud.retail.partner.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.PartnerServiceApplication;
import com.iwhalecloud.retail.partner.dto.SupplierAccountDTO;
import com.iwhalecloud.retail.partner.dto.req.SupplierAccountAddReq;
import com.iwhalecloud.retail.partner.dto.req.SupplierAccountQueryReq;
import com.iwhalecloud.retail.partner.dto.req.SupplierAccountUpdateReq;
import com.iwhalecloud.retail.partner.dto.resp.SupplierAccountAddResp;
import com.iwhalecloud.retail.partner.service.SupplierAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = PartnerServiceApplication.class)
@RunWith(SpringRunner.class)
public class SupplierAccountTest {
    @Autowired
    private SupplierAccountService supplierAccountService;

    @Test
    public void getSupplierAccountPage(){
        SupplierAccountQueryReq req = new SupplierAccountQueryReq();
        req.setPageNo(1);
        req.setPageSize(10);
        req.setSupplierId("2");
        Page<SupplierAccountDTO> list = supplierAccountService.querySupplierAccount(req);
        System.out.print("结果：" + list.toString());
    }

    @Test
    public void addSupplierAccount(){
        SupplierAccountAddReq req = new SupplierAccountAddReq();
        req.setAccount("微信-号");
        req.setAccountType("1");
        req.setSupplierId("2");
        SupplierAccountAddResp result = supplierAccountService.createSupplierAccount(req);
        System.out.print("结果：" + result.toString());
    }

    @Test
    public void updateSupplierAccount(){
        SupplierAccountUpdateReq req = new SupplierAccountUpdateReq();
        req.setAccount("微信号2");
        req.setAccountId("1067706324193693698");
        int result = supplierAccountService.editSupplierAccount(req);
        System.out.print("结果：" + result);
    }
}