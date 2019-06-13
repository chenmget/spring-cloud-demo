package com.iwhalecloud.retail.order2b.service.impl.workflow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.manager.PurApplyManager;
import com.iwhalecloud.retail.workflow.dto.req.HandlerUser;
import com.iwhalecloud.retail.workflow.extservice.WFServiceExecutor;
import com.iwhalecloud.retail.workflow.extservice.params.ServiceParamContext;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductChangePriceNextNodePeopleImpl implements WFServiceExecutor  {
	
	@Autowired
    private PurApplyManager purApplyManager;
	
	@Override
	public ResultVO<List<HandlerUser>> execute(ServiceParamContext serviceParamContext) {
		
		List<HandlerUser> handlerUsers = Lists.newArrayList();
		log.info("ProductBrandNodeRightsServiceExecutorImpl业务ID=" + serviceParamContext.getBusinessId());
        log.info("ProductBrandNodeRightsServiceExecutorImpl动态参数=" + serviceParamContext.getDynamicParam());
        log.info("ProductBrandNodeRightsServiceExecutorImpl业务参数类型=" + serviceParamContext.getParamsType());
        log.info("ProductBrandNodeRightsServiceExecutorImpl业务参数值=" + serviceParamContext.getParamsValue());
        System.out.println("--------------");
        String batchId = serviceParamContext.getBusinessId();
        HandlerUser handlerUser = new HandlerUser();
        String isFixedLine = purApplyManager.selectisFixedLineByBatchId(batchId);
        if("1".equals(isFixedLine)) {//如果是固网
        	handlerUser.setHandlerUserId("5845");
        	handlerUser.setHandlerUserName("李洁");
        }else {
        	handlerUser.setHandlerUserId("5852");
        	handlerUser.setHandlerUserName("李燕燕");
        }
        handlerUsers.add(handlerUser);
        
		return ResultVO.success(handlerUsers);
	}

}
