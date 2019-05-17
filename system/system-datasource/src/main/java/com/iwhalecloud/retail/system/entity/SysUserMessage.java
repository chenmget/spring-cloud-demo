package com.iwhalecloud.retail.system.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * SysUserMessage
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("SYS_USER_MESSAGE")
@KeySequence(value = "seq_sys_user_message_id", clazz = String.class)
@ApiModel(value = "对应模型SYS_USER_MESSAGE, 对应实体SysUserMessage类")
public class SysUserMessage implements Serializable {
    /**表名常量*/
    public static final String TNAME = "SYS_USER_MESSAGE";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * messageId
  	 */
	@TableId
	@ApiModelProperty(value = "messageId")
  	private String messageId;
  	
  	/**
  	 * 记录用户Id
  	 */
	@ApiModelProperty(value = "记录用户Id")
  	private String userId;
  	
  	/**
  	 * 1：业务通知类（如待发货、待支付提醒）
	 * 2：业务告警类（如库存预警）
	 * 3：业务异常类（如订单异常等）
  	 */
	@ApiModelProperty(value = "1：业务通知类（如待发货、待支付提醒）2：业务告警类（如库存预警）3：业务异常类（如订单异常等）")
  	private java.lang.String messageType;
  	
  	/**
  	 * title
  	 */
	@ApiModelProperty(value = "title")
  	private java.lang.String title;
  	
  	/**
  	 * content
  	 */
	@ApiModelProperty(value = "content")
  	private java.lang.String content;
  	
  	/**
  	 * taskId
  	 */
	@ApiModelProperty(value = "taskId")
  	private java.lang.String taskId;
  	
  	/**
  	 * 记录消息展示的开始时间
  	 */
	@ApiModelProperty(value = "记录消息展示的开始时间")
  	private java.util.Date beginTime;
  	
  	/**
  	 * 记录消息展示的结束时间
  	 */
	@ApiModelProperty(value = "记录消息展示的结束时间")
  	private java.util.Date endTime;
  	
  	/**
  	 * 记录通知中的附件地址
  	 */
	@ApiModelProperty(value = "记录通知中的附件地址")
  	private java.lang.String fileUrl;
  	
  	/**
  	 * createTime
  	 */
	@ApiModelProperty(value = "createTime")
  	private java.util.Date createTime;
  	
  	/**
  	 * createUserId
  	 */
	@ApiModelProperty(value = "createUserId")
  	private java.lang.String createUserId;
  	
  	/**
  	 * updateTime
  	 */
	@ApiModelProperty(value = "updateTime")
  	private java.util.Date updateTime;
  	
  	/**
  	 * updateUserId
  	 */
	@ApiModelProperty(value = "updateUserId")
  	private java.lang.String updateUserId;
  	
  	/**
  	 * 0：无效
	 * 1：有效
  	 */
	@ApiModelProperty(value = "0：无效 1：有效")
  	private java.lang.String status;
  	
	/**
  	 * 0：未读 
	 * 1：已读
  	 */
	@ApiModelProperty(value = "readFlag")
  	private String readFlag;
	
	/**
  	 * 已读时间
	 * 
  	 */
	@ApiModelProperty(value = "readTime")
  	private java.util.Date readTime;
	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** messageId. */
		messageId("messageId","MESSAGE_ID"),
		
		/** 记录用户Id. */
		userId("userId","USER_ID"),
		
		/** 1：业务通知类（如待发货、待支付提醒）
		 * 2：业务告警类（如库存预警）
		 * 3：业务异常类（如订单异常等）.
		 * */
		messageType("messageType","MESSAGE_TYPE"),
		
		/** title. */
		title("title","TITLE"),
		
		/** content. */
		content("content","CONTENT"),
		
		/** taskId. */
		taskId("taskId","TASK_ID"),
		
		/** 记录消息展示的开始时间. */
		beginTime("beginTime","BEGIN_TIME"),
		
		/** 记录消息展示的结束时间. */
		endTime("endTime","END_TIME"),
		
		/** 记录通知中的附件地址. */
		fileUrl("fileUrl","FILE_URL"),
		
		/** createTime. */
		createTime("createTime","CREATE_TIME"),
		
		/** createUserId. */
		createUserId("createUserId","CREATE_USER_ID"),
		
		/** updateTime. */
		updateTime("updateTime","UPDATE_TIME"),
		
		/** updateUserId. */
		updateUserId("updateUserId","UPDATE_USER_ID"),
		
		/** 0：无效
		 * 1：有效. */
		status("status","STATUS"),

    	/**
      	 * 0：未读 
    	 * 1：已读
      	 */
    	readFlag("readFlag","READ_FLAG"),
    	
    	/**
      	 * 已读时间
      	 */
    	readTime("readTime","READ_TIME");
    	
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
