package com.iwhalecloud.retail.order2b.service.impl;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.busiservice.PayService;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PayOrderRequest;
import com.iwhalecloud.retail.order2b.service.OrderPayOpenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderPayOpenServiceImpl implements OrderPayOpenService {

    @Autowired
    private PayService payService;

    @Override
    public ResultVO pay(PayOrderRequest request) {
       return payService.pay(request);
    }
}
