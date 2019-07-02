package com.iwhalecloud.retail.report.dto.response;

import com.iwhalecloud.retail.dto.PageVO;

import lombok.Data;

@Data
public class PurchaseAmountResp extends PageVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String purchaseAmount;//进货金额
	private String purchaseNum;//数量
	private String goodsId;//商品ID

}
