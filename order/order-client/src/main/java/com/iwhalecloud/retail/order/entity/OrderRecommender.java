package com.iwhalecloud.retail.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * OrderRecommender
 * @author he.sw
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型ord_order_recommender, 对应实体OrderRecommend类")
@TableName("ord_order_recommender")
public class OrderRecommender implements Serializable {

	private static final long serialVersionUID = 1L;


	/**表名常量*/
    public static final String TNAME = "ord_order_recommender";
  
  	
  	//属性 begin
  	/**
  	 * recommendId
  	 */
  	@TableId(type = IdType.ID_WORKER_STR)
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
	 * userName
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
  	 * shoppingId(厅店)
  	 */
	@ApiModelProperty(value = "shopId")
  	private java.lang.String shopId;
	/**
	 * shoppingId(厅店)
	 */
	@ApiModelProperty(value = "shopName")
	private java.lang.String shopName;
	/**
	 * shoppingAddress厅店地址
	 */
	@ApiModelProperty(value = "shopAddress")
	private java.lang.String shopAddress;
	/**
  	 * customerPhone
  	 */
	@ApiModelProperty(value = "customerPhone")
  	private java.lang.String customerPhone;
  	/**
  	 * createTime
  	 */
	@ApiModelProperty(value = "createDate")
  	private java.util.Date createDate;
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
  	 * percentage(揽装总金额)
  	 */
	@ApiModelProperty(value = "totalOrderMoney")
  	private java.lang.Double totalOrderMoney;
	/**
	 * percentage(揽装总量)
	 */
	@ApiModelProperty(value = "totalOrderAmount")
	private java.lang.Integer totalOrderAmount;
  	

}
