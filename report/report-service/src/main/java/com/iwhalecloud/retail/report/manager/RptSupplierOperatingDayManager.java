package com.iwhalecloud.retail.report.manager;

import com.iwhalecloud.retail.report.dto.request.MktResInstEventReq;
import com.iwhalecloud.retail.report.dto.request.RptSupplierOperatingDayReq;
import com.iwhalecloud.retail.report.dto.response.MktResEventruchu;
import com.iwhalecloud.retail.report.dto.response.MktResInstResq;
import com.iwhalecloud.retail.report.dto.response.ParMerchantResp;
import com.iwhalecloud.retail.report.dto.response.PurchaseAmountResp;
import com.iwhalecloud.retail.report.mapper.RptSupplierOperatingDayMapper;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class RptSupplierOperatingDayManager {

    @Resource
    private RptSupplierOperatingDayMapper rptSupplierOperatingDayMapper;

    public List<ParMerchantResp> hqParMerchantInfo(){
    	return rptSupplierOperatingDayMapper.hqParMerchantInfo();
    }
    
  //库存的
    public List<MktResInstResq> hqMktResInstInfo(String mktResStoreId) {
        return rptSupplierOperatingDayMapper.hqMktResInstInfo(mktResStoreId);
    }
    
  //入库的
    public List<MktResEventruchu> hqmktResEventInfoRu(MktResInstEventReq req) {
        return rptSupplierOperatingDayMapper.hqmktResEventInfoRu(req);
    }
    //出库的
    public List<MktResEventruchu> hqmktResEventInfoChu (MktResInstEventReq req) {
		return rptSupplierOperatingDayMapper.hqmktResEventInfoChu(req);
	}
  //获取变动事件的数量
    public String hqEventTypeNum (String mktResEventId) {
		return rptSupplierOperatingDayMapper.hqEventTypeNum(mktResEventId);
	}
  //获取进货金额
    public PurchaseAmountResp hqPurchaseAmount(String orderId) {
		return rptSupplierOperatingDayMapper.hqPurchaseAmount(orderId);
	}
  //看入库的机型在库存里面有没有记录
    public String hqIsHaveRecord(MktResInstEventReq req) {
		return rptSupplierOperatingDayMapper.hqIsHaveRecord(req);
	}
    //插表
    public void getDataForRptSupplierOperatingDay(RptSupplierOperatingDayReq req) {
    	rptSupplierOperatingDayMapper.getDataForRptSupplierOperatingDay(req);
    }
  //更新入库
  	public void updateRptSupplierRu(RptSupplierOperatingDayReq req) {
  		rptSupplierOperatingDayMapper.updateRptSupplierRu(req);
  	}
  	//更新出库
  	public void updateRptSupplierChu(RptSupplierOperatingDayReq req) {
  		rptSupplierOperatingDayMapper.updateRptSupplierChu(req);
  	}
    
}
