package com.iwhalecloud.retail.partner.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 供应商账户信息
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("PAR_SUPPLIER_ACCOUNT")
@KeySequence(value="seq_par_supplier_account_id",clazz = String.class)
@ApiModel(value = "对应模型PAR_SUPPLIER_ACCOUNT, 对应实体SupplierAccount类")
public class SupplierAccount implements Serializable {
    /**表名常量*/
    public static final String TNAME = "ES_SUPPLIER_ACCOUNT";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * ACCOUNT_ID
  	 */
	@TableId
	@ApiModelProperty(value = "ACCOUNT_ID")
  	private java.lang.String accountId;
  	
  	/**
  	 * SUPPLIER_ID
  	 */
	@ApiModelProperty(value = "SUPPLIER_ID")
  	private java.lang.String supplierId;
  	
  	/**
  	 * ACCOUNT_TYPE
  	 */
	@ApiModelProperty(value = "ACCOUNT_TYPE 1:微信支付   2:翼支付  3:支付宝")
  	private java.lang.String accountType;
  	
  	/**
  	 * ACCOUNT
  	 */
	@ApiModelProperty(value = "ACCOUNT")
  	private java.lang.String account;
  	
  	/**
  	 * IS_DEFAULT
  	 */
	@ApiModelProperty(value = "IS_DEFAULT  0:未删除   1:已删除")
  	private java.lang.String isDefault;
  	
  	/**
  	 * 状态0 有效、1失效
  	 */
	@ApiModelProperty(value = "状态 0 有效、1失效")
  	private java.lang.String state;
  	
  	/**
  	 * 平台
  	 */
	@ApiModelProperty(value = "平台")
  	private java.lang.String sourceFrom;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** ACCOUNT_ID. */
		accountId("accountId","ACCOUNT_ID"),
		
		/** SUPPLIER_ID. */
		supplierId("supplierId","SUPPLIER_ID"),
		
		/** ACCOUNT_TYPE. */
		accountType("accountType","ACCOUNT_TYPE"),
		
		/** ACCOUNT. */
		account("account","ACCOUNT"),
		
		/** IS_DEFAULT. */
		isDefault("isDefault","IS_DEFAULT"),
		
		/** 状态. */
		state("state","STATE"),
		
		/** 平台. */
		sourceFrom("sourceFrom","SOURCE_FROM");

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
