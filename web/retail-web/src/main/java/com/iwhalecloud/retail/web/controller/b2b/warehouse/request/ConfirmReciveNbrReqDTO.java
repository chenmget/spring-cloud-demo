package com.iwhalecloud.retail.web.controller.b2b.warehouse.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * ResourceInst
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "调拨确认")
public class ConfirmReciveNbrReqDTO implements Serializable {

  	private static final long serialVersionUID = 1L;

	/**
	 * 申请单ID
	 */
	@ApiModelProperty(value = "申请单ID")
	private String resReqId;

	/**
	 * 商家Id
	 */
	@ApiModelProperty(value = "商家Id。")
	private String merchantId;

	/**
	 * 是否通过 0通过，1不通过
	 */
	@ApiModelProperty(value = "是否通过 0通过，1不通过")
	private String isPass;

}
