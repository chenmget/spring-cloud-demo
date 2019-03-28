package com.iwhalecloud.retail.order.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.resquest.SelectOrderRequest;
import com.iwhalecloud.retail.order.ropservice.OperatorOrderService;
import com.iwhalecloud.retail.order.service.OperatorOrderOpenService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class OperatorOrderOpenServiceImpl implements OperatorOrderOpenService {

    @Autowired
    private OperatorOrderService operatorOrderService;



    @Override
    public CommonResultResp selectOrder(SelectOrderRequest request) {
        return operatorOrderService.selectOrder(request);
    }



    @Override
    public CommonResultResp selectOrderDetail(SelectOrderRequest request) {
        return operatorOrderService.selectOrderDetail(request);
    }
}
