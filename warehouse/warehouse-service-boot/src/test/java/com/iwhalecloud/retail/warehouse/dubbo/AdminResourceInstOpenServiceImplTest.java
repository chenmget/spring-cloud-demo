package com.iwhalecloud.retail.warehouse.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.request.AdminResourceInstDelReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstAddReq;
import com.iwhalecloud.retail.warehouse.service.AdminResourceInstService;
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
public class AdminResourceInstOpenServiceImplTest {

    @Reference
    private AdminResourceInstService adminResourceInstService;

    @Test
    public void addResourceInst() {
        String json = "{\n" +
                "\t\"createStaff\": \"1079205153170591745\",\n" +
                "\t\"ctCode\": {\n" +
                "\t\t\"20190315009\": \"5\",\n" +
                "\t\t\"20190315010\": \"6\"\n" +
                "\t},\n" +
                "\t\"eventType\": \"1001\",\n" +
                "\t\"merchantId\": \"4306241024815\",\n" +
                "\t\"merchantTypes\": [\"1\"],\n" +
                "\t\"mktResId\": \"1085728975513956354\",\n" +
                "\t\"mktResInstNbrs\": [\"20190315019\", \"20190315020\"],\n" +
                "\t\"mktResInstType\": \"1\",\n" +
                "\t\"orderId\": \"\",\n" +
                "\t\"regionId\": \"0731\",\n" +
                "\t\"sourceType\": \"1\",\n" +
                "\t\"statusCd\": \"1302\",\n" +
                "\t  \"storageType\":\"1001\"\n" +
                "}";
        Gson gson = new Gson();
        ResourceInstAddReq req  = gson.fromJson(json, new TypeToken<ResourceInstAddReq>(){}.getType());
        adminResourceInstService.addResourceInst(req);
    }

    @Test
    public void updateResourceInstByIds() {
        AdminResourceInstDelReq req = new AdminResourceInstDelReq();
        req.setMktResInstIds(Lists.newArrayList("1087187305600696321","1087187305822994433"));
        req.setCheckStatusCd(Lists.newArrayList(ResourceConst.STATUSCD.DELETED.getCode(),
                ResourceConst.STATUSCD.AUDITING.getCode(),
                ResourceConst.STATUSCD.ALLOCATIONED.getCode(),
                ResourceConst.STATUSCD.ALLOCATIONING.getCode(),
                ResourceConst.STATUSCD.RESTORAGEING.getCode(),
                ResourceConst.STATUSCD.RESTORAGED.getCode(),
                ResourceConst.STATUSCD.SALED.getCode()));
        req.setStatusCd(ResourceConst.STATUSCD.DELETED.getCode());
        adminResourceInstService.updateResourceInstByIds(req);
    }
}