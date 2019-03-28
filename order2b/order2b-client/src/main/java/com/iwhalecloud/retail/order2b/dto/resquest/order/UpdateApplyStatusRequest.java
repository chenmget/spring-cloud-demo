package com.iwhalecloud.retail.order2b.dto.resquest.order;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateApplyStatusRequest extends UpdateOrderStatusRequest implements Serializable {

    @ApiModelProperty("申请单号")
    private String orderApplyId;

    @ApiModelProperty("备注")
    private String remark;


}
