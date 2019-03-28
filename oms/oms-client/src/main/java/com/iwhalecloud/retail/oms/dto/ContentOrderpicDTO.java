package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ContentOrderpic
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型t_content_orderpic, 对应实体ContentOrderpic类")
public class ContentOrderpicDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * matid
  	 */
	@ApiModelProperty(value = "matid")
  	private java.lang.Long matid;
	
	/**
  	 * contentid
  	 */
	@ApiModelProperty(value = "contentid")
  	private java.lang.Long contentid;
	
	/**
  	 * playorder
  	 */
	@ApiModelProperty(value = "playorder")
  	private java.lang.Integer playorder;
	
	/**
  	 * objtype
  	 */
	@ApiModelProperty(value = "objtype")
  	private java.lang.Integer objtype;
	
	/**
  	 * objid
  	 */
	@ApiModelProperty(value = "objid")
  	private java.lang.String objid;
	
	/**
  	 * objurl
  	 */
	@ApiModelProperty(value = "objurl")
  	private java.lang.String objurl;
	
	/**
  	 * oprid
  	 */
	@ApiModelProperty(value = "oprid")
  	private java.lang.String oprid;
	
	/**
  	 * upddate
  	 */
	@ApiModelProperty(value = "upddate")
  	private java.util.Date upddate;
	
  	
}
