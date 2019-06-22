package com.iwhalecloud.retail.system.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * UserCollection
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("sys_user_collection")
@ApiModel(value = "对应模型sys_user_collection, 对应实体UserCollection类")
@KeySequence(value = "seq_sys_user_collection_id",clazz = String.class)
public class UserCollection implements Serializable {
    /**表名常量*/
    public static final String TNAME = "sys_user_collection";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * id
  	 */
	@TableId
	@ApiModelProperty(value = "id")
  	private java.lang.String id;
  	
  	/**
  	 * 用户ID
  	 */
	@ApiModelProperty(value = "用户ID")
  	private java.lang.String userId;
  	
  	/**
  	 * 对象类型：1. B2C商品 2.B2C门店 3.B2B商品 4.B2B供应商
  	 */
	@ApiModelProperty(value = "对象类型：1. B2C商品 2.B2C门店 3.B2B商品 4.B2B供应商")
  	private java.lang.String objType;
  	
  	/**
  	 * 当对象类型为 1.B2C商品时，记录商品ID 当对象类型为 2.B2C店铺时，记录门店ID 当对象类型为 3.B2B商品时，记录B2B商品ID 当对象类型为 4.B2B供应商时，记录B2B供应商ID
  	 */
	@ApiModelProperty(value = "当对象类型为 1.B2C商品时，记录商品ID 当对象类型为 2.B2C店铺时，记录门店ID 当对象类型为 3.B2B商品时，记录B2B商品ID 当对象类型为 4.B2B供应商时，记录B2B供应商ID")
  	private java.lang.String objId;
  	
  	/**
  	 * createDate
  	 */
	@ApiModelProperty(value = "createDate")
  	private java.util.Date createDate;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** id. */
		id("id","ID"),
		
		/** 用户ID. */
		userId("userId","USER_ID"),
		
		/** 对象类型：1. B2C商品 2.B2C门店 3.B2B商品 4.B2B供应商. */
		objType("objType","OBJ_TYPE"),
		
		/** 当对象类型为 1.B2C商品时，记录商品ID 当对象类型为 2.B2C店铺时，记录门店ID 当对象类型为 3.B2B商品时，记录B2B商品ID 当对象类型为 4.B2B供应商时，记录B2B供应商ID. */
		objId("objId","OBJ_ID"),
		
		/** createDate. */
		createDate("createDate","CREATE_DATE");

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
