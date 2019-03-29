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
 * GroupMerchant  
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("mem_group_merchant")
@KeySequence(value = "seq_mem_group_merchantid", clazz = String.class)
@ApiModel(value = "对应模型mem_group_merchant, 对应实体GroupMerchant  类")
public class GroupMerchant implements Serializable {
    /**表名常量*/
    public static final String TNAME = "mem_group_merchant";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
	 * ID
	 */
	@TableId
	@ApiModelProperty(value = "ID")
	private String id;

  	/**
  	 * 商家ID
  	 */
	@ApiModelProperty(value = "商家ID")
  	private String merchId;
  	
  	/**
  	 * 会员群ID
  	 */
	@ApiModelProperty(value = "会员群ID")
  	private String groupId;
  	
  	/**
  	 * 是否有效 1有效  0无效
  	 */
	@ApiModelProperty(value = "是否有效 1有效  0无效")
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
		merchId("merchId","MERCH_ID"),
		
		/** 会员群ID. */
		groupId("groupId","GROUP_ID"),
		
		/** 是否有效 1有效  0无效. */
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
