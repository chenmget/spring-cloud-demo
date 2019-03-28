package com.iwhalecloud.retail.goods2b.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@Data
@ApiModel(value = "产品审核下一步流程参数")
public class NextProductFlowReq implements Serializable {
    @ApiModelProperty(value = "产品ID")
    @NotEmpty(message = "产品BaseID不能为空")
    private String productBaseId;
    @ApiModelProperty(value = "处理人")
    private String dealer;

    @ApiModelProperty(value = "处理人建议")
    private String dealMsg;

}
