package com.iwhalecloud.retail.goods.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ProdSpecValues
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_spec_values, 对应实体ProdSpecValues类")
public class ProdSpecValuesDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 值ID
  	 */
	@ApiModelProperty(value = "值ID")
  	private String specValueId;
	
	/**
  	 * 规格ID
  	 */
	@ApiModelProperty(value = "规格ID")
  	private String specId;
	
	/**
  	 * 值名称
  	 */
	@ApiModelProperty(value = "值名称")
  	private String specValue;
	
	/**
  	 * 值类型
  	 */
	@ApiModelProperty(value = "值类型")
  	private Long specType;

	/**
	 * 产品ID
	 */
	@ApiModelProperty(value = "产品ID")
	private String  productId;

	/**
	 * 价格
	 */
	@ApiModelProperty(value = "价格")
	private String  price;

	/**
	 * 库存
	 */
	@ApiModelProperty(value = "库存")
	private String  store;
}
