package com.iwhalecloud.retail.workflow.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * @author Z
 * e 2019/1/5
 */
@Data
public class RouteNextReq implements Serializable {

    @NotEmpty(message = "任务ID不能为空")
    @ApiModelProperty(value = "任务ID")
    private String taskId;

    @ApiModelProperty(value = "任务项ID",hidden = true)
    private String taskItemId;

    @ApiModelProperty(value = "下一个节点ID")
    private String nextNodeId;

    @ApiModelProperty(value = "下一步路由ID")
    private String routeId;

    @ApiModelProperty(value = "当前节点处理用户ID",hidden = true)
    private String handlerUserId;

    @ApiModelProperty(value = "当前节点处理用户名称",hidden = true)
    private String handlerUserName;

    @ApiModelProperty(value = "处理意见")
    private String handlerMsg;

    @ApiModelProperty(value = "下一环节处理用户")
    private List<HandlerUser> nextHandlerUser;

}
