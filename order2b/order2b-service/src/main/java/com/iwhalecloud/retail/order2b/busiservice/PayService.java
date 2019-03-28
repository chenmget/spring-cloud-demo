package com.iwhalecloud.retail.order2b.busiservice;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PayOrderRequest;
import com.iwhalecloud.retail.order2b.entity.Order;

public interface PayService {


    ResultVO pay(PayOrderRequest request);

    /**
     * 订单支付
     */
    ResultVO orderPay(PayOrderRequest request, Order order);

    /**
     * 定金支付
     */
    ResultVO advanceOrderPay(PayOrderRequest request,Order order);

    /**
     * 尾款支付
     */
    ResultVO restPay(PayOrderRequest request,Order order);
}
