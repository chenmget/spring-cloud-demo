package com.iwhalecloud.retail.order2b.service;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.PreCheckOrderResp;
import com.iwhalecloud.retail.order2b.dto.response.CreateOrderResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.CreateOrderRequest;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PreCreateOrderReq;


public interface OrderCreateOpenService {

    ResultVO<PreCheckOrderResp> preCheckOrderItem(PreCreateOrderReq req);

    ResultVO<CreateOrderResp> createOrder(CreateOrderRequest request);

}
