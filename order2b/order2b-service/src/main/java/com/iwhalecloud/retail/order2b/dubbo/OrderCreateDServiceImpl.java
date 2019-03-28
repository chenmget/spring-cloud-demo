package com.iwhalecloud.retail.order2b.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.CreateOrderResp;
import com.iwhalecloud.retail.order2b.dto.response.PreCheckOrderResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.CreateOrderRequest;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PreCreateOrderReq;
import com.iwhalecloud.retail.order2b.service.OrderCreateOpenService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class OrderCreateDServiceImpl implements OrderCreateOpenService {


    @Autowired
    private  OrderCreateOpenService orderCreateOpenService;
    @Override
    public ResultVO<PreCheckOrderResp> preCheckOrderItem(PreCreateOrderReq req) {
        return orderCreateOpenService.preCheckOrderItem(req);
    }

    @Override
    public ResultVO<CreateOrderResp> createOrder(CreateOrderRequest request) {
        return orderCreateOpenService.createOrder(request);
    }
}
