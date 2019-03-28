package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * ConfirmReciveReq
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "调拨确认收货")
public class ConfirmReciveNbrReq implements Serializable {

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
	 * 更新人
	 */
	@ApiModelProperty(value = "更新人")
	private String updateStaff;

	/**
	 * 商家类型
	 */
	@ApiModelProperty(value = "商家类型")
	private String merchantType;

}
