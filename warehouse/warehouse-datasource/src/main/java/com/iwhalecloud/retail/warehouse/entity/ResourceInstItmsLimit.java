package com.iwhalecloud.retail.warehouse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * MerchantLimit
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("mkt_res_itms_limit")
@ApiModel(value = "mkt_res_itms_limit, 对应实体MerchantLimit2类")
public class ResourceInstItmsLimit implements Serializable {
    /**表名常量*/
    public static final String TNAME = "par_merchant_limit";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 商家ID
  	 */
	@TableId(type = IdType.INPUT)
	@ApiModelProperty(value = "lanId")
  	private String lanId;
  	
  	/**
  	 * 限额
  	 */
	@ApiModelProperty(value = "地市每月特殊串码录入额度")
  	private Long maxSerialNum;
  	
  	/**
  	 * 本月已使用
  	 */
	@ApiModelProperty(value = "本月已使用")
  	private Long serialNumUsed;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 商家ID. */
		lanId("lanId","LAN_ID"),
		
		/** 限额. */
		maxSerialNum("maxSerialNum","MAX_SERIAL_NUM"),
		
		/** 本月已使用. */
		serialNumUsed("serialNumUsed","SERIAL_NUM_USED");

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
