package com.iwhalecloud.retail.report.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.report.dto.request.MktResInstEventReq;
import com.iwhalecloud.retail.report.dto.request.RptSupplierOperatingDayReq;
import com.iwhalecloud.retail.report.dto.response.MktResEventruchu;
import com.iwhalecloud.retail.report.dto.response.MktResInstResq;
import com.iwhalecloud.retail.report.dto.response.ParMerchantResp;

@Mapper
public interface RptSupplierOperatingDayMapper extends BaseMapper<RptSupplierOperatingDayReq> {

	public void getDataForRptSupplierOperatingDay(@Param("req") RptSupplierOperatingDayReq rptSupplierOperatingDayReq);

	public List<ParMerchantResp> hqParMerchant();
	
	//第二步：通过商家ID获取供应商仓库
	public String hqMktResStore(@Param("req") MktResInstEventReq req);
	//当前库存
	public List<MktResInstResq> hqMktResInst(@Param("req") MktResInstEventReq req);
	//入库
	public List<MktResEventruchu> hqmktResEventru(@Param("req") MktResInstEventReq req);
	//出库
	public List<MktResEventruchu> hqmktResEventchu(@Param("req") MktResInstEventReq req);
}

