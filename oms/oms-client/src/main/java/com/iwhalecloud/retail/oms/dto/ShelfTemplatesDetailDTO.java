package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ShelfTemplatesDetail
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型SHELF_TEMPLATES_DETAIL, 对应实体ShelfTemplatesDetail类")
public class ShelfTemplatesDetailDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * id
  	 */
	@ApiModelProperty(value = "id")
  	private java.lang.Long id;
	
	/**
  	 * gmtCreate
  	 */
	@ApiModelProperty(value = "gmtCreate")
  	private java.util.Date gmtCreate;
	
	/**
  	 * gmtModified
  	 */
	@ApiModelProperty(value = "gmtModified")
  	private java.util.Date gmtModified;
	
	/**
  	 * creator
  	 */
	@ApiModelProperty(value = "creator")
  	private java.lang.String creator;
	
	/**
  	 * modifier
  	 */
	@ApiModelProperty(value = "modifier")
  	private java.lang.String modifier;
	
	/**
  	 * isDeleted
  	 */
	@ApiModelProperty(value = "isDeleted")
  	private java.lang.Integer isDeleted;
	
	/**
  	 * defCategoryId
  	 */
	@ApiModelProperty(value = "defCategoryId")
  	private java.lang.String defCategoryId;
	
	/**
  	 * shelfTemplatesNumber
  	 */
	@ApiModelProperty(value = "shelfTemplatesNumber")
  	private java.lang.String shelfTemplatesNumber;
	
	/**
  	 * operatingPositionId
  	 */
	@ApiModelProperty(value = "operatingPositionId")
  	private java.lang.String operatingPositionId;
	
  	
}
