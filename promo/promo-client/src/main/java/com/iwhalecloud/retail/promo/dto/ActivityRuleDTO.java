package com.iwhalecloud.retail.promo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ActivityRule
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型act_activity_rule, 对应实体ActivityRule类")
public class ActivityRuleDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * ID
  	 */
	@ApiModelProperty(value = "ID")
  	private java.lang.String id;
	
	/**
  	 * 营销活动code
  	 */
	@ApiModelProperty(value = "营销活动Id")
  	private java.lang.String marketingActivityId;
	
	/**
  	 * ruleId
  	 */
	@ApiModelProperty(value = "ruleId")
  	private java.lang.String ruleId;
	
	/**
  	 * ruleName
  	 */
	@ApiModelProperty(value = "ruleName")
  	private java.lang.String ruleName;
	
	/**
  	 * remark
  	 */
	@ApiModelProperty(value = "remark")
  	private java.lang.String remark;
	
	/**
  	 * settlementRule
  	 */
	@ApiModelProperty(value = "settlementRule")
  	private java.lang.String settlementRule;
	
	/**
  	 * useStartTime
  	 */
	@ApiModelProperty(value = "useStartTime")
  	private java.util.Date useStartTime;
	
	/**
  	 * useEndTime
  	 */
	@ApiModelProperty(value = "useEndTime")
  	private java.util.Date useEndTime;
	
	/**
  	 * deductionScale
  	 */
	@ApiModelProperty(value = "deductionScale")
  	private java.lang.String deductionScale;
	
	/**
  	 * calculationRule
  	 */
	@ApiModelProperty(value = "calculationRule")
  	private java.lang.String calculationRule;
	
//	/**
//  	 * 记录首次创建的员工标识。
//  	 */
//	@ApiModelProperty(value = "记录首次创建的员工标识。")
//  	private java.lang.String createStaff;
//
//	/**
//  	 * 记录首次创建的时间。
//  	 */
//	@ApiModelProperty(value = "记录首次创建的时间。")
//  	private java.util.Date createDate;
//
//	/**
//  	 * 记录每次修改的员工标识。
//  	 */
//	@ApiModelProperty(value = "记录每次修改的员工标识。")
//  	private java.lang.String updateStaff;
//
//	/**
//  	 * 记录每次修改的时间。
//  	 */
//	@ApiModelProperty(value = "记录每次修改的时间。")
//  	private java.util.Date updateDate;
	/**
	 * 记录首次创建的员工标识。
	 */
	@ApiModelProperty(value = "创建人。")
	private java.lang.String creator;

	/**
	 * 记录首次创建的时间。
	 */
	@ApiModelProperty(value = "创建时间。")
	private java.util.Date gmtCreate;

	/**
	 * 记录每次修改的员工标识。
	 */
	@ApiModelProperty(value = "修改人。")
	private java.lang.String modifier;

	/**
	 * 记录每次修改的时间。
	 */
	@ApiModelProperty(value = "修改时间。")
	private java.util.Date gmtModified;
	/**
	 * 是否删除：0未删、1删除。
	 */
	@ApiModelProperty(value = "是否删除：0未删、1删除。")
	private String isDeleted;
  	
}
