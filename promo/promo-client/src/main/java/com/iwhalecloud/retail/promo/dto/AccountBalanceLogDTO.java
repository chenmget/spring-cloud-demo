package com.iwhalecloud.retail.promo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * AccountBalanceLog
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型ACCOUNT_BALANCE_LOG, 对应实体AccountBalanceLog类")
public class AccountBalanceLogDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 余额账本日志标识
  	 */
	@ApiModelProperty(value = "余额账本日志标识")
  	private String balanceLogId;
	
	/**
  	 * 账户余额标识
  	 */
	@ApiModelProperty(value = "账户余额标识")
  	private String accountBalanceId;
	
	/**
  	 * 为每个余额流水生成的唯一编号，只具有逻辑上的含义，没有物理意义。
  	 */
	@ApiModelProperty(value = "为每个余额流水生成的唯一编号，只具有逻辑上的含义，没有物理意义。")
  	private String operIncomeId;
	
	/**
  	 * 余额来源原金额
  	 */
	@ApiModelProperty(value = "余额来源原金额")
  	private Long srcAmount;
	
	/**
  	 * 支出操作流水
  	 */
	@ApiModelProperty(value = "支出操作流水")
  	private String operPayoutId;
	
	/**
  	 * 支出金额
  	 */
	@ApiModelProperty(value = "支出金额")
  	private Long payoutAmount;
	
	/**
  	 * 状态时间
  	 */
	@ApiModelProperty(value = "状态时间")
  	private java.util.Date statusDate;
	
	/**
  	 * 状态; 1000 有效，1100 失效
  	 */
	@ApiModelProperty(value = "状态; 1000 有效，1100 失效")
  	private String statusCd;
	
  	
}
