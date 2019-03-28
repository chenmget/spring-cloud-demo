package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 软文内容详情表
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型T_CONTENT_TEXT_DETAIL, 对应实体ContentTextDetail类")
public class ContentTextDetailDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * ID
  	 */
	@ApiModelProperty(value = "ID")
  	private Long id;
	
	/**
  	 * 软文ID
  	 */
	@ApiModelProperty(value = "软文ID")
  	private Long textId;
	
	/**
  	 * 软文内容ID
  	 */
	@ApiModelProperty(value = "软文内容ID")
  	private Long txtContentId;
	
	/**
  	 * 软文内容类型
  	 */
	@ApiModelProperty(value = "软文内容类型")
  	private Integer txtContentType;
	
	/**
  	 * 软文内容序列
  	 */
	@ApiModelProperty(value = "软文内容序列")
  	private Integer txtContentSeq;
	
	/**
  	 * 软文内容值
  	 */
	@ApiModelProperty(value = "软文内容值")
  	private String txtContentData;
	
	/**
  	 * 创建时间
  	 */
	@ApiModelProperty(value = "创建时间")
  	private java.util.Date gmtCreat;
	
	/**
  	 * 修改时间
  	 */
	@ApiModelProperty(value = "修改时间")
  	private java.util.Date gmtModify;
	
  	
}
