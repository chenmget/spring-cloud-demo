package com.iwhalecloud.retail.order2b.dto.model.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 预售订单信息
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年03月04日
 */
@Data
public class AdvanceOrderDTO implements Serializable {
    @ApiModelProperty("主键Id")
    private String id;

    @ApiModelProperty("订单号")
    private String orderId;

    @ApiModelProperty("预售活动ID")
    private String activityId;

    @ApiModelProperty("定金")
    private Double advanceAmount;

    @ApiModelProperty("定金支付开始时间")
    private String advancePayBegin;

    @ApiModelProperty("定金支付结束时间")
    private String advancePayEnd;

    @ApiModelProperty("定金支付类型")
    private String advancePaymentType;

    @ApiModelProperty("定金付款情况")
    private String advancePayStatus;

    @ApiModelProperty("定金支付金额")
    private String advancePayMoney;

    @ApiModelProperty("定金支付时间")
    private String advancePayTime;

    @ApiModelProperty("定金支付方式")
    private String advancePayType;

    @ApiModelProperty("定金支付流水号")
    private String advancePayCode;

    @ApiModelProperty("尾款")
    private Double restAmount;

    @ApiModelProperty("尾款支付开始时间")
    private String restPayBegin;

    @ApiModelProperty("尾款支付结束时间")
    private String restPayEnd;

    @ApiModelProperty("尾款付款情况")
    private String restPayStatus;

    @ApiModelProperty("尾款支付类型")
    private String restPaymentType;

    @ApiModelProperty("尾款支付金额")
    private String restPayMoney;

    @ApiModelProperty("尾款支付时间")
    private String restPayTime;

    @ApiModelProperty("尾款支付方式")
    private String restPayType;

    @ApiModelProperty("尾款支付流水号")
    private String restPayCode;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建人ID")
    private String createUserId;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("请求来源")
    private String sourceFrom;
}
