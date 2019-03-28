package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 默认内容详情
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型DEFAULT_CONTENT, 对应实体DefaultContent类")
public class DefaultContentDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * ID
  	 */
	@ApiModelProperty(value = "ID")
  	private java.lang.Long id;
	
	/**
  	 * 创建时间
  	 */
	@ApiModelProperty(value = "创建时间")
  	private java.util.Date gmtCreate;
	
	/**
  	 * 修改时间
  	 */
	@ApiModelProperty(value = "修改时间")
  	private java.util.Date gmtModified;
	
	/**
  	 * 创建人
  	 */
	@ApiModelProperty(value = "创建人")
  	private java.lang.String creator;
	
	/**
  	 * 修改人	
  	 */
	@ApiModelProperty(value = "修改人	")
  	private java.lang.String modifier;
	
	/**
  	 * 是否删除：0未删、1删除	
  	 */
	@ApiModelProperty(value = "是否删除：0未删、1删除	")
  	private java.lang.Long isDeleted;
	
	/**
  	 * 内容名称	
  	 */
	@ApiModelProperty(value = "内容名称	")
  	private java.lang.String contentTittle;
	
	/**
  	 * 内容标签	
  	 */
	@ApiModelProperty(value = "内容标签	")
  	private java.lang.String contentTips;
	
	/**
  	 * 内容关键字	
  	 */
	@ApiModelProperty(value = "内容关键字	")
  	private java.lang.String contentKeyword;
	
	/**
  	 * 内容简介	
  	 */
	@ApiModelProperty(value = "内容简介	")
  	private java.lang.String contentBrief;
	
	/**
  	 * 内容详情	
  	 */
	@ApiModelProperty(value = "内容详情	")
  	private java.lang.String contentDetail;
	
	/**
  	 * 内容图片	
  	 */
	@ApiModelProperty(value = "内容图片	")
  	private java.lang.String contentPictures;
	
	/**
  	 * 内容扩展属性	
  	 */
	@ApiModelProperty(value = "内容扩展属性	")
  	private java.lang.String contentExtensionInfo;
	
  	
}
