package com.iwhalecloud.retail.order.dto.resquest.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDetailDTO implements Serializable{

    @ApiModelProperty(value = "子订单id")
    private String oldOrderItemId;
    @ApiModelProperty(value = "退货数量")
    private Integer num;
}
