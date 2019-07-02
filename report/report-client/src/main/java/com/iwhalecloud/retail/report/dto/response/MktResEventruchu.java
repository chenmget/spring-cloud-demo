package com.iwhalecloud.retail.report.dto.response;

import com.iwhalecloud.retail.dto.PageVO;
import lombok.Data;

@Data
public class MktResEventruchu extends PageVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String productBaseId;
	private String mktResEventId;
	private String eventType;
	private String objId;
	private String productId;
	private String goodsId;
	private String price;
	private String typeId;
}