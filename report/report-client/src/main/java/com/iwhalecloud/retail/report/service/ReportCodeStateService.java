package com.iwhalecloud.retail.report.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.request.ReportCodeStatementsReq;
import com.iwhalecloud.retail.report.dto.response.ReportCodeStatementsResp;

public interface ReportCodeStateService {

	//串码报表
	public ResultVO<Page<ReportCodeStatementsResp>> getCodeStatementsReport(ReportCodeStatementsReq req);
	
	//导出串码报表
	public ResultVO<List<ReportCodeStatementsResp>> getCodeStatementsReportdc(ReportCodeStatementsReq req);
}
