package com.iwhalecloud.retail.warehouse.dubbo.workflow;

import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.service.GreenChannelProcessingPassActionService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseServiceApplication.class)
@Slf4j
public class GreenChannelProcessingPassActionImplTest {
    @Autowired
    private GreenChannelProcessingPassActionService passActionService;

    @Test
    public void run(){
        InvokeRouteServiceRequest request = new InvokeRouteServiceRequest();
        request.setBusinessId("1087274333727121410");
        passActionService.run(request);
    }

}