package com.iwhalecloud.retail.report.dto.response;

import com.iwhalecloud.retail.dto.PageVO;

import lombok.Data;

@Data
public class MktResInstResq extends PageVO {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String productId;
	private String productBaseName;
	private String productName;
	private String brandId;
	private String brandName;
	private String priceLevel;
	private String productBaseId;

}
