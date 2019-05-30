package com.iwhalecloud.retail.promo.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * ActivityScope
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("act_activity_scope")
@KeySequence(value = "seq_act_activity_scope_id", clazz = String.class)
@ApiModel(value = "对应模型act_activity_scope, 对应实体ActivityScope类")
public class ActivityScope implements Serializable {
    /**表名常量*/
    public static final String TNAME = "act_activity_scope";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * ID
  	 */
	@TableId
	@ApiModelProperty(value = "ID")
  	private java.lang.String id;
  	
  	/**
  	 * 营销活动编号
  	 */
	@ApiModelProperty(value = "营销活动Id")
  	private java.lang.String marketingActivityId;
  	
  	/**
  	 * 地市
  	 */
	@ApiModelProperty(value = "地市")
  	private java.lang.String lanId;
  	
  	/**
  	 * 市县
  	 */
	@ApiModelProperty(value = "市县")
  	private java.lang.String city;
  	
  	/**
  	 * 供应商名称
  	 */
	@ApiModelProperty(value = "供应商名称")
  	private java.lang.String supplierName;
  	
  	/**
  	 * 供应商编码
  	 */
	@ApiModelProperty(value = "供应商编码")
  	private java.lang.String supplierCode;

	@ApiModelProperty(value = "创建人。")
	private java.lang.String creator;

	/**
	 * 记录首次创建的时间。
	 */
	@ApiModelProperty(value = "创建时间。")
	private java.util.Date gmtCreate;
	/**
	 * 记录每次修改的员工标识。
	 */
	@ApiModelProperty(value = "修改人。")
	private java.lang.String modifier;

	/**
	 * 记录每次修改的时间。
	 */
	@ApiModelProperty(value = "修改时间。")
	private java.util.Date gmtModified;
	/**
	 * 是否删除：0未删、1删除。
	 */
	@ApiModelProperty(value = "是否删除：0未删、1删除。")
	private String isDeleted;

	/**
	 * 状态,0：待审核,1：有效,-1：审核不通过
	 */
	@ApiModelProperty(value = "状态,0：待审核,1：有效,-1：审核不通过")
	private java.lang.String status;
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** ID. */
		id("id","ID"),
		
		/** 营销活动ID. */
		marketingActivityId("marketingActivityID","MARKETING_ACTIVITY_ID"),
		
		/** 地市. */
		lanId("lanId","LAN_ID"),
		
		/** 市县. */
		city("city","CITY"),
		
		/** 供应商名称. */
		supplierName("supplierName","SUPPLIER_NAME"),
		
		/** 供应商编码. */
		supplierCode("supplierCode","SUPPLIER_CODE"),
		
//		/** 记录首次创建的员工标识。. */
//		createStaff("createStaff","CREATE_STAFF"),
//
//		/** 记录首次创建的时间。. */
//		createDate("createDate","CREATE_DATE"),
//
//		/** 记录每次修改的员工标识。. */
//		updateStaff("updateStaff","UPDATE_STAFF"),
//
//		/** 记录每次修改的时间。. */
//		updateDate("updateDate","UPDATE_DATE");
		/** 是否删除：0未删、1删除。. */
		isDeleted("isDeleted","IS_DELETED"),
		/** 状态,0：待审核,1：有效,-1：审核不通过. */
		status("status","STATUS"),
		/** 记录首次创建的员工标识。. */
		creator("creator","CREATOR"),

		/** 记录首次创建的时间。. */
		gmtCreate("gmtCreate","GMT_CREATE"),

		/** 记录每次修改的员工标识。. */
		modifier("modifier","MODIFIER"),

		/** 记录每次修改的时间。. */
		gmtModified("gmtModified","GMT_MODIFIED");

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
