package com.iwhalecloud.retail.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.report.dto.request.MktResInstEventReq;
import com.iwhalecloud.retail.report.dto.request.RptSupplierOperatingDayReq;
import com.iwhalecloud.retail.report.dto.response.MktResEventruchu;
import com.iwhalecloud.retail.report.dto.response.MktResInstResq;
import com.iwhalecloud.retail.report.dto.response.ParMerchantResp;
import com.iwhalecloud.retail.report.dto.response.PurchaseAmountResp;
import com.iwhalecloud.retail.report.manager.RptSupplierOperatingDayManager;
import com.iwhalecloud.retail.report.service.RptSupplierOperatingDayJobService;

@Service
public class RptSupplierOperatingDayServiceImpl implements RptSupplierOperatingDayJobService {

	@Autowired
	private RptSupplierOperatingDayManager rptSupplierOperatingDayManager;
	
	//获取供应商列表
	@Override
	public List<ParMerchantResp> hqParMerchantInfo(){
		return rptSupplierOperatingDayManager.hqParMerchantInfo();
	}
	
	//获取当前库存
	@Override
	public List<MktResInstResq> hqMktResInstInfo(String mktResStoreId) {
        return rptSupplierOperatingDayManager.hqMktResInstInfo(mktResStoreId);
    }
	
	//获取入库记录信息
	@Override
	public List<MktResEventruchu> hqmktResEventInfoRu (MktResInstEventReq req) {
		return rptSupplierOperatingDayManager.hqmktResEventInfoRu(req);
	}
	
	//获取入库记录信息
	@Override
	public List<MktResEventruchu> hqmktResEventInfoChu (MktResInstEventReq req) {
		return rptSupplierOperatingDayManager.hqmktResEventInfoChu(req);
	}
	
	//获取变动事件的数量
	@Override
	public String hqEventTypeNum (String mktResEventId) {
		return rptSupplierOperatingDayManager.hqEventTypeNum(mktResEventId);
	}
	
	//获取进货金额
	@Override
	public PurchaseAmountResp hqPurchaseAmount(String orderId) {
		return rptSupplierOperatingDayManager.hqPurchaseAmount(orderId);
	}
	//看入库的机型在库存里面有没有记录
	@Override
	public String hqIsHaveRecord(MktResInstEventReq req) {
		return rptSupplierOperatingDayManager.hqIsHaveRecord(req);
	}
	//插表
	@Override
	public void getDataForRptSupplierOperatingDay(RptSupplierOperatingDayReq req) {
		rptSupplierOperatingDayManager.getDataForRptSupplierOperatingDay(req);
    }
	//更新入库
	@Override
	public void updateRptSupplierRu(RptSupplierOperatingDayReq req) {
		rptSupplierOperatingDayManager.updateRptSupplierRu(req);
	}
	//更新出库
	@Override
	public void updateRptSupplierChu(RptSupplierOperatingDayReq req) {
		rptSupplierOperatingDayManager.updateRptSupplierChu(req);
	}
}
