package com.iwhalecloud.retail.order.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.resquest.SelectOrderRequest;
import com.iwhalecloud.retail.order.ropservice.MemberOrderService;
import com.iwhalecloud.retail.order.service.MemberOrderOpenService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class MemberOrderOpenServiceImpl implements MemberOrderOpenService {

    @Autowired
    private MemberOrderService memberOrderService;

    @Override
    public CommonResultResp selectOrder(SelectOrderRequest request) {
        return memberOrderService.selectOrder(request);
    }

    @Override
    public CommonResultResp selectOrderDetail(SelectOrderRequest request) {
        return memberOrderService.selectOrderDetail(request);
    }
}
