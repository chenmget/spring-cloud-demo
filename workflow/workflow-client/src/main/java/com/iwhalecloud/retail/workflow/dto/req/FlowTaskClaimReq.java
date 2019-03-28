package com.iwhalecloud.retail.workflow.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * 流程任务领取
 * @author Z
 * @date 2019/1/5
 */
@Data
public class FlowTaskClaimReq implements Serializable {

    @ApiModelProperty(value = "任务项ID")
    private String taskItemId;

    @ApiModelProperty(value = "领取用户ID")
    private String userId;

    @ApiModelProperty(value = "领取用户名称")
    private String userName;

    @NotEmpty(message = "任务ID不能为空")
    @ApiModelProperty(value = "任务ID")
    private String taskId;

}
