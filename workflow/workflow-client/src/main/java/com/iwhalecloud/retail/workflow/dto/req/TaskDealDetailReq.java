package com.iwhalecloud.retail.workflow.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 待办任务处理详情请求类
 * @author Z
 * @date 2019/1/11
 */
@Data
public class TaskDealDetailReq implements Serializable {

    @ApiModelProperty(value = "任务项ID")
    private String taskItemId;

    @ApiModelProperty(value = "任务ID")
    private String taskId;

    @ApiModelProperty(value = "处理用户ID")
    private String userId;

    @ApiModelProperty(value = "处理用户名称")
    private String userName;
}
