package com.iwhalecloud.retail.web.controller.b2b.order.service;

import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderExportUtil {

    public static List<ExcelTitleName> getOrder() {
        List<ExcelTitleName> orderMap = new ArrayList<>();
        orderMap.add(new ExcelTitleName("orderId", "订单号"));
        orderMap.add(new ExcelTitleName("orderTypeName", "订单类型"));
        orderMap.add(new ExcelTitleName("createTime", "下单时间"));
        orderMap.add(new ExcelTitleName("userName", "买家名称"));
        orderMap.add(new ExcelTitleName("statusName", "订单状态"));
        orderMap.add(new ExcelTitleName("supplierName", "供应商名称"));
        orderMap.add(new ExcelTitleName("paymentName", "付款类型"));
        orderMap.add(new ExcelTitleName("payStatusName", "付款状态"));
        orderMap.add(new ExcelTitleName("payTime", "付款时间"));
        orderMap.add(new ExcelTitleName("payName", "付款方式"));
        orderMap.add(new ExcelTitleName("shipTypeName", "物流方式"));
//        orderMap.add(new ExcelTitleName("goodsAmount", "商品总价"));
//        orderMap.add(new ExcelTitleName("shipAmount", "物流费用"));
//        orderMap.add(new ExcelTitleName("couponAmount", "折扣金额"));
//        orderMap.add(new ExcelTitleName("orderAmount", "订单金额"));
        orderMap.add(new ExcelTitleName("goodsAmount100", "商品总价"));
        orderMap.add(new ExcelTitleName("shipAmount100", "物流费用"));
        orderMap.add(new ExcelTitleName("couponAmount100", "折扣金额"));
        orderMap.add(new ExcelTitleName("orderAmount100", "订单金额"));
        return orderMap;
    }

    public static List<ExcelTitleName> getAdvanceOrder() {
        List<ExcelTitleName> orderMap = new ArrayList<>();
        orderMap.add(new ExcelTitleName("orderId", "订单号"));
        orderMap.add(new ExcelTitleName("orderTypeName", "订单类型"));
        orderMap.add(new ExcelTitleName("createTime", "下单时间"));
        orderMap.add(new ExcelTitleName("userName", "买家名称"));
        orderMap.add(new ExcelTitleName("statusName", "订单状态"));
        orderMap.add(new ExcelTitleName("supplierName", "供应商名称"));
        orderMap.add(new ExcelTitleName("shipTypeName", "物流方式"));
//        orderMap.add(new ExcelTitleName("goodsAmount", "商品总价"));
        orderMap.add(new ExcelTitleName("goodsAmount100", "商品总价"));
//        orderMap.add(new ExcelTitleName("shipAmount", "物流费用"));
        orderMap.add(new ExcelTitleName("shipAmount100", "物流费用"));
//        orderMap.add(new ExcelTitleName("couponAmount", "折扣金额"));
        orderMap.add(new ExcelTitleName("couponAmount100", "折扣金额"));
//        orderMap.add(new ExcelTitleName("orderAmount", "订单金额"));
        orderMap.add(new ExcelTitleName("orderAmount100", "订单金额"));

//        orderMap.add(new ExcelTitleName("advanceAmount", "定金"));
        orderMap.add(new ExcelTitleName("advanceAmount100", "定金"));
        orderMap.add(new ExcelTitleName("advancePayTime", "定金支付时间"));
        orderMap.add(new ExcelTitleName("advancePayTypeName", "定金支付方式"));
        orderMap.add(new ExcelTitleName("advancePayStatusName", "定金支付状态"));
//        orderMap.add(new ExcelTitleName("restAmount", "尾款"));
        orderMap.add(new ExcelTitleName("restAmount100", "尾款"));
        orderMap.add(new ExcelTitleName("restPayBegin", "尾款开始支付时间"));
        orderMap.add(new ExcelTitleName("restPayStatusName", "尾款支付状态"));
        orderMap.add(new ExcelTitleName("restPayTypeName", "尾款支付方式"));
        orderMap.add(new ExcelTitleName("restPayTime", "尾款支付时间"));

        return orderMap;
    }


    public static List<ExcelTitleName> getOrderItem() {
        List<ExcelTitleName> orderMap = new ArrayList<>();
        orderMap.add(new ExcelTitleName("itemId", "订单项号"));
        orderMap.add(new ExcelTitleName("goodsName", "商品名称"));
        orderMap.add(new ExcelTitleName("productName", "产品名称"));
        orderMap.add(new ExcelTitleName("num", "数量"));
        orderMap.add(new ExcelTitleName("price", "价格"));
        orderMap.add(new ExcelTitleName("deliveryNum", "发货数量"));
        orderMap.add(new ExcelTitleName("receivedNum", "收货数量"));
        return orderMap;
    }

    public static List<ExcelTitleName> getOrderItemDetail() {
        List<ExcelTitleName> orderMap = new ArrayList<>();
        orderMap.add(new ExcelTitleName("itemId", "订单项号"));
        orderMap.add(new ExcelTitleName("goodsName", "商品名称"));
        orderMap.add(new ExcelTitleName("productName", "产品名称"));
        orderMap.add(new ExcelTitleName("resNbr", "串码"));
        orderMap.add(new ExcelTitleName("batchId", "发货批次"));
        orderMap.add(new ExcelTitleName("stateName", "状态"));
        return orderMap;
    }


    public static List<ExcelTitleName> getOrderApply() {
        List<ExcelTitleName> orderMap = new ArrayList<>();
        orderMap.add(new ExcelTitleName("orderApplyId", "申请单号"));
        orderMap.add(new ExcelTitleName("serviceTypeName", "申请类型"));
        orderMap.add(new ExcelTitleName("createTime", "申请时间"));
        orderMap.add(new ExcelTitleName("submitNum", "提交数量"));
        orderMap.add(new ExcelTitleName("returnReson", "退货原因"));
        orderMap.add(new ExcelTitleName("refundType", "退款方式"));
        orderMap.add(new ExcelTitleName("applyStateName", "申请状态"));
        orderMap.add(new ExcelTitleName("orderId", "订单编号"));
        orderMap.add(new ExcelTitleName("orderCreateTime", "下单时间"));
        orderMap.add(new ExcelTitleName("supplierName", "供应商名称"));
        orderMap.add(new ExcelTitleName("userName", "买家名称"));
        orderMap.add(new ExcelTitleName("orderStatusName", "订单状态"));
        return orderMap;
    }

    public static List<ExcelTitleName> getOrderApplyDetail() {
        List<ExcelTitleName> orderMap = new ArrayList<>();
        orderMap.add(new ExcelTitleName("goodsName", "商品名称"));
        orderMap.add(new ExcelTitleName("productName", "产品名称"));
        orderMap.add(new ExcelTitleName("resNbr", "串码"));
        orderMap.add(new ExcelTitleName("stateName", "状态"));
        return orderMap;
    }




}
