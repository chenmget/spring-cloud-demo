package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * ContentPublish
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型t_content_publish, 对应实体ContentPublish类")
public class ContentPublishDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
	 * ID
	 */
	@ApiModelProperty(value = "ID")
	private java.lang.Long Id;
	/**
  	 * contentid
  	 */
	@ApiModelProperty(value = "contentid")
  	private java.lang.Long contentid;
	
	/**
  	 * waytype
  	 */
	@ApiModelProperty(value = "waytype")
  	private java.lang.String waytype;
	
	/**
  	 * area
  	 */
	@ApiModelProperty(value = "area")
  	private java.lang.String area;
	
	/**
  	 * oprid
  	 */
	@ApiModelProperty(value = "oprid")
  	private java.lang.String oprid;
	
	/**
  	 * publishdate
  	 */
	@ApiModelProperty(value = "publishdate")
  	private java.util.Date publishdate;
	
	/**
  	 * effdate
  	 */
	@ApiModelProperty(value = "effdate")
  	private java.util.Date effdate;
	
	/**
  	 * expdate
  	 */
	@ApiModelProperty(value = "expdate")
  	private java.util.Date expdate;

	/**
	 * tag
	 */
	@ApiModelProperty(value = "tag")
  	private java.lang.String tag;

    /**
     * waytypeList
     */
    @ApiModelProperty(value = "waytypeList")
    private List<String> waytypeList;

    /**
     * areaList
     */
    @ApiModelProperty(value = "areaList")
    private List<String> areaList;
}
