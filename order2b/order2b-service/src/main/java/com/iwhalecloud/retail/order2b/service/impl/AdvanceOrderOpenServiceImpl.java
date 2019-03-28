package com.iwhalecloud.retail.order2b.service.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.order2b.busiservice.CancelOrderService;
import com.iwhalecloud.retail.order2b.config.Order2bContext;
import com.iwhalecloud.retail.order2b.consts.order.ActionFlowType;
import com.iwhalecloud.retail.order2b.dto.base.OrderRequest;
import com.iwhalecloud.retail.order2b.dto.resquest.order.CancelOrderRequest;
import com.iwhalecloud.retail.order2b.entity.AdvanceOrder;
import com.iwhalecloud.retail.order2b.manager.AdvanceOrderManager;
import com.iwhalecloud.retail.order2b.service.AdvanceOrderOpenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class AdvanceOrderOpenServiceImpl implements AdvanceOrderOpenService {

    @Resource
    private AdvanceOrderManager advanceOrderManager;


    @Autowired
    private CancelOrderService cancelOrderService;

    @Override
    public void cancelOverTimePayOrder() {
        List<AdvanceOrder> advanceOrders = advanceOrderManager.queryOverTimeAdvancePayOrder();

        log.info("AdvanceOrderOpenServiceImpl.cancelOverTimePayOrder -->advanceOrders={}", JSON.toJSONString(advanceOrders));
        if (!CollectionUtils.isEmpty(advanceOrders)) {
            for (AdvanceOrder advanceOrder : advanceOrders) {

                try {
                    // 设置本地网和source_from
                    OrderRequest request = new OrderRequest();
                    request.setLanId(advanceOrder.getLanId());
                    request.setSourceFrom(advanceOrder.getSourceFrom());
                    Order2bContext.setDBLanId(request);

                    CancelOrderRequest cancelOrderRequest = new CancelOrderRequest();
                    //系统管理员
                    cancelOrderRequest.setUserId("-1");
                    cancelOrderRequest.setOrderId(advanceOrder.getOrderId());
                    cancelOrderRequest.setFlowType(ActionFlowType.ORDER_HANDLER_QX.getCode());
                    cancelOrderRequest.setCancelReason("过期自动取消预售单");

                    cancelOrderService.autoCancelOrder(cancelOrderRequest);
                } finally {
                    Order2bContext.remove();
                }
            }
        }
    }
}
