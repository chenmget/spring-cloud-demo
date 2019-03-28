package com.iwhalecloud.retail.goods.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ProdGoodsCat
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_goods_cat, 对应实体ProdGoodsCat类")
public class ProdGoodsCatDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * catId
  	 */
	@ApiModelProperty(value = "catId")
  	private String catId;
	
	/**
  	 * 类别名称
  	 */
	@ApiModelProperty(value = "类别名称")
  	private String name;
	
	/**
  	 * 上级类别ID
  	 */
	@ApiModelProperty(value = "上级类别ID")
  	private Long parentId;
	
	/**
  	 * 类别路径
  	 */
	@ApiModelProperty(value = "类别路径")
  	private String catPath;
	
	/**
  	 * 排序
  	 */
	@ApiModelProperty(value = "排序")
  	private Long catOrder;
	
  	
}
