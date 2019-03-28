package com.iwhalecloud.retail.order2b.busiservice;

import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.CancelOrderRequest;
import com.iwhalecloud.retail.order2b.dto.resquest.order.UpdateOrderStatusRequest;

public interface CancelOrderService {

    CommonResultResp cancelOrder(UpdateOrderStatusRequest request);

    CommonResultResp autoCancelOrder(CancelOrderRequest request);

}
