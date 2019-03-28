package com.iwhalecloud.retail.workflow.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Z
 * @date 2019/1/5
 */
@Data
public class TaskListReq implements Serializable {

    @ApiModelProperty(value = "处理人ID",hidden = true)
    private String handlerUserId;
}
