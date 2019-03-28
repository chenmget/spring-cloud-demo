package com.iwhalecloud.retail.order.service;

import com.iwhalecloud.retail.order.dto.model.OrderZFlowModel;

import java.util.List;

public interface OrderMonitoring {

    //订单的执行监控
    public List<OrderZFlowModel> orderExecutionMonitoring(OrderZFlowModel dto);

    //订单提醒
    public int orderRemind();

    //订单催单
    public int orderReminder();

    //订单超时监控
    public int orderTimeoutMonitoring();

    //订单撤销监控
    public int orderRevocationMonitoring();

    //费用监控

}
