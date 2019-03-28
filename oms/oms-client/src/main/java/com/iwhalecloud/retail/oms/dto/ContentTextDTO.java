package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * ContentText
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型t_content_text, 对应实体ContentText类")
public class ContentTextDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * contentid
  	 */
	@ApiModelProperty(value = "contentid")
  	private java.lang.Long contentid;
	
	/**
  	 * textid
  	 */
	@ApiModelProperty(value = "textid")
  	private java.lang.Long textid;
	
	/**
  	 * url
  	 */
	@ApiModelProperty(value = "url")
  	private java.lang.String url;
	
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

	/**
	 * upddate
	 */
	@ApiModelProperty(value = "softtext")
	private java.lang.String softtext;

	@ApiModelProperty(value = "ContentTextDetail")
	private List<ContentTextDetailDTO> contentTextDetails;

}
