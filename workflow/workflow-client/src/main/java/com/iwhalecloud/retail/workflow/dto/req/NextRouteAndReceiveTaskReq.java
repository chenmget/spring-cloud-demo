package com.iwhalecloud.retail.workflow.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mzl
 * @date 2019/2/28
 */
@Data
public class NextRouteAndReceiveTaskReq implements Serializable {

    private static final long serialVersionUID = -6652855768387182177L;

    @ApiModelProperty(value = "formId")
    private String formId;

    @ApiModelProperty(value = "当前节点处理用户ID",hidden = true)
    private String handlerUserId;

    @ApiModelProperty(value = "当前节点处理用户名称",hidden = true)
    private String handlerUserName;

    @ApiModelProperty(value = "处理意见")
    private String handlerMsg;
}
