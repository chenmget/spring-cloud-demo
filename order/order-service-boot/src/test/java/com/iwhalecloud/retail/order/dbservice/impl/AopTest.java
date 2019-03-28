package com.iwhalecloud.retail.order.dbservice.impl;

import com.iwhalecloud.retail.order.OrderServiceApplication;
import com.iwhalecloud.retail.order.dto.resquest.SelectOrderRequest;
import com.iwhalecloud.retail.order.ropservice.MemberOrderService;
import com.iwhalecloud.retail.order.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderServiceApplication.class)
public class AopTest {

    @Autowired
    private TestService testService;

    @Autowired
    private MemberOrderService memberOrderService;

    @Test
    public void selectDetail() {
        SelectOrderRequest orderRequestDTO = new SelectOrderRequest();
        orderRequestDTO.setOrderId("2018112615464985300827815");
        orderRequestDTO.setLoginUserType("");
        orderRequestDTO.setMemberId("181031588100419686");
//        orderRequestDTO.setOrderStatus("2");
        memberOrderService.selectOrderDetail(orderRequestDTO);
    }


}
