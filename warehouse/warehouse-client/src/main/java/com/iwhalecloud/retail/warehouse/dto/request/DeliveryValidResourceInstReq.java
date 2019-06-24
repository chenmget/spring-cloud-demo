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
public class DeliveryValidResourceInstReq implements Serializable {

  	private static final long serialVersionUID = 1L;

	/**
	 * 产品id集合
	 */
	@ApiModelProperty(value = "产品id集合")
	private List<String> productIdList;

	/**
	 * 营销资源实例编码。
	 */
	@ApiModelProperty(value = "营销资源实例编码")
	private List<String> mktResInstNbrList;

	/**
	 * 资源店中商ID
	 */
	@ApiModelProperty(value = "商家ID")
	private String merchantId;

	/**
	 * mktResStoreId
	 */
	@ApiModelProperty(value = "mktResStoreId")
	private String mktResStoreId;


}
