package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * ContentBase
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型t_content_base, 对应实体ContentBase类")
public class ContentBaseDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 内容ID
  	 */
	@ApiModelProperty(value = "内容ID")
  	private java.lang.Long contentId;
	
	/**
  	 * 内容ID
  	 */
	@ApiModelProperty(value = "内容ID")
  	private java.lang.String title;
	
	/**
  	 * 内容说明	
  	 */
	@ApiModelProperty(value = "内容说明	")
  	private java.lang.String desp;
	
	/**
  	 * 归属目录ID
  	 */
	@ApiModelProperty(value = "归属目录ID")
  	private java.lang.Long catalogId;
	
	/**
  	 * 生效时间
  	 */
	@ApiModelProperty(value = "生效时间")
  	private java.util.Date effDate;
	
	/**
  	 * 失效时间	
  	 */
	@ApiModelProperty(value = "失效时间	")
  	private java.util.Date expDate;
	
	/**
  	 * 内容类型
  	 */
	@ApiModelProperty(value = "内容类型")
  	private java.lang.Integer type;
	
	/**
  	 * 内容状态
  	 */
	@ApiModelProperty(value = "内容状态")
  	private java.lang.Integer status;
	
	/**
  	 * 操作人
  	 */
	@ApiModelProperty(value = "操作人")
  	private java.lang.String oprId;
	
	/**
  	 * 更新时间
  	 */
	@ApiModelProperty(value = "更新时间")
  	private java.util.Date updDate;
	
	/**
  	 * 文案
  	 */
	@ApiModelProperty(value = "文案")
  	private java.lang.String copywriter;
	
	/**
  	 * 最大图片数
  	 */
	@ApiModelProperty(value = "最大图片数")
  	private java.lang.Integer maxpicnum;
	
	/**
  	 * 轮播间隔
  	 */
	@ApiModelProperty(value = "轮播间隔")
  	private java.lang.Integer playinterval;

	@ApiModelProperty("归属区域")
	private String areaCode;

	/**
	 * 操作类型标签
	 */
	@ApiModelProperty(value = "操作类型标签")
	private java.lang.String tag;

	/**
	 * 素材
	 */
	@ApiModelProperty(value = "素材")
	private List<ContentMaterialDTO> contentMaterials;

}
