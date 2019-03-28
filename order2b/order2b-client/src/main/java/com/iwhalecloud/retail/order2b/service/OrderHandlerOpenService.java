package com.iwhalecloud.retail.order2b.service;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.resquest.order.UpdateOrderStatusRequest;


public interface OrderHandlerOpenService {



    ResultVO updateOrder(UpdateOrderStatusRequest request);

}
