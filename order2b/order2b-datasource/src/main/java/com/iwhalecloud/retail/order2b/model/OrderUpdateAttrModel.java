package com.iwhalecloud.retail.order2b.model;

import lombok.Data;

@Data
public class OrderUpdateAttrModel {

    //订单号
    private String orderId;

    //付款状态 0未付款，1已付款
    private String payStatus;

    //发货信息 0 未发货，1已发货
    private String shipStatus;

    private String payType;

    //订单状态
    private String status;

    //供货商id
    private String shipUserId;

    //操作类型 C,J,H,QX,SC
    private String flowType;

    //手机提货码
    private String getGoodsCode;

    private Double payMoney;

    private String payTime;

    private String payCode;

    private String shipTime;

    private String lanId;

    private String receiveTime;


}
