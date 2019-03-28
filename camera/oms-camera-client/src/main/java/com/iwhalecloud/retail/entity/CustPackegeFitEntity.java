package com.iwhalecloud.retail.entity;

import java.io.Serializable;


/**
 * 图片识别结果dto
 *
 */
public class CustPackegeFitEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String packageName;// 

	private String custPhone;// 

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getCustPhone() {
		return custPhone;
	}

	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}

	
	
}
