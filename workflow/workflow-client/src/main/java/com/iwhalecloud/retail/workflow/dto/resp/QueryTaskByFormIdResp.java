package com.iwhalecloud.retail.workflow.dto.resp;

import com.iwhalecloud.retail.workflow.dto.TaskDTO;
import com.iwhalecloud.retail.workflow.dto.TaskItemDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author li.xinhang
 * @date 2019/4/1
 */
@Data
public class QueryTaskByFormIdResp {

    @ApiModelProperty(value = "业务ID")
    private String formId;

    @ApiModelProperty(value = "流程信息")
    private TaskDTO taskDTO;

    @ApiModelProperty(value = "工作项信息")
    private TaskItemDTO taskItemDTO;
}
