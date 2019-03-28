package com.iwhalecloud.retail.param.req;


/**
 * @Description 客群日报表接口-请求参数
 * @author  zhangJun
 * @date    2018年9月6日
 * @version 1.0
 */
public class QueryRepeatsInfoReq {

	
	private String repeatsId;//回头客id
	private String personId;//vip

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}
	
	public String getRepeatsId() {
		return repeatsId;
	}

	public void setRepeatsId(String repeatsId) {
		this.repeatsId = repeatsId;
	}

	
	
	
	
}
