package com.iwhalecloud.retail.order2b.dto.response;

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
@ApiModel(value = "对应模型ord_order_RECOMMENDER, 对应实体OrderRecommend类")
public class OrderRecommenderRankResp implements Serializable {

	private static final long serialVersionUID = 1L;


	/**表名常量*/
    public static final String TNAME = "ord_order_RECOMMENDER";
  
  	/**
  	 * rankNo
  	 */
	@ApiModelProperty(value = "rankNo")
  	private java.lang.Integer rankNo;
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
  	 * shopId(厅店)
  	 */
	@ApiModelProperty(value = "shopId")
  	private java.lang.String shopId;
	/**
	 * shopAddress厅店地址
	 */
	@ApiModelProperty(value = "shopAddress")
	private java.lang.String shopAddress;
	/**
	 * shopName厅店名称
	 */
	@ApiModelProperty(value = "shopName")
	private java.lang.String shopName;

	/**
  	 * percentage(总揽装金额)
  	 */
	@ApiModelProperty(value = "totalOrderMoney")
  	private java.lang.Double totalOrderMoney;
	

}
