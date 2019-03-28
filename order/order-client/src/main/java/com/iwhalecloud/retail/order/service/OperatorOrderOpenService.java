package com.iwhalecloud.retail.order.service;

import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.resquest.SelectOrderRequest;

public interface OperatorOrderOpenService {

    /**
     *  查询订单
     */
    CommonResultResp selectOrder(SelectOrderRequest request);



    /**
     * 查询订单详情
     */
    CommonResultResp selectOrderDetail(SelectOrderRequest request);


}
