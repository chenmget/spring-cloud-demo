package com.iwhalecloud.retail.order2b.dto.resquest.purapply;

import com.iwhalecloud.retail.dto.PageVO;

import lombok.Data;

@Data
public class UpdateCorporationPriceReq extends PageVO {
	
	private static final long serialVersionUID = 1L;
	private String sn;	//营销资源实例编码
//	private String productId;	//产品ID
	private String corporationPrice;  //政企价格
	private String productPrice;	//产品ID和政企价格合起来	

}
