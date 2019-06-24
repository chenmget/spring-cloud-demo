package com.iwhalecloud.retail.warehouse.service.impl;

import com.google.common.collect.Lists;
import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceReqDetailUpdateReq;
import com.iwhalecloud.retail.warehouse.manager.ResourceReqDetailManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseServiceApplication.class)
@Slf4j
public class ResourceInstReqServiceImplTest {

    @Autowired
    private ResourceReqDetailManager resourceReqDetailManager;


    @Test
    public void tradeInResourceInst(){
        ResourceReqDetailUpdateReq req = new ResourceReqDetailUpdateReq();
        req.setStatusCd("1002");
        req.setMktResReqItemIdList(Lists.newArrayList("12334431"));
        resourceReqDetailManager.updateResourceReqDetailStatusCd(req);
    }
}