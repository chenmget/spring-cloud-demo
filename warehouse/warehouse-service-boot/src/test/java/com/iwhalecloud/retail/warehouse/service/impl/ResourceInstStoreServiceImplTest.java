package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.dto.ResourceInstStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.request.GetProductQuantityByMerchantReq;
import com.iwhalecloud.retail.warehouse.dto.request.InventoryWaringReq;
import com.iwhalecloud.retail.warehouse.dto.request.ProductQuantityItem;
import com.iwhalecloud.retail.warehouse.dto.request.UpdateStockReq;
import com.iwhalecloud.retail.warehouse.dto.response.InventoryWarningResp;
import com.iwhalecloud.retail.warehouse.service.ResourceInstStoreService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseServiceApplication.class)
@Slf4j
public class ResourceInstStoreServiceImplTest {

    @Reference
    private ResourceInstStoreService resourceInstStoreService;

    @Test
    public void getProductQuantityByMerchant() {
        GetProductQuantityByMerchantReq req = new GetProductQuantityByMerchantReq();
        req.setMerchantId("1");
        List<ProductQuantityItem> itemList = new ArrayList<>();
        ProductQuantityItem item = new ProductQuantityItem();
        item.setNum(100L);
        item.setProductId("1");
        itemList.add(item);
        req.setItemList(itemList);
        String json = "{\"itemList\":[{\"num\":2,\"productId\":\"1085133312128376834\"}],\"merchantId\":\"4301811025392\"}";
        Gson gson = new Gson();
        req  = gson.fromJson(json, new TypeToken<GetProductQuantityByMerchantReq>(){}.getType());
        resourceInstStoreService.getProductQuantityByMerchant(req);
    }

    @Test
    public void updateStock() {
        UpdateStockReq req = new UpdateStockReq();
        List<ProductQuantityItem> list = new ArrayList<>();
        ProductQuantityItem item = new ProductQuantityItem();
        item.setNum(10L);
        item.setProductId("1");
        list.add(item);
        req.setItemList(list);
        req.setMerchantId("1");
        resourceInstStoreService.updateStock(req);
    }

    @Test
    public void getProductQuantityByMerchant1() {
    }

    @Test
    public void updateResourceInstStore(){
        ResourceInstStoreDTO req = new ResourceInstStoreDTO();
        req.setMerchantId("4301811025392");
        req.setMktResId("10412537");
        req.setMktResStoreId("11");
        req.setQuantity(1L);
        req.setQuantityAddFlag(false);
        req.setStatusCd("1302");
        resourceInstStoreService.updateResourceInstStore(req);
    }

    @Test
    public void queryInventoryWarningTest(){
        List<InventoryWaringReq> req = Lists.newArrayList();
        InventoryWaringReq req1 = new InventoryWaringReq();
        req1.setProductId("100000114");
        req1.setMerchantId("4301811025392");
        req.add(req1);
        InventoryWaringReq req2 = new InventoryWaringReq();
        req2.setProductId("100000861");
        req2.setMerchantId("4301811025392");
        req.add(req2);
        InventoryWaringReq req3 = new InventoryWaringReq();
        req3.setProductId("10000586");
        req3.setMerchantId("4301811025392");
        req.add(req3);
        ResultVO<List<InventoryWarningResp>> resultVO = resourceInstStoreService.queryInventoryWarning(req);
        log.info("ResourceInstStoreServiceImplTest.queryInventoryWarningTest resp={}", JSON.toJSON(resultVO.getResultData()));
    }

    @Test
    public void getQuantityByMerchantId(){
        ResultVO<Integer> amount = resourceInstStoreService.getQuantityByMerchantId("4300001063072");
        System.out.print("amount==============="+ amount.getResultData());
    }

}