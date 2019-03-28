package com.iwhalecloud.retail.order.dbservice;

import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.resquest.BuilderOrderRequest;
import com.iwhalecloud.retail.order.model.BuilderOrderDTO;
import com.iwhalecloud.retail.order.model.DeliverEntity;
import com.iwhalecloud.retail.order.model.OrderEntity;

import java.util.List;

public interface BuilderOrderService {


    /**
     * 立即购买加入购物车
     */
    CommonResultResp<OrderEntity> addCartLJGM(BuilderOrderRequest request);

    /**
     * 订单表数组装
     * ord_order(orderEntityDTO)
     */
    CommonResultResp<OrderEntity> builderOrder(BuilderOrderRequest request);

    /**
     * 订单子表组装
     * ord_order_items(orderItemEntityDTO)
     */
    CommonResultResp<List<BuilderOrderDTO>> builderOrderItem(BuilderOrderRequest request);

    /**
     * 物流表
     * ord_DELIVERY (deliverEntityDTO)
     */
    CommonResultResp<DeliverEntity> builderDelivery(BuilderOrderRequest request);

    /**
     * 创建订单(数据准备)
     */
    CommonResultResp crateOrderData(List<BuilderOrderDTO> list);

    /**
     *  保存订单
     *  （插入ord_order，ord_order_item,ord_DELIVERY）
     */
    CommonResultResp saveOrder(List<BuilderOrderDTO> list);

    /**
     * 订单完成
     */
    CommonResultResp builderFinish(BuilderOrderRequest request, List<BuilderOrderDTO> list);

}
