package com.iwhalecloud.retail.report.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.report.dto.request.ReportCodeStatementsReq;
import com.iwhalecloud.retail.report.dto.response.ReportCodeStatementsResp;
import com.iwhalecloud.retail.report.entity.RptResInstDetailDay;

@Mapper
public interface ReportCodeStateMapper extends BaseMapper<RptResInstDetailDay>  {

	public Page<ReportCodeStatementsResp> getCodeStatementsReport(Page<ReportCodeStatementsResp> page,@Param("req") ReportCodeStatementsReq reportStorePurchaserReq);
	
	public Page<ReportCodeStatementsResp> getCodeStatementsReportAdmin(Page<ReportCodeStatementsResp> page,@Param("req") ReportCodeStatementsReq reportStorePurchaserReq);

	public List<ReportCodeStatementsResp> getCodeStatementsReport(@Param("req") ReportCodeStatementsReq reportStorePurchaserReq);
	
	public List<ReportCodeStatementsResp> getCodeStatementsReportAdmin(@Param("req") ReportCodeStatementsReq reportStorePurchaserReq);


}

