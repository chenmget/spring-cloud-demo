package com.iwhalecloud.retail.order2b.service.impl;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.busiservice.PayService;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.consts.order.OrderPayType;
import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.response.CheckPayResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PayOrderRequest;
import com.iwhalecloud.retail.order2b.dto.resquest.order.UpdateOrderStatusRequest;
import com.iwhalecloud.retail.order2b.entity.OrderItem;
import com.iwhalecloud.retail.order2b.manager.OrderManager;
import com.iwhalecloud.retail.order2b.reference.GoodsManagerReference;
import com.iwhalecloud.retail.order2b.service.OrderPayOpenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderPayOpenServiceImpl implements OrderPayOpenService {

    @Autowired
    private PayService payService;

    @Autowired
    private OrderManager orderManager;

    @Autowired
    private GoodsManagerReference goodsManagerReference;

    @Override
    public ResultVO pay(PayOrderRequest request) {
        return payService.pay(request);
    }

    @Override
    public ResultVO<CheckPayResp> checkPay(UpdateOrderStatusRequest request) {
        ResultVO<CheckPayResp> resultVO = new ResultVO<>();
        CheckPayResp checkPayResp = new CheckPayResp();
        checkPayResp.setOrderId(request.getOrderId());
        List<OrderItem> orderItems = orderManager.selectOrderItemsList(request.getOrderId());
        if(CollectionUtils.isEmpty(orderItems)){
            return ResultVO.error("未找到订单项");
        }
        CommonResultResp<List<String>> resp = goodsManagerReference.getGoodsPayTypeList(orderItems);
        if (resp.isFailure()) {
            return ResultVO.error(resp.getResultMsg());
        }
        List<String> payTypeList = new ArrayList<>();
        payTypeList.add(OrderPayType.PAY_TYPE_1.getCode());
        payTypeList.add(OrderPayType.PAY_TYPE_3.getCode());

        if(!CollectionUtils.isEmpty(resp.getResultData())){
            payTypeList.retainAll(resp.getResultData());
        }
        checkPayResp.setOrderId(request.getOrderId());
        checkPayResp.setPayTypeList(payTypeList);
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resultVO;
    }
}
