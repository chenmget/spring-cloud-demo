package com.iwhalecloud.retail.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * SysUserMessage
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型SYS_USER_MESSAGE, 对应实体SysUserMessage类")
public class SysUserMessageDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * messageId
  	 */
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
  	private String messageType;

	/**
  	 * title
  	 */
	@ApiModelProperty(value = "title")
  	private String title;

	/**
  	 * content
  	 */
	@ApiModelProperty(value = "content")
  	private String content;

	/**
  	 * taskId
  	 */
	@ApiModelProperty(value = "taskId")
  	private String taskId;

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
  	private String fileUrl;

	/**
  	 * createTime
  	 */
	@ApiModelProperty(value = "createTime")
  	private java.util.Date createTime;

	/**
  	 * createUserId
  	 */
	@ApiModelProperty(value = "createUserId")
  	private String createUserId;

	/**
  	 * updateTime
  	 */
	@ApiModelProperty(value = "updateTime")
  	private java.util.Date updateTime;

	/**
  	 * updateUserId
  	 */
	@ApiModelProperty(value = "updateUserId")
  	private String updateUserId;

	/**
  	 * 0：无效
	 * 1：有效
  	 */
	@ApiModelProperty(value = "0：无效 1：有效")
  	private String status;
	
  	
}
