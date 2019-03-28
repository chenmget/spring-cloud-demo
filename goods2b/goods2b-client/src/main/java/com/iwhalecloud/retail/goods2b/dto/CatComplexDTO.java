package com.iwhalecloud.retail.goods2b.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * CatComplex
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_cat_complex, 对应实体CatComplex类")
public class CatComplexDTO implements java.io.Serializable {

	private static final long serialVersionUID = 3647086370544241970L;

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
  	 * targetType
  	 */
	@ApiModelProperty(value = "targetType")
  	private String targetType;
	
	/**
  	 * targetId
  	 */
	@ApiModelProperty(value = "targetId")
  	private String targetId;
	
	/**
  	 * targetName
  	 */
	@ApiModelProperty(value = "targetName")
  	private String targetName;
	
	/**
  	 * targetOrder
  	 */
	@ApiModelProperty(value = "targetOrder")
  	private Long targetOrder;
	
	/**
  	 * createDate
  	 */
	@ApiModelProperty(value = "createDate")
  	private java.util.Date createDate;
	
  	
}
