package com.iwhalecloud.retail.workflow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Task
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("wf_task")
@ApiModel(value = "对应模型wf_task, 对应实体Task类")
@KeySequence(value = "seq_wf_task_id",clazz = String.class)
public class Task implements Serializable {
    /**表名常量*/
    public static final String TNAME = "wf_task";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * taskId
  	 */
	@TableId
	@ApiModelProperty(value = "taskId")
  	private java.lang.String taskId;
  	
  	/**
  	 * formId
  	 */
	@ApiModelProperty(value = "formId")
  	private java.lang.String formId;
  	
  	/**
  	 * taskTitle
  	 */
	@ApiModelProperty(value = "任务标题")
  	private java.lang.String taskTitle;
  	
  	/**
  	 * 流程：1
            工单：2
  	 */
	@ApiModelProperty(value = "流程：1\n" +
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
            @ApiModelProperty(value = "任务类型为1流程时 " +
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

                    "任务类型为2工单时 " +
                    "2010：2B订单销售")
  	private java.lang.String taskSubType;
  	
  	/**
  	 * 如果task_type非流程类为-1
  	 */
	@ApiModelProperty(value = "如果task_type非流程类为-1")
  	private java.lang.String processId;
  	
  	/**
  	 * 1:处理中
            2：办结
  	 */
	@ApiModelProperty(value = "1:处理中\n" +
            "2：办结")
  	private java.lang.String taskStatus;
  	
  	/**
  	 * 第一个发起流程的用户ID
  	 */
	@ApiModelProperty(value = "第一个发起流程的用户ID")
  	private java.lang.String createUserId;
  	
  	/**
  	 * 第一个发起流程的用户名称
  	 */
	@ApiModelProperty(value = "第一个发起流程的用户名称，申请人如果是商家，“申请人”就显示商家名称，如果是电信管理员则显示姓名")
  	private java.lang.String createUserName;
  	
  	/**
  	 * 创建流程的时间
  	 */
	@ApiModelProperty(value = "创建流程的时间")
  	private java.util.Date createTime;

    @ApiModelProperty(value = "当前节点ID")
    private java.lang.String curNodeId;

    @ApiModelProperty(value = "当前节点名称")
    private java.lang.String curNodeName;

    @ApiModelProperty(value = "最后处理时间")
    private java.util.Date lastDealTime;

    @ApiModelProperty(value = "扩展信息1，如果申请人是商家，该字段信息显示“地市+区县”信息，如果是电信人员则显示“岗位+部门”信息")
    private java.lang.String extends1;

	@ApiModelProperty(value = "-1：无参数\n" +
			"1：json\n" +
			"2：字符串")
	private java.lang.Integer paramsType;

	@ApiModelProperty(value = "业务参数类型在启动流程的时候传入，便于在环节流转时直接获取需要的数据。")
	private java.lang.String paramsValue;
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** taskId. */
		taskId("taskId","task_id"),
		
		/** formId. */
		formId("formId","form_id"),
		
		/** taskTitle. */
		taskTitle("taskTitle","task_title"),
		
		/** 流程：1
            工单：2. */
		taskType("taskType","task_type"),
		
		/** 任务类型为1流程时
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
                    2010：2B订单销售. */
		taskSubType("taskSubType","task_sub_type"),
		
		/** 如果task_type非流程类为-1. */
		processId("processId","process_id"),
		
		/** 1:处理中
            2：办结. */
		taskStatus("taskStatus","task_status"),
		
		/** 第一个发起流程的用户ID. */
		createUserId("createUserId","create_user_id"),
		
		/** 第一个发起流程的用户名称. */
		createUserName("createUserName","create_user_name"),
		
		/** 创建流程的时间. */
		createTime("createTime","create_time");

		private String fieldName;
		private String tableFieldName;
		FieldNames(String fieldName, String tableFieldName){
			this.fieldName = fieldName;
			this.tableFieldName = tableFieldName;
		}

		public String getFieldName() {
			return fieldName;
		}

		public String getTableFieldName() {
			return tableFieldName;
		}
	}

}
