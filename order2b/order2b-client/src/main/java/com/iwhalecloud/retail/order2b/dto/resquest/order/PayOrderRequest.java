package com.iwhalecloud.retail.order2b.dto.resquest.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PayOrderRequest extends UpdateOrderStatusRequest implements Serializable {

    @ApiModelProperty(value = "支付金额")
    private Double paymoney;

    private String payCode;

    @ApiModelProperty("支付方式：1翼支付,3线下支付")
    private String payType;

    @ApiModelProperty("支付凭证")
    private String refundImgUrl;

    @ApiModelProperty("申请单号")
    private String orderApplyId;

    @ApiModelProperty("支付备注")
    private String payRemark;

    private String payStatus;

    @ApiModelProperty("接收方银行编码")
    private String recBankId;
    @ApiModelProperty("接收方银行账号")
    private String recAccount;
    @ApiModelProperty("接收方账号名称")
    private String recAccountName;
}
