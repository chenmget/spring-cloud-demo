package com.iwhalecloud.retail.workflow.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by z on 2019/1/23.
 */
@Data
public class TaskItemListReq implements Serializable {

    @ApiModelProperty(value = "业务单号")
    private java.lang.String formId;
}
