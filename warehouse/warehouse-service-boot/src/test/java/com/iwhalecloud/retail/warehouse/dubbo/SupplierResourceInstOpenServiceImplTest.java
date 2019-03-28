package com.iwhalecloud.retail.warehouse.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.service.SupplierResourceInstService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseServiceApplication.class)
@Slf4j
public class SupplierResourceInstOpenServiceImplTest {

    @Reference
    private SupplierResourceInstService supplierResourceInstService;

    @Test
    public void addResourceInst() {
        String json = "{" +
                "\"createStaff\": \"1077840960118870018\"," +
                "\"ctCode\": {" +
                "\"20190314180401\": \"1\"" +
                "}," +
                "\"eventType\": \"1001\"," +
                "\"merchantId\": \"4301811022885\"," +
                "\"mktResId\": \"1085066587772973058\"," +
                "\"mktResInstNbrs\": [\"20190314180401\"]," +
                "\"mktResStoreId\": \"31\"," +
                "\"regionId\": \"0731\"," +
                "\"sourceType\": \"1\"," +
                "\"statusCd\": \"1302\"" +
                "}";
        Gson gson = new Gson();
        ResourceInstAddReq req  = gson.fromJson(json, new TypeToken<ResourceInstAddReq>(){}.getType());
        supplierResourceInstService.addResourceInst(req);
    }

    @Test
    public void delResourceInst() {
        ResourceInstUpdateReq req = new ResourceInstUpdateReq();
        req.setMktResInstNbrs(Lists.newArrayList("20190314180401","20190314180401"));
        req.setCheckStatusCd(Lists.newArrayList(ResourceConst.STATUSCD.DELETED.getCode(),
                ResourceConst.STATUSCD.AUDITING.getCode(),
                ResourceConst.STATUSCD.ALLOCATIONED.getCode(),
                ResourceConst.STATUSCD.ALLOCATIONING.getCode(),
                ResourceConst.STATUSCD.RESTORAGEING.getCode(),
                ResourceConst.STATUSCD.RESTORAGED.getCode(),
                ResourceConst.STATUSCD.SALED.getCode()));
        req.setMerchantId("4301811022885");
        req.setStatusCd(ResourceConst.STATUSCD.DELETED.getCode());
        supplierResourceInstService.delResourceInst(req);
    }

    @Test
    public void allocateResourceInst() {
        String json = "{\"createStaff\":\"1077840960118870018\",\"mktResInstIds\":[\"10000008\"],\"destStoreId\":\"31\",\"mktResStoreId\":\"41\"}";
        Gson gson = new Gson();
        SupplierResourceInstAllocateReq req  = gson.fromJson(json, new TypeToken<SupplierResourceInstAllocateReq>(){}.getType());
        supplierResourceInstService.allocateResourceInst(req);
    }

    @Test
    public void deliveryOutResourceInst() {
        DeliveryResourceInstReq req = new DeliveryResourceInstReq();
        String json = "{\"deliveryResourceInstItemList\":[{\"mktResInstNbrs\":[\"20190314180401\"],\"orderItemId\":\"20190129103506630775894\",\"productId\":\"1089765013613895682\"}],\"sellerMerchantId\":\"4301811025392\"}";
        Gson gson = new Gson();
        req  = gson.fromJson(json, new TypeToken<DeliveryResourceInstReq>(){}.getType());
        supplierResourceInstService.deliveryOutResourceInst(req);
    }

    @Test
    public void deliveryInResourceInst() {
        String json = "{\"buyerMerchantId\":\"4300001063072\",\"deliveryResourceInstItemList\":[{\"mktResInstNbrs\":[\"20190314180401\"],\"orderItemId\":\"20190222162311506741831\",\"productId\":\"1098427109759229953\"}]}";
        Gson gson = new Gson();
        DeliveryResourceInstReq req = gson.fromJson(json, new TypeToken<DeliveryResourceInstReq>(){}.getType());
        supplierResourceInstService.deliveryInResourceInst(req);
    }

    @Test
    public void backDeliveryOutResourceInst() {
        String json = "{\"buyerMerchantId\":\"4300002062771\"," +
                "\"deliveryResourceInstItemList\"" +
                ":[{\"mktResInstNbrs\":[\"20190314180401\"]," +
                "\"orderItemId\":\"20190225090207222641306\"," +
                "\"productId\":\"1098427109759229953\"}]}";
        Gson gson = new Gson();
        DeliveryResourceInstReq req = gson.fromJson(json, new TypeToken<DeliveryResourceInstReq>(){}.getType());
        supplierResourceInstService.backDeliveryOutResourceInst(req);
    }

    @Test
    public void backDeliveryInResourceInst() {
        String json = "{\"deliveryResourceInstItemList\":" +
                "[{\"mktResInstNbrs\":[\"20190314180401\"]," +
                "\"orderItemId\":\"20190225090207222641306\"," +
                "\"productId\":\"1098427109759229953\"}],\"sellerMerchantId\":\"4301811022885\"}";
        Gson gson = new Gson();
        DeliveryResourceInstReq req = gson.fromJson(json, new TypeToken<DeliveryResourceInstReq>(){}.getType());
        supplierResourceInstService.backDeliveryInResourceInst(req);
    }

    @Test
    public void updateInstState() {
        ResourceInstUpdateReq req = new ResourceInstUpdateReq();
        req.setMktResInstNbrs(Lists.newArrayList("20190314180401","20190314180401"));
        req.setCheckStatusCd(Lists.newArrayList(ResourceConst.STATUSCD.DELETED.getCode(),
                ResourceConst.STATUSCD.AUDITING.getCode(),
                ResourceConst.STATUSCD.ALLOCATIONED.getCode(),
                ResourceConst.STATUSCD.ALLOCATIONING.getCode(),
                ResourceConst.STATUSCD.RESTORAGEING.getCode(),
                ResourceConst.STATUSCD.RESTORAGED.getCode(),
                ResourceConst.STATUSCD.SALED.getCode()));
        req.setMerchantId("4301811022885");
        req.setStatusCd(ResourceConst.STATUSCD.RESTORAGED.getCode());
        supplierResourceInstService.updateInstState(req);
    }

}