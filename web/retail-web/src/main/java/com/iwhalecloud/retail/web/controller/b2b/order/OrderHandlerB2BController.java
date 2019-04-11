package com.iwhalecloud.retail.web.controller.b2b.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.CheckPayResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PayOrderRequest;
import com.iwhalecloud.retail.order2b.dto.resquest.order.UpdateOrderStatusRequest;
import com.iwhalecloud.retail.order2b.service.OrderHandlerOpenService;
import com.iwhalecloud.retail.order2b.service.OrderPayOpenService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/api/b2b/order/handler")
@Slf4j
public class OrderHandlerB2BController {

    /**
     * 订单创建
     */

    @Reference
    private OrderHandlerOpenService orderHandlerOpenService;
    @Reference
    private OrderPayOpenService orderPayService;


    /**
     * 处理
     */
    @RequestMapping(value = "/updateOrder", method = RequestMethod.PUT)
    @UserLoginToken
    public ResultVO updateOrder(@RequestBody UpdateOrderStatusRequest request) {
        request.setUserId(UserContext.getUserId());
        request.setUserCode(UserContext.getUser().getRelCode());
        return orderHandlerOpenService.updateOrder(request);
    }

    @RequestMapping(value = "/checkPay", method = RequestMethod.GET)
    @UserLoginToken
    public ResultVO<CheckPayResp> checkPay(@RequestParam("orderId") String orderId,
                             @RequestParam(value = "lanId" , required = false) String lanId) {
        UpdateOrderStatusRequest request = new UpdateOrderStatusRequest();
        request.setOrderId(orderId);
        request.setLanId(lanId);
        request.setUserCode(UserContext.getUser().getRelCode());
        request.setUserId(UserContext.getUserId());
        ResultVO<CheckPayResp> resultVO=orderPayService.checkPay(request);
        if(resultVO.getResultData()!=null){
            resultVO.getResultData().setPayToken(String.valueOf(System.currentTimeMillis()));
        }
        return resultVO;
    }

    @RequestMapping(value = "/pay", method = RequestMethod.PUT)
    @UserLoginToken
    public ResultVO pay(@RequestBody PayOrderRequest request) {
        request.setUserId(UserContext.getUserId());
        ResultVO<CheckPayResp> resultVO = orderPayService.checkPay(request);
        if (resultVO.isSuccess()) {
            CheckPayResp resp = resultVO.getResultData();
            List<String> payList = resp.getPayTypeList();
            if (!payList.contains(request.getPayType())) {
                return ResultVO.error("支付类型不匹配");
            }
        } else {
            return resultVO;
        }
        return orderPayService.pay(request);
    }

    @RequestMapping(value = "/noticePayment", method = RequestMethod.GET)
    public ResultVO noticePayment(@RequestParam("orderId") String orderId, @RequestParam("lanId") String lanId,
                                  @RequestParam("payMoney") Double payMoney, @RequestParam("payType") String payType
            , @RequestParam("flowType") String flowType, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String callback = request.getParameter("callback");
        PayOrderRequest payOrderRequest = new PayOrderRequest();
        payOrderRequest.setOrderId(orderId);
        payOrderRequest.setPaymoney(payMoney);
        payOrderRequest.setLanId(lanId);
        payOrderRequest.setPayType(payType);
        payOrderRequest.setFlowType(flowType);
        ResultVO result = orderPayService.pay(payOrderRequest);
        String json = JSON.toJSONString(result);
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.print(callback + "(" + json + ")");
        return null;
    }
}
