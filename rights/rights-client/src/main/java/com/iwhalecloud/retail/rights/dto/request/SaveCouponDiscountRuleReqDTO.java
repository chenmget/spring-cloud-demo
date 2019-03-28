package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class SaveCouponDiscountRuleReqDTO extends AbstractRequest implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	
	/**
  	 * 优惠券标识
  	 */
	@NotBlank
	@ApiModelProperty(value = "优惠券标识")
  	private String mktResId;
  	
  	/**
  	 * 抵扣金额或折扣
  	 */
	@ApiModelProperty(value = "抵扣金额或折扣")
  	private Double discountValue;
  	
  	/**
  	 * 记录抵扣的上限
  	 */
	@ApiModelProperty(value = "记录抵扣的上限")
  	private Double maxValue;
  	
  	/**
  	 * 记录抵扣的下限
  	 */
	@ApiModelProperty(value = "记录抵扣的下限")
  	private Double minValue;
  	
  	/**
  	 * 记录使用优惠券需要满足的条件
  	 */
	@ApiModelProperty(value = "记录使用优惠券需要满足的条件")
  	private Double ruleAmount;
  	
  	/**
  	 * 记录优惠券的使用类型LOVB=RES-C-0045

商品、商铺内、总额
  	 */
	@ApiModelProperty(value = "记录优惠券的使用类型LOVB=RES-C-0045商品、商铺内、总额")
  	private String useType;
  	
  	/**
  	 * 标志同一个优惠券的多个优惠券实例是否可叠加使用。LOVB=PUB-C-0006
  	 */
	@ApiModelProperty(value = "标志同一个优惠券的多个优惠券实例是否可叠加使用。LOVB=PUB-C-0006")
  	private String reuseFlag;
  	
  	/**
  	 * 标志不同优惠券的多个实例是否可混合使用。LOVB=PUB-C-0006
  	 */
	@ApiModelProperty(value = "标志不同优惠券的多个实例是否可混合使用。LOVB=PUB-C-0006")
  	private String mixUseFlag;

	/**
	 * 是否循环使用
	 */
	@ApiModelProperty(value = "是否循环使用")
	private String recycleFlag;
  	
  	/**
  	 * 抵扣规则描述
  	 */
	@ApiModelProperty(value = "抵扣规则描述")
  	private String discountRuleDesc;
	
  	/**
  	 * 创建人
  	 */
	@NotNull
	@ApiModelProperty(value = "创建人")
  	private String createStaff;
  	
  	
}
