package com.iwhalecloud.retail.web.controller.b2b.rights.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class SaveRightsReq implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 优惠券标识
	 */
	private String mktResId;
	
	/**
	 * 领取数量
	 */
	private Long supplyNum;
	/**
	 * 发放渠道类型LOVB=RES-C-0047
     * 网厅、微信、短信
	 */
	private String provChannelType;
	
}
