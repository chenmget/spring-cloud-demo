package com.iwhalecloud.retail.order.dto.resquest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SendGoodsRequest extends UpdateOrderStatusRequest implements Serializable {

    @ApiModelProperty(value = "物流单号")
    private String logiNo;
    @ApiModelProperty(value = "物流公司")
    private String logiName;
    @ApiModelProperty(value = "是否需要物流 1需要 0 不需要")
    private String needShipping;
    @ApiModelProperty(value = "物流ID")
    private String logiId;
    @ApiModelProperty(value = "提货码")
    private String getCode;
    @ApiModelProperty(value = "发货数量")
    private String shipNum;



}
