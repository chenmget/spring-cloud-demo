package com.iwhalecloud.retail.workflow.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * TaskItem
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("wf_task_item")
@ApiModel(value = "对应模型wf_task_item, 对应实体TaskItem类")
@KeySequence(value = "seq_wf_task_item_id",clazz = String.class)
public class TaskItem implements Serializable {
    /**表名常量*/
    public static final String TNAME = "wf_task_item";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 任务项ID  
  	 */
	@TableId
	@ApiModelProperty(value = "任务项ID  ")
  	private String taskItemId;
  	
  	/**
  	 * 任务ID  
  	 */
	@ApiModelProperty(value = "任务ID  ")
  	private String taskId;
  	
  	/**
  	 * 任务类型  流程：1
            工单：2
  	 */
	@ApiModelProperty(value = "任务类型  流程：1\n" +
            "工单：2")
  	private String taskType;
  	
  	/**
  	 * 路由ID  任务类型不为1（流程类时为-1）
  	 */
	@ApiModelProperty(value = "路由ID  任务类型不为1（流程类时为-1）")
  	private String routeId;
  	
  	/**
  	 * 路由名称  任务类型不为1（流程类时为空）
  	 */
	@ApiModelProperty(value = "路由名称  任务类型不为1（流程类时为空）")
  	private String routeName;
  	
  	/**
  	 * 上一节点  任务类型不为1（流程类时为-1）
  	 */
	@ApiModelProperty(value = "上一节点  任务类型不为1（流程类时为-1）")
  	private String preNodeId;
  	
  	/**
  	 * 上一节点名称  任务类型不为1（流程类时为空）
  	 */
	@ApiModelProperty(value = "上一节点名称  任务类型不为1（流程类时为空）")
  	private String preNodeName;
  	
  	/**
  	 * 当前节点  任务类型不为1（流程类时为-1）
  	 */
	@ApiModelProperty(value = "当前节点  任务类型不为1（流程类时为-1）")
  	private String curNodeId;
  	
  	/**
  	 * 当前节点名称  任务类型不为1（流程类时为空）
  	 */
	@ApiModelProperty(value = "当前节点名称  任务类型不为1（流程类时为空）")
  	private String curNodeName;
  	
  	/**
  	 * 状态  1：待领取
            2：待处理
            3：已处理
  	 */
	@ApiModelProperty(value = "状态  1：待领取\n" +
            "2：待处理\n" +
            "3：已处理")
  	private String itemStatus;
  	
  	/**
  	 * 创建时间  当前记录的创建时间
  	 */
	@ApiModelProperty(value = "创建时间  当前记录的创建时间")
  	private java.util.Date createTime;
  	
  	/**
  	 * 任务分派时间  任务领取的时间
  	 */
	@ApiModelProperty(value = "任务分派时间  任务领取的时间")
  	private java.util.Date assignTime;
  	
  	/**
  	 * 任务办理时间  任务处理完的时间
  	 */
	@ApiModelProperty(value = "任务办理时间  任务处理完的时间")
  	private java.util.Date handlerTime;
  	
  	/**
  	 * 任务处理人  
  	 */
	@ApiModelProperty(value = "任务处理人  ")
  	private String handlerUserId;
  	
  	/**
  	 * 任务处理人名称  
  	 */
	@ApiModelProperty(value = "任务处理人名称  ")
  	private String handlerUserName;
  	
  	/**
  	 * 处理意见  
  	 */
	@ApiModelProperty(value = "处理意见  ")
  	private String handlerMsg;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 任务项ID  . */
		taskItemId("taskItemId","task_item_id"),
		
		/** 任务ID  . */
		taskId("taskId","task_id"),
		
		/** 任务类型  流程：1
            工单：2. */
		taskType("taskType","task_type"),
		
		/** 路由ID  任务类型不为1（流程类时为-1）. */
		routeId("routeId","route_id"),
		
		/** 路由名称  任务类型不为1（流程类时为空）. */
		routeName("routeName","route_name"),
		
		/** 上一节点  任务类型不为1（流程类时为-1）. */
		preNodeId("preNodeId","pre_node_id"),
		
		/** 上一节点名称  任务类型不为1（流程类时为空）. */
		preNodeName("preNodeName","pre_node_name"),
		
		/** 当前节点  任务类型不为1（流程类时为-1）. */
		curNodeId("curNodeId","cur_node_id"),
		
		/** 当前节点名称  任务类型不为1（流程类时为空）. */
		curNodeName("curNodeName","cur_node_name"),
		
		/** 状态  1：待领取
            2：待处理
            3：已处理. */
		itemStatus("itemStatus","item_status"),
		
		/** 创建时间  当前记录的创建时间. */
		createTime("createTime","create_time"),
		
		/** 任务分派时间  任务领取的时间. */
		assignTime("assignTime","assign_time"),
		
		/** 任务办理时间  任务处理完的时间. */
		handlerTime("handlerTime","handler_time"),
		
		/** 任务处理人  . */
		handlerUserId("handlerUserId","handler_user_id"),
		
		/** 任务处理人名称  . */
		handlerUserName("handlerUserName","handler_user_name"),
		
		/** 处理意见  . */
		handlerMsg("handlerMsg","handler_msg");

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
