package com.iwhalecloud.retail.order2b.dto.resquest.purapply;

import java.io.Serializable;

import com.iwhalecloud.retail.dto.PageVO;

import lombok.Data;

@Data
public class MemMemberAddressReq extends PageVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String addrId;//
	private String MEMBER_ID;//会员ID
	private String COUNTRY;//国家
	private String PROVINCE_ID;//
	private String CITY_ID;//
	private String REGION_ID;//
	private String REGION;
	private String CITY;
	private String PROVINCE;
	private String ADDR;//具体地址
	private String ZIP;//邮箱
	private String EMAIL;//邮箱
	private String IS_DEFAULT;//是否默认地址 1是 0否
	private String CONSIGEE_NAME;//收货人名称
	private String CONSIGEE_MOBILE;//收货人联系方式
	private String LAST_UPDATE;//更新时间

}
