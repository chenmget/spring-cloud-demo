package com.iwhalecloud.retail.order.manager;

import com.iwhalecloud.retail.order.mapper.MainHandlerOrderMapper;
import com.iwhalecloud.retail.order.mapper.OrderItemMapper;
import com.iwhalecloud.retail.order.model.OrderFlowEntity;
import com.iwhalecloud.retail.order.model.OrderUpdateAttrEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

@Component
public class OrderCoreManager {


    @Resource
    private MainHandlerOrderMapper mainHandlerOrderMapper;

    @Resource
    private OrderItemMapper orderItemMapper;


    /**
     * 订单状态初始化
     */
    public int initOrderStatus(OrderUpdateAttrEntity orderStatus) {
        return mainHandlerOrderMapper.initOrderStatus(orderStatus);
    }

    /**
     * 支付(在线支付)
     */
    public int payByZXZF(OrderUpdateAttrEntity orderStatus) {
        return mainHandlerOrderMapper.payByZXZF(orderStatus);
    }

    /**
     * 发货
     */
    public int deliverGoods(OrderUpdateAttrEntity orderStatus) {
        int status = mainHandlerOrderMapper.updateDeliverGoodsStatus(orderStatus);
        int item = orderItemMapper.updateDeliverGoodsNum(orderStatus);
        return item + status;
    }

    /**
     * 收货确认
     */
    public int collectGoods(OrderUpdateAttrEntity orderStatus) {
        return mainHandlerOrderMapper.collectGoods(orderStatus);
    }

    /**
     * 查询订单状态
     */
    public OrderUpdateAttrEntity selectOrderStatus(OrderUpdateAttrEntity dto) {
        return mainHandlerOrderMapper.selectOrderStatus(dto);
    }

    /**
     * 删除订单
     */
    public int deleteOrder(OrderUpdateAttrEntity dto) {
        return mainHandlerOrderMapper.deleteOrder(dto);
    }

    //    串码录入
    public int updateOrderItemsByOrderId(OrderFlowEntity request) {
        if (CollectionUtils.isEmpty(request.getItem())) {
            return 0;
        }

        return mainHandlerOrderMapper.updateOrderItemsByOrderId(request);
    }

    public int updateOrderStatus(OrderUpdateAttrEntity orderStatus){
        return mainHandlerOrderMapper.updateOrderStatus(orderStatus);
    }


}
