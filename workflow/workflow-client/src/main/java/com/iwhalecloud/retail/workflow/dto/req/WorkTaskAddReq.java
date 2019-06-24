package com.iwhalecloud.retail.workflow.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Z
 * @date 2019/1/5
 */
@Data
public class WorkTaskAddReq implements Serializable {

    /**
     * formId
     */
    @ApiModelProperty(value = "formId")
    private java.lang.String formId;

    /**
     * taskTitle
     */
    @ApiModelProperty(value = "taskTitle")
    private java.lang.String taskTitle;

    @ApiModelProperty("任务类型")
    private String taskType;

    @ApiModelProperty("任务子类型")
    private String taskSubType;

//    /**
//     * 流程：1
//     工单：2
//     */
//    @ApiModelProperty(value = "流程：1\n" +
//            "工单：2")
//    private java.lang.String taskType;
//
//    @ApiModelProperty(value = "任务类型为1流程时 " +
//            "      1010：厂家串码管理流程 " +
//            "      1020：厂家固网终端抽检流程 " +
//            "      1030：供应商库存管理流程 " +
//            "      1040：集采终端调货流程 " +
//            "      1050：地包商调货流程 " +
//            "      1060：零售商调货流程 " +
//            "      1070：零售商标签管理流程 " +
//            "      1080：零售商非交易类串码限额申请流程 " +
//            "      1090：零售商非交易类串码入库流程 " +
//            "      1100：装维人员补录串码入库流程 " +
//            "      1110：厂家产品管理流程 " +
//            "      1120：供应商经营权限管理流程 " +
//            "      1130：供应商商品上下架管理流程 " +
//            "      1140：营销活动配置流程 " +
//            "      1150：前置补贴管理流程 " +
//            "      1160：销售补贴管理流程 " +
//            "      1170：促销资源流程 " +
//            "      1180：价保流程 " +
//            "      1190：2B退货流程 " +
//            "      1200：移动终端换机流程 " +
//            "      1210：2B移动终端换机流程 " +
//            "      1220：固网终端备机入库流程 " +
//            "      1230：固网备机下发流程 " +
//            "      1240：2C固网售后置换流程 " +
//            "      1250：2C固网售后维修流程 " +
//            "      1260：售后返厂流程 " +
//
//            "任务类型为2工单时 " +
//            "2010：2B订单销售")
//    private java.lang.String taskSubType;
//
//    @ApiModelProperty(value = "如果task_type非流程类为-1")
//    private java.lang.String processId;

    /**
     * 第一个发起流程的用户ID
     */
    @ApiModelProperty(value = "第一个发起流程的用户ID")
    private java.lang.String createUserId;

    /**
     * 第一个发起流程的用户名称
     */
    @ApiModelProperty(value = "申请人如果是商家，“申请人”就显示商家名称，如果是电信管理员则显示姓名")
    private java.lang.String createUserName;

    @ApiModelProperty(value = "上一步骤名称")
    private java.lang.String preNodeName;

    @ApiModelProperty(value = "下一个步骤名称")
    private java.lang.String nextNodeName;

    @ApiModelProperty(value = "如果申请人是商家，该字段信息显示“地市+区县”信息，如果是电信人员则显示“岗位+部门”信息")
    private java.lang.String extends1;

    @ApiModelProperty(value = "可以处理工单的用户集合")
    private List<UserInfo> handlerUsers;


    @Data
    public static class UserInfo implements Serializable {
        @ApiModelProperty(value = "用户ID")
        private String userId;
        @ApiModelProperty(value = "用户名称")
        private String userName;
    }

}
