package com.iwhalecloud.retail.order2b.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

/**
 * OrderRecommender
 * @author he.sw
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型ord_order_recommender, 对应实体OrderRecommend类")
public class OrderRecommenderResp implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
  	 * recommendId
  	 */
	@ApiModelProperty(value = "recommenderId")
  	private java.lang.String recommenderId;
  	/**
  	 * orderId
  	 */
	@ApiModelProperty(value = "orderId")
  	private java.lang.String orderId;
	/**
	 * staffId
	 */
	@ApiModelProperty(value = "staffId")
	private java.lang.String staffId;
	/**
	 * staffName
	 */
	@ApiModelProperty(value = "staffName")
	private java.lang.String staffName;
	/**
  	 * goodId
  	 */
	@ApiModelProperty(value = "goodId")
  	private java.lang.String goodId;
	/**
  	 * goodName
  	 */
	@ApiModelProperty(value = "goodName")
  	private java.lang.String goodName;
	/**
  	 * goodName
  	 */
	@ApiModelProperty(value = "goodPrice")
  	private java.lang.Double goodPrice;
	/**
	 * goodAmount
	 */
	@ApiModelProperty(value = "goodAmount")
	private java.lang.Integer goodAmount;
	/**
  	 * shippingId(厅店)
  	 */
	@ApiModelProperty(value = "shopId")
  	private java.lang.String shopId;
	/**
	 * shopName(厅店名称)
	 */
	@ApiModelProperty(value = "shopName")
	private java.lang.String shopName;
	/**
	 * shopAddress(厅店地址)
	 */
	@ApiModelProperty(value = "shopAddress")
	private java.lang.String shopAddress;
	/**
  	 * customerPhone
  	 */
	@ApiModelProperty(value = "customerPhone")
  	private java.lang.String customerPhone;
  	/**
  	 * orderPrice
  	 */
	@ApiModelProperty(value = "orderPrice")
  	private java.lang.Double orderPrice;
	/**
  	 * percentage(提成)
  	 */
	@ApiModelProperty(value = "percentage")
  	private java.lang.Double percentage;
	/**
  	 * percentage(总提成)
  	 */
	@ApiModelProperty(value = "totalPercentage")
  	private java.lang.Double totalPercentage;
	/**
  	 * percentage(总揽装金额)
  	 */
	@ApiModelProperty(value = "totalOrderMoney")
  	private java.lang.Double totalOrderMoney;
	/**
	 * percentage(揽装总量)
	 */
	@ApiModelProperty(value = "totalOrderAmount")
	private java.lang.Integer totalOrderAmount;
	/**
  	 * createTime
  	 */
	@ApiModelProperty(value = "createDate")
  	private java.util.Date createDate;


}
