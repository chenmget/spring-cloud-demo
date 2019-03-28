package com.iwhalecloud.retail.goods2b.dto;

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
public class TagsDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
	 * tagId
	 */
	@ApiModelProperty(value = "tagId")
	private String tagId;

	/**
	 * tagType
	 */
	@ApiModelProperty(value = "tagType")
	private String tagType;

	/**
	 * tagName
	 */
	@ApiModelProperty(value = "tagName")
	private String tagName;

	/**
	 * createStaff
	 */
	@ApiModelProperty(value = "createStaff")
	private String createStaff;

	/**
	 * updateStaff
	 */
	@ApiModelProperty(value = "updateStaff")
	private String updateStaff;

}
