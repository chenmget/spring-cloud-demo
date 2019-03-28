package com.iwhalecloud.retail.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * NoticeUser
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型sys_notice_user, 对应实体NoticeUser类")
public class NoticeUserDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * ID
  	 */
	@ApiModelProperty(value = "ID")
  	private String id;
	
	/**
  	 * 公告ID
  	 */
	@ApiModelProperty(value = "公告ID")
  	private String noticeId;
	
	/**
  	 * 用户ID
  	 */
	@ApiModelProperty(value = "用户ID")
  	private String userId;
	
	/**
  	 * 用户名称
  	 */
	@ApiModelProperty(value = "用户名称")
  	private String userName;
	
	/**
  	 * 状态 	0：未读 1：已读
  	 */
	@ApiModelProperty(value = "状态 	0：未读 1：已读")
  	private String status;
	
	/**
  	 * 已读时间
  	 */
	@ApiModelProperty(value = "已读时间")
  	private java.util.Date readTime;
	
  	
}
