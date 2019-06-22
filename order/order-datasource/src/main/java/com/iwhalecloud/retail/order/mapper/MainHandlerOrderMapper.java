package com.iwhalecloud.retail.order.mapper;

import com.iwhalecloud.retail.order.model.OrderFlowEntity;
import com.iwhalecloud.retail.order.model.OrderUpdateAttrEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MainHandlerOrderMapper {

    /**
     * 订单状态初始化
     */
    int initOrderStatus(OrderUpdateAttrEntity orderStatus);

    /**
     * 支付(在线支付)
     */
    int payByZXZF(OrderUpdateAttrEntity orderStatus);

    /**
     * 发货
     */
    int updateDeliverGoodsStatus(OrderUpdateAttrEntity orderStatus);

    /**
     * 收货确认
     */
    int collectGoods(OrderUpdateAttrEntity orderStatus);

    /**
     *  删除订单
     */
    int deleteOrder(OrderUpdateAttrEntity orderStatus);

    /**
     *  查询订单状态（校验）
     */
    OrderUpdateAttrEntity selectOrderStatus(OrderUpdateAttrEntity statusDTO);

    /**
     *   串码录入
     *   根据orderId，goodsId更新 ord_order_item
     */
    int updateOrderItemsByOrderId(OrderFlowEntity dto);

    //更新订单状态
    int updateOrderStatus(OrderUpdateAttrEntity statusDTO);




}
