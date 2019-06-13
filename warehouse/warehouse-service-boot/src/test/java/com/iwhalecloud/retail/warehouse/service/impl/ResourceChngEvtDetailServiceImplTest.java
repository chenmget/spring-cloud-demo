package com.iwhalecloud.retail.warehouse.service.impl;

import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.busiservice.ResouceEventService;
import com.iwhalecloud.retail.warehouse.dto.ResouceEventDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseServiceApplication.class)
@Slf4j
public class ResourceChngEvtDetailServiceImplTest {

    @Autowired
    private ResouceEventService eventService;
    @Test
    public void insertResouceEvent() {
//        {"createDate":1560392935000,"createStaff":"4301811025392","destStoreId":"21","eventType":"1003","merchantId":"4301811025392","mktResId":"10128903","mktResStoreId":"33",
//                "objId":"201906132110001185","objType":"1002"}
        ResouceEventDTO eventDTO = new ResouceEventDTO();
        eventDTO.setEventDesc("测试1");
        eventDTO.setMktResEventName("测试1");
        eventDTO.setMktResEventNbr("测试1");
        eventDTO.setObjId("201906132110001185");
        eventDTO.setObjType("1002");
        eventDTO.setMerchantId("4301811025392");
        eventDTO.setStatusCd("1");
        eventDTO.setCreateStaff("4301811025392");
        eventDTO.setCreateOrgId("123123123");
        eventDTO.setCreateDate(new Date());
        eventDTO.setDestStoreId("21");
        eventDTO.setEventType("1003");
        eventDTO.setMktResId("10128903");
        eventDTO.setMktResStoreId("33");
        eventService.insertResouceEvent(eventDTO);
    }
}