package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UpdateCouponDiscountRuleReqDTO extends AbstractRequest implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	
	/**
  	 * 抵扣规则标识
  	 */
	@NotNull
	@ApiModelProperty(value = "抵扣规则标识")
  	private Long discountRuleId;
  	
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
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private String remark;
  	
  	/**
  	 * 修改人
  	 */
	@NotNull
	@ApiModelProperty(value = "修改人")
  	private String updateStaff;

	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private java.util.Date updateDate;
  	
}
