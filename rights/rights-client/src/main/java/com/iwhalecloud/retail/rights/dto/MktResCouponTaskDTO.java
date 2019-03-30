package com.iwhalecloud.retail.rights.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * MktResCouponTask
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型mkt_res_coupon_task, 对应实体MktResCouponTask类")
public class MktResCouponTaskDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 优惠券实例编码
  	 */
	@ApiModelProperty(value = "优惠券实例编码")
  	private String taskId;
	
	/**
  	 * 营销活动标识
  	 */
	@ApiModelProperty(value = "营销活动标识")
  	private String marketingActivityId;
	
	/**
  	 * 营销资源标识id
  	 */
	@ApiModelProperty(value = "营销资源标识id")
  	private String mktResId;
	
	/**
  	 * 优惠券赠送的客户统一账号
  	 */
	@ApiModelProperty(value = "优惠券赠送的客户统一账号")
  	private String custAcctId;
	
	/**
  	 * 发放记录标识
  	 */
	@ApiModelProperty(value = "发放记录标识")
  	private String provRecId;
	
	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private String remark;
	
	/**
  	 * 记录状态变更的时间。
  	 */
	@ApiModelProperty(value = "记录状态变更的时间。")
  	private java.util.Date statusDate;
	
	/**
  	 * 记录首次创建的员工标识。
  	 */
	@ApiModelProperty(value = "记录首次创建的员工标识。")
  	private String createStaff;
	
	/**
  	 * 记录首次创建的时间。
  	 */
	@ApiModelProperty(value = "记录首次创建的时间。")
  	private java.util.Date createDate;
	
	/**
  	 * 记录每次修改的员工标识。
  	 */
	@ApiModelProperty(value = "记录每次修改的员工标识。")
  	private String updateStaff;
	
	/**
  	 * 记录每次修改的时间。
  	 */
	@ApiModelProperty(value = "记录每次修改的时间。")
  	private java.util.Date updateDate;
	
	/**
  	 * 记录状态
  	 */
	@ApiModelProperty(value = "记录状态")
  	private String statusCd;
	
  	
}
