package com.iwhalecloud.retail.order.service;

import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.resquest.*;

public interface OrderManagerOpenService {

    /**
     * 创建订单
     */
    CommonResultResp builderOrder(BuilderOrderRequest request);

    /**
     * 取消订单，删除订单，评论，收货
     */
    CommonResultResp updateOrderStatus(UpdateOrderStatusRequest request);

    /**
     * 发货
     */
    CommonResultResp sendGoods(SendGoodsRequest request);

    /**
     *  揽装录入，订单补录，串码录入
     */

    CommonResultResp orderHandler(OrderInfoEntryRequest request);

    /**
     *  支付
     */

    CommonResultResp payOrder(PayOrderRequest request);


}
