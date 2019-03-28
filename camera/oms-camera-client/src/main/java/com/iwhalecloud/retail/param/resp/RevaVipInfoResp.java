package com.iwhalecloud.retail.param.resp;

import com.iwhalecloud.retail.param.resp.dto.VipFaceInfoDto;

import java.util.List;

/**
 * @Description 会员详情接口-返回参数
 * @author zhangJun
 * @date 2018年9月6日
 * @version 1.0
 */
public class RevaVipInfoResp {

	private String personTypeId;
	private String personTypeName;
	private String star;
	private String areaname;
	private String city;
	private String count;
	private List<VipFaceInfoDto> list;
	private String areacode;
	private String cardNo;
	private String personCode;
	private String personName;
	private String province;
	private String phone;//手机号码
	private String nickname;
	private String communication;
	private String vipBinding;
	private String customerNo;
	private String status;
	private String homeAddress;
	public String getPersonTypeId() {
		return personTypeId;
	}
	public void setPersonTypeId(String personTypeId) {
		this.personTypeId = personTypeId;
	}
	public String getPersonTypeName() {
		return personTypeName;
	}
	public void setPersonTypeName(String personTypeName) {
		this.personTypeName = personTypeName;
	}
	public String getStar() {
		return star;
	}
	public void setStar(String star) {
		this.star = star;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public List<VipFaceInfoDto> getList() {
		return list;
	}
	public void setList(List<VipFaceInfoDto> list) {
		this.list = list;
	}
	public String getAreacode() {
		return areacode;
	}
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getPersonCode() {
		return personCode;
	}
	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getCommunication() {
		return communication;
	}
	public void setCommunication(String communication) {
		this.communication = communication;
	}
	public String getVipBinding() {
		return vipBinding;
	}
	public void setVipBinding(String vipBinding) {
		this.vipBinding = vipBinding;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	
	
	
	
	

}
