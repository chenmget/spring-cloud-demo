package com.iwhalecloud.retail.order2b.service.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.order2b.Order2BServiceApplication;
import com.iwhalecloud.retail.order2b.consts.OrderManagerConsts;
import com.iwhalecloud.retail.order2b.consts.order.OrderPaymentType;
import com.iwhalecloud.retail.order2b.consts.order.OrderShipType;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderGoodsItemDTO;
import com.iwhalecloud.retail.order2b.dto.resquest.order.CreateOrderRequest;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PreCreateOrderReq;
import com.iwhalecloud.retail.order2b.model.UserInfoModel;
import com.iwhalecloud.retail.order2b.reference.MemberInfoReference;
import com.iwhalecloud.retail.order2b.service.OrderCreateOpenService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes =Order2BServiceApplication.class)
@RunWith(SpringRunner.class)
public class OrderHandleServiceImplTest {

    @Autowired
    private OrderCreateOpenService orderCreateOpenService;

    @Test
    public void createOrder() throws Exception {

        CreateOrderRequest request=new CreateOrderRequest();

        request.setAddressId("1068831942234132482");
        request.setSourceType(OrderManagerConsts.ORDER_SOURCE_TYPE_LJGM);
        request.setTypeCode("2");
        request.setShipType(OrderShipType.ORDER_SHIP_TYPE_1.getCode());
        request.setMemberId("1077840181882560514");
        request.setMerchantId("100002");
        request.setPaymentType(OrderPaymentType.ORDER_PAY_CODE_3.getCode());
        request.setPaymentName(OrderPaymentType.ORDER_PAY_CODE_3.getName());
        request.setOrderType("1");

        request.setRegisterAddress("广东省广州市番禺区巨大创业园");
        request.setRegisterPhone("10010");
        request.setTaxPayerNo("纳税人识别号");
        request.setInvoiceTitle("浩鲸云");
        request.setInvoiceType("2");
        request.setRegisterBank("中国银行");
        request.setRegisterBankAcct("12323213237878656");

        request.setRemark("国服第一丶"+System.currentTimeMillis());
        request.setShopId("10000");
        request.setShopName("会江第一分店");

        List<OrderGoodsItemDTO> orderGoodsItemDTOList=new ArrayList<>();
        OrderGoodsItemDTO orderGoodsItemDTO=new OrderGoodsItemDTO();
        orderGoodsItemDTO.setNum(1);
        orderGoodsItemDTO.setProductId("1077895352720969729");
        orderGoodsItemDTO.setGoodsId("1077376919157895170");
        orderGoodsItemDTOList.add(orderGoodsItemDTO);
        request.setGoodsItem(orderGoodsItemDTOList);
        System.out.println(JSON.toJSONString(request));
        orderCreateOpenService.createOrder(request);

    }

    @Autowired
    private MemberInfoReference memberInfoReference;
    @Test
    public void test(){
        UserInfoModel memberModel=memberInfoReference.selectUserInfo("4301211021582");
        System.out.println(memberModel);
    }
//    地包商
//"    \"memberId\":\"1077840960118870018\",\n" +
//        "    \"merchantId\":\"4301811022885\",\n" +

    //省包商 1077839559879852033  4301811025392
    @Test
    public  void crateOrder(){
        String a="{\"addressId\":\"1085138016384245762\",\"goodsItem\":[{\"goodsId\":\"1101453244726239233\",\"num\":1,\"productId\":\"1101089045995012097\"}],\"memberId\":\"1082191485979451394\",\"merchantId\":\"4301811025392\",\"paymentName\":\"线上支付\",\"paymentType\":\"1\",\"remark\":\"\",\"shipAmount\":\"0\",\"shipType\":\"1\",\"sourceType\":\"LJGM\",\"typeCode\":\"2\"}";
        CreateOrderRequest createOrderRequest=JSON.parseObject(a,CreateOrderRequest.class);
        orderCreateOpenService.createOrder(createOrderRequest);
    }



    @Test
    public void suanfei(){
        String params="{\n" +
                "    \"activityId\":\"1103222728411545602\",\n" +
                "    \"couponInsList\":[\n" +
                "\n" +
                "    ],\n" +
                "    \"goodsItem\":[\n" +
                "        {\n" +
                "            \"goodsId\":\"1103206252593270785\",\n" +
                "            \"goodsName\":\"zhy0303\",\n" +
                "            \"num\":1,\n" +
                "            \"productId\":\"1101089045995012097\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"isMerchantConfirm\":\"1\",\n" +
                "    \"memberId\":\"1077840960118870018\",\n" +
                "    \"merchantId\":\"4301811025392\",\n" +
                "    \"orderCat\":\"1\",\n" +
                "    \"sourceFrom\":\"YHJ\",\n" +
                "    \"sourceType\":\"LJGM\",\n" +
                "    \"userCode\":\"4301811022885\",\n" +
                "    \"userId\":\"1077840960118870018\"\n" +
                "}";
        PreCreateOrderReq req=JSON.parseObject(params,PreCreateOrderReq.class);
        orderCreateOpenService.preCheckOrderItem(req);
    }

    @Test
    public void advanceTest(){
        String para="{\n" +
                "    \"activityId\":\"1103222728411545602\",\n" +
                "    \"memberId\":\"1077840960118870018\",\n" +
                "    \"merchantId\":\"4301811025392\",\n" +
                "    \"typeCode\":\"2\",\n" +
                "    \"sourceFrom\":\"YHJ\",\n" +
                "    \"sourceType\":\"LJGM\",\n" +
                "    \"userCode\":\"4301811022885\",\n" +
                "    \"userId\":\"1077840960118870018\",\n" +
                "    \"remark\":\"\",\n" +
                "    \"paymentType\":\"1\",\n" +
                "    \"paymentName\":\"线上支付\",\n" +
                "    \"shipType\":\"1\",\n" +
                "    \"shipAmount\":0,\n" +
                "    \"addressId\":\"1085084832865337345\",\n" +
                "    \"goodsItem\":[\n" +
                "        {\n" +
                "            \"goodsId\":\"1103206252593270785\",\n" +
                "            \"productId\":\"1101089045995012097\",\n" +
                "            \"num\":1\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        CreateOrderRequest request=JSON.parseObject(para,CreateOrderRequest.class);
        orderCreateOpenService.createOrder(request);
    }

    @Test
    public  void couponSf(){
        String params="{\n" +
                "    \"activityId\":\"\",\n" +
                "    \"couponInsList\":[\n" +
                "        {\n" +
                "            \"couponCode\":\"1103269394152206337\",\n" +
                "            \"couponDesc\":\"(test)满100减20元\",\n" +
                "            \"couponMKId\":\"1103229902445555713\",\n" +
                "            \"couponType\":\"1\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"couponCode\":\"1103269185124888578\",\n" +
                "            \"couponDesc\":\"(test)满100减10元\",\n" +
                "            \"couponMKId\":\"1103230346290999298\",\n" +
                "            \"couponType\":\"1\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"goodsItem\":[\n" +
                "        {\n" +
                "            \"goodsId\":\"1102826596250361858\",\n" +
                "            \"goodsName\":\"oppor R19\",\n" +
                "            \"num\":1,\n" +
                "            \"productId\":\"1102825024783065090\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"isMerchantConfirm\":\"1\",\n" +
                "    \"memberId\":\"1077840960118870018\",\n" +
                "    \"merchantId\":\"4301811025392\",\n" +
                "    \"orderCat\":\"1\",\n" +
                "    \"sourceFrom\":\"YHJ\",\n" +
                "    \"sourceType\":\"LJGM\",\n" +
                "    \"userCode\":\"4301811022885\",\n" +
                "    \"userId\":\"1077840960118870018\"\n" +
                "}";

        PreCreateOrderReq req=JSON.parseObject(params,PreCreateOrderReq.class);
        orderCreateOpenService.preCheckOrderItem(req);
    }

}