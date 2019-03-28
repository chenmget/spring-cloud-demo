package com.iwhalecloud.retail.report.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.request.ReportDeSaleDaoReq;
import com.iwhalecloud.retail.report.dto.response.ProductListAllResp;
import com.iwhalecloud.retail.report.dto.response.ReportDeSaleDaoResq;

public interface ReportService {
    
    //地包进销存明细报表
    public ResultVO<Page<ReportDeSaleDaoResq>> getReportDeSaleList(ReportDeSaleDaoReq req);

    public ResultVO<List<ProductListAllResp>> listProductAll(String brandId);

	public ResultVO<List<ReportDeSaleDaoResq>> reportDeSaleExport(ReportDeSaleDaoReq req);
}
