package com.iwhalecloud.retail.warehouse.busiservice.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.request.PageProductReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstValidReq;
import com.iwhalecloud.retail.warehouse.runable.SupplierRunableTask;
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

    @Autowired
    private SupplierRunableTask supplierRunableTask;

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
        /**
         * {"mktResId":"100005214","typeId":"10350158","mktResInstNbrs":["20191111019001521456321457891001","20191111019001521456321457891002","20191111019001521456321457891003","20191111019001521456321457891004","20191111019001521456321457891005","20191111019001521456321457891006","20191111019001521456321457891007","20191111019001521456321457891008","20191111019001521456321457891009","20191111019001521456321457891010","20191111019001521456321457891011","20191111019001521456321457891012","20191111019001521456321457891013","20191111019001521456321457891014","20191111019001521456321457891015","20191111019001521456321457891016","20191111019001521456321457891017","20191111019001521456321457891018","20191111019001521456321457891019","20191111019001521456321457891020"],"snCodeMap":{"20191111019001521456321457891001":"123","20191111019001521456321457891002":"456","20191111019001521456321457891003":"789","20191111019001521456321457891004":"790","20191111019001521456321457891005":"791","20191111019001521456321457891006":"792","20191111019001521456321457891007":"793","20191111019001521456321457891008":"794","20191111019001521456321457891009":"795","20191111019001521456321457891010":"796","20191111019001521456321457891011":"797","20191111019001521456321457891012":"798","20191111019001521456321457891013":"799","20191111019001521456321457891014":"800","20191111019001521456321457891015":"801","20191111019001521456321457891016":"802","20191111019001521456321457891017":"803","20191111019001521456321457891018":"804","20191111019001521456321457891019":"805","20191111019001521456321457891020":"806"},"macCodeMap":{"20191111019001521456321457891001":"321","20191111019001521456321457891002":"654","20191111019001521456321457891003":"987","20191111019001521456321457891004":"988","20191111019001521456321457891005":"989","20191111019001521456321457891006":"990","20191111019001521456321457891007":"991","20191111019001521456321457891008":"992","20191111019001521456321457891009":"993","20191111019001521456321457891010":"994","20191111019001521456321457891011":"995","20191111019001521456321457891012":"996","20191111019001521456321457891013":"997","20191111019001521456321457891014":"998","20191111019001521456321457891015":"999","20191111019001521456321457891016":"1000","20191111019001521456321457891017":"1001","20191111019001521456321457891018":"1002","20191111019001521456321457891019":"1003","20191111019001521456321457891020":"1004"},"snCodeList":["123","456","789","790","791","792","793","794","795","796","797","798","799","800","801","802","803","804","805","806"],"macCodeList":["321","654","987","988","989","990","991","992","993","994","995","996","997","998","999","1000","1001","1002","1003","1004"],"mktResInstType":"1","detailCode":"3"}
         */
        String json = "{\"mktResId\":\"24277339\",\"typeId\":\"201903142030001\",\"mktResInstNbrs\":[\"1230600000106\"],\"mktResInstType\":\"1\",\"detailCode\":\"0\"}";
        Gson gson = new Gson();
        ResourceInstValidReq req  = gson.fromJson(json, new TypeToken<ResourceInstValidReq>(){}.getType());
        ResourceInstValidReq resourceInstValidReq = new ResourceInstValidReq();
        resourceInstValidReq.setCtCodeList(Lists.newArrayList("2"));
        resourceInstValidReq.setDetailCode("2");
        resourceInstValidReq.setMktResId("10656458");
        resourceInstValidReq.setMktResInstNbrs(Lists.newArrayList("12333333"));
        resourceInstValidReq.setMktResInstType("1");
        resourceInstValidReq.setMktResStoreId("10423104");
        resourceInstValidReq.setSnCodeList(Lists.newArrayList("122"));
        resourceInstValidReq.setTypeId("440001");
        validAndAddRunableTask.exceutorValid(req);
    }

    @Test
    public void exceutorValidForSupplier() {
        String json = "{\"typeId\":\"201903142030001\",\"mktResStoreId\":\"10425103\",\"mktResInstNbrs\":[\"1230600000103\"]}";
        Gson gson = new Gson();
        ResourceInstValidReq req  = gson.fromJson(json, new TypeToken<ResourceInstValidReq>(){}.getType());
        supplierRunableTask.exceutorValidForSupplier(req);
    }
}