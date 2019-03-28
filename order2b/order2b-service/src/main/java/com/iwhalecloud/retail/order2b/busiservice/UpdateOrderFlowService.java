package com.iwhalecloud.retail.order2b.busiservice;

import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PayOrderRequest;
import com.iwhalecloud.retail.order2b.dto.resquest.order.UpdateApplyStatusRequest;
import com.iwhalecloud.retail.order2b.dto.resquest.order.UpdateOrderStatusRequest;
import com.iwhalecloud.retail.order2b.entity.Order;
import com.iwhalecloud.retail.order2b.entity.OrderApply;
import com.iwhalecloud.retail.order2b.model.OrderUpdateAttrModel;

public interface UpdateOrderFlowService {

    /**
     * 订单操作
     */
    CommonResultResp checkFlowType(UpdateOrderStatusRequest request, Order order);

    /**
     * 申请单操作
     */
    CommonResultResp checkFlowTypeApply(UpdateApplyStatusRequest request, OrderApply apply);
    /**
     * 支付
     */
    CommonResultResp pay(PayOrderRequest requestDTO);

    /**
     * 支付定金
     * @param requestDTO
     * @return
     */
    CommonResultResp advancePay(PayOrderRequest requestDTO);

    /**
     * 支付尾款
     * @param requestDTO
     * @return
     */
    CommonResultResp restPay(PayOrderRequest requestDTO);

    /**
     * 发货
     */
    CommonResultResp sendGoods(OrderUpdateAttrModel requestDTO);


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
     * 删除订单（目前不需要）
     */
    CommonResultResp deleteOrder(UpdateOrderStatusRequest requestDTO);

    /**
     * 商家确认
     */
    CommonResultResp merchantConfirm(UpdateOrderStatusRequest requestDTO);

}
