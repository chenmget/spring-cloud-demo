package com.iwhalecloud.retail.workflow.bizservice.impl;

import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.config.DubboConsumer;
import com.iwhalecloud.retail.workflow.extservice.WFServiceExecutor;
import com.iwhalecloud.retail.workflow.extservice.params.ServiceParamContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 远程调用服务代理类
 * @author z
 * @date 2019/3/26
 */
@Service
@Slf4j
public class WFServiceExecutorProxyImpl implements WFServiceExecutor {

    @Autowired
    private DubboConsumer dubboConsumer;

    @Override
    public ResultVO execute(ServiceParamContext serviceParamContext) {

        String classPath = "com.iwhalecloud.retail.workflow.extservice.WFServiceExecutor";
        String serviceGroup = serviceParamContext.getServiceGroup();
        ReferenceConfig<WFServiceExecutor> reference = dubboConsumer.getConsumer(classPath,serviceGroup);
        log.info("invokeRouteService:{}",reference);
        if(reference == null){
            return ResultVO.error("获取消费服务失败");
        }
        ResultVO resultVO = reference.get().execute(serviceParamContext);
        log.info("invokeRouteService.run:{}",resultVO);
        if (resultVO == null || !resultVO.isSuccess()) {
            log.error("RunRouteServiceImpl.invokeRouteService invoke fail,classPath={},serviceGroup={},resultVO={}"
                    ,classPath,serviceGroup, JSON.toJSONString(resultVO));
        }

        return resultVO;
    }
}
