package com.iwhalecloud.retail.goods.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ProdTags
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_tags, 对应实体ProdTags类")
public class ProdTagsDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * tagId
  	 */
	@ApiModelProperty(value = "tagId")
  	private String tagId;
	
	/**
  	 * tagName
  	 */
	@ApiModelProperty(value = "tagName")
  	private String tagName;
	
  	
}
