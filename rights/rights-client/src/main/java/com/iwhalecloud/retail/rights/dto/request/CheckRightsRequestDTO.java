package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CheckRightsRequestDTO extends AbstractRequest implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	/**
  	 * 优惠券实例标识,多个用逗号分割
  	 */
	@ApiModelProperty(value = "优惠券实例标识")
  	private java.lang.String couponInstId;
	
	/**
  	 * 客户编号
  	 */
	@NotBlank
	@ApiModelProperty(value = "客户编号")
  	private java.lang.String custNum;
	
	/**
  	 * 订单金额
  	 */
	@NotNull
	@ApiModelProperty(value = "订单金额")
  	private java.lang.Double orderPrice;
}
