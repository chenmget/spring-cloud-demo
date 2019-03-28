package com.iwhalecloud.retail.warehouse.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.request.AdminResourceInstDelReq;
import com.iwhalecloud.retail.warehouse.dto.request.DeliveryResourceInstReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstUpdateReq;
import com.iwhalecloud.retail.warehouse.service.MerchantResourceInstService;
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
public class MerchantResourceInstOpenServiceImplTest {

    @Reference
    private MerchantResourceInstService merchantResourceInstService;

    @Test
    public void delResourceInstForMerchant() {
        ResourceInstUpdateReq req = new ResourceInstUpdateReq();
        req.setMktResInstNbrs(Lists.newArrayList("20190313002","20190313003"));
        req.setCheckStatusCd(Lists.newArrayList(ResourceConst.STATUSCD.DELETED.getCode(),
                ResourceConst.STATUSCD.AUDITING.getCode(),
                ResourceConst.STATUSCD.ALLOCATIONED.getCode(),
                ResourceConst.STATUSCD.ALLOCATIONING.getCode(),
                ResourceConst.STATUSCD.RESTORAGEING.getCode(),
                ResourceConst.STATUSCD.RESTORAGED.getCode(),
                ResourceConst.STATUSCD.SALED.getCode()));
        req.setMerchantId("4306241024815");
        req.setStatusCd(ResourceConst.STATUSCD.DELETED.getCode());
        merchantResourceInstService.delResourceInstForMerchant(req);
    }

    @Test
    public void delResourceInst() {
        ResourceInstUpdateReq req = new ResourceInstUpdateReq();
//        req.setMktResInstNbrs(Lists.newArrayList("20190313002","20190313003"));
//        req.setCheckStatusCd(Lists.newArrayList(ResourceConst.STATUSCD.DELETED.getCode(),
//                ResourceConst.STATUSCD.AUDITING.getCode(),
//                ResourceConst.STATUSCD.ALLOCATIONED.getCode(),
//                ResourceConst.STATUSCD.ALLOCATIONING.getCode(),
//                ResourceConst.STATUSCD.RESTORAGEING.getCode(),
//                ResourceConst.STATUSCD.RESTORAGED.getCode(),
//                ResourceConst.STATUSCD.SALED.getCode()));
//        req.setMerchantId("4306241024815");
//        req.setStatusCd(ResourceConst.STATUSCD.DELETED.getCode());

        String json = "{\"checkStatusCd\":[\"1301\",\"1211\",\"1210\",\"1305\",\"1205\",\"1203\"],\"merchantId\":\"4306241024815\",\"mktResInstNbrs\":[\"201903151524002,201903151524002\"],\"statusCd\":\"1110\",\"updateStaff\":\"1079205153170591745\"}";
        json = "{\"checkStatusCd\":[\"1301\",\"1211\",\"1210\",\"1305\",\"1205\",\"1203\"],\"merchantId\":\"4306241024815\",\"mktResInstNbrs\":[\"201903151732\"],\"statusCd\":\"1110\",\"updateStaff\":\"1079205153170591745\"}";
        Gson gson = new Gson();
        req  = gson.fromJson(json, new TypeToken<ResourceInstUpdateReq>(){}.getType());
        merchantResourceInstService.delResourceInst(req);
    }

    @Test
    public void addResourceInst() {
        String json = "{\n" +
                "\t\"createStaff\": \"1079205153170591745\",\n" +
                "\t\"ctCode\": {\n" +
                "\t\t\"20190313002\": \"5\",\n" +
                "\t\t\"20190313003\": \"6\"\n" +
                "\t},\n" +
                "\t\"eventType\": \"1001\",\n" +
                "\t\"merchantId\": \"4306241024815\",\n" +
                "\t\"merchantTypes\": [\"1\"],\n" +
                "\t\"mktResId\": \"1085728975513956354\",\n" +
                "\t\"mktResInstNbrs\": [\"20190313002\", \"20190313003\"],\n" +
                "\t\"mktResInstType\": \"1\",\n" +
                "\t\"orderId\": \"\",\n" +
                "\t\"regionId\": \"0731\",\n" +
                "\t\"sourceType\": \"1\",\n" +
                "\t\"statusCd\": \"1302\",\n" +
                "\t  \"storageType\":\"1001\"\n" +
                "}";
        Gson gson = new Gson();
        ResourceInstAddReq req  = gson.fromJson(json, new TypeToken<ResourceInstAddReq>(){}.getType());
        merchantResourceInstService.addResourceInst(req);
    }
}