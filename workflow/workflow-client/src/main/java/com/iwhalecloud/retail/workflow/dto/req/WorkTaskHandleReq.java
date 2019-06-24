package com.iwhalecloud.retail.workflow.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Z
 * @date 2019/1/5
 */
@Data
public class WorkTaskHandleReq implements Serializable {

    /**
     * formId
     */
    @ApiModelProperty(value = "formId")
    private String formId;

    @ApiModelProperty(value = "处理用户ID")
    private String handlerUserId;

    @ApiModelProperty(value = "处理用户名称")
    private String handlerUserName;

    @ApiModelProperty("任务类型")
    private String taskType;

    @ApiModelProperty("任务子类型")
    private String taskSubType;
}
