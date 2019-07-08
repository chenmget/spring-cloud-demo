package com.iwhalecloud.retail.goods2b.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * GoodsProductRel
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_goods_product_rel, 对应实体GoodsProductRel类")
public class GoodsProductRelDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 关联ID
  	 */
	@ApiModelProperty(value = "关联ID")
  	private String goodsProductRelId;

	/**
	 * 产品基本信息ID
	 */
	@ApiModelProperty(value = "产品基本信息ID")
	private String productBaseId;
	
	/**
  	 * 商品ID
  	 */
	@ApiModelProperty(value = "商品ID")
  	private String goodsId;
	
	/**
  	 * 产品ID
  	 */
	@ApiModelProperty(value = "产品ID")
  	private String productId;
	
	/**
  	 * 上架数量
  	 */
	@ApiModelProperty(value = "上架数量")
  	private Long supplyNum;
	
	/**
  	 * 提货价
  	 */
	@ApiModelProperty(value = "提货价")
  	private Long deliveryPrice;
	
	/**
  	 * 最小起批量
  	 */
	@ApiModelProperty(value = "最小起批量")
  	private Long minNum;
	
	/**
  	 * 订购上限
  	 */
	@ApiModelProperty(value = "订购上限")
  	private Long maxNum;
	
	/**
  	 * 规格别名
  	 */
	@ApiModelProperty(value = "规格别名")
  	private String specName;

	/**
	 * 是否有库存
	 */
	@ApiModelProperty(value = "是否有库存")
	private Integer isHaveStock;

	/**
	 * 活动预付款，如预售活动的定金。冗余用字段
	 */
	@ApiModelProperty(value = "活动预付款，如预售活动的定金。冗余用字段")
	private Long advancePayAmount;

	/**
	 * 初始提货价
	 */
	@ApiModelProperty(value = "初始提货价")
	private Long initialPrice;
}
