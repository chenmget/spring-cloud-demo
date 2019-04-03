package com.iwhalecloud.retail.promo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * AccountBalanceType
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型ACCOUNT_BALANCE_TYPE, 对应实体AccountBalanceType类")
public class AccountBalanceTypeDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 余额账本所属的余额类型。ACC-C-0009
  	 */
	@ApiModelProperty(value = "余额账本所属的余额类型。ACC-C-0009")
  	private String balanceTypeId;
	
	/**
  	 * 余额类型的名称，活动名称+返利，活动名称+价保
  	 */
	@ApiModelProperty(value = "余额类型的名称，活动名称+返利，活动名称+价保")
  	private String balanceTypeName;
	
	/**
  	 * 活动ID
  	 */
	@ApiModelProperty(value = "活动ID")
  	private String actId;
	
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
	@ApiModelProperty(value = "记录创建的员工。 ")
  	private String createStaff;
	
	/**
  	 * 记录创建的时间。
  	 */
	@ApiModelProperty(value = "记录创建的时间。")
  	private java.util.Date createDate;
	
	/**
  	 * 记录修改的员工。 
  	 */
	@ApiModelProperty(value = "记录修改的员工。 ")
  	private String updateStaff;
	
	/**
  	 * 记录修改的时间。
  	 */
	@ApiModelProperty(value = "记录修改的时间。")
  	private java.util.Date updateDate;
	
	/**
  	 * 记录备注信息。
  	 */
	@ApiModelProperty(value = "记录备注信息。")
  	private String remark;
	
  	
}
