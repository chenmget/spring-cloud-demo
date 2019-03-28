package com.iwhalecloud.retail.pay.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * smsSend
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("ES_SMS_SEND")
@ApiModel(value = "对应模型ES_SMS_SEND, 对应实体smsSend类")
public class SmsSend implements Serializable {
    /**表名常量*/
    public static final String TNAME = "ES_SMS_SEND";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * smsId
  	 */
	@TableId(type = IdType.ID_WORKER_STR)
	@ApiModelProperty(value = "smsId")
  	private java.lang.String smsId;
  	
  	/**
  	 * monitorSystem
  	 */
	@ApiModelProperty(value = "monitorSystem")
  	private java.lang.String monitorSystem;
  	
  	/**
  	 * smsUser
  	 */
	@ApiModelProperty(value = "smsUser")
  	private java.lang.String smsUser;
  	
  	/**
  	 * smsType
  	 */
	@ApiModelProperty(value = "smsType")
  	private java.lang.String smsType;
  	
  	/**
  	 * sendTime
  	 */
	@ApiModelProperty(value = "sendTime")
  	private java.util.Date sendTime;
  	
  	/**
  	 * smsContent
  	 */
	@ApiModelProperty(value = "smsContent")
  	private java.lang.String smsContent;
  	
  	/**
  	 * sendFlag
  	 */
	@ApiModelProperty(value = "sendFlag")
  	private java.lang.String sendFlag;
  	
  	/**
  	 * sourceFrom
  	 */
	@ApiModelProperty(value = "sourceFrom")
  	private java.lang.String sourceFrom;
  	
  	/**
  	 * reserve0
  	 */
	@ApiModelProperty(value = "reserve0")
  	private java.lang.String reserve0;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** smsId. */
		smsId("smsId","SMS_ID"),
		
		/** monitorSystem. */
		monitorSystem("monitorSystem","MONITOR_SYSTEM"),
		
		/** smsUser. */
		smsUser("smsUser","SMS_USER"),
		
		/** smsType. */
		smsType("smsType","SMS_TYPE"),
		
		/** sendTime. */
		sendTime("sendTime","SEND_TIME"),
		
		/** smsContent. */
		smsContent("smsContent","SMS_CONTENT"),
		
		/** sendFlag. */
		sendFlag("sendFlag","SEND_FLAG"),
		
		/** sourceFrom. */
		sourceFrom("sourceFrom","SOURCE_FROM"),
		
		/** reserve0. */
		reserve0("reserve0","RESERVE0");

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
