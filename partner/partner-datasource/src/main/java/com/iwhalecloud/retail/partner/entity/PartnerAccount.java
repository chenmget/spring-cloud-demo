package com.iwhalecloud.retail.partner.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 代理商账户信息
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("PAR_PARTNER_ACCOUNT")
@ApiModel(value = "对应模型PAR_PARTNER_ACCOUNT, 对应实体PartnerAccount类")
@KeySequence(value="seq_par_partner_account_id",clazz = String.class)

public class PartnerAccount implements Serializable {
    /**表名常量*/
    public static final String TNAME = "PAR_PARTNER_ACCOUNT";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * ACCOUNT_ID
  	 */
	@TableId
	@ApiModelProperty(value = "ACCOUNT_ID")
  	private java.lang.String accountId;
  	
  	/**
  	 * PARTNER_ID
  	 */
	@ApiModelProperty(value = "PARTNER_ID")
  	private java.lang.String partnerId;
  	
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
  	 * 状态 0 有效、1 失效
  	 */
	@ApiModelProperty(value = "状态 0 有效、1 失效")
  	private java.lang.String state;

  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** ACCOUNT_ID. */
		accountId("accountId","ACCOUNT_ID"),
		
		/** PARTNER_ID. */
		partnerId("partnerId","PARTNER_ID"),
		
		/** ACCOUNT_TYPE. */
		accountType("accountType","ACCOUNT_TYPE"),
		
		/** ACCOUNT. */
		account("account","ACCOUNT"),
		
		/** IS_DEFAULT. */
		isDefault("isDefault","IS_DEFAULT"),
		
		/** 状态 0 有效、1 失效. */
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
