package com.iwhalecloud.retail.order2b.dto.resquest.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class THSendGoodsRequest extends UpdateApplyStatusRequest implements Serializable {

    @ApiModelProperty(value = "物流单号")
    private String logiNo;
    @ApiModelProperty(value = "物流公司")
    private String logiName;
    @ApiModelProperty(value = "物流ID")
    private String logiId;

}
