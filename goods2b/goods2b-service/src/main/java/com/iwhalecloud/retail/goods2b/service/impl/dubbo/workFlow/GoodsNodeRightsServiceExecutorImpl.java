package com.iwhalecloud.retail.goods2b.service.impl.dubbo.workFlow;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.dto.req.HandlerUser;
import com.iwhalecloud.retail.workflow.extservice.WFServiceExecutor;
import com.iwhalecloud.retail.workflow.extservice.params.NodeRightsServiceParamContext;
import com.iwhalecloud.retail.workflow.extservice.params.ServiceParamContext;
import lombok.extern.slf4j.Slf4j;

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
        System.out.println("--------------");
        //以下实现获取处理人的逻辑
        return ResultVO.success(Lists.newArrayList());
    }
}
