package com.iwhalecloud.retail.rights.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 记录优惠券与能优惠的对象，如商品、销售品、产品、积分等。
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型COUPON_APPLY_OBJECT, 对应实体CouponApplyObject类")
public class CouponApplyObjectRespDTO implements Serializable {
	
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 优惠券适用对象标识
  	 */
	@ApiModelProperty(value = "优惠券适用对象标识")
  	private String applyObjectId;
  	
  	/**
  	 * 优惠券标识
  	 */
	@ApiModelProperty(value = "优惠券标识")
  	private String mktResId;
  	
  	/**
  	 * 适用对象类型LOVB=RES-C-0044
商品、销售品、积分等
  	 */
	@ApiModelProperty(value = "适用对象类型LOVB=RES-C-0044商品、销售品、积分等")
  	private String objType;
  	
	/**
  	 * 对象类型名称
  	 */
	@ApiModelProperty(value = "对象类型名称")
  	private String objTypeName;
  	/**
  	 * 适用对象标识
  	 */
	@ApiModelProperty(value = "适用对象标识兑换关系")
  	private String objId;
  	
  	/**
  	 * 对象名称
  	 */
	@ApiModelProperty(value = "对象名称")
  	private String objName;
	
	/**
  	 * 营销资源名称
  	 */
	@ApiModelProperty(value = "营销资源名称")
  	private String mktResName;
  	
  	
}
