package com.iwhalecloud.retail.web.controller.b2b.goods.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
@Data
@ApiModel(value = "产品审核流程参数")
public class StartProductFlowReqDTO implements Serializable {
    @ApiModelProperty(value = "产品BaseID")
    @NotEmpty(message = "产品BaseID不能为空")
    private String productBaseId;
    @ApiModelProperty(value = "处理人建议")
    private String dealMsg;
}
