package com.iwhalecloud.retail.order2b.entity;

import com.iwhalecloud.retail.order2b.config.TableTimeValidate;
import com.iwhalecloud.retail.order2b.config.WhaleCloudDBKeySequence;
import lombok.Data;

@Data
public class OrderItem {

    private String lanId;

    private String itemId;
    private String orderId;
    private String goodsId;
    private Integer num;
    private Integer deliveryNum;
    private Integer receiveNum;
    private Integer returnNum;
    private Integer replaceNum;
    private String goodsName;
    private String productName;
    private Double price;
    private Double couponPrice;
    private String unit;
    private String sourceFrom;
    private String itemType;
    private String productId;
    private String createTime;
    private String specId;
    private String specDesc;
    private String image;

    private String sn;
    private String brandName;


}
