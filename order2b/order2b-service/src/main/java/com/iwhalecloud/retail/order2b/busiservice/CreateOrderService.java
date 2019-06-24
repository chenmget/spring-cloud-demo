package com.iwhalecloud.retail.order2b.busiservice;

import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.response.CreateOrderResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.CreateOrderRequest;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PreCreateOrderReq;
import com.iwhalecloud.retail.order2b.entity.Order;
import com.iwhalecloud.retail.order2b.model.BuilderOrderModel;
import com.iwhalecloud.retail.order2b.model.CartItemModel;
import com.iwhalecloud.retail.order2b.model.CreateOrderLogModel;

import java.util.List;

public interface CreateOrderService {

    /**
     * 前端数据转成order数据
     */
    CommonResultResp<Order> initRequestToOrder(CreateOrderRequest request);

    /**
     * 查询订单数据
     */
    CommonResultResp<List<CartItemModel>> selectCartGoods(CreateOrderRequest request);

    /**
     * 下单规则校验
     */
    CommonResultResp checkRule(PreCreateOrderReq request, List<CartItemModel> list);


    /**
     * 组装订单项组装
     */
    CommonResultResp<List<BuilderOrderModel>> AssembleOrderItem(List<CartItemModel> list,Order order);

    /**
     * 订单其他业务处理（库存，优惠券）
     */
    CommonResultResp<CreateOrderLogModel> orderOtherHandler(CreateOrderRequest request, List<BuilderOrderModel> list);

    /**
     * 订单金额计算
     */
    CommonResultResp orderTotalAmount(List<BuilderOrderModel> list);

    /**
     * 创建订单
     */
    CommonResultResp builderOrder(CreateOrderRequest request,List<BuilderOrderModel> list);


    /**
     * 下单成功
     */
    CommonResultResp<CreateOrderResp> builderFinishOnSuccess(CreateOrderRequest request, List<BuilderOrderModel> list);

    /**
     * 下单失败(取消订单，回滚)
     */
    CommonResultResp builderFinishOnFailure(List<BuilderOrderModel> list,CreateOrderLogModel log);


}
