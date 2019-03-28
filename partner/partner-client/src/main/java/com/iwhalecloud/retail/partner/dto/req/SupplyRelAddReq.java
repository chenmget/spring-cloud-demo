package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 新增供应商供货关系信息
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "supplyRelCreateMsgReq")
public class SupplyRelAddReq implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;

	/**
  	 * 供应商ID
  	 */
	@ApiModelProperty(value = "supplierId")
  	private String supplierId;

	/**
	 * 代理商ID
	 */
	@ApiModelProperty(value = "partnerId")
	private String partnerId;


}
