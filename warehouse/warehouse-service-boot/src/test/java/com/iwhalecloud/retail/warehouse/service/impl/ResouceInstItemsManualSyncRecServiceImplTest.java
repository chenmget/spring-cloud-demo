package com.iwhalecloud.retail.warehouse.service.impl;

import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.dto.request.ResouceInstItmsManualSyncRecPageReq;
import com.iwhalecloud.retail.warehouse.service.ResouceInstItemsManualSyncRecService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author he.sw
 * @date 2019/07/07 16:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseServiceApplication.class)
@Slf4j
public class ResouceInstItemsManualSyncRecServiceImplTest {
    @Autowired
    private ResouceInstItemsManualSyncRecService resouceInstItemsManualSyncRecService;

    @Test
    public void listResourceItemsManualSyncRec() {
        ResouceInstItmsManualSyncRecPageReq req = new ResouceInstItmsManualSyncRecPageReq();
        req.setPageSize(5);
        req.setPageNo(1);
//        req.setAddOptionType("1");
        resouceInstItemsManualSyncRecService.listResourceItemsManualSyncRec(req);
    }

}
