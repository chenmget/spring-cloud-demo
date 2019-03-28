package com.iwhalecloud.retail.rights.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

/**
 * 记录优惠券领用后的可以使用的有效期规则
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型COUPON_EFF_EXP_RULE, 对应实体CouponEffExpRule类")
public class CouponEffExpRuleRespDTO implements Serializable {
	
  	private static final long serialVersionUID = 1L;
  
  	
  	/**
  	 * 优惠券生失效规则标识，唯一主键
  	 */
	@ApiModelProperty(value = "优惠券生失效规则标识，唯一主键")
  	private String effExpRuleId;
  	
  	/**
  	 * 优惠券标识
  	 */
	@ApiModelProperty(value = "优惠券标识")
  	private String mktResId;
  	
  	/**
  	 * 优惠券领用后，可以使用的有效期时长
  	 */
	@ApiModelProperty(value = "优惠券领用后，可以使用的有效期时长")
  	private Long periodTime;
  	
  	/**
  	 * 有效期时间单位。LOVB=OFF-C-0036
  	 */
	@ApiModelProperty(value = "有效期时间单位。LOVB=OFF-C-0036")
  	private String periodUnit;
  	
  	/**
  	 * 优惠券领用后，可以使用的开始时间
  	 */
	@ApiModelProperty(value = "优惠券领用后，可以使用的开始时间")
  	private java.util.Date effDate;
  	
  	/**
  	 * 优惠券领用后，可以使用的截止时间
  	 */
	@ApiModelProperty(value = "优惠券领用后，可以使用的截止时间")
  	private java.util.Date expDate;
  	
  	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private String remark;
  	
	/**
	 * 记录首次创建的员工标识。
	 */
	@ApiModelProperty(value = "记录首次创建的员工标识。")
	private Long createStaff;
	
  	
  	
}
