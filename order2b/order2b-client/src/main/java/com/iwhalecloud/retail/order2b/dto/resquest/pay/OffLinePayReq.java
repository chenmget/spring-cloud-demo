package com.iwhalecloud.retail.order2b.dto.resquest.pay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OffLinePayReq implements Serializable {

    @ApiModelProperty("订单号")
    private String orderId;

    @ApiModelProperty("支付金额，单位：分")
    private String orderAmount;

    @ApiModelProperty("支付状态：0 订单生成，1 支付中，2 支付成功， 3 业务处理完成")
    private String payStatus;

    @ApiModelProperty("操作类型 用于区分付费/退费：1001  收费，1002  退费，1003  预付费,  DJZF  定金支付,  WKZF  尾款支付,C  支付")
    private String operationType;

    @ApiModelProperty("登记请求信息")
    private String payData;

    @ApiModelProperty("加密后的请求信息")
    private String payDataMd;

    /**
     * 接收方银行编码
     */
    @ApiModelProperty(value = "接收方银行编码")
    private java.lang.String recBankId;

    /**
     * 接收方银行账号
     */
    @ApiModelProperty(value = "接收方银行账号")
    private java.lang.String recAccount;

    /**
     * 接收方账号名称
     */
    @ApiModelProperty(value = "接收方账号名称")
    private java.lang.String recAccountName;

}
