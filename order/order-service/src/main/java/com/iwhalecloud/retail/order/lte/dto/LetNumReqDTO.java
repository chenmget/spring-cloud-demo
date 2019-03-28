package com.iwhalecloud.retail.order.lte.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class LetNumReqDTO implements Serializable {

    @ApiModelProperty(value = "操作类型：号码预占，号码释放")
    private String handlerType;

    @ApiModelProperty(value = "入网号码")
    private String nerPhone;
}
