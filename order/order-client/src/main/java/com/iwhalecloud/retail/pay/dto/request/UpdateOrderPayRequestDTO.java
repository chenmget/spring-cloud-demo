package com.iwhalecloud.retail.pay.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateOrderPayRequestDTO implements Serializable {
    //属性 begin
    /**
     * transactionId
     */
    @ApiModelProperty(value = "transactionId")
    private java.lang.String transactionId;

    /**
     * 状态:插入_1000;支付成功_1100;支付失败_1200 :
     */
    @ApiModelProperty(value = "状态:插入_1000;支付成功_1100;支付失败_1200 :")
    private java.lang.String status;

    /**
     * 支付单id
     */
    @ApiModelProperty(value = "支付单id 支付平台生成的支付单主ID，业务渠道保存")
    private String payId;

    /**
     * 支付平台请求支付渠道交易流水
     */
    @ApiModelProperty(value = "支付平台请求支付渠道交易流水")
    private String transSeq;

    /**
     * 支付渠道流水号
     */
    @ApiModelProperty(value = "支付渠道流水号")
    private String upTransSeq;

    /**
     * 支付渠道请求银行流水号
     */
    @ApiModelProperty(value = "支付渠道请求银行流水号")
    private String payChanlReqTransSeq;

    /**
     * 银行流水号
     */
    @ApiModelProperty(value = "银行流水号")
    private String bankTransSeq;
}
