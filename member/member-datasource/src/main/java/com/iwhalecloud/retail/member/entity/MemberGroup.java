package com.iwhalecloud.retail.member.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * MemberGroup
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("mem_member_group")
@KeySequence(value = "seq_mem_member_group_id", clazz = String.class)
@ApiModel(value = "对应模型mem_member_group, 对应实体MemberGroup类")
public class MemberGroup implements Serializable {
    /**表名常量*/
    public static final String TNAME = "mem_member_group";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 会员群ID
  	 */
	@TableId
	@ApiModelProperty(value = "会员群ID")
  	private String groupId;
  	
  	/**
  	 * 会员ID
  	 */
	@ApiModelProperty(value = "会员ID")
  	private String memId;
  	
  	/**
  	 * 群成员状态 1有效  0无效
  	 */
	@ApiModelProperty(value = "群成员状态 1有效  0无效")
  	private String status;
  	
  	/**
  	 * 创建时间
  	 */
	@ApiModelProperty(value = "创建时间")
  	private java.util.Date createDate;
  	
  	/**
  	 * 创建人
  	 */
	@ApiModelProperty(value = "创建人")
  	private String createStaff;
  	
  	/**
  	 * 更新时间
  	 */
	@ApiModelProperty(value = "更新时间")
  	private java.util.Date updateDate;
  	
  	/**
  	 * 更新人
  	 */
	@ApiModelProperty(value = "更新人")
  	private String updateStaff;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 会员群ID. */
		groupId("groupId","GROUP_ID"),
		
		/** 会员ID. */
		memId("memId","MEM_ID"),
		
		/** 群成员状态 1有效  0无效. */
		status("status","STATUS"),
		
		/** 创建时间. */
		createDate("createDate","CREATE_DATE"),
		
		/** 创建人. */
		createStaff("createStaff","CREATE_STAFF"),
		
		/** 更新时间. */
		updateDate("updateDate","UPDATE_DATE"),
		
		/** 更新人. */
		updateStaff("updateStaff","UPDATE_STAFF");

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
