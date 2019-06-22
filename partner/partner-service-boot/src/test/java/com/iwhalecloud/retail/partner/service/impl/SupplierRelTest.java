package com.iwhalecloud.retail.partner.service.impl;


import com.iwhalecloud.retail.partner.PartnerServiceApplication;
import com.iwhalecloud.retail.partner.dto.SupplyRelDTO;
import com.iwhalecloud.retail.partner.dto.req.SupplyRelAddReq;
import com.iwhalecloud.retail.partner.dto.req.SupplyRelDeleteReq;
import com.iwhalecloud.retail.partner.service.SupplyRelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@SpringBootTest(classes = PartnerServiceApplication.class)
@RunWith(SpringRunner.class)
public class SupplierRelTest {

    @Autowired
    private SupplyRelService supplyRelService;

    @Test
    public void addSupplyRel(){
        SupplyRelAddReq req = new SupplyRelAddReq();
        req.setPartnerId("1");
        req.setSupplierId("2");
        SupplyRelDTO result = supplyRelService.addSupplyRel(req);
        System.out.print("结果：" + result.toString());
    }

    @Test
    public void delSupplyRel(){
        SupplyRelDeleteReq req = new SupplyRelDeleteReq();
        req.setRelIds(Arrays.asList("1067752910940618754","1067753196836954113","1067753465578586114"));

        int result = supplyRelService.deleteSupplyRel(req);
        System.out.print("结果：" + result);
    }

}
