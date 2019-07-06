package com.iwhalecloud.retail.order2b.dto.resquest.pay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ToBestPayReq implements Serializable {

    @ApiModelProperty("订单号")
    String orderId;

    @ApiModelProperty("订单金额")
    String orderAmount;

    @ApiModelProperty("收款账号")
    String orgLoginCode;

    @ApiModelProperty("支付类型")
    String operationType;

    @ApiModelProperty("网络")
    String net;
}
