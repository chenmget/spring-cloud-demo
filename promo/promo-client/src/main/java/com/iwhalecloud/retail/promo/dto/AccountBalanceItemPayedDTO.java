package com.iwhalecloud.retail.promo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * AccountBalanceItemPayed
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型ACCOUNT_BALANCE_ITEM_PAYED, 对应实体AccountBalanceItemPayed类")
public class AccountBalanceItemPayedDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * acctBalanceItemPayedId
  	 */
	@ApiModelProperty(value = "acctBalanceItemPayedId")
  	private String acctBalanceItemPayedId;
	
	/**
  	 * 为每个余额流水生成的唯一编号，只具有逻辑上的含义，没有物理意义。
  	 */
	@ApiModelProperty(value = "为每个余额流水生成的唯一编号，只具有逻辑上的含义，没有物理意义。")
  	private String operPayoutId;
	
	/**
  	 * 余额支出对应的账目标识
  	 */
	@ApiModelProperty(value = "余额支出对应的账目标识")
  	private String acctItemId;
	
	/**
  	 * 操作后余额帐本的余额
  	 */
	@ApiModelProperty(value = "操作后余额帐本的余额")
  	private Long balance;
	
	/**
  	 * 状态; 1000 有效，1100 失效
  	 */
	@ApiModelProperty(value = "状态; 1000 有效，1100 失效")
  	private String statusCd;
	
	/**
  	 * 状态发生改变的时间
  	 */
	@ApiModelProperty(value = "状态发生改变的时间")
  	private java.util.Date statusDate;
	
  	
}
