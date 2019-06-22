package com.iwhalecloud.retail.goods2b.service.impl.dubbo.workFlow;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.dto.req.HandlerUser;
import com.iwhalecloud.retail.workflow.extservice.WFServiceExecutor;
import com.iwhalecloud.retail.workflow.extservice.params.ServiceParamContext;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 获取商品权限用户
 *
 * @author z
 * @date 2019/3/26
 */
@Service
@Slf4j
public class GoodsNodeRightsServiceExecutorImpl implements WFServiceExecutor {

    @Override
    public ResultVO<List<HandlerUser>> execute(ServiceParamContext context) {

        log.info("业务ID=" + context.getBusinessId());
        log.info("动态参数=" + context.getDynamicParam());
        log.info("业务参数类型=" + context.getParamsType());
        log.info("业务参数值=" + context.getParamsValue());
        System.out.println("--------------");

        List<HandlerUser> handlerUsers = new ArrayList<>();
        HandlerUser user = new HandlerUser();
        user.setHandlerUserId("1");
        user.setHandlerUserName("管理员");
        handlerUsers.add(user);
        //以下实现获取处理人的逻辑
        return ResultVO.success(handlerUsers);
    }
}
