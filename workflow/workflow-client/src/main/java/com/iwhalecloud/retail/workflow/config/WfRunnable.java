package com.iwhalecloud.retail.workflow.config;

import com.iwhalecloud.retail.dto.ResultVO;

public interface WfRunnable {

    ResultVO run(InvokeRouteServiceRequest params);

}
