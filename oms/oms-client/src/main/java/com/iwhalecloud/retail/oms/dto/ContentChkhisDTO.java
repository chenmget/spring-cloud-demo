package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ContentChkhis
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型t_content_chkhis, 对应实体ContentChkhis类")
public class ContentChkhisDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * logid
  	 */
	@ApiModelProperty(value = "logid")
  	private java.lang.Integer logid;
	
	/**
  	 * contentid
  	 */
	@ApiModelProperty(value = "contentid")
  	private java.lang.Long contentid;
	
	/**
  	 * commitoprid
  	 */
	@ApiModelProperty(value = "commitoprid")
  	private java.lang.String commitoprid;
	
	/**
  	 * commitdate
  	 */
	@ApiModelProperty(value = "commitdate")
  	private java.util.Date commitdate;
	
	/**
  	 * oprid
  	 */
	@ApiModelProperty(value = "oprid")
  	private java.lang.String oprid;
	
	/**
  	 * chkdate
  	 */
	@ApiModelProperty(value = "chkdate")
  	private java.util.Date chkdate;
	
	/**
  	 * result
  	 */
	@ApiModelProperty(value = "result")
  	private java.lang.Integer result;
	
	/**
  	 * desp
  	 */
	@ApiModelProperty(value = "desp")
  	private java.lang.String desp;
	
  	
}
