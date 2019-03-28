package com.iwhalecloud.retail.system.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Notice
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("sys_notice")
@KeySequence(value="seq_sys_notice_id",clazz = String.class)
@ApiModel(value = "对应模型sys_notice, 对应实体Notice类")
public class Notice implements Serializable {
    /**表名常量*/
    public static final String TNAME = "sys_notice";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 通知ID
  	 */
	@TableId
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
	@ApiModelProperty(value = "状态 0：无效 1：有效 2：待审核 3：审核不通过")
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
	 * 附件路径
	 */
	@ApiModelProperty(value = "附件路径")
	private String fileUrl;


  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 通知ID. */
		noticeId("noticeId","NOTICE_ID"),
		
		/** 通知类型 1：业务类 2：热点消息 3：通知公告. */
		noticeType("noticeType","NOTICE_TYPE"),
		
		/** 通知标题. */
		noticeTitle("noticeTitle","NOTICE_TITLE"),
		
		/** 通知内容. */
		noticeContent("noticeContent","NOTICE_CONTENT"),
		
		/** 发布类型  1：系统类 ，表示不需要指定用户 2：定向类，表示指定用户阅读。关联sys_notice_user表. */
		publishType("publishType","PUBLISH_TYPE"),
		
		/** 创建时间. */
		createTime("createTime","CREATE_TIME"),
		
		/** 创建用户ID. */
		createUserId("createUserId","CREATE_USER_ID"),
		
		/** 修改时间. */
		updateTime("updateTime","UPDATE_TIME"),
		
		/** 修改用户ID. */
		updateUserId("updateUserId","UPDATE_USER_ID"),
		
		/** 状态 	0：无效 1：有效 2：待审核 3：审核不通过 */
		status("status","STATUS"),

		/** 公告开始时间 */
		beginTime("beginTime","BEGIN_TIME"),

		/** 公告结束时间 */
		endTime("endTime","END_TIME"),

		/** 附件路径 */
		fileUrl("fileUrl","FILE_URL");

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
