package com.iwhalecloud.retail.goods.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ProdBrand
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_brand, 对应实体ProdBrand类")
public class ProdBrandDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 品牌ID
  	 */
	@ApiModelProperty(value = "品牌ID")
  	private String brandId;
	
	/**
  	 * 品牌名称
  	 */
	@ApiModelProperty(value = "品牌名称")
  	private String name;
	
	/**
  	 * 品牌网站
  	 */
	@ApiModelProperty(value = "品牌网站")
  	private String url;
	
	/**
  	 * 品牌编码
  	 */
	@ApiModelProperty(value = "品牌编码")
  	private String brandCode;
	
	/**
  	 * 品牌描述
  	 */
	@ApiModelProperty(value = "品牌描述")
  	private String brief;
	
  	
}
