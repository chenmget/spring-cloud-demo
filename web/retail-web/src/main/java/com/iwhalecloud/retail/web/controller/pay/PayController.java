package com.iwhalecloud.retail.web.controller.pay;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.web.annotation.PassToken;
import com.iwhalecloud.retail.web.utils.ResponseComUtil;
import com.iwhalecloud.retail.order.consts.order.ActionFlowType;
import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.resquest.PayOrderRequest;
import com.iwhalecloud.retail.order.service.OrderManagerOpenService;
import com.iwhalecloud.retail.pay.dto.request.NotifyRequestDTO;
import com.iwhalecloud.retail.pay.dto.request.PayParamsRequest;
import com.iwhalecloud.retail.pay.service.PayManagerOpenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/pay")
public class PayController {


    @Reference
    private PayManagerOpenService payManagerOpenService;

    @Reference
    private OrderManagerOpenService orderManagerOpenService;


    /**
     * 去支付
     *
     * @return
     */
    @PostMapping("/toPay")
    @PassToken
    public ResultVO toPay(@RequestBody PayParamsRequest toPayRequest) {
        ResultVO resultVO = new ResultVO();
//        CommonResultResp resp = payManagerOpenService.toPay(toPayRequest);
        PayOrderRequest payOrderRequest=new PayOrderRequest();
        payOrderRequest.setPaymoney(toPayRequest.getPaymoney());
        payOrderRequest.setFlowType(ActionFlowType.ORDER_HANDLER_ZF.getCode());
        payOrderRequest.setOrderId(toPayRequest.getOrderId());
        CommonResultResp resp= orderManagerOpenService.payOrder(payOrderRequest);
        ResponseComUtil.orderRespToResultVO(resp, resultVO);
        return resultVO;
    }

    @PostMapping("/notifyUrl")
    @PassToken
    public ResultVO notify(@RequestBody NotifyRequestDTO notifyRequest) {
        ResultVO resultVO = new ResultVO();
        CommonResultResp resp = payManagerOpenService.notify(notifyRequest);
        ResponseComUtil.orderRespToResultVO(resp, resultVO);
        return resultVO;
    }

}
