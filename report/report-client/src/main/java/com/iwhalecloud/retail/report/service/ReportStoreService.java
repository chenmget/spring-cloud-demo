package com.iwhalecloud.retail.report.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.request.ReportDeSaleDaoReq;
import com.iwhalecloud.retail.report.dto.request.ReportStSaleDaoReq;
import com.iwhalecloud.retail.report.dto.response.ReportStSaleDaoResp;

public interface ReportStoreService {
    //门店进销存明细报表
    public ResultVO<Page<ReportStSaleDaoResp>> getReportStSaleList(ReportStSaleDaoReq req);
    //导出
    public ResultVO<List<ReportStSaleDaoResp>> getReportStSaleListdc(ReportStSaleDaoReq req);

}
