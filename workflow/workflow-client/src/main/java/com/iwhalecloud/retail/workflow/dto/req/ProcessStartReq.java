package com.iwhalecloud.retail.workflow.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 流程启动承载对象
 * @author Z
 * @date 2019/1/4
 */
@Setter
@Getter
@NoArgsConstructor
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

    @ApiModelProperty(value = "-1：无参数\n" +
            "1：json\n" +
            "2：字符串")
    private java.lang.Integer paramsType;

    @ApiModelProperty(value = "业务参数类型在启动流程的时候传入，便于在环节流转时直接获取需要的数据。")
    private java.lang.String paramsValue;


    public ProcessStartReq(String processId, String title, String formId, String applyUserId, String applyUserName, String taskSubType, String extends1) {
        this.processId = processId;
        this.title = title;
        this.formId = formId;
        this.applyUserId = applyUserId;
        this.applyUserName = applyUserName;
        this.taskSubType = taskSubType;
        this.extends1 = extends1;
    }
}
