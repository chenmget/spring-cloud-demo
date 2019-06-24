package com.iwhalecloud.retail.warehouse.service.impl;

import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstCheckService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseServiceApplication.class)
@Slf4j
public class ResourceInstCheckServiceImplTest {

    @Autowired
    private ResourceInstCheckService tradeResourceInstService;


    @Test
    public void tradeInResourceInst(){
        tradeResourceInstService.greenChannelValid("10519293", "10171065");
    }
}