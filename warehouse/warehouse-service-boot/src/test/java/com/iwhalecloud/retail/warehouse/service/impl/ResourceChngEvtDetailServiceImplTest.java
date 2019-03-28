package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.dto.ResouceEventDTO;
import com.iwhalecloud.retail.warehouse.busiservice.ResouceEventService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseServiceApplication.class)
@Slf4j
public class ResourceChngEvtDetailServiceImplTest {

    @Reference
    private ResouceEventService eventService;
    @Test
    public void insertResourceChngEvtDetail() {
        ResouceEventDTO eventDTO = new ResouceEventDTO();
        eventDTO.setEventDesc("测试1");
        eventDTO.setMktResEventName("测试1");
        eventDTO.setMktResEventNbr("测试1");
        eventDTO.setObjId("123123123");
        eventDTO.setMerchantId("123123123");
        eventDTO.setStatusCd("1");
        eventDTO.setCreateStaff("123123123");
        eventDTO.setCreateOrgId("123123123");
        eventService.insertResouceEvent(eventDTO);
    }
}