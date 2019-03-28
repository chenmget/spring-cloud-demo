package com.iwhalecloud.retail.goods2b.dto;

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
@ApiModel(value = "对应模型prod_cat, 对应实体ProdCat类")
public class CatDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * catId
  	 */
	@ApiModelProperty(value = "catId")
	private String catId;

	@ApiModelProperty(value = "类别名称")
	private String catName;

	@ApiModelProperty(value = "上级类别ID")
	private String parentCatId;

	@ApiModelProperty(value = "类别路径")
	private String catPath;

	@ApiModelProperty(value = "排序")
	private Integer catOrder;

	@ApiModelProperty(value = "类型ID")
	private String typeId;
	
  	
}
