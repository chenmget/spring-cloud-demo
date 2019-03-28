package com.iwhalecloud.retail.order2b.service.impl;

import com.iwhalecloud.retail.order2b.TestBase;
import com.iwhalecloud.retail.order2b.consts.order.ActionFlowType;
import com.iwhalecloud.retail.order2b.consts.order.OrderServiceType;
import com.iwhalecloud.retail.order2b.dto.resquest.order.OrderApplyReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.THReceiveGoodsReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.THSendGoodsRequest;
import com.iwhalecloud.retail.order2b.dto.resquest.order.UpdateApplyStatusRequest;
import com.iwhalecloud.retail.order2b.service.OrderAfterSaleOpenService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class TKTHOrderAfterSaleServiceTest extends TestBase {

    @Autowired
    private OrderAfterSaleOpenService orderAfterSaleService;

    @Test
    public void testTK(){
        OrderApplyReq req=new OrderApplyReq();
        req.setServiceType(OrderServiceType.ORDER_SHIP_TYPE_2.getCode());
        req.setOrderId("20190116190811736671966");
        req.setOrderItemId("20190116190811743682790");
        req.setSubmitNum(1);

        req.setUserCode("10010");
        req.setHandlerId("gs");
        req.setUserId("10000");

        req.setResNbrs("");


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
    public void testTH(){
        OrderApplyReq req=new OrderApplyReq();
        req.setServiceType(OrderServiceType.ORDER_SHIP_TYPE_4.getCode());
        req.setOrderId("20190116144832260704332");
        req.setOrderItemId("20190116144832318146299");
        req.setSubmitNum(1);

        req.setUserCode("4301811022885");
        req.setUserId("1077840960118870018");

        req.setResNbrs("20190116001");


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
        updateOrderStatusRequest.setOrderApplyId("20190123142032663106210");
        updateOrderStatusRequest.setFlowType(ActionFlowType.ORDER_HANDLER_TKSH.getCode());
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
        sendGoodsRequest.setOrderApplyId("20190123142032663106210");
        orderAfterSaleService.userReturnGoods(sendGoodsRequest);
    }

    @Test
    public void recelveGoods(){
        THReceiveGoodsReq sendGoodsRequest=new THReceiveGoodsReq();
        sendGoodsRequest.setUserCode("gs");
        sendGoodsRequest.setUserId("10010");
        sendGoodsRequest.setOrderApplyId("20190123142032663106210");
        List<String> list=new ArrayList<>();
        list.add("20190116001");
        sendGoodsRequest.setResNbrList(list);
        orderAfterSaleService.sellerReceiveGoods(sendGoodsRequest);
    }

    @Test
    public void fk(){
        UpdateApplyStatusRequest updateOrderStatusRequest=new UpdateApplyStatusRequest();
        updateOrderStatusRequest.setOrderApplyId("20190123142032663106210");
        updateOrderStatusRequest.setFlowType(ActionFlowType.ORDER_HANDLER_SJTK.getCode());
        updateOrderStatusRequest.setUserCode("gs");
        updateOrderStatusRequest.setUserId("10000");
        orderAfterSaleService.handlerApplying(updateOrderStatusRequest);
    }

    @Test
    public void wc(){
        UpdateApplyStatusRequest updateOrderStatusRequest=new UpdateApplyStatusRequest();
        updateOrderStatusRequest.setOrderId("20190121160526503720148");
        updateOrderStatusRequest.setFlowType(ActionFlowType.ORDER_HANDLER_TKWC.getCode());
        updateOrderStatusRequest.setUserCode("gs");
        updateOrderStatusRequest.setUserId("10000");
        updateOrderStatusRequest.setConfirmType("1");
        orderAfterSaleService.handlerApplying(updateOrderStatusRequest);
    }
}
