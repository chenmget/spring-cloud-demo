package com.iwhalecloud.retail.promo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * AccountBalanceRule
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型ACCOUNT_BALANCE_RULE, 对应实体AccountBalanceRule类")
public class AccountBalanceRuleDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 规则标识
  	 */
	@ApiModelProperty(value = "规则标识")
  	private String ruleId;
	
	/**
  	 * 余额账本所属的余额类型。ACC-C-0009
  	 */
	@ApiModelProperty(value = "余额账本所属的余额类型。ACC-C-0009")
  	private String balanceTypeId;
	
	/**
  	 * 规则类型，10 返利使用规则
  	 */
	@ApiModelProperty(value = "规则类型，10 返利使用规则")
  	private String ruleType;
	
	/**
  	 * 余额对象类型，10 商家、20 产品
  	 */
	@ApiModelProperty(value = "余额对象类型，10 商家、20 产品")
  	private String objType;
	
	/**
  	 * 余额对象标识
  	 */
	@ApiModelProperty(value = "余额对象标识")
  	private String objId;
	
	/**
  	 * 生效时间
  	 */
	@ApiModelProperty(value = "生效时间")
  	private java.util.Date effDate;
	
	/**
  	 * 失效时间
  	 */
	@ApiModelProperty(value = "失效时间")
  	private java.util.Date expDate;
	
	/**
  	 * 状态; 1000 有效，1100 失效
  	 */
	@ApiModelProperty(value = "状态; 1000 有效，1100 失效")
  	private String statusCd;
	
	/**
  	 * 状态变更的时间。
  	 */
	@ApiModelProperty(value = "状态变更的时间。")
  	private java.util.Date statusDate;
	
	/**
  	 * 记录创建的员工。
  	 */
	@ApiModelProperty(value = "记录创建的员工。")
  	private String createStaff;
	
	/**
  	 * 记录创建的时间。
  	 */
	@ApiModelProperty(value = "记录创建的时间。")
  	private java.util.Date createDate;
	
	/**
  	 * 记录修改的员工。
  	 */
	@ApiModelProperty(value = "记录修改的员工。")
  	private String updateStaff;
	
	/**
  	 * 记录修改的时间。
  	 */
	@ApiModelProperty(value = "记录修改的时间。")
  	private java.util.Date updateDate;
	
  	
}
