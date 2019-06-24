package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstValidReq;
import com.iwhalecloud.retail.warehouse.service.MerchantResourceInstService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseServiceApplication.class)
@Slf4j
public class MerchantResourceInstServiceImplTest {

    @Reference
    private MerchantResourceInstService merchantResourceInstService;



    //场景一：省包(卖) 地保(买)  省包发货
    @Test
    public void deliveryOutResourceInstS2D() {
        ResourceInstValidReq req = new ResourceInstValidReq();
        req.setDetailCode("4");
        req.setMktResInstNbrs(Lists.newArrayList("1234", "1235", "1236", "1237"));
        Map<String, String> nbrAndSn = new HashMap();
        nbrAndSn.put("1234", "4321");
        nbrAndSn.put("1235", "5321");
        nbrAndSn.put("1236", "6321");
        nbrAndSn.put("1237", "7321");
        req.setSnCodeMap(nbrAndSn);
        req.setSnCodeList(Lists.newArrayList("4321", "5321", "6321", "7321"));
        req.setTypeId("123456");
        req.setMktResInstType("5");
        merchantResourceInstService.validNbr(req);
    }

}