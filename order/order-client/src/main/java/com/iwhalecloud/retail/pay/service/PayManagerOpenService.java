package com.iwhalecloud.retail.pay.service;

import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.pay.dto.request.NotifyRequestDTO;
import com.iwhalecloud.retail.pay.dto.request.PayParamsRequest;

public interface PayManagerOpenService {

    CommonResultResp toPay(PayParamsRequest params);

    CommonResultResp notify(NotifyRequestDTO params);
}
