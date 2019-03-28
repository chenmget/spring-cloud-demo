package com.iwhalecloud.retail.order.dto.resquest;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import com.iwhalecloud.retail.order.dto.PageVO;

/**
 * OrderRecommender
 * @author he.sw
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型ord_order_RECOMMENDER, 对应实体OrderRecommend类")
public class ListOrderRecommenderReq extends PageVO{

	private static final long serialVersionUID = 1L;

  	/**
  	 * orderId
  	 */
  	private java.lang.String orderId;
	/**
	 * staffId
	 */
	private java.lang.String staffId;

}
