package com.iwhalecloud.retail.promo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * AccountBalancePayout
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型ACCOUNT_BALANCE_PAYOUT, 对应实体AccountBalancePayout类")
public class AccountBalancePayoutDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 为每个余额流水生成的唯一编号，只具有逻辑上的含义，没有物理意义。
  	 */
	@ApiModelProperty(value = "为每个余额流水生成的唯一编号，只具有逻辑上的含义，没有物理意义。")
  	private String operPayoutId;
	
	/**
  	 * 余额明细ID
  	 */
	@ApiModelProperty(value = "余额明细ID")
  	private String accountBalanceDetailId;
	
	/**
  	 * 为每个余额帐本生成的唯一编号，只具有逻辑上的含义，没有物理意义。
  	 */
	@ApiModelProperty(value = "为每个余额帐本生成的唯一编号，只具有逻辑上的含义，没有物理意义。")
  	private String acctBalanceId;
	
	/**
  	 * 账目类型
  	 */
	@ApiModelProperty(value = "账目类型")
  	private String acctItemType;
	
	/**
  	 * 如果是扣费销帐，记录操作对应的帐单号
  	 */
	@ApiModelProperty(value = "如果是扣费销帐，记录操作对应的帐单号")
  	private String billId;
	
	/**
  	 * 区分用户付款记录的唯一标识。余额提取时必填。
  	 */
	@ApiModelProperty(value = "区分用户付款记录的唯一标识。余额提取时必填。")
  	private String paymentId;
	
	/**
  	 * 操作类型，LOVB=ACC-C-0014
  	 */
	@ApiModelProperty(value = "操作类型，LOVB=ACC-C-0014")
  	private String operType;
	
	/**
  	 * 操作的金额
  	 */
	@ApiModelProperty(value = "操作的金额")
  	private Long amount;
	
	/**
  	 * 操作后余额帐本的余额
  	 */
	@ApiModelProperty(value = "操作后余额帐本的余额")
  	private Long balance;
	
	/**
  	 * 记录对应来源终端携带的流水号
  	 */
	@ApiModelProperty(value = "记录对应来源终端携带的流水号")
  	private String extSerialId;
	
	/**
  	 * 操作的员工标识
  	 */
	@ApiModelProperty(value = "操作的员工标识")
  	private String staffId;
	
	/**
  	 * 组织标识
  	 */
	@ApiModelProperty(value = "组织标识")
  	private String orgId;
	
	/**
  	 * 操作发生的时间
  	 */
	@ApiModelProperty(value = "操作发生的时间")
  	private java.util.Date operDate;
	
	/**
  	 * 该操作被打印的次数
  	 */
	@ApiModelProperty(value = "该操作被打印的次数")
  	private Long prnCount;
	
	/**
  	 * 余额支出的具体描述信息，便于查询展示及统计
  	 */
	@ApiModelProperty(value = "余额支出的具体描述信息，便于查询展示及统计")
  	private String payoutDesc;
	
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
