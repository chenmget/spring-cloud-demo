package com.iwhalecloud.retail.report.dto.request;

import com.iwhalecloud.retail.dto.PageVO;
import lombok.Data;

@Data
public class MktResInstEventReq extends PageVO{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String merchantId;
	private String mktResStoreId;
	private String productBaseId;
}
