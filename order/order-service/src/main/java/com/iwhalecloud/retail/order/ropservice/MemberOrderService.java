package com.iwhalecloud.retail.order.ropservice;

import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.resquest.SelectOrderRequest;

public interface MemberOrderService {

    CommonResultResp selectOrder(SelectOrderRequest request);

    CommonResultResp selectOrderDetail(SelectOrderRequest request);
}
