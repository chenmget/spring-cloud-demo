package com.iwhalecloud.retail.warehouse.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.dto.ResouceStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.request.StorePageReq;
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
public class ResouceStoreManagerTest {


    @Autowired
    private ResouceStoreManager resouceStoreManager;

    @Test
    public void getStore() {
        String merchantId = "4301811022885";
        ResouceStoreDTO dto = this.resouceStoreManager.getStore(merchantId,"1300");
        assertNotNull(dto);
    }

    public void pageStore(){
        String json = "{\"merchantIds\":[\"4301811022885\"],\"pageNo\":1,\"pageSize\":10,\"sourceFrom\":\"YHJ\"}";
        Gson gson = new Gson();
        StorePageReq req =  gson.fromJson(json, new TypeToken<StorePageReq>(){}.getType());
        resouceStoreManager.pageStore(req);
    }
}