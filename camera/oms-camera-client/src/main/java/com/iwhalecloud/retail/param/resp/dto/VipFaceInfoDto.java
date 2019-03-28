package com.iwhalecloud.retail.param.resp.dto;

/**
 *
 * @Description   会员详情接口-返回参数-会员图片信息
 * @author  zhangJun
 * @date    2018年9月6日
 * @version 1.0
 */
public class VipFaceInfoDto {
	
	private String faceinfoCode;
	private String faceinfoUrl;
	private String star;
	
	
	public String getFaceinfoCode() {
		return faceinfoCode;
	}
	public void setFaceinfoCode(String faceinfoCode) {
		this.faceinfoCode = faceinfoCode;
	}
	public String getFaceinfoUrl() {
		return faceinfoUrl;
	}
	public void setFaceinfoUrl(String faceinfoUrl) {
		this.faceinfoUrl = faceinfoUrl;
	}
	public String getStar() {
		return star;
	}
	public void setStar(String star) {
		this.star = star;
	}
	
	
	
	
}
