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
	private String memberId;//会员ID
	private String country;//国家
	private String provinceId;//
	private String cityId;//
	private String regionId;//
	private String region;
	private String city;
	private String province;
	private String addr;//具体地址
	private String zip;//邮箱
	private String emall;//邮箱
	private String isEffect;//是否默认地址 1是 0否
	private String isDefault;//是否默认地址 1是 0否
	private String consigeeName;//收货人名称
	private String consigeeMobile;//收货人联系方式
	private String lastUpdate;//更新时间

}
