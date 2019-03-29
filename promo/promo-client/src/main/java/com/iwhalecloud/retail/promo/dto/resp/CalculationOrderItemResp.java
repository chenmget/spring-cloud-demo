package com.iwhalecloud.retail.promo.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/25 20:43
 * 操作后账户余额=入账金额+剩余余额(未操作前的余额)
 */
@Data
public class CalculationOrderItemResp implements Serializable {

    @ApiModelProperty(value = "入账金额")
    private String amount;
    @ApiModelProperty(value = "操作后账户余额")
    private String balance;
    @ApiModelProperty(value = "剩余余额")
    private String curAmount;

    @ApiModelProperty(value = "订单ID")
    private String orderId;
    @ApiModelProperty(value = "订单项ID")
    private String orderItemId;

}
