package com.iwhalecloud.retail.goods2b.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * TagTel
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_tag_tel, 对应实体TagTel类")
public class TagTelDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1119523945771098330L;
  
  	
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
	 * productId
	 */
	@ApiModelProperty(value = "productId")
	private String productId;
	
  	
}
