package com.iwhalecloud.retail.param.resp;

import com.iwhalecloud.retail.entity.CustInfoEntity;
import com.iwhalecloud.retail.entity.CustPackegeBuyEntity;
import com.iwhalecloud.retail.entity.CustPackegeFitEntity;
import com.iwhalecloud.retail.entity.CustTagEntity;

import java.util.List;

/**
 * @Description 返回参数
 * @author zhangJun
 * @date 2018年9月6日
 * @version 1.0
 */
public class CameraVipInfoResp {
	
	
	private CustInfoEntity custInfoEntity;
	private List<CustPackegeBuyEntity> buyList;
	private List<CustPackegeFitEntity> fitList;
	private List<CustTagEntity> tagList;
	public CustInfoEntity getCustInfoEntity() {
		return custInfoEntity;
	}
	public void setCustInfoEntity(CustInfoEntity custInfoEntity) {
		this.custInfoEntity = custInfoEntity;
	}
	public List<CustPackegeBuyEntity> getBuyList() {
		return buyList;
	}
	public void setBuyList(List<CustPackegeBuyEntity> buyList) {
		this.buyList = buyList;
	}
	public List<CustPackegeFitEntity> getFitList() {
		return fitList;
	}
	public void setFitList(List<CustPackegeFitEntity> fitList) {
		this.fitList = fitList;
	}
	public List<CustTagEntity> getTagList() {
		return tagList;
	}
	public void setTagList(List<CustTagEntity> tagList) {
		this.tagList = tagList;
	}
	
	
	
	
	
}
