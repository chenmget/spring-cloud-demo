package com.iwhalecloud.retail.order2b.dto.response.purapply;

import lombok.Data;

import java.io.Serializable;

@Data
public class PurApplyItemResp   implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String purPrice;
	private String productId;


}
