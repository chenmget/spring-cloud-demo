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
@ApiModel(value = "订单发货:订单项")
public class DeliveryResourceInstItem implements Serializable {

  	private static final long serialVersionUID = 1L;

	/**
	 * 订单项id
	 */
	@ApiModelProperty(value = "订单项id")
	private String orderItemId;

	/**
	 * 产品id
	 */
	@ApiModelProperty(value = "产品id")
	private String productId;

	/**
	 * 串码实例
	 */
	@ApiModelProperty(value = "串码实例")
	private List<String> mktResInstNbrs;

	/**
	 * 营销资源实例的销售价格
	 */
	@ApiModelProperty(value = "营销资源实例的销售价格")
	private Double salesPrice;
}
