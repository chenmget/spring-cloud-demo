package com.iwhalecloud.retail.order.dbservice;

import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.resquest.BuilderOrderRequest;
import com.iwhalecloud.retail.order.dto.resquest.OrderInfoEntryRequest;
import com.iwhalecloud.retail.order.model.OrderUpdateAttrEntity;

public interface OrderInitVerifyService {

    /**
     * 初始化订单
     */
    CommonResultResp initOrderInfo(BuilderOrderRequest request, OrderUpdateAttrEntity orderStatusDTO);

    /**
     *  查询订单状态(校验状态)
     */
    CommonResultResp checkOrderStatus(OrderUpdateAttrEntity dto);


    /**
     * 串码校验
     */
    CommonResultResp checkMobileString(OrderInfoEntryRequest request);


}
