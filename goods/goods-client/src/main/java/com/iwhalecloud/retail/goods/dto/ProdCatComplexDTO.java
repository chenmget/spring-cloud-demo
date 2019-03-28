package com.iwhalecloud.retail.goods.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ProdCatComplex
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_cat_complex, 对应实体ProdCatComplex类")
public class ProdCatComplexDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * id
  	 */
	@ApiModelProperty(value = "id")
  	private String id;
	
	/**
  	 * catId
  	 */
	@ApiModelProperty(value = "catId")
  	private String catId;
	
	/**
  	 * goodsId
  	 */
	@ApiModelProperty(value = "goodsId")
  	private String goodsId;
	
	/**
  	 * 商品名称
  	 */
	@ApiModelProperty(value = "商品名称")
  	private String goodsName;
	
	/**
  	 * createTime
  	 */
	@ApiModelProperty(value = "createTime")
  	private java.util.Date createTime;
	
  	
}
