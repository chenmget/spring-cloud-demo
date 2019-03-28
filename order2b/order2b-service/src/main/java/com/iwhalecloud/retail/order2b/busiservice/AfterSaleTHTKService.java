package com.iwhalecloud.retail.order2b.busiservice;

import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.OrderApplyReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PayOrderRequest;
import com.iwhalecloud.retail.order2b.dto.resquest.order.UpdateApplyStatusRequest;
import com.iwhalecloud.retail.order2b.entity.Order;
import com.iwhalecloud.retail.order2b.entity.OrderApply;

public interface AfterSaleTHTKService {

    /**
     * 校验
     */
    CommonResultResp tkCheck(OrderApplyReq req);

    /**
     * 活动校验
     */
    CommonResultResp checkActivity(OrderApplyReq req,Order order);


    /**
     * 组装请求数据
     */
    OrderApply initRequestToApply(OrderApplyReq req);

    /**
     * 生成请求单
     */
    CommonResultResp builderOrderApply(OrderApplyReq req,OrderApply orderApply);

    /**
     * 审核、完成
     */
    CommonResultResp handlerApply(UpdateApplyStatusRequest request, OrderApply orderApply);

    /**
     * 退款
     */
    CommonResultResp returnAmount(PayOrderRequest request);
}
