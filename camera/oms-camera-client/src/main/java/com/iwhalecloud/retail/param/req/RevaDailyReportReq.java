package com.iwhalecloud.retail.param.req;


/**
 * @Description 客群日报表接口-请求参数
 * @author  zhangJun
 * @date    2018年9月6日
 * @version 1.0
 */
public class RevaDailyReportReq {

	
	private String date;
	private String lastDate;
	private String province;
	private String city;
	private String areacode;
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getLastDate() {
		return lastDate;
	}
	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAreacode() {
		return areacode;
	}
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	
	
	
}
