package com.iwhalecloud.retail.workflow.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 流程启动承载对象
 * @author Z
 * @date 2019/1/4
 */
@Data
public class ProcessStartReq implements Serializable {

    @ApiModelProperty(value = "流程ID")
    private String processId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "业务主单ID")
    private String formId;

    @ApiModelProperty(value = "申请用户ID：创建流程的用户ID")
    private String applyUserId;

    @ApiModelProperty(value = "申请人如果是商家，“申请人”就显示商家名称，如果是电信管理员则显示姓名")
    private String applyUserName;

    @ApiModelProperty(value = "下一环节处理用户")
    private List<HandlerUser> nextHandlerUser;

    @ApiModelProperty(value = "流程类型")
    private String taskSubType;

    @ApiModelProperty(value = "如果申请人是商家，该字段信息显示“地市+区县”信息，如果是电信人员则显示“岗位+部门”信息")
    private java.lang.String extends1;

}
