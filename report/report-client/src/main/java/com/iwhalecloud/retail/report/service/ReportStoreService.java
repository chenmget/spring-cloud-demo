package com.iwhalecloud.retail.report.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.request.ReportStSaleDaoReq;
import com.iwhalecloud.retail.report.dto.response.ReportStSaleDaoResp;

import java.util.List;

public interface ReportStoreService {
    //门店进销存明细报表
    public ResultVO<Page<ReportStSaleDaoResp>> getReportStSaleList(ReportStSaleDaoReq req);
    //导出
    public ResultVO<List<ReportStSaleDaoResp>> getReportStSaleListdc(ReportStSaleDaoReq req);

}
