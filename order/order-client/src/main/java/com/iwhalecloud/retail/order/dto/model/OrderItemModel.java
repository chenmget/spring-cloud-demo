package com.iwhalecloud.retail.order.dto.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderItemModel implements Serializable {
    private String itemId;
    private String orderId;
    private String goodsId;
    private String specId;
    private Integer num;
    private String shipNum;
    private String name;
    private Double price;
    private Double couponPrice;
    private String itemType;
    private String productId;
    private String imei;
    private String createTime;
    private String lvId;

    private String specDesc;
    private String image;

    private Integer isChrr;
}
