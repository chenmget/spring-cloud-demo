package com.iwhalecloud.retail.rights.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CheckRightsResponseDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
  	 * 优惠券实例标识,多个用逗号分割
  	 */
	@ApiModelProperty(value = "优惠券实例标识")
  	private java.lang.String couponInstId;
	
	
	/**
	 * 优惠券名称,多个用逗号分割
	 */
	@ApiModelProperty(value = "优惠券名称")
	private java.lang.String mktResName;
	
	
	/**
  	 * 优惠金额
  	 */
	@ApiModelProperty(value = "优惠金额")
  	private java.lang.Double discountPrice = 0D;
}
