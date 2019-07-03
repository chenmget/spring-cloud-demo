package com.iwhalecloud.retail.report.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.report.dto.request.MktResInstEventReq;
import com.iwhalecloud.retail.report.dto.request.RptSupplierOperatingDayReq;
import com.iwhalecloud.retail.report.dto.response.MktResEventruchu;
import com.iwhalecloud.retail.report.dto.response.MktResInstResq;
import com.iwhalecloud.retail.report.dto.response.ParMerchantResp;
import com.iwhalecloud.retail.report.dto.response.PurchaseAmountResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RptSupplierOperatingDayMapper extends BaseMapper<RptSupplierOperatingDayReq> {

	List<ParMerchantResp> hqParMerchantInfo();
	
	List<MktResInstResq> hqMktResInstInfo(@Param("mktResStoreId") String mktResStoreId) ;
	
	List<MktResEventruchu> hqmktResEventInfoRu(@Param("req") MktResInstEventReq req);
	
	List<MktResEventruchu> hqmktResEventInfoChu (@Param("req") MktResInstEventReq req);
	//获取变动事件的数量
    String hqEventTypeNum (@Param("mktResEventId") String mktResEventId);
    
    PurchaseAmountResp hqPurchaseAmount(@Param("orderId") String orderId) ;
    
    String hqIsHaveRecord(@Param("req") MktResInstEventReq req);
    
    void getDataForRptSupplierOperatingDay(@Param("req") RptSupplierOperatingDayReq req);
    //更新入库
  	void updateRptSupplierRu(@Param("req") RptSupplierOperatingDayReq req) ;
  	//更新出库
  	void updateRptSupplierChu(@Param("req") RptSupplierOperatingDayReq req) ;
}

