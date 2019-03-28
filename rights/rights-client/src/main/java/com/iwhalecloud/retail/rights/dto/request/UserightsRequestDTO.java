package com.iwhalecloud.retail.rights.dto.request;

import java.io.Serializable;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserightsRequestDTO extends AbstractRequest implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	/**
  	 * 优惠券实例标识,多个用逗号分割
  	 */
	@ApiModelProperty(value = "优惠券实例标识")
  	private java.lang.String couponInstId;
	
	/**
  	 * 订单编号
  	 */
	@ApiModelProperty(value = "订单编号")
  	private java.lang.String orderId;
	
	/**
  	 * 客户编号
  	 */
	@ApiModelProperty(value = "客户编号")
  	private java.lang.String custNum;
	
	/**
  	 * 创建人
  	 */
	@ApiModelProperty(value = "修改人")
  	private java.lang.String createStaff;
	
	/**
	 * 优惠券状态
	 */
	@ApiModelProperty(value = "优惠券状态")
	private java.lang.String statusCd;
}
