package com.iwhalecloud.retail.workflow.dto.req;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 我的待办查询入参
 * @author Z
 * @date 2019/1/5
 */
@Data
public class TaskPageReq extends PageVO {

    @ApiModelProperty(value = "流程类型[" +
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
    private String taskSubType;


    /**
     * taskTitle
     */
    @ApiModelProperty(value = "任务标题")
    private String taskTitle;

    /**
     * 第一个发起流程的用户ID
     */
    @ApiModelProperty(value = "申请人ID")
    private String createUserId;

    @ApiModelProperty(value = "第一个发起流程的用户名称")
    private java.lang.String createUserName;

    @ApiModelProperty(value = "处理人ID",hidden = true)
    private String handlerUserId;

    @ApiModelProperty(value = "当前节点")
    private String curNodeName;

    @ApiModelProperty(value = "最后处理开始时间，格式YYYY-MM-DD")
    private String lastDealTimeStart;

    @ApiModelProperty(value = "最后处理结束时间，格式YYYY-MM-DD")
    private String lastDealTimeEnd;
}
