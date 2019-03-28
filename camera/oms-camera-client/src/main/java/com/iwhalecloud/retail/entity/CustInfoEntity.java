package com.iwhalecloud.retail.entity;

import java.io.Serializable;


/**
 * 图片识别结果dto
 *
 */
public class CustInfoEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String custPhone;// 

	private String star;// 

	private String isAuthen;// 

	private String birthday;// 

	private String custName;// 

	private String city;// 
	
	private String custImage;// 

	
	public String getCustImage() {
		return custImage;
	}

	public void setCustImage(String custImage) {
		this.custImage = custImage;
	}

	public String getCustPhone() {
		return custPhone;
	}

	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}

	public String getStar() {
		return star;
	}

	public void setStar(String star) {
		this.star = star;
	}

	public String getIsAuthen() {
		return isAuthen;
	}

	public void setIsAuthen(String isAuthen) {
		this.isAuthen = isAuthen;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	
	
}
