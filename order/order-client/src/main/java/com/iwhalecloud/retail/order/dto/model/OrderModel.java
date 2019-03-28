package com.iwhalecloud.retail.order.dto.model;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderModel implements Serializable {


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
    private String recommendId;

    private String shopId;//厅店id
    private  String shopName;//厅店名称

    private String shipTime;

    public String getPayTime() {
        return timeFormat(payTime);
    }

    public String getLastUpdate() {
        return timeFormat(lastUpdate);
    }

    public String getShipTime() {
        return timeFormat(shipTime);
    }

    public String getAfterTime() {
        return timeFormat(afterTime);
    }

    public String getCreateTime() {
        return timeFormat(createTime);
    }

    private String timeFormat(String payTime){
        if(StringUtils.isEmpty(payTime)){
            return payTime;
        }
        payTime=payTime.replace(".0","");
        return payTime;
    }
}
