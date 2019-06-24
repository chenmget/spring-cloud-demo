package com.iwhalecloud.retail.warehouse.busiservice.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.request.PageProductReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstValidReq;
import com.iwhalecloud.retail.warehouse.runable.ValidAndAddRunableTask;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseServiceApplication.class)
@Slf4j
public class ResourceInstServiceImplTest {

    @Autowired
    private ResourceInstService resourceInstService;

    @Autowired
    private ValidAndAddRunableTask validAndAddRunableTask;

    /**
     * 间接验证constant常量值
     */
    @Test
    public void selectProduct() {
        PageProductReq req = new PageProductReq();
        req.setSourceType(ResourceConst.SOURCE_TYPE.RETAILER.getCode());
        ResultVO resp = resourceInstService.selectProduct(req);
        log.info("ResourceInstServiceImplTest.selectProduct req={} resp={}", JSON.toJSONString(req), JSON.toJSONString(resp));
    }

    @Test
    public void exceutorValid() {
        ResourceInstValidReq resourceInstValidReq = new ResourceInstValidReq();
        resourceInstValidReq.setCtCodeList(Lists.newArrayList("2"));
        resourceInstValidReq.setDetailCode("2");
        resourceInstValidReq.setMktResId("10656458");
        resourceInstValidReq.setMktResInstNbrs(Lists.newArrayList("12333333"));
        resourceInstValidReq.setMktResInstType("1");
        resourceInstValidReq.setMktResStoreId("10423104");
        resourceInstValidReq.setSnCodeList(Lists.newArrayList("122"));
        resourceInstValidReq.setTypeId("440001");
        validAndAddRunableTask.exceutorValid(resourceInstValidReq);
    }
}