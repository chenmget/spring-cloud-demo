package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * ContentPic
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型t_content_pic, 对应实体ContentPic类")
public class ContentPicDTO implements java.io.Serializable {
    
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
  	 * objtype
  	 */
	@ApiModelProperty(value = "objtype")
  	private java.lang.Integer objtype;
	
	/**
  	 * objid
  	 */
	@ApiModelProperty(value = "objid")
  	private java.lang.String objid;

	@ApiModelProperty(value = "商品名称")
	private String objName;
	
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

	public Integer getObjtype() {
		return objtype;
	}

	public void setObjtype(Integer objtype) {
		this.objtype = objtype;
	}

	public String getObjid() {
		return objid;
	}

	public void setObjid(String objid) {
		this.objid = objid;
	}

	public String getObjurl() {
		return objurl;
	}

	public void setObjurl(String objurl) {
		this.objurl = objurl;
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
