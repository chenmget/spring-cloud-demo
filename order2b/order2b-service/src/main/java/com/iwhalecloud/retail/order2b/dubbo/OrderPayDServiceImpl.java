package com.iwhalecloud.retail.order2b.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PayOrderRequest;
import com.iwhalecloud.retail.order2b.service.OrderPayOpenService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class OrderPayDServiceImpl implements OrderPayOpenService {

    @Autowired
    private OrderPayOpenService orderPayService;

    @Override
    public ResultVO pay(PayOrderRequest request) {
        return orderPayService.pay(request);
    }
}
