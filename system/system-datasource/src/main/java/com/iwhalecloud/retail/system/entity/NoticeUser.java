package com.iwhalecloud.retail.system.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * NoticeUser
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("sys_notice_user")
@KeySequence(value="seq_sys_notice_user_id",clazz = String.class)
@ApiModel(value = "对应模型sys_notice_user, 对应实体NoticeUser类")
public class NoticeUser implements Serializable {
    /**表名常量*/
    public static final String TNAME = "sys_notice_user";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * ID
  	 */
	@TableId
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
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** ID. */
		id("id","ID"),
		
		/** 公告ID. */
		noticeId("noticeId","NOTICE_ID"),
		
		/** 用户ID. */
		userId("userId","USER_ID"),
		
		/** 用户名称. */
		userName("userName","USER_NAME"),
		
		/** 状态 	0：未读 1：已读. */
		status("status","STATUS"),
		
		/** 已读时间. */
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
