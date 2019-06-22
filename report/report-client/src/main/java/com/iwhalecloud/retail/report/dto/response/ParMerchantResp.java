package com.iwhalecloud.retail.report.dto.response;

import com.iwhalecloud.retail.dto.PageVO;
import lombok.Data;

@Data
public class ParMerchantResp extends PageVO {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String merchantId;
	private String merchantCode;
	private String merchantName;
	private String lanId;
	private String city;
	
}
