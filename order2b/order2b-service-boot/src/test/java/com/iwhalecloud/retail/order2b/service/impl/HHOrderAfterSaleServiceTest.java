package com.iwhalecloud.retail.order2b.service.impl;

import com.iwhalecloud.retail.order2b.TestBase;
import com.iwhalecloud.retail.order2b.consts.order.ActionFlowType;
import com.iwhalecloud.retail.order2b.consts.order.OrderServiceType;
import com.iwhalecloud.retail.order2b.dto.model.order.SendGoodsItemDTO;
import com.iwhalecloud.retail.order2b.dto.resquest.order.*;
import com.iwhalecloud.retail.order2b.service.OrderAfterSaleOpenService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class HHOrderAfterSaleServiceTest extends TestBase {

    @Autowired
    private OrderAfterSaleOpenService orderAfterSaleService;

    @Test
    public void testHH(){
        OrderApplyReq req=new OrderApplyReq();
        req.setServiceType(OrderServiceType.ORDER_SHIP_TYPE_3.getCode());
        req.setOrderId("20190116165847261882876");
        req.setOrderItemId("20190116165847288272386");
        req.setSubmitNum(1);

        req.setUserCode("10010");
        req.setHandlerId("gs");
        req.setUserId("10000");

        req.setResNbrs("20190116002");


        req.setApplyProof("1"); //申请凭证：1有发票、2有检测报告
        req.setGoodReturnType("1"); //商品返回方式：1上门取件、2快递
        req.setReturnedKind("3");  //必填】1在线支付  3线下付款

        req.setRefundType("4"); //退款方式:1退款至账户余额、2原支付方式返回、3退款至银行卡，4：线下退款
        req.setRefundValue("100");

            req.setQuestionDesc("我不想要了"); //问题描述
        req.setReturnReson("太坑了"); //原因
        req.setUploadImgUrl("https://gy.ztesoft.com/group1/M00/00/15/Ci0vWVw9wI-AR4ViAAYt5Ng3DbE102.jpg");

        req.setReturnedAccount("1001 2345 5677 67889");
        req.setBankInfo("中国银行");
        req.setAccountHolderName("人生如狗");
        orderAfterSaleService.createAfter(req);
}


    @Test
    public void thsh(){
        UpdateApplyStatusRequest updateOrderStatusRequest=new UpdateApplyStatusRequest();
        updateOrderStatusRequest.setOrderApplyId("20190125164643168591714");
        updateOrderStatusRequest.setFlowType(ActionFlowType.ORDER_HANDLER_HHSH.getCode());
        updateOrderStatusRequest.setUserCode("gs");
        updateOrderStatusRequest.setUserId("10000");
        updateOrderStatusRequest.setConfirmType("1");
        orderAfterSaleService.handlerApplying(updateOrderStatusRequest);
    }

    @Test
    public void returnGOODS(){
        THSendGoodsRequest sendGoodsRequest=new THSendGoodsRequest();
        sendGoodsRequest.setUserCode("gs");
        sendGoodsRequest.setUserId("10010");
        sendGoodsRequest.setLogiNo("123");
        sendGoodsRequest.setLogiName("中国");
        sendGoodsRequest.setOrderApplyId("20190125164643168591714");
        orderAfterSaleService.userReturnGoods(sendGoodsRequest);
    }

    @Test
    public void recelveGoods(){
        THReceiveGoodsReq sendGoodsRequest=new THReceiveGoodsReq();
        sendGoodsRequest.setUserCode("gs");
        sendGoodsRequest.setUserId("10010");
        sendGoodsRequest.setOrderApplyId("20190125164643168591714");
        List<String> list=new ArrayList<>();
        list.add("20190116002");
        sendGoodsRequest.setResNbrList(list);
        orderAfterSaleService.sellerReceiveGoods(sendGoodsRequest);
    }

    @Autowired
    private OrderAfterSaleOpenService orderAfterSaleOpenService;

    @Test
    public void deliveryGoods(){
        SendGoodsRequest sendGoodsRequest=new SendGoodsRequest();
        sendGoodsRequest.setShipNum(1);
        sendGoodsRequest.setLogiId("10010");
        sendGoodsRequest.setUserId("1077839559879852033");
        sendGoodsRequest.setUserCode("4301811025392");
        sendGoodsRequest.setLogiName("中国联通");
//        sendGoodsRequest.setOrderId("20190112134221491797620");
        sendGoodsRequest.setOrderApplyId("20190125164643168591714");
        sendGoodsRequest.setLogiNo(String.valueOf(System.currentTimeMillis()));

        List<SendGoodsItemDTO> list=new ArrayList<>();
        SendGoodsItemDTO itemDTO=new SendGoodsItemDTO();
        itemDTO.setGoodsId("1085135518260977666");
        itemDTO.setItemId("20190116165847288272386");
        itemDTO.setProductId("1085133312128376834");
        List<String> list1=new ArrayList<>();
        for (int i=0;i<1;i++){
            list1.add("123412341234");
        }

        itemDTO.setResNbrList(list1);
        list.add(itemDTO);
        sendGoodsRequest.setGoodsItemDTOList(list);

        orderAfterSaleOpenService.sellerDeliverGoods(sendGoodsRequest);
    }

    @Test
    public void cancelApply(){
        UpdateApplyStatusRequest updateApplyStatusRequest=new UpdateApplyStatusRequest();
        updateApplyStatusRequest.setOrderApplyId("20190227143906007344775");
        updateApplyStatusRequest.setUserId("1234");
        orderAfterSaleOpenService.cancelApply(updateApplyStatusRequest);
    }


}
