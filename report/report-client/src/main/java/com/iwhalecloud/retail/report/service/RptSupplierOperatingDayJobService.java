package com.iwhalecloud.retail.report.service;

import java.util.List;

import com.iwhalecloud.retail.report.dto.request.MktResInstEventReq;
import com.iwhalecloud.retail.report.dto.request.RptSupplierOperatingDayReq;
import com.iwhalecloud.retail.report.dto.response.MktResEventruchu;
import com.iwhalecloud.retail.report.dto.response.MktResInstResq;
import com.iwhalecloud.retail.report.dto.response.ParMerchantResp;
import com.iwhalecloud.retail.report.dto.response.PurchaseAmountResp;

public interface RptSupplierOperatingDayJobService {
	
	//获取供应商列表
	public List<ParMerchantResp> hqParMerchantInfo();
	//获取当前库存
	public List<MktResInstResq> hqMktResInstInfo(String mktResStoreId);
	//获取入库信息
	public List<MktResEventruchu> hqmktResEventInfoRu (MktResInstEventReq req);
	//获取出库信息
	public List<MktResEventruchu> hqmktResEventInfoChu (MktResInstEventReq req);
	//获取事件变动的数量
	public String hqEventTypeNum(String mktResEventId);
	//获取进货金额
	public PurchaseAmountResp hqPurchaseAmount(String orderId);
	//看入库的机型在库存里面有没有记录
	public String hqIsHaveRecord(MktResInstEventReq req);
	//插表
	public void getDataForRptSupplierOperatingDay(RptSupplierOperatingDayReq req) ;
	//更新入库
	public void updateRptSupplierRu(RptSupplierOperatingDayReq req);
	//更新出库
	public void updateRptSupplierChu(RptSupplierOperatingDayReq req);
}
