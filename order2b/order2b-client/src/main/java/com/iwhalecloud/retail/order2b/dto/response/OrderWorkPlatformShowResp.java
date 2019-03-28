package com.iwhalecloud.retail.order2b.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "订单现量统计响应类")
public class OrderWorkPlatformShowResp implements Serializable {

    @ApiModelProperty(value = "待卖家确认")
    private int buyerConfirmCount;

    @ApiModelProperty(value = "待我付款")
    private int buyerPayCount;

    @ApiModelProperty(value = "待卖家发货")
    private int buyerSendCount;

    @ApiModelProperty(value = "待我收货")
    private int buyerReceiveCount;

    @ApiModelProperty(value = "待我评价")
    private int buyerCommentCount;

    @ApiModelProperty(value = "退换货")
    private int buyerReturnCount;

    @ApiModelProperty(value = "当日交易数量")
    private int buyerOrderCountToday;

    @ApiModelProperty(value = "当日交易额")
    private double buyerOrderAmountToday;

    @ApiModelProperty(value = "当月交易数量")
    private int buyerOrderCountThisMonth;

    @ApiModelProperty(value = "当月交易额")
    private double buyerOrderAmountThisMonth;

    @ApiModelProperty(value = "当年交易数量")
    private int buyerOrderCountThisYear;

    @ApiModelProperty(value = "当年交易额")
    private double buyerOrderAmountThisYear;

    @ApiModelProperty(value = "待我确认")
    private int sellerConfirmCount;

    @ApiModelProperty(value = "待买家付款")
    private int sellerPayCount;

    @ApiModelProperty(value = "待我发货")
    private int sellerSendCount;

    @ApiModelProperty(value = "待买家收货")
    private int sellerReceiveCount;

    @ApiModelProperty(value = "售后")
    private int sellerDoneCount;

    @ApiModelProperty(value = "当日销售量")
    private int sellerOrderCountToday;

    @ApiModelProperty(value = "当日销售额")
    private double sellerOrderAmountToday;

    @ApiModelProperty(value = "当月销售量")
    private int sellerOrderCountThisMonth;

    @ApiModelProperty(value = "当月销售额")
    private double sellerOrderAmountThisMonth;

    @ApiModelProperty(value = "当年销售量")
    private int sellerOrderCountThisYear;

    @ApiModelProperty(value = "当年销售额")
    private double sellerOrderAmountThisYear;

    @ApiModelProperty(value = "共计销售量")
    private int sellerOrderCountTotal;

    @ApiModelProperty(value = "共计销售额")
    private double sellerOrderAmountTotal;
}
