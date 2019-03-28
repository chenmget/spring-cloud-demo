package com.iwhalecloud.retail.param.req;


/**
 * @Description 客群周报表接口-请求参数
 * @author  zhangJun
 * @date    2018年9月6日
 * @version 1.0
 */
public class RevaWeekReportReq {
	

	private	int week;
	private	int year;
	private	int lastWeek;
	private	int lastYear;
	private String startDate;
	private String endDate;
	private String province;
	private String city;
	private String areacode;
	
	
	
	
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getLastWeek() {
		return lastWeek;
	}
	public void setLastWeek(int lastWeek) {
		this.lastWeek = lastWeek;
	}
	public int getLastYear() {
		return lastYear;
	}
	public void setLastYear(int lastYear) {
		this.lastYear = lastYear;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
