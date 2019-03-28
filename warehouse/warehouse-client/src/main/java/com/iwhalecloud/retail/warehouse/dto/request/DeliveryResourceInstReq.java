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
@ApiModel(value = "订单发货")
public class DeliveryResourceInstReq implements Serializable {

  	private static final long serialVersionUID = 1L;

	/**
	 * 卖家
	 */
	@ApiModelProperty(value = "卖家")
	private String sellerMerchantId;

	/**
	 * 卖家
	 */
	@ApiModelProperty(value = "买家")
	private String buyerMerchantId;

	/**
	 * 订单项id
	 */
	@ApiModelProperty(value = "订单项id")
	private List<DeliveryResourceInstItem> deliveryResourceInstItemList;

	/**
	 * 修改在途库存参数
	 */
	@ApiModelProperty(value = "修改在途库存参数")
	private UpdateStockReq updateStockReq;

	/**
	 * 订单号
	 */
	@ApiModelProperty(value = "订单号")
	private String orderId;

}
