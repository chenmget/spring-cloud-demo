package com.iwhalecloud.retail.order.dto.resquest;

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
@ApiModel(value = "对应模型ord_order_RECOMMEND, 对应实体OrderRecommend类")
public class AddOrderRecommenderReqDTO implements Serializable {

	private static final long serialVersionUID = 1L;


	/**表名常量*/
    public static final String TNAME = "ord_order_RECOMMEND";
  
  	/**
  	 * orderId
  	 */
	@ApiModelProperty(value = "orderId")
  	private java.lang.String orderId;
	/**
	 * userId
	 */
	@ApiModelProperty(value = "staffId")
	private java.lang.String staffId;
  	
}
