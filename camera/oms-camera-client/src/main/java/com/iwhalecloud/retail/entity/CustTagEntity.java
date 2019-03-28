package com.iwhalecloud.retail.entity;

import java.io.Serializable;


/**
 * 图片识别结果dto
 *
 */
public class CustTagEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String custPhone;// 

	private String tagText;// 

	public String getCustPhone() {
		return custPhone;
	}

	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}

	public String getTagText() {
		return tagText;
	}

	public void setTagText(String tagText) {
		this.tagText = tagText;
	}

	
}
