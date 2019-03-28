package com.iwhalecloud.retail.order.dto.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderCollectionModel implements Serializable {

    @ApiModelProperty(value = "订单号")
    private String order_id;

    @ApiModelProperty(value = "付款状态 0未付款，1已付款")
    private String payStatus;

    @ApiModelProperty(value = "发货信息 0 未发货，1已发货")
    private String shipStatus;

    private String payType;

    @ApiModelProperty(value = "订单状态")
    private String status;

    //供货商id
    private String shipUserId;

    //操作类型 C,J,H,QX,SC
    private String flowType;

    //手机提货码
    private String getGoodsCode;


    private Double paymoney;

    //支付流水号
    private String payCode;
}
