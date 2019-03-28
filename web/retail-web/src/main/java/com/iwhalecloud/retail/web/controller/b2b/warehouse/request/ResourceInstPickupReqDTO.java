package com.iwhalecloud.retail.web.controller.b2b.warehouse.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;


/**
 * ResourceInst
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "串码领用")
public class ResourceInstPickupReqDTO implements Serializable {

  	private static final long serialVersionUID = 1L;

	/**
	 * 申请单Id
	 */
	@NotEmpty(message = "申请单Id不能为空")
	@ApiModelProperty(value = "申请单Id")
	private String resReqId;

	/**
	 * 商家Id
	 */
	@NotBlank(message = "商家Id不能为空")
	@ApiModelProperty(value = "商家Id。")
	private String merchantId;

}
