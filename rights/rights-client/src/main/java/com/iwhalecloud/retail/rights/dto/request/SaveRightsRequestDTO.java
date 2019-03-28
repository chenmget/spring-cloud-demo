package com.iwhalecloud.retail.rights.dto.request;

import java.io.Serializable;

import com.iwhalecloud.retail.dto.AbstractRequest;
import lombok.Data;

@Data
public class SaveRightsRequestDTO extends AbstractRequest implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 优惠券标识
	 */
	private String mktResId;
	
	/**
	 * 客户编号
	 */
	private String custNum;
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
