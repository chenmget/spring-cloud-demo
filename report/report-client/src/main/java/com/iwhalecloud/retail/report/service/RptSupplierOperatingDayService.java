package com.iwhalecloud.retail.report.service;

import java.util.List;

import com.iwhalecloud.retail.report.dto.request.MktResInstEventReq;
import com.iwhalecloud.retail.report.dto.request.RptSupplierOperatingDayReq;
import com.iwhalecloud.retail.report.dto.response.MktResEventruchu;
import com.iwhalecloud.retail.report.dto.response.MktResInstResq;
import com.iwhalecloud.retail.report.dto.response.ParMerchantResp;
import com.iwhalecloud.retail.report.dto.response.PurchaseAmountResp;

public interface RptSupplierOperatingDayService {
	
	//获取供应商经营日报表数据
	public void hqRptSupplierOperatingDayData();
	
}
