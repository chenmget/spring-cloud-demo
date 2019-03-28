package com.iwhalecloud.retail.goods2b.service.impl.dubbo.workFlow;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.utils.SpringContextUtils;
import com.iwhalecloud.retail.workflow.extservice.WFServiceExecutor;
import com.iwhalecloud.retail.workflow.extservice.params.ServiceParamContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 商品模块服务流程控制器
 * @author z
 * @date 2019/3/26
 */

@Slf4j
@Service(group = "goods")
public class GoodsServiceExecutorImpl implements WFServiceExecutor {

    @Override
    public ResultVO execute(ServiceParamContext paramContext) {

        final String classPath = paramContext.getClassPath();
        try {
            WFServiceExecutor localServiceExecutor = SpringContextUtils.getBeanByPath(classPath);
            return localServiceExecutor.execute(paramContext);
        } catch (ClassNotFoundException e) {
            log.error("GoodsServiceExecutorImpl.execute error," , e);

            return ResultVO.error(e.getMessage());
        }
    }
}
