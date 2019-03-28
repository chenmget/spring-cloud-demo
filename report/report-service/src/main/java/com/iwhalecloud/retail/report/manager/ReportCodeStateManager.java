package com.iwhalecloud.retail.report.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.report.dto.request.ReportCodeStatementsReq;
import com.iwhalecloud.retail.report.dto.response.ReportCodeStatementsResp;
import com.iwhalecloud.retail.report.mapper.ReportCodeStateMapper;

@Component
public class ReportCodeStateManager {

	@Resource
    private ReportCodeStateMapper reportCodeStateMapper;

	public Page<ReportCodeStatementsResp> getCodeStatementsReport(ReportCodeStatementsReq req) {
		Page<ReportCodeStatementsResp> page=new Page<>(req.getPageNo(),req.getPageSize());
		Page<ReportCodeStatementsResp> pageReport =reportCodeStateMapper.getCodeStatementsReport(page,req);
	        return pageReport;
	}
	
	public List<ReportCodeStatementsResp> getCodeStatementsReportdc(ReportCodeStatementsReq req) {
		List<ReportCodeStatementsResp> pageReport =reportCodeStateMapper.getCodeStatementsReport(req);
	        return pageReport;
	}
}
