package com.iwhalecloud.retail.order.model;

import com.iwhalecloud.retail.order.config.WhaleCloudDBKeySequence;
import lombok.Data;

import java.io.Serializable;

@Data
@WhaleCloudDBKeySequence(keySeqName = "itemId")
public class OrderItemEntity implements Serializable{

    private String itemId;
    private String orderId;
    private String goodsId;
    private String specId;
    private Integer num;
    private String shipNum;
    private String name;
    private Double price;
    private Double couponPrice;
    /**
     * 1:手机
     */
    private String itemType;
    private String productId;
    private String imei;
    private String createTime;
    private String lvId;

    private String specDesc;
    private String image;
    private Integer isChrr;
}
