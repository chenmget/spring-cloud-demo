package com.iwhalecloud.retail.workflow.dto.resp;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author Z
 * @date 2019/1/8
 */
public class TaskListResp implements Serializable {
    //属性 begin
    /**
     * taskId
     */
    @ApiModelProperty(value = "任务ID")
    private java.lang.String taskId;

    /**
     * formId
     */
    @ApiModelProperty(value = "主单ID")
    private java.lang.String formId;

    /**
     * taskTitle
     */
    @ApiModelProperty(value = "标题")
    private java.lang.String taskTitle;

    /**
     * 流程：1
     工单：2
     */
    @ApiModelProperty(value = "流程大类[流程：1\n" +
            "工单：2")
    private java.lang.String taskType;

    /**
     * 任务类型为1流程时
     1010：厂家串码管理流程
     1020：厂家固网终端抽检流程
     1030：供应商库存管理流程
     1040：集采终端调货流程
     1050：地包商调货流程
     1060：零售商调货流程
     1070：零售商标签管理流程
     1080：零售商非交易类串码限额申请流程
     1090：零售商非交易类串码入库流程
     1100：装维人员补录串码入库流程
     1110：厂家产品管理流程
     1120：供应商经营权限管理流程
     1130：供应商商品上下架管理流程
     1140：营销活动配置流程
     1150：前置补贴管理流程
     1160：销售补贴管理流程
     1170：促销资源流程
     1180：价保流程
     1190：2B退货流程
     1200：移动终端换机流程
     1210：2B移动终端换机流程
     1220：固网终端备机入库流程
     1230：固网备机下发流程
     1240：2C固网售后置换流程
     1250：2C固网售后维修流程
     1260：售后返厂流程

     任务类型为2工单时
     2010：2B订单销售
     */
    @ApiModelProperty(value = "流程类型[ " +
            "      1010：厂家串码管理流程 " +
            "      1020：厂家固网终端抽检流程 " +
            "      1030：供应商库存管理流程 " +
            "      1040：集采终端调货流程 " +
            "      1050：地包商调货流程 " +
            "      1060：零售商调货流程 " +
            "      1070：零售商标签管理流程 " +
            "      1080：零售商非交易类串码限额申请流程 " +
            "      1090：零售商非交易类串码入库流程 " +
            "      1100：装维人员补录串码入库流程 " +
            "      1110：厂家产品管理流程 " +
            "      1120：供应商经营权限管理流程 " +
            "      1130：供应商商品上下架管理流程 " +
            "      1140：营销活动配置流程 " +
            "      1150：前置补贴管理流程 " +
            "      1160：销售补贴管理流程 " +
            "      1170：促销资源流程 " +
            "      1180：价保流程 " +
            "      1190：2B退货流程 " +
            "      1200：移动终端换机流程 " +
            "      1210：2B移动终端换机流程 " +
            "      1220：固网终端备机入库流程 " +
            "      1230：固网备机下发流程 " +
            "      1240：2C固网售后置换流程 " +
            "      1250：2C固网售后维修流程 " +
            "      1260：售后返厂流程 " +
            "      2010：2B订单销售]")
    private java.lang.String taskSubType;

    /**
     * 第一个发起流程的用户名称
     */
    @ApiModelProperty(value = "申请人")
    private java.lang.String createUserName;

    /**
     * 创建流程的时间
     */
    @ApiModelProperty(value = "申请时间")
    private java.util.Date createTime;

    @ApiModelProperty(value = "任务项ID  ")
    private java.lang.String taskItemId;

    /**
     * 当前节点名称  任务类型不为1（流程类时为空）
     */
    @ApiModelProperty(value = "当前环节")
    private java.lang.String curNodeName;

    /**
     * 状态  1：待领取
     2：待处理
     3：已处理
     */
    @ApiModelProperty(value = "状态  1：待领取\n" +
            "2：待处理\n" +
            "3：已处理")
    private java.lang.String itemStatus;

    /**
     * 任务分派时间  任务领取的时间
     */
    @ApiModelProperty(value = "分配时间")
    private java.util.Date assignTime;
}
