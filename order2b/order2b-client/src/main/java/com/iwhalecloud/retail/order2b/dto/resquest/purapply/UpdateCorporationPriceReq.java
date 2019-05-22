package com.iwhalecloud.retail.order2b.dto.resquest.purapply;

import com.iwhalecloud.retail.dto.PageVO;

import lombok.Data;

@Data
public class UpdateCorporationPriceReq extends PageVO {
	
	private static final long serialVersionUID = 1L;
	private String productId;	//产品ID
	private String corporationPrice;  //政企价格

}
