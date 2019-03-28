package com.iwhalecloud.retail.goods2b.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@Data
@ApiModel(value = "产品审核流程参数")
public class StartProductFlowReq  implements Serializable {
    @ApiModelProperty(value = "产品BaseId")
    @NotEmpty(message = "产品BaseId不能为空")
    private String productBaseId;
    @ApiModelProperty(value = "处理人")
    private String dealer;

    @ApiModelProperty(value = "处理人建议")
    private String dealMsg;

    @ApiModelProperty(value = "产品名称")
    private String productName;


}
