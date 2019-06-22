package com.iwhalecloud.retail.order.dto;

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
@ApiModel(value = "对应模型ord_order_RECOMMEND, 对应实体OrderRecommend类")
public class OrderRecommenderDTO implements Serializable {

	private static final long serialVersionUID = 1L;


	/**表名常量*/
    public static final String TNAME = "ord_order_RECOMMEND";
  
  	
  	//属性 begin
  	/**
  	 * recommendId
  	 */
	@ApiModelProperty(value = "recommendId")
  	private java.lang.String recommendId;
  	/**
  	 * orderId
  	 */
	@ApiModelProperty(value = "orderId")
  	private java.lang.String orderId;
	/**
	 * userId
	 */	
	@ApiModelProperty(value = "userId")
	private java.lang.String userId;
  	/**
  	 * createTime
  	 */
	@ApiModelProperty(value = "createTime")
  	private java.util.Date createTime;
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
  	

  		

}
