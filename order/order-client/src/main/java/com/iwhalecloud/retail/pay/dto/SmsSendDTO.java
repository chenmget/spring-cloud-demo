package com.iwhalecloud.retail.pay.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * smsSend
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型ES_SMS_SEND, 对应实体smsSend类")
public class SmsSendDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * smsId
  	 */
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
	
  	
}
