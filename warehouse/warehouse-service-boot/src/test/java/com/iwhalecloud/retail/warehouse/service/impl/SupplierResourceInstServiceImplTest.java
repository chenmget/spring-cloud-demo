package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.service.SupplierResourceInstService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseServiceApplication.class)
@Slf4j
public class SupplierResourceInstServiceImplTest {

    @Reference
    private SupplierResourceInstService supplierResourceInstService;

    @Autowired
    private ResourceInstService resourceInstService;

    //场景一：省包(卖) 地保(买)  省包发货
    @Test
    public void deliveryOutResourceInstS2D() {
        DeliveryResourceInstReq req = new DeliveryResourceInstReq();
        req.setBuyerMerchantId("123");
        List<DeliveryResourceInstItem> items = new ArrayList<>();
        DeliveryResourceInstItem item1 = new DeliveryResourceInstItem();
        List<String> mktResInstNbrs = new ArrayList<>();
        mktResInstNbrs.add("123412341234");
        mktResInstNbrs.add("123123123");
        item1.setMktResInstNbrs(mktResInstNbrs);
        item1.setOrderItemId("20190111095459782645628");
        item1.setProductId("1077895352720969729");
        items.add(item1);
        req.setDeliveryResourceInstItemList(items);
        req.setSellerMerchantId("4301811025392");
        supplierResourceInstService.deliveryOutResourceInst(req);
    }

    //场景一：省包(卖) 地保(买)  省包发货
    @Test
    public void deliveryInResourceInstS2D() {
        DeliveryResourceInstReq req = new DeliveryResourceInstReq();
        List<DeliveryResourceInstItem> list = new ArrayList<>();
        DeliveryResourceInstItem item1 = new DeliveryResourceInstItem();
        List<String> mktResInstNbrs = new ArrayList<>();
        mktResInstNbrs.add("20190116001");
        item1.setMktResInstNbrs(mktResInstNbrs);
        item1.setProductId("1085133312128376834");
        item1.setOrderItemId("20190116144832318146299");
        list.add(item1);
        req.setDeliveryResourceInstItemList(list);
        req.setBuyerMerchantId("4301811022885");
        supplierResourceInstService.deliveryInResourceInst(req);
    }

    //场景二：地保(卖) 零售商(买)  省包发货
    @Test
    public void deliveryOutResourceInstS2L() {
        DeliveryResourceInstReq req = new DeliveryResourceInstReq();
        req.setBuyerMerchantId("123");
        List<DeliveryResourceInstItem> items = new ArrayList<>();
        DeliveryResourceInstItem item1 = new DeliveryResourceInstItem();
        List<String> mktResInstNbrs = new ArrayList<>();
        mktResInstNbrs.add("123412341234");
        mktResInstNbrs.add("123123123");
        item1.setMktResInstNbrs(mktResInstNbrs);
        item1.setOrderItemId("20190111095459782645628");
        item1.setProductId("1077895352720969729");
        items.add(item1);
        req.setDeliveryResourceInstItemList(items);
        req.setSellerMerchantId("4301811025392");

        String json = "{\"deliveryResourceInstItemList\":[{\"mktResInstNbrs\":[\"013001\"],\"orderItemId\":\"20190129103506630775894\",\"productId\":\"1089765013613895682\"}],\"sellerMerchantId\":\"4301811025392\"}";
        Gson gson = new Gson();
        req  = gson.fromJson(json, new TypeToken<DeliveryResourceInstReq>(){}.getType());
        supplierResourceInstService.deliveryOutResourceInst(req);
    }

    //场景二：地保(卖) 零售商(买)   省包发货
    @Test
    public void deliveryInResourceInstS2L() {
        DeliveryResourceInstReq req = new DeliveryResourceInstReq();
        List<DeliveryResourceInstItem> list = new ArrayList<>();
        DeliveryResourceInstItem item1 = new DeliveryResourceInstItem();
        List<String> mktResInstNbrs = new ArrayList<>();
        mktResInstNbrs.add("20190116001");
        item1.setMktResInstNbrs(mktResInstNbrs);
        item1.setProductId("1085133312128376834");
        item1.setOrderItemId("20190116144832318146299");
        list.add(item1);
        req.setDeliveryResourceInstItemList(list);
        req.setBuyerMerchantId("4300001063072");
        supplierResourceInstService.deliveryInResourceInst(req);
    }

    @Test
    public void deliveryInResourceInst(){
        String json = "{\"buyerMerchantId\":\"4300001063072\",\"deliveryResourceInstItemList\":[{\"mktResInstNbrs\":[\"20191111019902\"],\"orderItemId\":\"20190222162311506741831\",\"productId\":\"1098427109759229953\"}]}";
        Gson gson = new Gson();
        DeliveryResourceInstReq req = gson.fromJson(json, new TypeToken<DeliveryResourceInstReq>(){}.getType());
        supplierResourceInstService.deliveryInResourceInst(req);
    }

    @Test
    public void addResourceInst() {
//        String json = "{\"createDate\":1547805599375,\"createStaff\":\"1077840960118870018\",\"ctCode\":{\"20190118180401\":\"1\"},\"eventType\":\"1001\",\"merchantId\":\"4301811022885\",\"mktResId\":\"1085066587772973058\",\"mktResInstNbrs\":[\"20190118180401\"],\"mktResStoreId\":\"31\",\"regionId\":\"0731\",\"sourceType\":\"1\",\"statusCd\":\"1302\"}";
        String json = "{" +
                "\"createStaff\": \"1077840960118870018\"," +
                "\"ctCode\": {" +
                "\"20190118180401\": \"1\"" +
                "}," +
                "\"eventType\": \"1001\"," +
                "\"merchantId\": \"4301811022885\"," +
                "\"mktResId\": \"1085066587772973058\"," +
                "\"mktResInstNbrs\": [\"20190123180401\"]," +
                "\"mktResStoreId\": \"31\"," +
                "\"regionId\": \"0731\"," +
                "\"sourceType\": \"1\"," +
                "\"statusCd\": \"1302\"" +
                "}";
        json = "{\"createStaff\":\"1077839559879852033\",\"eventType\":\"1001\",\"merchantId\":\"4301811025392\",\"mktResId\":\"100000861\",\"mktResInstNbrs\":[\"201903201502\"],\"mktResInstType\":\"2\",\"mktResStoreId\":\"21\",\"regionId\":\"0731\",\"sourceType\":\"1\",\"statusCd\":\"1202\",\"storageType\":\"1044\"}";
        Gson gson = new Gson();
        ResourceInstAddReq req  = gson.fromJson(json, new TypeToken<ResourceInstAddReq>(){}.getType());
        supplierResourceInstService.addResourceInst(req);
    }

    /**
     * 省包调拨 一次性掉接口走 跨多个dubbo服务
     */
    @Test
    public void allocateResourceInst() {
        String json = "{\"createStaff\":\"1077840960118870018\",\"mktResInstIds\":[\"1090848474668589057\"],\"destStoreId\":\"31\",\"mktResStoreId\":\"41\"}";
        Gson gson = new Gson();
        SupplierResourceInstAllocateReq req  = gson.fromJson(json, new TypeToken<SupplierResourceInstAllocateReq>(){}.getType());
        supplierResourceInstService.allocateResourceInst(req);
    }

    /**
     * 省包调拨 更新串码状态
     */


    /**
     * 省包调拨 一次性掉接口走 跨多个dubbo服务
     */
    @Test
    public void allocateResourceInstUpdateResourceInstByIds() {
        String json = "{\"createStaff\":\"1077840960118870018\",\"mktResInstIds\":[\"1087233827800846337\"],\"mktResStoreId\":\"43\"}";
        Gson gson = new Gson();
        SupplierResourceInstAllocateReq req  = gson.fromJson(json, new TypeToken<SupplierResourceInstAllocateReq>(){}.getType());
        String createStaff = req.getCreateStaff();
        AdminResourceInstDelReq updateReq = new AdminResourceInstDelReq();
        BeanUtils.copyProperties(req, updateReq);
        // 设置状态校验条件
        List<String> checkStatusCd = Lists.newArrayList(
                ResourceConst.STATUSCD.DELETED.getCode(),
                ResourceConst.STATUSCD.AUDITING.getCode(),
                ResourceConst.STATUSCD.ALLOCATIONED.getCode(),
                ResourceConst.STATUSCD.ALLOCATIONING.getCode(),
                ResourceConst.STATUSCD.RESTORAGEING.getCode(),
                ResourceConst.STATUSCD.RESTORAGED.getCode(),
                ResourceConst.STATUSCD.SALED.getCode());
        updateReq.setCheckStatusCd(checkStatusCd);
        updateReq.setStatusCd(ResourceConst.STATUSCD.ALLOCATIONING.getCode());
        updateReq.setUpdateStaff(req.getCreateStaff());
        updateReq.setEventType(ResourceConst.EVENTTYPE.ALLOT.getCode());
        ResultVO updateResultVO = resourceInstService.updateResourceInstByIds(updateReq);
        log.info("SupplierResourceInstServiceImpl.allocateResourceInst resourceInstService.updateResourceInstByIds req={},resp={}", JSON.toJSONString(updateReq), JSON.toJSONString(updateResultVO));
    }

    @Test
    public void validResourceInst() {
        String json = "{\"merchantId\":\"4301811025392\",\"productIds\":[{\"mktResInstNbr\":[\"123\"],\"productId\":\"1089765013613895682\"}]}";
        Gson gson = new Gson();
        ValidResourceInstReq req = gson.fromJson(json, new TypeToken<ValidResourceInstReq>(){}.getType());
        ResultVO<Boolean> resultVO = supplierResourceInstService.validResourceInst(req);
        log.info("resultVO={}", JSON.toJSONString(resultVO));
    }

    @Test
    public void backDeliveryOutResourceInst(){
        String json = "{\"buyerMerchantId\":\"4300001063072\"," +
                "\"deliveryResourceInstItemList\"" +
                ":[{\"mktResInstNbrs\":[\"23232334443448\"]," +
                "\"orderItemId\":\"20190225090207222641306\"," +
                "\"productId\":\"1098427109759229953\"}]}";
        Gson gson = new Gson();
        DeliveryResourceInstReq req = gson.fromJson(json, new TypeToken<DeliveryResourceInstReq>(){}.getType());
        supplierResourceInstService.backDeliveryOutResourceInst(req);
    }

    @Test
    public void backDeliveryInResourceInst(){
        String json = "{\"deliveryResourceInstItemList\":" +
                "[{\"mktResInstNbrs\":[\"20191111019909\"]," +
                "\"orderItemId\":\"20190225090207222641306\"," +
                "\"productId\":\"1098427109759229953\"}],\"sellerMerchantId\":\"4301811022885\"}";
        Gson gson = new Gson();
        DeliveryResourceInstReq req = gson.fromJson(json, new TypeToken<DeliveryResourceInstReq>(){}.getType());
        supplierResourceInstService.backDeliveryInResourceInst(req);
    }
}