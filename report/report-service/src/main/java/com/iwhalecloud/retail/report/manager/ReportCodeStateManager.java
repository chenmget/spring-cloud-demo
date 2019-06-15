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
	//串码明细报表管理员查询
	public Page<ReportCodeStatementsResp> getCodeStatementsReportAdmin(ReportCodeStatementsReq req) {
		Page<ReportCodeStatementsResp> page=new Page<>(req.getPageNo(),req.getPageSize());
		Page<ReportCodeStatementsResp> pageReport =reportCodeStateMapper.getCodeStatementsReportAdmin(page,req);
	        return pageReport;
	}
	
	public List<ReportCodeStatementsResp> getCodeStatementsReportdc(ReportCodeStatementsReq req) {
		List<ReportCodeStatementsResp> pageReport =reportCodeStateMapper.getCodeStatementsReport(req);
	        return pageReport;
	}
	//串码明细报表管理员导出
	public List<ReportCodeStatementsResp> getCodeStatementsReportAdmindc(ReportCodeStatementsReq req) {
		List<ReportCodeStatementsResp> pageReport =reportCodeStateMapper.getCodeStatementsReportAdmin(req);
	        return pageReport;
	}
	
}
