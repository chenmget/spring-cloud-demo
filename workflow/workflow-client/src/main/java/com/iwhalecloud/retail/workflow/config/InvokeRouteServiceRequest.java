package com.iwhalecloud.retail.workflow.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class InvokeRouteServiceRequest<T> implements Serializable {
    /**
     * 业务id
     */
    private String businessId;

    @ApiModelProperty(value = "处理用户ID")
    private String handlerUserId;

    @ApiModelProperty(value = "处理用户名称")
    private String handlerUserName;

    @ApiModelProperty(value = "-1：无参数\n" +
            "1：json\n" +
            "2：字符串")
    private java.lang.Integer paramsType;

    @ApiModelProperty(value = "业务参数类型在启动流程的时候传入，便于在环节流转时直接获取需要的数据。")
    private java.lang.String paramsValue;

    /**
     * 业务数据
     */
    private T businessData;

}
