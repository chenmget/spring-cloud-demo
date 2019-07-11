package com.iwhalecloud.retail.goods2b.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ProdCRMType
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("prod_crm_type")
@ApiModel(value = "对应模型prod_crm_type, 对应实体ProdCRMType类")
public class ProdCRMTypeDto implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_crm_type";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * typeId
  	 */
	@TableId(type = IdType.ID_WORKER)
	@ApiModelProperty(value = "typeId")
  	private String typeId;
  	
  	/**
  	 * typeName
  	 */
	@ApiModelProperty(value = "typeName")
  	private String typeName;
  	
  	/**
  	 * 产品类型的父级类型
  	 */
	@ApiModelProperty(value = "产品类型的父级类型")
  	private String parentTypeId;
  	
  	/**
  	 * catOrder
  	 */
	@ApiModelProperty(value = "catOrder")
  	private Long catOrder;
  	
  	/**
  	 * isDeleted
  	 */
	@ApiModelProperty(value = "isDeleted")
  	private String isDeleted;
  	
  	/**
  	 * createStaff
  	 */
	@ApiModelProperty(value = "createStaff")
  	private String createStaff;
  	
  	/**
  	 * createDate
  	 */
	@ApiModelProperty(value = "createDate")
  	private java.util.Date createDate;
  	
  	/**
  	 * updateStaff
  	 */
	@ApiModelProperty(value = "updateStaff")
  	private String updateStaff;
  	
  	/**
  	 * updateDate
  	 */
	@ApiModelProperty(value = "updateDate")
  	private java.util.Date updateDate;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** typeId. */
		typeId("typeId","TYPE_ID"),
		
		/** typeName. */
		typeName("typeName","TYPE_NAME"),
		
		/** 产品类型的父级类型. */
		parentTypeId("parentTypeId","PARENT_TYPE_ID"),
		
		/** catOrder. */
		catOrder("catOrder","CAT_ORDER"),
		
		/** isDeleted. */
		isDeleted("isDeleted","is_deleted"),
		
		/** createStaff. */
		createStaff("createStaff","create_staff"),
		
		/** createDate. */
		createDate("createDate","create_date"),
		
		/** updateStaff. */
		updateStaff("updateStaff","update_staff"),
		
		/** updateDate. */
		updateDate("updateDate","update_date");

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
