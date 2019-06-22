package com.iwhalecloud.retail.goods2b.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ProdBrand
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("prod_brand")
@KeySequence(value="seq_prod_brand_id",clazz = String.class)
@ApiModel(value = "对应模型prod_brand, 对应实体ProdBrand类")
public class Brand implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_brand";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 品牌ID
  	 */
	@ApiModelProperty(value = "品牌ID")
	@TableId
  	private String brandId;
  	
  	/**
  	 * 品牌名称
  	 */
	@ApiModelProperty(value = "品牌名称")
  	private String name;
  	
  	/**
  	 * 品牌编码
  	 */
	@ApiModelProperty(value = "品牌编码")
  	private String brandCode;
  	
  	/**
  	 * 品牌描述
  	 */
	@ApiModelProperty(value = "品牌描述")
  	private String brandDesc;

	/**
	 * 外部品牌编码
	 */
	@ApiModelProperty(value = "外部品牌编码")
	private String brandCodeOuter;


	/**
	 * isDeleted
	 */
	@ApiModelProperty(value = "isDeleted")
	private String isDeleted;

	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人")
	private String createStaff;

	/**
	 * createDate
	 */
	@ApiModelProperty(value = "createDate")
	private java.util.Date createDate;

	/**
	 * 修改人
	 */
	@ApiModelProperty(value = "修改人")
	private String updateStaff;

	/**
	 * updateDate
	 */
	@ApiModelProperty(value = "updateDate")
	private java.util.Date updateDate;

	@ApiModelProperty(value = "归属厂家ID")
	private String manufacturerId;

	@ApiModelProperty(value = "归属厂家名称")
	private String manufacturerName;
  	
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** 品牌ID */
        brandId("brandId","BRAND_ID"),
        /** 品牌名称 */
        name("NAME","NAME"),
        /** 品牌网站 */
		brandUrl("brandUrl","BRAND_URL"),
        /** 品牌编码 */
        brandCode("brandCode","BRAND_CODE"),
        /** 品牌描述 */
		brandDesc("brandDesc","BRAND_DESC"),
		/** 创建人. */
		createStaff("createStaff","create_staff"),

		/** createDate. */
		createDate("createDate","create_date"),

		/** 修改人. */
		updateStaff("updateStaff","update_staff"),

		/** updateDate. */
		updateDate("updateDate","update_date"),

		/** manufacturerId. */
		manufacturerId("manufacturerId","MANUFACTURER_ID"),

		/** manufacturerName. */
		manufacturerName("manufacturerName","MANUFACTURER_NAME");

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
