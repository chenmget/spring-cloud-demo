package com.iwhalecloud.retail.order.dbservice.impl;


import com.iwhalecloud.retail.order.OrderServiceApplication;
import com.iwhalecloud.retail.order.consts.order.ActionFlowType;
import com.iwhalecloud.retail.order.dto.model.ContractPInfoModel;
import com.iwhalecloud.retail.order.dto.resquest.BuilderOrderRequest;
import com.iwhalecloud.retail.order.dto.resquest.PayOrderRequest;
import com.iwhalecloud.retail.order.dto.resquest.SendGoodsRequest;
import com.iwhalecloud.retail.order.dto.resquest.UpdateOrderStatusRequest;
import com.iwhalecloud.retail.order.manager.OrderZFlowManager;
import com.iwhalecloud.retail.order.model.OrderUpdateAttrEntity;
import com.iwhalecloud.retail.order.dbservice.ContractOrderService;
import com.iwhalecloud.retail.order.dbservice.OrderZFlowService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderServiceApplication.class)
public class ZFlowOrderServiceTest {

    @Autowired
    private OrderZFlowService orderZFlowService;

    //发货
    @Test
    public void testSendGoods() {
        SendGoodsRequest sendGoodsRequestDTO = new SendGoodsRequest();
        sendGoodsRequestDTO.setLogiId("12345566");
        sendGoodsRequestDTO.setOrderId("2018112710435050400867871");
        sendGoodsRequestDTO.setNeedShipping("1");
        sendGoodsRequestDTO.setLogiName("中国电信");
        sendGoodsRequestDTO.setLogiNo(System.currentTimeMillis() + "");
        orderZFlowService.sendGoods(sendGoodsRequestDTO);
    }


    @Test
    public void updateOrderStatus2() {
        UpdateOrderStatusRequest updateOrderStatusRequestDTO = new UpdateOrderStatusRequest();
        updateOrderStatusRequestDTO.setOrderId("2018112710435050400867871");//2018102317450259600938695
        updateOrderStatusRequestDTO.setFlowType("J");
        updateOrderStatusRequestDTO.setMemberId("151012609600174677");
        orderZFlowService.sureReciveGoods(updateOrderStatusRequestDTO);
    }

    @Test
    public void pay(){
        PayOrderRequest payOrderRequest=new PayOrderRequest();
        payOrderRequest.setOrderId("2018112710435050400867871");
        payOrderRequest.setPaymoney(12.0);
        payOrderRequest.setFlowType(ActionFlowType.ORDER_HANDLER_ZF.getCode());
        orderZFlowService.pay(payOrderRequest);
    }

    @Autowired
    private OrderZFlowManager orderZFlowManager;
    @Test
    public void insetIntoZflow(){
        BuilderOrderRequest request=new BuilderOrderRequest();
        request.setBindType("1");
        request.setPayType("1");
        request.setOrderType("1");
        request.setTypeCode(1);
        String orderId=System.currentTimeMillis()+"";
        System.out.println(orderId);
        orderZFlowManager.insertFlowList(request,orderId);



    }
    @Autowired
    private ContractOrderService contractOrderManager;

    @Test
    public void inserCon(){
        ContractPInfoModel contractPInfoDTO=new ContractPInfoModel();
        contractPInfoDTO.setContractName("zzzz");
        contractPInfoDTO.setIcNum("360782000000000000");
        contractPInfoDTO.setAuthentication("aabbcccdddeee");
        contractPInfoDTO.setContractPhone("18988889999");
        contractPInfoDTO.setPhone("13250299515");
        OrderUpdateAttrEntity req=new OrderUpdateAttrEntity();
        req.setOrderId(System.currentTimeMillis()+"");
        contractOrderManager.insertContractInfo(contractPInfoDTO,req);

    }

}
