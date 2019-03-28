package com.iwhalecloud.retail.order.dto.resquest;

import com.iwhalecloud.retail.order.dto.base.ModifyBaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateOrderStatusRequest extends ModifyBaseRequest implements Serializable {

    @ApiModelProperty(value = "orderId")
    private String orderId;


    @ApiModelProperty(value = "C支付;H发;J收货确认;QX取消;PJ评价;SC删除")
    private String flowType;

}
