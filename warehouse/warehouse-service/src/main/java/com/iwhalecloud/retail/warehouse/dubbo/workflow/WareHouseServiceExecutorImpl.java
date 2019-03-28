package com.iwhalecloud.retail.warehouse.dubbo.workflow;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.util.SpringContextUtils;
import com.iwhalecloud.retail.workflow.extservice.WFServiceExecutor;
import com.iwhalecloud.retail.workflow.extservice.params.ServiceParamContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 库存模块服务流程控制器
 * @author z
 * @date 2019/3/26
 */

@Slf4j
@Service(group = "warehouse")
public class WareHouseServiceExecutorImpl implements WFServiceExecutor {

    @Override
    public ResultVO execute(ServiceParamContext paramContext) {

        final String classPath = paramContext.getClassPath();
        try {
            WFServiceExecutor localServiceExecutor = SpringContextUtils.getBeanByPath(classPath);
            return localServiceExecutor.execute(paramContext);
        } catch (ClassNotFoundException e) {
            log.error("WareHouseServiceExecutorImpl.execute error," , e);

            return ResultVO.error(e.getMessage());
        }
    }
}
