package com.iwhalecloud.retail.order.dto.resquest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PayOrderRequest extends UpdateOrderStatusRequest implements Serializable {

    @ApiModelProperty(value = "支付金额")
    private Double paymoney;

    private String payCode;
}
