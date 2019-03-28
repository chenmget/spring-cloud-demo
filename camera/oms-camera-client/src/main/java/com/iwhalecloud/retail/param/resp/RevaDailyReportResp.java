package com.iwhalecloud.retail.param.resp;

import com.iwhalecloud.retail.param.resp.dto.CustomerDto;

import java.util.List;

/**
 * @Description 客群日报表接口-返回参数
 * @author zhangJun
 * @date 2018年9月6日
 * @version 1.0
 */
public class RevaDailyReportResp {
	
	
	private int weekCount;
	private String lastOrdinaryFemalesage;
	private String lastOrdinaryFemalesage3;
	private String lastOrdinaryFemalesage1;
	private String lastOrdinaryFemalesage4;
	private String lastOrdinaryFemalesage5;
	private String lastOrdinaryMalesage3;
	private String lastOrdinaryMalesage4;
	private String lastOrdinaryMalesage1;
	private String lastOrdinaryMalesage2;
	private String lastOrdinaryVips;
	private String lastOrdinaryMalesage5;
    List<CustomerDto> customer;
	public String getLastOrdinaryFemalesage() {
		return lastOrdinaryFemalesage;
	}

	public void setLastOrdinaryFemalesage(String lastOrdinaryFemalesage) {
		this.lastOrdinaryFemalesage = lastOrdinaryFemalesage;
	}

	public String getLastOrdinaryFemalesage3() {
		return lastOrdinaryFemalesage3;
	}

	public void setLastOrdinaryFemalesage3(String lastOrdinaryFemalesage3) {
		this.lastOrdinaryFemalesage3 = lastOrdinaryFemalesage3;
	}

	public String getLastOrdinaryFemalesage1() {
		return lastOrdinaryFemalesage1;
	}

	public void setLastOrdinaryFemalesage1(String lastOrdinaryFemalesage1) {
		this.lastOrdinaryFemalesage1 = lastOrdinaryFemalesage1;
	}

	public String getLastOrdinaryFemalesage4() {
		return lastOrdinaryFemalesage4;
	}

	public void setLastOrdinaryFemalesage4(String lastOrdinaryFemalesage4) {
		this.lastOrdinaryFemalesage4 = lastOrdinaryFemalesage4;
	}

	public String getLastOrdinaryFemalesage5() {
		return lastOrdinaryFemalesage5;
	}

	public void setLastOrdinaryFemalesage5(String lastOrdinaryFemalesage5) {
		this.lastOrdinaryFemalesage5 = lastOrdinaryFemalesage5;
	}

	public String getLastOrdinaryMalesage3() {
		return lastOrdinaryMalesage3;
	}

	public void setLastOrdinaryMalesage3(String lastOrdinaryMalesage3) {
		this.lastOrdinaryMalesage3 = lastOrdinaryMalesage3;
	}

	public String getLastOrdinaryMalesage4() {
		return lastOrdinaryMalesage4;
	}

	public void setLastOrdinaryMalesage4(String lastOrdinaryMalesage4) {
		this.lastOrdinaryMalesage4 = lastOrdinaryMalesage4;
	}

	public String getLastOrdinaryMalesage1() {
		return lastOrdinaryMalesage1;
	}

	public void setLastOrdinaryMalesage1(String lastOrdinaryMalesage1) {
		this.lastOrdinaryMalesage1 = lastOrdinaryMalesage1;
	}

	public String getLastOrdinaryMalesage2() {
		return lastOrdinaryMalesage2;
	}

	public void setLastOrdinaryMalesage2(String lastOrdinaryMalesage2) {
		this.lastOrdinaryMalesage2 = lastOrdinaryMalesage2;
	}

	public String getLastOrdinaryVips() {
		return lastOrdinaryVips;
	}

	public void setLastOrdinaryVips(String lastOrdinaryVips) {
		this.lastOrdinaryVips = lastOrdinaryVips;
	}

	public String getLastOrdinaryMalesage5() {
		return lastOrdinaryMalesage5;
	}

	public void setLastOrdinaryMalesage5(String lastOrdinaryMalesage5) {
		this.lastOrdinaryMalesage5 = lastOrdinaryMalesage5;
	}

	public List<CustomerDto> getCustomer() {
		return customer;
	}

	public void setCustomer(List<CustomerDto> customer) {
		this.customer = customer;
	}

	public int getWeekCount() {
		return weekCount;
	}

	public void setWeekCount(int weekCount) {
		this.weekCount = weekCount;
	}

	

}
