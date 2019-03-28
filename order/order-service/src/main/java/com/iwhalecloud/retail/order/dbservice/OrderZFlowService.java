package com.iwhalecloud.retail.order.dbservice;

import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.resquest.OrderInfoEntryRequest;
import com.iwhalecloud.retail.order.dto.resquest.PayOrderRequest;
import com.iwhalecloud.retail.order.dto.resquest.SendGoodsRequest;
import com.iwhalecloud.retail.order.dto.resquest.UpdateOrderStatusRequest;

public interface OrderZFlowService {
    /**
     * 支付
     */
    CommonResultResp pay(PayOrderRequest requestDTO);

    /**
     * 发货
     */
    CommonResultResp sendGoods(SendGoodsRequest requestDTO);

    /**
     * 串码录入
     */
    CommonResultResp addCMRR(OrderInfoEntryRequest requestDTO);

    /**
     * 揽装录入
     */
    CommonResultResp addLZRR(OrderInfoEntryRequest requestDTO);

    /**
     * 订单补录
     */
    CommonResultResp addDDBR(OrderInfoEntryRequest requestDTO);

    /**
     * 确认收货
     */
    CommonResultResp sureReciveGoods(UpdateOrderStatusRequest requestDTO);

    /**
     * 评价
     */
    CommonResultResp evaluate(UpdateOrderStatusRequest requestDTO);

    /**
     * 取消订单
     */
    CommonResultResp cancelOrder(UpdateOrderStatusRequest requestDTO);

    /**
     * 删除订单
     */
    CommonResultResp deleteOrder(UpdateOrderStatusRequest requestDTO);

}
