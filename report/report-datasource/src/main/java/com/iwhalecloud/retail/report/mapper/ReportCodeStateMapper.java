package com.iwhalecloud.retail.report.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.report.dto.request.MktResStoreIdReq;
import com.iwhalecloud.retail.report.dto.request.ReportCodeStatementsReq;
import com.iwhalecloud.retail.report.dto.response.ReportCodeStatementsResp;
import com.iwhalecloud.retail.report.entity.RptResInstDetailDay;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReportCodeStateMapper extends BaseMapper<RptResInstDetailDay>  {

	public Page<ReportCodeStatementsResp> getCodeStatementsReport(Page<ReportCodeStatementsResp> page,@Param("req") ReportCodeStatementsReq reportStorePurchaserReq);
	
	public Page<ReportCodeStatementsResp> getCodeStatementsReportAdmin(Page<ReportCodeStatementsResp> page,@Param("req") ReportCodeStatementsReq reportStorePurchaserReq);

	public List<ReportCodeStatementsResp> getCodeStatementsReport(@Param("req") ReportCodeStatementsReq reportStorePurchaserReq);
	
	public List<ReportCodeStatementsResp> getCodeStatementsReportAdmin(@Param("req") ReportCodeStatementsReq reportStorePurchaserReq);

	String getMktResStoreId(@Param("req") MktResStoreIdReq req);
}

