package com.iwhalecloud.retail.goods.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ProdGoodsSpec
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_goods_spec, 对应实体ProdGoodsSpec类")
public class ProdGoodsSpecDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * relId
  	 */
	@ApiModelProperty(value = "relId")
  	private String relId;
	
	/**
  	 * 规格ID
  	 */
	@ApiModelProperty(value = "规格ID")
  	private String specId;
	
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
  	 * 规格值ID
  	 */
	@ApiModelProperty(value = "规格值ID")
  	private String specValueId;
	
  	
}
