package com.iwhalecloud.retail.partner.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 供应商账户信息
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型PAR_SUPPLIER_ACCOUNT, 对应实体SupplierAccount类")
public class SupplierAccountDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * ACCOUNT_ID
  	 */
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
	@ApiModelProperty(value = "ACCOUNT_TYPE")
  	private java.lang.String accountType;
	
	/**
  	 * ACCOUNT
  	 */
	@ApiModelProperty(value = "ACCOUNT")
  	private java.lang.String account;
	
	/**
  	 * IS_DEFAULT
  	 */
	@ApiModelProperty(value = "IS_DEFAULT")
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
	
  	
}
