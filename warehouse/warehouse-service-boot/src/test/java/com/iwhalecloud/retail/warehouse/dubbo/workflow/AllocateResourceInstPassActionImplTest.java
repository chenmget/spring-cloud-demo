package com.iwhalecloud.retail.warehouse.dubbo.workflow;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.warehouse.service.AllocateResourceInstPassActionService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import org.junit.Test;

import static org.junit.Assert.*;

public class AllocateResourceInstPassActionImplTest {

    @Reference
    private AllocateResourceInstPassActionService allocateResourceInstPassActionService;

    @Test
    public void run() {
        InvokeRouteServiceRequest params = new InvokeRouteServiceRequest();
        params.setBusinessId("");
        params.setBusinessData(null);
        allocateResourceInstPassActionService.run(params);
    }
}