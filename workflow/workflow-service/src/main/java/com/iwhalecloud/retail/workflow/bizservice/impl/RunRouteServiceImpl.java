package com.iwhalecloud.retail.workflow.bizservice.impl;

import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.bizservice.RunRouteService;
import com.iwhalecloud.retail.workflow.config.DubboConsumer;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import com.iwhalecloud.retail.workflow.config.WfRunnable;
import com.iwhalecloud.retail.workflow.entity.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author mzl
 * @date 2019/1/7
 */
@Slf4j
@com.alibaba.dubbo.config.annotation.Service
public class RunRouteServiceImpl implements RunRouteService {

    @Autowired
    private DubboConsumer dubboConsumer;

    @Override
    public ResultVO invokeRouteService(InvokeRouteServiceRequest invokeRouteServiceRequest, List<Service> serviceList) {
        //执行业务代码
        for (Service service : serviceList) {
            String classPath = service.getClassPath();
            String serviceGroup = service.getServiceGroup();
            ReferenceConfig<WfRunnable> reference = dubboConsumer.getConsumer(classPath, serviceGroup);
            log.info("invokeRouteService:{}", reference);
            if (reference == null) {
                return ResultVO.error("获取消费服务失败");
            }
            ResultVO resultVO = reference.get().run(invokeRouteServiceRequest);
            log.info("invokeRouteService.run:{}", resultVO);
            if (resultVO == null || !resultVO.isSuccess()) {
                log.error("RunRouteServiceImpl.invokeRouteService invoke fail,classPath={},serviceGroup={},resultVO={}",
                        classPath, serviceGroup, JSON.toJSONString(resultVO));
                return resultVO;
            }

        }
        return ResultVO.success();
    }
}
