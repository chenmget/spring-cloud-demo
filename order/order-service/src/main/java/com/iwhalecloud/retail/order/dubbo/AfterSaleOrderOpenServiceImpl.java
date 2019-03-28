package com.iwhalecloud.retail.order.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.resquest.order.ApplyOrderRequestDTO;
import com.iwhalecloud.retail.order.dto.resquest.order.SelectApplyOrderRequestDTO;
import com.iwhalecloud.retail.order.dto.resquest.order.UpdateApplyOrderRequestDTO;
import com.iwhalecloud.retail.order.ropservice.AfterSaleOrderService;
import com.iwhalecloud.retail.order.service.AfterSaleOrderOpenService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AfterSaleOrderOpenServiceImpl implements AfterSaleOrderOpenService {

    @Autowired
    private AfterSaleOrderService afterSaleOrderService;
    @Override
    public CommonResultResp addOrderApply(ApplyOrderRequestDTO request) {
        return afterSaleOrderService.addOrderApply(request);
    }

    @Override
    public CommonResultResp selectApplyOrder(SelectApplyOrderRequestDTO request) {
        return afterSaleOrderService.selectApplyOrder(request);
    }

    @Override
    public CommonResultResp updateTHApplyOrder(UpdateApplyOrderRequestDTO request) {
        return afterSaleOrderService.updateTHApplyOrder(request);
    }

    @Override
    public CommonResultResp updateHHApplyOrder(UpdateApplyOrderRequestDTO request) {
        return afterSaleOrderService.updateHHApplyOrder(request);
    }
}
