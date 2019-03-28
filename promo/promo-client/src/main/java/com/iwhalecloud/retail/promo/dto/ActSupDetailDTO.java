package com.iwhalecloud.retail.promo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ActSupDetail
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "")
public class ActSupDetailDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;

	/**
	 * 订单编号
	 */
	@ApiModelProperty(value = "订单编号")
	private String orderId;

	/**
	 * 产品名称
	 */
	@ApiModelProperty(value = "产品名称")
	private String productName;

	/**
	 * 产品型号
	 */
	@ApiModelProperty(value = "产品型号")
	private String unitType;

	/**
	 * 产品颜色
	 */
	@ApiModelProperty(value = "产品颜色")
	private String color;

	/**
	 * 产品内存
	 */
	@ApiModelProperty(value = "产品内存")
	private String memory;

	/**
	 * 产品编码
	 */
	@ApiModelProperty(value = "产品编码")
	private String productCode;

	/**
	 * 订单的创建时间
	 */
	@ApiModelProperty(value = "订单的创建时间")
	private java.util.Date orderTime;

	/**
	 * 串码的发货时间
	 */
	@ApiModelProperty(value = "串码的发货时间")
	private java.util.Date shipTime;

	/**
	 * 价格
	 */
	@ApiModelProperty(value = "价格")
	private Long price;

	/**
	 * 优惠额或补贴额
	 */
	@ApiModelProperty(value = "优惠额或补贴额")
	private Long discount;

	/**
	 * 供应商名称
	 */
	@ApiModelProperty(value = "供应商名称")
	private String supplierName;

	/**
	 * 商家名称
	 */
	@ApiModelProperty(value = "商家名称")
	private String merchantName;

	/**
	 * 规格名称
	 */
	@ApiModelProperty(value = "规格名称")
	private String specName;

}
