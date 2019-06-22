package com.iwhalecloud.retail.report.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.request.ReportCodeStatementsReq;
import com.iwhalecloud.retail.report.dto.response.ReportCodeStatementsResp;

import java.util.List;

public interface ReportCodeStateService {

	//串码报表
	public ResultVO<Page<ReportCodeStatementsResp>> getCodeStatementsReport(ReportCodeStatementsReq req);
	
	//串码报表管理员
		public ResultVO<Page<ReportCodeStatementsResp>> getCodeStatementsReportAdmin(ReportCodeStatementsReq req);
	
	//导出串码报表
	public ResultVO<List<ReportCodeStatementsResp>> getCodeStatementsReportdc(ReportCodeStatementsReq req);
	
	//导出串码报表管理员
	public ResultVO<List<ReportCodeStatementsResp>> getCodeStatementsReportAdmindc(ReportCodeStatementsReq req);
}
