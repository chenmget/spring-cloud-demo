package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.dto.request.DeliveryResourceInstReq;
import com.iwhalecloud.retail.warehouse.service.SupplierResourceInstService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseServiceApplication.class)
@Slf4j
public class MarketingResourceInstServiceImplTest {

    @Autowired
    private SupplierResourceInstService marketingResourceInstService;

    @Test
    public void deliveryInResourceInst() {
        String json = "{\"buyerMerchantId\":\"4300001063072\",\"deliveryResourceInstItemList\":[{\"mktResInstNbrs\":[\"81191111011206\"],\"orderItemId\":\"2019031806272\",\"productId\":\"100000114\"}],\"orderId\":\"2019031805265\"}";
        Gson gson = new Gson();
        DeliveryResourceInstReq req  = gson.fromJson(json, new TypeToken<DeliveryResourceInstReq>(){}.getType());
        marketingResourceInstService.deliveryInResourceInst(req);

    }
}