package com.iwhalecloud.retail.warehouse.service.impl;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListResp;
import com.iwhalecloud.retail.warehouse.service.SupplierResourceInstService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseServiceApplication.class)
@Slf4j
public class ResourceInstServiceImplTest {

    @Autowired
    private SupplierResourceInstService supplierResourceInstService;

    @Autowired
    private ResourceInstService resourceInstService;

    @Test
    public void validResourceInst1(){
        DeliveryValidResourceInstReq req = new DeliveryValidResourceInstReq();
        List<ValidResourceInstItem> items = new ArrayList<>();
        List<String> productIdList = new ArrayList<>();
        List<String> mktResInstNbrlist = new ArrayList<>();
        mktResInstNbrlist.add("4444444455555");
        productIdList.add("10114880");
        req.setMerchantId("4301811022885");
        req.setProductIdList(productIdList);
        req.setMktResInstNbrList(mktResInstNbrlist);
        ResultVO<Boolean> resultVO = supplierResourceInstService.validResourceInst(req);
        Assert.assertTrue(resultVO.getResultData());
    }


    /**
     * 厂商添加串码 供应商添加串码
     */
    @Test
    public void addResourceInst() {
        String json = "{\n" +
                "\t\"createStaff\": \"1079205153170591745\",\n" +
                "\t\"ctCode\": {\n" +
                "\t\t\"20190122005\": \"5\",\n" +
                "\t\t\"20190122006\": \"6\"\n" +
                "\t},\n" +
                "\t\"eventType\": \"1001\",\n" +
                "\t\"merchantId\": \"4306241024815\",\n" +
                "\t\"merchantTypes\": [\"1\"],\n" +
                "\t\"mktResId\": \"1085728975513956354\",\n" +
                "\t\"mktResInstNbrs\": [\"20190121005\", \"20190121006\"],\n" +
                "\t\"mktResInstType\": \"1\",\n" +
                "\t\"orderId\": \"\",\n" +
                "\t\"regionId\": \"0731\",\n" +
                "\t\"sourceType\": \"1\",\n" +
                "\t\"statusCd\": \"1302\",\n" +
                "\t  \"storageType\":\"1001\"\n" +
                "}";
        Gson gson = new Gson();
        ResourceInstAddReq req  = gson.fromJson(json, new TypeToken<ResourceInstAddReq>(){}.getType());
        req.setEventType(ResourceConst.EVENTTYPE.PUT_STORAGE.getCode());
        CopyOnWriteArrayList<String> newList = new CopyOnWriteArrayList(req.getMktResInstNbrs());
        resourceInstService.addResourceInstByMerchant(req, newList);
    }

    /**
     * 查询厂商串码列表 供应商串码列表
     */
    @Test
    public void getResourceInstList() {
        String json = "{\"merchantId\":\"4306241024815\",\"pageNo\":1,\"pageSize\":10}";
        json = "{\"pageNo\":1,\"pageSize\":10}";
//        json = "{\"pageNo\":1,\"pageSize\":10,\"merchantId\":\"4301811025392\",\"unitTypeName\":\"test\"}";
        //json = "{\"merchantId\":\"4301811025392\",\"pageNo\":1,\"pageSize\":10}";
        json = "{\"mktResInstNbrs\":[\"20190315012\",\"20190315011\"]}";

        Gson gson = new Gson();
        ResourceInstListPageReq req  = gson.fromJson(json, new TypeToken<ResourceInstListPageReq>(){}.getType());
        resourceInstService.getResourceInstList(req);
    }

    /**
     * 厂商删除串码
     */
    @Test
    public void delResourceInst() {
        String json = "{\"checkStatusCd\":[\"1308\"],\"merchantId\":\"4306241024815\",\"mktResInstNbrs\":[\"20190121001\"],\"statusCd\":\"1308\",\"updateStaff\":\"1079205153170591745\"}";
        Gson gson = new Gson();
        ResourceInstUpdateReq req  = gson.fromJson(json, new TypeToken<ResourceInstUpdateReq>(){}.getType());
        req.setEventType(ResourceConst.EVENTTYPE.CANCEL.getCode());
        resourceInstService.updateResourceInst(req);
    }
    @Test
    public void updateInstState(){
        ResourceInstUpdateReq req = new ResourceInstUpdateReq();
        req.setMerchantId("4301811025392");
        req.setStatusCd("111111");
        List<String> mktResInstNbrs = Lists.newArrayList();
        mktResInstNbrs.add("123");
        mktResInstNbrs.add("456");
        mktResInstNbrs.add("0066");
        req.setMktResInstNbrs(mktResInstNbrs);
        resourceInstService.updateInstState(req);
    }

    @Test
    public void listResourceInst(){
        ResourceInstListReq req = new ResourceInstListReq();
        req.setMktResStoreId("21");
//        req.setMerchantId("4301811025392");
//        List<String> mktResInstNbrs = Lists.newArrayList();
//        mktResInstNbrs.add("123");
//        mktResInstNbrs.add("456");
//        mktResInstNbrs.add("0066");
//        req.setMktResInstNbrs(mktResInstNbrs);
        ResultVO<List<ResourceInstListResp>> listResultVO = resourceInstService.listResourceInst(req);
    }

}
