package com.iwhalecloud.retail.order2b.service.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.order2b.TestBase;
import com.iwhalecloud.retail.order2b.busiservice.DeliverGoodsService;
import com.iwhalecloud.retail.order2b.consts.order.ActionFlowType;
import com.iwhalecloud.retail.order2b.dto.model.order.SendGoodsItemDTO;
import com.iwhalecloud.retail.order2b.dto.resquest.order.ReceiveGoodsReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SendGoodsRequest;
import com.iwhalecloud.retail.order2b.manager.OrderManager;
import com.iwhalecloud.retail.order2b.model.OrderUpdateAttrModel;
import com.iwhalecloud.retail.order2b.reference.MemberInfoReference;
import com.iwhalecloud.retail.order2b.service.OrderDRGoodsOpenService;
import com.iwhalecloud.retail.order2b.service.OrderHandlerOpenService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UpdateOrderStatusServiceImplTest extends TestBase {

    @Autowired
    private OrderManager orderManager;
    @Test
    public void update(){
        OrderUpdateAttrModel updateAttrModel=new OrderUpdateAttrModel();
        updateAttrModel.setFlowType(ActionFlowType.ORDER_HANDLER_FH.getCode());
        updateAttrModel.setOrderId("20190104144627988973643");
        updateAttrModel.setFlowType("H");
        updateAttrModel.setShipUserId("gs");
        orderManager.updateOrderAttr(updateAttrModel);
    }

    @Autowired
    private OrderHandlerOpenService orderHandlerOpenService;
    @Autowired
    private OrderDRGoodsOpenService orderDRGoodsOpenService;

    @Test
    public void deliveryGoods(){
        SendGoodsRequest sendGoodsRequest=new SendGoodsRequest();
        sendGoodsRequest.setShipNum(1);
        sendGoodsRequest.setLogiId("10010");
        sendGoodsRequest.setUserId("1077839559879852033");
        sendGoodsRequest.setUserCode("4301811025392");
        sendGoodsRequest.setLogiName("中国联通");
        sendGoodsRequest.setOrderId("20190112134221491797620");
        sendGoodsRequest.setLogiNo(String.valueOf(System.currentTimeMillis()));

        List<SendGoodsItemDTO> list=new ArrayList<>();
        SendGoodsItemDTO itemDTO=new SendGoodsItemDTO();
        itemDTO.setGoodsId("1077376919157895170");
        itemDTO.setItemId("20190112134221604604436");
        itemDTO.setProductId("1077895352720969729");
        List<String> list1=new ArrayList<>();
        for (int i=0;i<1;i++){
            list1.add("123412341234");
        }

        itemDTO.setResNbrList(list1);
        list.add(itemDTO);
        sendGoodsRequest.setGoodsItemDTOList(list);
        int sum=0;
        for(SendGoodsItemDTO s:list){
           sum+=s.getResNbrList().size();
        }
        orderDRGoodsOpenService.deliverGoods(sendGoodsRequest);
    }

    @Test
    public void receiveTest(){
        ReceiveGoodsReq receiveGoodsReq=new ReceiveGoodsReq();
        receiveGoodsReq.setUserCode("4301811022885");
        receiveGoodsReq.setUserId("1077840960118870018");
        List<SendGoodsItemDTO> list=new ArrayList<>();
        SendGoodsItemDTO itemDTO=new SendGoodsItemDTO();
        itemDTO.setItemId("20190112134221604604436");
        List<String> list1=new ArrayList<>();
        for (int i=0;i<1;i++){
            list1.add("265");
        }

        itemDTO.setResNbrList(list1);
        list.add(itemDTO);
        receiveGoodsReq.setOrderId("20190112134221491797620");
        receiveGoodsReq.setList(list);

        orderDRGoodsOpenService.receiveGoods(receiveGoodsReq);
    }

    @Autowired
    private MemberInfoReference memberInfoReference;
    @Test
    public void memberTest(){
        memberInfoReference.selectUserInfo("201406175741000822");
    }

    @Autowired
    private DeliverGoodsService deliverGoodsService;
    @Test
    public void outTest(){
        String a="{\"goodsItemDTOList\":[{\"itemId\":\"20190111095459782645628\",\"productId\":\"1077895352720969729\",\"resNbrList\":[\"123412341234\",\"123123123\"]}],\"logiId\":\"\",\"logiName\":\"\",\"logiNo\":\"123123123\",\"orderId\":\"20190111095459777552163\",\"shipNum\":2,\"userCode\":\"4301811025392\",\"userId\":\"1077839559879852033\"}";
        SendGoodsRequest ab= JSON.parseObject(a,SendGoodsRequest.class);
        deliverGoodsService.sendGoodsFinish(ab);
    }
}
