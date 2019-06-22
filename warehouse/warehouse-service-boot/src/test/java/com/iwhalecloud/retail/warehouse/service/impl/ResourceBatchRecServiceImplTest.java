package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceBatchRecService;
import com.iwhalecloud.retail.warehouse.dto.ResourceBatchRecDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseServiceApplication.class)
@Slf4j
public class ResourceBatchRecServiceImplTest {


    @Reference
    private ResourceBatchRecService batchRecService;
    @Test
    public void insertResourceBatchRec() {
        ResourceBatchRecDTO batchRecDTO = new ResourceBatchRecDTO();
        batchRecDTO.setCostPrice(22.0);
        batchRecDTO.setStatusCd("1");
        batchRecDTO.setStatusDate(Calendar.getInstance().getTime());
        batchRecDTO.setCreateDate(Calendar.getInstance().getTime());
        batchRecDTO.setCreateStaff("222");
        batchRecDTO.setLanId("23432");
        batchRecDTO.setRegionId("343");
        batchRecService.insertResourceBatchRec(batchRecDTO);
    }
}