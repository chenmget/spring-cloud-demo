package com.iwhalecloud.retail.order.ropservice;

import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.resquest.SelectOrderRequest;

public interface OperatorOrderService {

    /**
     * 查询订单
     */
    CommonResultResp selectOrder(SelectOrderRequest request);

    /**
     * 查询订单详情
     */
    CommonResultResp selectOrderDetail(SelectOrderRequest request);

}
