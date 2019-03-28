package com.iwhalecloud.retail.web.controller.rights.request;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CheckRightsReq implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	/**
  	 * 优惠券实例标识,多个用逗号分割
  	 */
	@ApiModelProperty(value = "优惠券实例标识")
  	private java.lang.String couponInstId;
	
	
	/**
  	 * 订单金额
  	 */
	@NotNull
	@ApiModelProperty(value = "订单金额")
  	private java.lang.Double orderPrice;
}
