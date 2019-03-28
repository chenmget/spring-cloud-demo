package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * ContentMaterial
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型t_content_material, 对应实体ContentMaterial类")
public class ContentMaterialDTO implements java.io.Serializable {
    
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
  	 * name
  	 */
	@ApiModelProperty(value = "name")
  	private java.lang.String name;
	
	/**
  	 * path
  	 */
	@ApiModelProperty(value = "path")
  	private java.lang.String path;
	
	/**
  	 * thumbpath
  	 */
	@ApiModelProperty(value = "thumbpath")
  	private java.lang.String thumbpath;
	
	/**
  	 * level
  	 */
	@ApiModelProperty(value = "level")
  	private java.lang.Integer lel;
	
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

	public Long getMatid() {
		return matid;
	}

	public void setMatid(Long matid) {
		this.matid = matid;
	}

	public Long getContentid() {
		return contentid;
	}

	public void setContentid(Long contentid) {
		this.contentid = contentid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getThumbpath() {
		return thumbpath;
	}

	public void setThumbpath(String thumbpath) {
		this.thumbpath = thumbpath;
	}

	public Integer getLel() {
		return lel;
	}

	public void setLel(Integer level) {
		this.lel = level;
	}

	public String getOprid() {
		return oprid;
	}

	public void setOprid(String oprid) {
		this.oprid = oprid;
	}

	public Date getUpddate() {
		return upddate;
	}

	public void setUpddate(Date upddate) {
		this.upddate = upddate;
	}
}
