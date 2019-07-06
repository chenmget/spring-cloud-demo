package com.iwhalecloud.retail.report.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.request.ReportDeSaleDaoReq;
import com.iwhalecloud.retail.report.dto.response.ProductListAllResp;
import com.iwhalecloud.retail.report.dto.response.ReportDeSaleDaoResq;
import com.iwhalecloud.retail.report.manager.ReportManager;
import com.iwhalecloud.retail.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
	 
	@Autowired
	private ReportManager reportManager;

	@Override
	public ResultVO<Page<ReportDeSaleDaoResq>> getReportDeSaleList(ReportDeSaleDaoReq req) {
		Page<ReportDeSaleDaoResq> list = (Page<ReportDeSaleDaoResq>) reportManager.ListReportDeSale(req);
        return ResultVO.success(list);	
	}

	@Override
	public ResultVO<List<ReportDeSaleDaoResq>> reportDeSaleExport(ReportDeSaleDaoReq req) {
		List<ReportDeSaleDaoResq> list =reportManager.reportDeSaleExport(req);
		return ResultVO.success(list);
	}

}