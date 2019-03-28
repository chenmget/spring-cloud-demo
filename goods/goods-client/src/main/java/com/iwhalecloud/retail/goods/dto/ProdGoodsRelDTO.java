package com.iwhalecloud.retail.goods.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ProdGoodsRel
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_goods_rel, 对应实体ProdGoodsRel类")
public class ProdGoodsRelDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 关联ID
  	 */
	@ApiModelProperty(value = "关联ID")
  	private String relId;
	
	/**
  	 * A端商品id
  	 */
	@ApiModelProperty(value = "A端商品id")
  	private String agoodsId;
	
	/**
  	 * Z端商品id
  	 */
	@ApiModelProperty(value = "Z端商品id")
  	private String zgoodsId;
	
	/**
  	 * 合约关联套餐CONTRACT_OFFER、终端关联购买计划TERMINAL_PLAN
  	 */
	@ApiModelProperty(value = "合约关联套餐CONTRACT_OFFER、终端关联购买计划TERMINAL_PLAN")
  	private String relType;
	
  	
}
