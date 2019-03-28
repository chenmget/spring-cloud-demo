package com.iwhalecloud.retail.order2b.dto.resquest.order;

import com.iwhalecloud.retail.order2b.dto.base.MRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateOrderStatusRequest extends MRequest implements Serializable {

    @ApiModelProperty(value = "orderId")
    private String orderId;


    @ApiModelProperty(value = "")
    private String flowType;

    @ApiModelProperty(value = "1：同意，2：不同意")
    private String confirmType;

    private String orderCat;


}
