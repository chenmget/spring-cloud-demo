package com.iwhalecloud.retail.order.ropservice.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.resquest.order.ApplyOrderRequestDTO;
import com.iwhalecloud.retail.order.dto.resquest.order.SelectApplyOrderRequestDTO;
import com.iwhalecloud.retail.order.dto.resquest.order.UpdateApplyOrderRequestDTO;
import com.iwhalecloud.retail.order.ropservice.AfterSaleOrderService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AfterSaleOrderServiceImpl implements AfterSaleOrderService {



    @Override
    public CommonResultResp addOrderApply(ApplyOrderRequestDTO request) {
        CommonResultResp resultVO = new CommonResultResp();
        return resultVO;
    }

    @Override
    public CommonResultResp selectApplyOrder(SelectApplyOrderRequestDTO request) {


        return new CommonResultResp();
    }

    @Override
    public CommonResultResp updateTHApplyOrder(UpdateApplyOrderRequestDTO request) {
//        orderMangerHandler.login(request.getMemberId(),request.getUserSessionId());
        CommonResultResp resultVO = new CommonResultResp();

        return resultVO;
    }

    @Override
    public CommonResultResp updateHHApplyOrder(UpdateApplyOrderRequestDTO request) {
//        orderMangerHandler.login(request.getMemberId(), request.getUserSessionId());
        CommonResultResp resultVO = new CommonResultResp();

        return resultVO;
    }
}
