package com.iwhalecloud.retail.partner.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 代理商账户信息
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型PAR_PARTNER_ACCOUNT, 对应实体PartnerAccount类")
public class PartnerAccountDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * ACCOUNT_ID
  	 */
	@ApiModelProperty(value = "ACCOUNT_ID")
  	private java.lang.String accountId;
	
	/**
  	 * PARTNER_ID
  	 */
	@ApiModelProperty(value = "PARTNER_ID")
  	private java.lang.String partnerId;
	
	/**
  	 * ACCOUNT_TYPE
	 * 1:微信支付   2:翼支付  3:支付宝
  	 */
	@ApiModelProperty(value = "ACCOUNT_TYPE  1:微信支付   2:翼支付  3:支付宝")
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
  	 * 状态 0 有效、1 失效
  	 */
	@ApiModelProperty(value = "状态 0 有效、1 失效")
  	private java.lang.String state;

}
