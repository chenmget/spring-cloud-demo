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
@ApiModel(value = "采购发货收货请求对象")
public class TradeResourceInstReq implements Serializable {

  	private static final long serialVersionUID = 1L;

	/**
	 * 发货用户
	 */
	@ApiModelProperty(value = "卖家")
	private String sellerMerchantId;

	/**
	 * 收货人的（十四个地市之一）的lanId
	 */
	@ApiModelProperty(value = "买家")
	private String lnaId;

	/**
	 * 订单项id
	 */
	@ApiModelProperty(value = "订单项")
	private List<TradeResourceInstItem> tradeResourceInstItemList;

	/**
	 * 订单号
	 */
	@ApiModelProperty(value = "订单号")
	private String orderId;

}
