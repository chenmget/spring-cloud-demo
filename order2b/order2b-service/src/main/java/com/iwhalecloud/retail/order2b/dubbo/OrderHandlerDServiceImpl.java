package com.iwhalecloud.retail.order2b.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.resquest.order.UpdateOrderStatusRequest;
import com.iwhalecloud.retail.order2b.service.OrderHandlerOpenService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class OrderHandlerDServiceImpl implements OrderHandlerOpenService {


    @Autowired
    private OrderHandlerOpenService orderHandleService;


    @Override
    public ResultVO updateOrder(UpdateOrderStatusRequest request) {
        return orderHandleService.updateOrder(request);
    }


}
