package com.iwhalecloud.retail.order.model;

import lombok.Data;

@Data
public class OrderUpdateAttrEntity {

    //订单号
    private String orderId;

    //付款状态 0未付款，1已付款
    private String payStatus;

    //发货信息 0 未发货，1已发货
    private String shipStatus;

    //付款方式 在线支付，货到付款
    private String payType;

    //订单状态
    private String status;

    //供货商id
    private String shipUserId;

    //操作类型 C,J,H,QX,SC
    private String flowType;

    //手机提货码
    private String getGoodsCode;

    private Double paymoney;

    private String payCode;

}
