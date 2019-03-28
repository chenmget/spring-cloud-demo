package com.iwhalecloud.retail.workflow.bizservice;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import com.iwhalecloud.retail.workflow.entity.Service;

import java.util.List;

public interface RunRouteService {

    /**
     * 执行业务方法
     */
    ResultVO invokeRouteService(InvokeRouteServiceRequest invokeRouteServiceRequest, List<Service> serviceList);

}
