package com.iwhalecloud.retail.order2b.dto.resquest.order;

import com.iwhalecloud.retail.order2b.dto.base.MRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class GetOrderUserInfoReq extends MRequest implements Serializable {

    @ApiModelProperty("订单id")
    private String orderId;

    @ApiModelProperty("订单项id")
    private String orderItemId;
}
