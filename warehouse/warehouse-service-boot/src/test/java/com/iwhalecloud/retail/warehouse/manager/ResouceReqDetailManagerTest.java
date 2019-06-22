package com.iwhalecloud.retail.warehouse.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.dto.ResourceReqDetailPageDTO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceReqDetailQueryReq;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseServiceApplication.class)
@Slf4j
public class ResouceReqDetailManagerTest {


    @Autowired
    private ResourceReqDetailManager resourceReqDetailManager;

    @Test
    public void getStore() {
        ResourceReqDetailQueryReq req=new ResourceReqDetailQueryReq();
        req.setPageNo(1);
        req.setPageSize(10);
//        req.setReqCode("12099332");
//        req.setMktResInstNbr("5001");
        req.setTypeId("201903142030001");
        Page<ResourceReqDetailPageDTO> respPage = resourceReqDetailManager.listResourceRequestPage(req);
        System.out.println(respPage.getRecords().toString());
    }

}