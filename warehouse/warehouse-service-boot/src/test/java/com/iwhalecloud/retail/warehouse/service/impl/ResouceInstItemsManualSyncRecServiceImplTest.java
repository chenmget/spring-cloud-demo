package com.iwhalecloud.retail.warehouse.service.impl;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.dto.request.ResouceInstItmsManualSyncRecAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResouceInstItmsManualSyncRecPageReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResouceInstItmsManualSyncRecListResp;
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
        req.setProductId("24917424");
        resouceInstItemsManualSyncRecService.listResourceItemsManualSyncRec(req);
    }

    @Test
    public void addResourceItemsManualSyncRec() {
        ResouceInstItmsManualSyncRecAddReq req = new ResouceInstItmsManualSyncRecAddReq();
        req.setCreateStaff("1");
        req.setBrandId("12344");
        req.setDestLanId("735");
        req.setMktResInstNbr("123456");
        req.setProductId("12345");
        req.setProductName("测试");
        req.setProductType("123");
        req.setUnitType("AB001");
        resouceInstItemsManualSyncRecService.addResourceItemsManualSyncRec(req);
    }

    @Test
    public void getDestLanIdByNbr() {
        ResultVO<ResouceInstItmsManualSyncRecListResp> resultVO = resouceInstItemsManualSyncRecService.getDestLanIdByNbr("123456");
        System.out.print(resultVO.toString());
    }
}
