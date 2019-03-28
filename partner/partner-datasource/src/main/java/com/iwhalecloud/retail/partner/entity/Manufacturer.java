package com.iwhalecloud.retail.partner.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Manufacturer
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("par_manufacturer")
@ApiModel(value = "对应模型par_manufacturer, 对应实体Manufacturer类")
@KeySequence(value="seq_par_manufacturer_id",clazz = String.class)

public class Manufacturer implements Serializable {
    /**表名常量*/
    public static final String TNAME = "par_manufacturer";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 厂商ID
  	 */
	@TableId
	@ApiModelProperty(value = "厂商ID")
  	private String manufacturerId;
  	
  	/**
  	 * 厂商编码
  	 */
	@ApiModelProperty(value = "厂商编码")
  	private String manufacturerCode;
  	
  	/**
  	 * 厂商名称
  	 */
	@ApiModelProperty(value = "厂商名称")
  	private String manufacturerName;
  	
  	/**
  	 * 厂商级别
  	 */
	@ApiModelProperty(value = "厂商级别")
  	private String manufacturerLevel;
  	
  	/**
  	 * 状态
  	 */
	@ApiModelProperty(value = "状态")
  	private String status;
  	
  	/**
  	 * 关联账号id
  	 */
	@ApiModelProperty(value = "关联账号id")
  	private String userId;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 厂商ID. */
		manufacturerId("manufacturerId","MANUFACTURER_ID"),
		
		/** 厂商编码. */
		manufacturerCode("manufacturerCode","MANUFACTURER_CODE"),
		
		/** 厂商名称. */
		manufacturerName("manufacturerName","MANUFACTURER_NAME"),
		
		/** 厂商级别. */
		manufacturerLevel("manufacturerLevel","MANUFACTURER_LEVEL"),
		
		/** 状态. */
		status("status","STATUS"),
		
		/** 关联账号id. */
		userId("userId","USER_ID");

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
