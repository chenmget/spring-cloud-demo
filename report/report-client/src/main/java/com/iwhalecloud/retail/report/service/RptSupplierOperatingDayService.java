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
	void hqRptSupplierOperatingDayData();
	//获取供应商列表
	List<ParMerchantResp> hqParMerchantInfo();
	//获取当前库存
	List<MktResInstResq> hqMktResInstInfo(String mktResStoreId);
	//获取入库信息
	List<MktResEventruchu> hqmktResEventInfoRu (MktResInstEventReq req);
	//获取出库信息
	List<MktResEventruchu> hqmktResEventInfoChu (MktResInstEventReq req);
	//获取事件变动的数量
	String hqEventTypeNum(String mktResEventId);
	//获取进货金额
	PurchaseAmountResp hqPurchaseAmount(String orderId);
	//看入库的机型在库存里面有没有记录
	String hqIsHaveRecord(MktResInstEventReq req);
	//插表
	void getDataForRptSupplierOperatingDay(RptSupplierOperatingDayReq req) ;
	//更新入库
	void updateRptSupplierRu(RptSupplierOperatingDayReq req);
	//更新出库
	void updateRptSupplierChu(RptSupplierOperatingDayReq req);
}
