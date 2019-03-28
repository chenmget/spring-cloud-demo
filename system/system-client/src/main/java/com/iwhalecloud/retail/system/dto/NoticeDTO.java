package com.iwhalecloud.retail.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * Notice
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型sys_notice, 对应实体Notice类")
public class NoticeDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 通知ID
  	 */
	@ApiModelProperty(value = "通知ID")
  	private String noticeId;
	
	/**
  	 * 通知类型 1：业务类 2：热点消息 3：通知公告
  	 */
	@ApiModelProperty(value = "通知类型 1：业务类 2：热点消息 3：通知公告")
  	private String noticeType;
	
	/**
  	 * 通知标题
  	 */
	@ApiModelProperty(value = "通知标题")
  	private String noticeTitle;
	
	/**
  	 * 通知内容
  	 */
	@ApiModelProperty(value = "通知内容")
  	private String noticeContent;
	
	/**
  	 * 发布类型  1：系统类 ，表示不需要指定用户 2：定向类，表示指定用户阅读。关联sys_notice_user表
  	 */
	@ApiModelProperty(value = "发布类型  1：系统类 ，表示不需要指定用户 2：定向类，表示指定用户阅读。关联sys_notice_user表")
  	private String publishType;
	
	/**
  	 * 创建时间
  	 */
	@ApiModelProperty(value = "创建时间")
  	private java.util.Date createTime;
	
	/**
  	 * 创建用户ID
  	 */
	@ApiModelProperty(value = "创建用户ID")
  	private String createUserId;
	
	/**
  	 * 修改时间
  	 */
	@ApiModelProperty(value = "修改时间")
  	private java.util.Date updateTime;
	
	/**
  	 * 修改用户ID
  	 */
	@ApiModelProperty(value = "修改用户ID")
  	private String updateUserId;
	
	/**
  	 * 状态 	0：无效 1：有效 2：待审核 3：审核不通过
  	 */
	@ApiModelProperty(value = "状态 	0：无效 1：有效 2：待审核 3：审核不通过")
  	private String status;

	/**
	 * 公告开始时间
	 */
	@ApiModelProperty(value = "公告开始时间")
	private java.util.Date beginTime;

	/**
	 * 公告结束时间
	 */
	@ApiModelProperty(value = "公告结束时间")
	private java.util.Date endTime;

	/**
	 * 文件路径
	 */
	@ApiModelProperty(value = "文件路径")
	private String fileUrl;

	/**
	 * 文件路径
	 */
	@ApiModelProperty(value = "文件路径")
	private List<FileDTO> file;


}
