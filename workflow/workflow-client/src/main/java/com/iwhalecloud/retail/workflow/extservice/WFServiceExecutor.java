package com.iwhalecloud.retail.workflow.extservice;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.extservice.params.ServiceParamContext;

/**
 * 服务执行控制器
 * @author z
 * @date 2019/3/26
 */
public interface WFServiceExecutor {

    /**
     * 服务执行
     * @param serviceParamContext
     * @return
     */
    ResultVO execute(ServiceParamContext serviceParamContext);

}
