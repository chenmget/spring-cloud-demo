package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * ResourceInst
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "订单发货：串码校验")
public class ValidResourceInstReq implements Serializable {

  	private static final long serialVersionUID = 1L;

	/**
	 * 记录营销资源实例编码。
	 */
	@ApiModelProperty(value = "记录营销资源实例编码。")
	private List<ValidResourceInstItem> productIds;

	/**
	 * 商家类型
	 */
	@ApiModelProperty(value = "商家类型")
	private String merchantType;

	/**
	 * 记录供应商编码
	 */
	@ApiModelProperty(value = "记录供应商编码")
	private java.lang.String supplierCode;

	/**
	 * 资源店中商ID
	 */
	@ApiModelProperty(value = "资源店中商ID")
	private java.lang.String merchantId;

}
