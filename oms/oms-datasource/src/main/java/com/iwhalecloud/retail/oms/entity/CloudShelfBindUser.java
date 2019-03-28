package com.iwhalecloud.retail.oms.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 云货架绑定人员关系表
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("R_CLOUD_SHELF_BIND_USER")
@ApiModel(value = "对应模型R_CLOUD_SHELF_BIND_USER, 对应实体CloudShelfBindUser类")
public class CloudShelfBindUser implements Serializable {
    /**表名常量*/
    public static final String TNAME = "R_CLOUD_SHELF_BIND_USER";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 自增ID
  	 */
	@TableId(type = IdType.ID_WORKER)
	@ApiModelProperty(value = "自增ID")
  	private java.lang.Long id;
  	
  	/**
  	 * 创建时间
  	 */
	@ApiModelProperty(value = "创建时间")
  	private java.util.Date gmtCreate;
  	
  	/**
  	 * 修改时间
  	 */
	@ApiModelProperty(value = "修改时间")
  	private java.util.Date gmtModified;
  	
  	/**
  	 * 创建人
  	 */
	@ApiModelProperty(value = "创建人")
  	private java.lang.String creator;
  	
  	/**
  	 * 修改人
  	 */
	@ApiModelProperty(value = "修改人")
  	private java.lang.String modifier;
  	
  	/**
  	 * 是否删除：0未删、1删除  
  	 */
	@ApiModelProperty(value = "是否删除：0未删、1删除  ")
  	private java.lang.Integer isDeleted;
  	
  	/**
  	 * 云货架编号
  	 */
	@ApiModelProperty(value = "云货架编号")
  	private java.lang.String cloudShelfNumber;
  	
  	/**
  	 * 员工工号
  	 */
	@ApiModelProperty(value = "员工工号")
  	private java.lang.String userId;
  	
  	/**
  	 * 员工身份：1管理员、2普通员工
  	 */
	@ApiModelProperty(value = "员工身份：1管理员、2普通员工")
  	private java.lang.Long userIdentity;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 自增ID. */
		id("id","ID"),
		
		/** 创建时间. */
		gmtCreate("gmtCreate","GMT_CREATE"),
		
		/** 修改时间. */
		gmtModified("gmtModified","GMT_MODIFIED"),
		
		/** 创建人. */
		creator("creator","CREATOR"),
		
		/** 修改人. */
		modifier("modifier","MODIFIER"),
		
		/** 是否删除：0未删、1删除  . */
		isDeleted("isDeleted","IS_DELETED"),
		
		/** 云货架编号. */
		cloudShelfNumber("cloudShelfNumber","CLOUD_SHELF_NUMBER"),
		
		/** 员工工号. */
		userId("userId","USER_ID"),
		
		/** 员工身份：1管理员、2普通员工. */
		userIdentity("userIdentity","USER_IDENTITY");

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
