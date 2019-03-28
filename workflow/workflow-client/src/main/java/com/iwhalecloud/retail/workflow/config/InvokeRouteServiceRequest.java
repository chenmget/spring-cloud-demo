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

    /**
     * 业务数据
     */
    private T businessData;

}
