package com.iwhalecloud.retail.order.dbservice.impl;


import com.iwhalecloud.retail.order.OrderServiceApplication;
import com.iwhalecloud.retail.order.consts.order.LoginUserType;
import com.iwhalecloud.retail.order.dto.resquest.SelectOrderRequest;
import com.iwhalecloud.retail.order.manager.OrderCoreManager;
import com.iwhalecloud.retail.order.manager.OrderManager;
import com.iwhalecloud.retail.order.model.OrderUpdateAttrEntity;
import com.iwhalecloud.retail.order.ropservice.MemberOrderService;
import com.iwhalecloud.retail.order.ropservice.OperatorOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderServiceApplication.class)
public class SelectOrderServiceTest {

    @Autowired
    private OperatorOrderService operatorOrderService;

    @Autowired
    private MemberOrderService memberOrderService;

    @Test
    public void selectDetail(){
        SelectOrderRequest orderRequestDTO=new SelectOrderRequest();
//        orderRequestDTO.setOrderId("20181203210414343006880");
        orderRequestDTO.setLoginUserType(LoginUserType.LOGIN_USER_TYPE_M.getCode());
        orderRequestDTO.setPageNo(2);
        orderRequestDTO.setMemberId("181019293400418098");
//        orderRequestDTO.setOrderStatus("2");
        memberOrderService.selectOrder(orderRequestDTO);
    }

    @Autowired
    private OrderManager orderManager;

    @Test
    public void testList(){
        SelectOrderRequest orderRequestDTO=new SelectOrderRequest();

        orderManager.selectMemberOrderList(orderRequestDTO);
    }

    @Autowired
    private OrderCoreManager orderCoreManager;

    @Test
    public  void testSelect(){
        OrderUpdateAttrEntity orderUpdateAttrEntity=new OrderUpdateAttrEntity();
        orderUpdateAttrEntity.setOrderId("20181204224256861879540");
        orderCoreManager.selectOrderStatus(orderUpdateAttrEntity);
    }


}
