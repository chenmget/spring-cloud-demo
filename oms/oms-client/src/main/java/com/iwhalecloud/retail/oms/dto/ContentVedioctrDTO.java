package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 云货架
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型T_CONTENT_VEDIOCTR, 对应实体ContentVedioctr类")
public class ContentVedioctrDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * ID
  	 */
	@ApiModelProperty(value = "ID")
  	private java.lang.Long id;
	
	/**
  	 * 内容id
  	 */
	@ApiModelProperty(value = "内容id")
  	private java.lang.Long contentId;
	
	/**
  	 * 播放控制ID
  	 */
	@ApiModelProperty(value = "播放控制ID")
  	private java.lang.Long playbackId;
	
	/**
  	 * 货架选择
  	 */
	@ApiModelProperty(value = "货架选择")
  	private java.lang.String storageNum;
	
	/**
  	 * 启动播放时长
  	 */
	@ApiModelProperty(value = "启动播放时长")
  	private java.lang.Long playbackTime;
	
	/**
  	 * 播放顺序
  	 */
	@ApiModelProperty(value = "播放顺序")
  	private java.lang.Long playbackSequence;
	
	/**
  	 * 是否启用
  	 */
	@ApiModelProperty(value = "是否启用")
  	private java.lang.Long isPlayback;
	
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
