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
    @ApiModelProperty(value = "-1：无参数\n" +
            "1：json\n" +
            "2：字符串")
    private java.lang.Integer paramsType;

    @ApiModelProperty(value = "业务参数类型在启动流程的时候传入，便于在环节流转时直接获取需要的数据。")
    private java.lang.String paramsValue;

}
