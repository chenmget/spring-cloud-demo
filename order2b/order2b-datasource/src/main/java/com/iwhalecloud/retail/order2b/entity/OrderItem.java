package com.iwhalecloud.retail.order2b.entity;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "商家编码")
    private String merchantCode;

    @ApiModelProperty(value = "商家名称")
    private String merchantName;

    @ApiModelProperty(value = "商家所属经营主体	")
    private String businessEntityName;


}
