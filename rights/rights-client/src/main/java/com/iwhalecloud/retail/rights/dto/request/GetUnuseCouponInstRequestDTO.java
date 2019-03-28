package com.iwhalecloud.retail.rights.dto.request;

import java.io.Serializable;

import com.iwhalecloud.retail.dto.AbstractRequest;
import lombok.Data;

@Data
public class GetUnuseCouponInstRequestDTO extends AbstractRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 权益内容标识
	 */
	private String mktResId;
	
	/**
	 * 实例状态
	 */
	private String statusCd;
	
	/**
	 * 领取数量
	 */
	private Long supplyNum;
	
	
}
