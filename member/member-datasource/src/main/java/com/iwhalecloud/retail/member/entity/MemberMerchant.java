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
 * MemberMerchant
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("mem_member_merchant")
@KeySequence(value = "seq_mem_member_merchant_id", clazz = String.class)
@ApiModel(value = "对应模型mem_member_merchant, 对应实体MemberMerchant类")
public class MemberMerchant implements Serializable {
    /**表名常量*/
    public static final String TNAME = "mem_member_merchant";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 商家ID
  	 */
	@TableId
	@ApiModelProperty(value = "ID")
	private String id;

	@ApiModelProperty(value = "商家ID")
  	private String merchId;
  	
  	/**
  	 * 会员ID
  	 */
	@ApiModelProperty(value = "会员ID")
  	private String memId;
  	
  	/**
  	 * 会员等级ID
  	 */
	@ApiModelProperty(value = "会员等级ID")
  	private Integer lvId;
  	
  	/**
  	 * 会员状态  1有效  0无效
  	 */
	@ApiModelProperty(value = "会员状态  1有效  0无效")
  	private String status;
  	
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
		/** 商家ID. */
		id("id","ID"),
		merchId("merchId","MERCH_ID"),
		
		/** 会员ID. */
		memId("memId","MEM_ID"),
		
		/** 会员等级ID. */
		lvId("lvId","LV_ID"),
		
		/** 会员状态  1有效  0无效. */
		status("status","STATUS"),
		
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
