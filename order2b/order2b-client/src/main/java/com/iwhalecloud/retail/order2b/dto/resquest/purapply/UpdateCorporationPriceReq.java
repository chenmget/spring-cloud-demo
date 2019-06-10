package com.iwhalecloud.retail.order2b.dto.resquest.purapply;

import java.util.List;

import com.iwhalecloud.retail.dto.PageVO;

import lombok.Data;

@Data
public class UpdateCorporationPriceReq extends PageVO {
	
	private static final long serialVersionUID = 1L;
	private String productId;	//产品ID
	private String applyUserId;
	private String corporationPrice;  //政企价格
//	private String productBaseId;
	private List<String> productPrice;	//产品ID和政企价格合起来
	private List<String> productList;
	private List<String> priceList;
	private String batchId;

}
