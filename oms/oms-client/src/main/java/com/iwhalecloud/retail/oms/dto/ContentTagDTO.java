package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * ContentTag
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型t_content_tag, 对应实体ContentTag类")
public class ContentTagDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 关联ID
  	 */
	@ApiModelProperty(value = "关联ID")
  	private java.lang.Long relaId;
	
	/**
  	 * 内容ID
  	 */
	@ApiModelProperty(value = "内容ID")
  	private java.lang.Long contentId;
	
	/**
  	 * 标签ID
  	 */
	@ApiModelProperty(value = "标签ID")
  	private java.lang.Long tagId;
	
	/**
  	 * 操作人
  	 */
	@ApiModelProperty(value = "操作人")
  	private java.lang.String oprId;
	
	/**
  	 * 操作时间	
  	 */
	@ApiModelProperty(value = "操作时间	")
  	private java.util.Date oprDate;

	public Long getRelaId() {
		return relaId;
	}

	public void setRelaId(Long relaId) {
		this.relaId = relaId;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public String getOprId() {
		return oprId;
	}

	public void setOprId(String oprId) {
		this.oprId = oprId;
	}

	public Date getOprDate() {
		return oprDate;
	}

	public void setOprDate(Date oprDate) {
		this.oprDate = oprDate;
	}
}
