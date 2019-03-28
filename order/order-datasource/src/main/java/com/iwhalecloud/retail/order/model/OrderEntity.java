package com.iwhalecloud.retail.order.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderEntity implements Serializable {

    private String orderId;
    private String sn;
    private String memberId;
    private String status;
    private String payStatus;
    private String shipStatus;
    private String shippingType;

    private String createTime;
    private String shipName;
    private String shipAddr;
    private String shipZip;
    private String shipEmail;
    private String shipMobile;

    private String goodsAmount;
    private String orderAmount;
    private String shippingAmount;
    private String goodsNum;
    private String remark;



    private String orderType;

    private String payTime;

    private String invoiceType;
    private String invoiceTitleDesc;
    private String invoiceContent;
    private String invoiceTitle;
    private String invoiceDetail;
    private String lastUpdate;

    //分销商 name
    private String sourceShopName;
    private String userId;

    private String payType;

    //供应商
    private String shipUserId;
    private String needShipping;
    private String lvId;

    //提货码
    private String getGoodsCode;


    private String batchId;
    private String shipTel;
    //支付金额
    private String paymoney;
    //绑定类型（）
    private String bindType;

    //订单补录
    private String replenishOrderId;//订单号
    private String afterTime; //时间
    private String afterRemark;  //备注

    /**
     * 优惠券
     */
    private String couponCode; //优惠券id
    private String couponDesc; //描述
    private Double orderCoupon; //优惠价格
    private String recommendId; //揽装录入ID

    private String shopId;//厅店id
    private  String shopName;//厅店名称

}
