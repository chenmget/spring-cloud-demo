package com.iwhalecloud.retail.rights.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 记录优惠券的使用规则信息
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型COUPON_DISCOUNT_RULE, 对应实体CouponDiscountRule类")
public class CouponDiscountRuleRespDTO implements Serializable {
	
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 抵扣规则标识
  	 */
	@ApiModelProperty(value = "抵扣规则标识")
  	private String discountRuleId;
  	
  	/**
  	 * 优惠券标识
  	 */
	@ApiModelProperty(value = "优惠券标识")
  	private String mktResId;
  	
	/**
	 * 优惠券名称
	 */
	@ApiModelProperty(value = "优惠券名称")
	private String mktResName;
	
  	/**
  	 * 记录抵扣的固定金额或折扣
  	 */
	@ApiModelProperty(value = "记录抵扣的固定金额或折扣")
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
	 * 标志不同优惠券的多个实例是否可循环使用。LOVB=PUB-C-0006
	 */
	@ApiModelProperty(value = "标志不同优惠券的多个实例是否可循环使用。LOVB=PUB-C-0006")
	private String recycleFlag;

  	/**
  	 * 抵扣规则描述
  	 */
	@ApiModelProperty(value = "抵扣规则描述")
  	private String discountRuleDesc;
  	
  	/**
  	 * 记录首次创建的员工标识。
  	 */
	@ApiModelProperty(value = "记录首次创建的员工标识。")
  	private Long createStaff;
  	
  	
  	//属性 end

}
