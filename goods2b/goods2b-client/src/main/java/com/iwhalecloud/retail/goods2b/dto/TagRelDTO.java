package com.iwhalecloud.retail.goods2b.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ProdTagRel
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_tag_rel, 对应实体ProdTagRel类")
public class TagRelDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * relId
  	 */
	@ApiModelProperty(value = "relId")
  	private String relId;
	
	/**
  	 * tagId
  	 */
	@ApiModelProperty(value = "tagId")
  	private String tagId;

	/**
	 * goodsId
	 */
	@ApiModelProperty(value = "productBaseId")
	private String productBaseId;
	
  	
}
