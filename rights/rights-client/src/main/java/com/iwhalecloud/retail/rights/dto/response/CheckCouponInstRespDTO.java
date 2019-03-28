package com.iwhalecloud.retail.rights.dto.response;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CheckCouponInstRespDTO extends PageVO {
	
	
	private static final long serialVersionUID = 1L;

	/**
  	 * 优惠券实例标识
  	 */
	@ApiModelProperty(value = "优惠券实例标识")
  	private java.lang.String couponInstId;
  	
  	/**
  	 * 优惠券实例编码
  	 */
	@ApiModelProperty(value = "优惠券实例编码")
  	private java.lang.String couponInstNbr;
  	
  	/**
  	 * 优惠券标识
  	 */
	@ApiModelProperty(value = "优惠券标识")
  	private java.lang.String mktResId;
  	
  	/**
  	 * 记录优惠券使用后最终的优惠额度信息
  	 */
	@ApiModelProperty(value = "记录优惠券使用后最终的优惠额度信息")
  	private java.lang.Long couponAmount;
  	

}
