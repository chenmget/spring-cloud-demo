package com.iwhalecloud.retail.warehouse.busiservice.impl;

import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.busiservice.ResouceInstTrackService;
import jdk.nashorn.internal.ir.annotations.Reference;
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
public class ResouceInstTrackServiceImplTest {

    @Autowired
    private ResouceInstTrackService resouceInstTrackService;

    @Test
    public void qryOrderIdByNbr() {
        String nbr = "201911223555601";
        String orderId = resouceInstTrackService.qryOrderIdByNbr(nbr);
        log.info("ResouceInstTrackServiceImplTest.qryOrderIdByNbr req={} resp={}", nbr, orderId);
    }
}