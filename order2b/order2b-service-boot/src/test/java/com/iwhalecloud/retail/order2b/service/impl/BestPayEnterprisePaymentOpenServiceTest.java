package com.iwhalecloud.retail.order2b.service.impl;

import com.iwhalecloud.retail.order2b.TestBase;
import com.iwhalecloud.retail.order2b.authpay.PayAuthorizationService;
import com.iwhalecloud.retail.order2b.authpay.handler.BestpayHandler;
import com.iwhalecloud.retail.order2b.authpay.handler.TradeCertificate;
import com.iwhalecloud.retail.order2b.authpay.service.BestpayServiceTemp;
import com.iwhalecloud.retail.order2b.authpay.service.v3.BestpayServiceV3;
import com.iwhalecloud.retail.order2b.consts.PayConsts;
import com.iwhalecloud.retail.order2b.consts.order.OrderAllStatus;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.AsynNotifyReq;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.ToPayReq;
import com.iwhalecloud.retail.order2b.mapper.OrderMapper;
import com.iwhalecloud.retail.order2b.service.BestPayEnterprisePaymentService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * @author guoxianwei 2019-03-01 11:02:30
 */

public class BestPayEnterprisePaymentOpenServiceTest extends TestBase {

    @Resource(name = "bestPayEnterprisePaymentOpenServiceImpl")
    private BestPayEnterprisePaymentService bestPayEnterprisePaymentService;

    @Resource
    private PayAuthorizationService payAuthorizationService;

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void toPay(){
        ToPayReq toPayReq = new ToPayReq();

        String orderId="201905212210000546";
        String orderAmount="500";
        toPayReq.setOrderId(orderId);
        toPayReq.setOrderAmount(orderAmount);
        bestPayEnterprisePaymentService.toPay(toPayReq);
    }
    @Test
    public void asynNotify(){
        AsynNotifyReq req = new AsynNotifyReq();
        req.setORGLOGINCODE("t3Vg4KybIuAWlSqBxzuepQ==");
        req.setPLATCODE("0020000000050033");
        req.setORDERID("1102399502298607618");
        req.setORDERAMOUNT("300");
        req.setORDERSTATUS("1");
        //req.setSIGNSTR("GdMfB6u+Up0fY9dZZlQQxsfgCgICfoKleBE58DsSKbZ/kZ8ZgiGTDjlW0t/cKS7Q1FsVLJdKcVt1K2TlWSeKkVE6onmWL59TpzS0ojDaGOt+5NZ5Uv1XG3e8Z87oyIj42tTHV0Xln7XE0tQHNdxHa8L9mrX919V3Ski/894wMSitT56HsO6hEtE8FzA59Hu2L/1DsylTQWV+HmwZ91ssnsowF8mJ6VoGgooUzbQD51yKqNaFw7fYkvyblxIaZYc19iyhmwVx9PPIB8d86VQY6HP/2xMhwih1wTosTV3qGPGpBlQvdbICGY5mr9CMLZOURY5e9afTaqrmT886h3igaw==");
        req.setSIGNSTR("Xay2yHzvAo6Hy7KG6f6qDF2qyAussWf5/F2BXi0/b7aVMmsdkPYNmyBKq0JqPUvpVUGR46SkdH6A7D509nZLINhin6NtW73i5AMobAWQFgI1+QfYS5epy4b7YAOT6jro75O0YD2LmOZEZLQNc/5+5D5i6sSYO9yCS2KikDDcdvaqcpQhhLUt3e06IdR1qEdvWs3HkJSkkSCTmvB5pDgpPGa01wqzpZutfxUFqSvShoUvYdoZDGPjLgAgqgpqzRNTFVubbTf+p/n0OYpfgTf531qw5eR8W1PAw6hvBgvyXhxxLe4V3UqcYGkDUtcicbskJA0u5FTkNZp7u6HfxNBjLA==");
        bestPayEnterprisePaymentService.asynNotify(req);
    }

    @Test
    public void authorizationApplication(){
        String orderId = "201905229410000556";
        payAuthorizationService.authorizationApplication(orderId, "C");
    }


    @Test
    public void authorizationConfirmation(){
        String orderId = "201905229410000556";
        payAuthorizationService.authorizationConfirmation(orderId);
    }

    @Test
    public void AuthorizationCancellation(){
        String orderId = "201905229410000556";
        payAuthorizationService.AuthorizationCancellation(orderId);
    }



    @Test
    public void updateOrder(){
        String orderId = "201904196910000326";
        orderMapper.updateStatusByOrderId(orderId, "1", "1");
    }



}
