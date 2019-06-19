package com.iwhalecloud.retail.warehouse.dubbo.workflow;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import org.junit.Test;

public class AllocateResourceInstNotPassActionImplTest {

    @Reference
    private AllocateResourceInstNotPassActionService service;

    @Test
    public void run() {
        InvokeRouteServiceRequest params = new InvokeRouteServiceRequest();
        params.setBusinessId("");
        params.setBusinessData(null);
        service.run(params);
    }
}