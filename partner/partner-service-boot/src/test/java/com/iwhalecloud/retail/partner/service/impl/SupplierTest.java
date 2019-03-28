package com.iwhalecloud.retail.partner.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.PartnerServiceApplication;
import com.iwhalecloud.retail.partner.dto.SupplierDTO;
import com.iwhalecloud.retail.partner.dto.req.SupplierListReq;
import com.iwhalecloud.retail.partner.dto.req.SupplierQueryReq;
import com.iwhalecloud.retail.partner.service.SupplierService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = PartnerServiceApplication.class)
@RunWith(SpringRunner.class)
public class SupplierTest {
    @Autowired
    private SupplierService supplierService;

    @Test
    public void getSupplierAccountPage(){
        SupplierQueryReq req = new SupplierQueryReq();
        req.setPageNo(1);
        req.setPageSize(10);
//        req.setSupplierId("2");
        Page<SupplierDTO> list = supplierService.pageSupplier(req);
        System.out.print("结果：" + list.toString());
    }

    @Test
    public void listSupplier(){
        SupplierListReq req = new SupplierListReq();
        req.setSupplierType("1");
        ResultVO resultVO = supplierService.listSupplier(req);
        System.out.print("结果：" + resultVO.toString());
    }

    @Test
    public void getSupplierByIds(){
        List<String> idlist = new ArrayList<>();
        idlist.add("1");
        List<SupplierDTO> list = supplierService.getSupplierListByIds(idlist);
        System.out.print("结果：" + list.toString());
    }
}
