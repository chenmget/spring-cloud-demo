package com.iwhalecloud.retail.goods2b.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * Goods
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_goods, 对应实体Goods类")
public class GoodsDetailDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
	/**
  	 * 商品ID
  	 */
	@ApiModelProperty(value = "商品ID")
  	private String goodsId;

	/**
  	 * 商品名称
  	 */
	@ApiModelProperty(value = "商品名称")
  	private String goodsName;

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
  	 * 商品编码
  	 */
	@ApiModelProperty(value = "商品编码")
  	private String sn;

	/**
	 * 市场价
	 */
	@ApiModelProperty(value = "市场价")
	private Double mktprice;

	/**
  	 * 卖点
  	 */
	@ApiModelProperty(value = "卖点")
  	private String sellingPoint;
	
	/**
	 * 提货价
	 */
	@ApiModelProperty(value = "提货价")
	private Double deliveryPrice;

	/**
	 * 规格别名
	 */
	@ApiModelProperty(value = "规格别名")
	private String specName;
  	
	/**
	 * 产品别名
	 */
	@ApiModelProperty(value = "产品别名")
	private String unitName;

	/**
	 * 品牌名称
	 */
	private String brandName;

	/**
	 * 预售标识(1=预售，0否)
	 */
	private Integer isAdvanceSale;

	/**
	 * 前置补贴标识(1=前置补贴，0否)
	 */
	private String isSubsidy;

	/**
	 * 是否分货
	 */
	@ApiModelProperty(value = "是否分货")
	private Integer isAllot;

	/**
	 * 商品发布对象
	 */
	@ApiModelProperty(value = "商品发布对象")
	private String targetType;

	/**
	 * 前置补贴标识(1=前置补贴，0否)
	 */
	private String isSubsidy;

}
