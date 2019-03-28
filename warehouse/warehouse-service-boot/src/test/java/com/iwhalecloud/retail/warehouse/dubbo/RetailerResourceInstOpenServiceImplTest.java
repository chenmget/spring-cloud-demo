package com.iwhalecloud.retail.warehouse.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.service.RetailerResourceInstService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseServiceApplication.class)
@Slf4j
public class RetailerResourceInstOpenServiceImplTest {

    @Reference
    private RetailerResourceInstService retailerResourceInstService;

    @Test
    public void addResourceInstByGreenChannel() {
        String json = "{\"mktResId\":\"1089765444700266498\",\"regionId\":\"0731\",\"merchantId\":\"4300001063072\",\"mktResInstNbrs\":[\"20190314180402\"]}";
        Gson gson = new Gson();
        ResourceInstAddReq req =  gson.fromJson(json, new TypeToken<ResourceInstAddReq>(){}.getType());
        ResultVO resultVO = retailerResourceInstService.addResourceInstByGreenChannel(req);
        log.info("RetailerResourceInstServiceImplTest.addResourceInstByGreenChannel result:{}", JSON.toJSONString(resultVO));
    }

    @Test
    public void delResourceInst() {
        ResourceInstUpdateReq req =  new ResourceInstUpdateReq();

        req.setMktResInstNbrs(Lists.newArrayList("20190314180402","20190314180402"));
        req.setCheckStatusCd(Lists.newArrayList(ResourceConst.STATUSCD.DELETED.getCode(),
                ResourceConst.STATUSCD.AUDITING.getCode(),
                ResourceConst.STATUSCD.ALLOCATIONED.getCode(),
                ResourceConst.STATUSCD.ALLOCATIONING.getCode(),
                ResourceConst.STATUSCD.RESTORAGEING.getCode(),
                ResourceConst.STATUSCD.RESTORAGED.getCode(),
                ResourceConst.STATUSCD.SALED.getCode()));
        req.setMerchantId("4300001063072");
        req.setStatusCd(ResourceConst.STATUSCD.DELETED.getCode());
        ResultVO resultVO = retailerResourceInstService.delResourceInst(req);
        log.info("RetailerResourceInstServiceImplTest.delResourceInst result:{}", JSON.toJSONString(resultVO));
    }

    @Test
    public void listResourceInst() {
    }

    @Test
    public void pickResourceInst() {
        ResourceInstPickupReq req = new ResourceInstPickupReq();
        req.setMktResInstNbrs(Lists.newArrayList("20190314180402","20190314180402"));
        req.setMerchantId("4300001063072");
        req.setMktResInstIds(Lists.newArrayList("100001498"));
        ResultVO resultVO = retailerResourceInstService.pickResourceInst(req);
    }

    @Test
    public void retreatStorageResourceInst() {

    }

    @Test
    public void getBatch() {

    }

    @Test
    public void allocateResourceInst() {
        String json = "{\"createStaff\":\"1077840960118870018\",\"mktResInstIds\":[\"100015021\"],\"destStoreId\":\"31\",\"mktResStoreId\":\"41\"}";
        Gson gson = new Gson();
        RetailerResourceInstAllocateReq req  = gson.fromJson(json, new TypeToken<RetailerResourceInstAllocateReq>(){}.getType());
        ResultVO resultVO = retailerResourceInstService.allocateResourceInst(req);
    }

    @Test
    public void confirmReciveNbr() {

    }

    @Test
    public void confirmRefuseNbr() {

    }
}