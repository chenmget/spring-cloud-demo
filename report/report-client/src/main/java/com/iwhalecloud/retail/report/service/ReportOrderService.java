package com.iwhalecloud.retail.report.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.request.ReportOrderDaoReq;
import com.iwhalecloud.retail.report.dto.request.ReportOrderNbrDaoReq;
import com.iwhalecloud.retail.report.dto.response.ReportOrderNbrResp;
import com.iwhalecloud.retail.report.dto.response.ReportOrderResp;

public interface ReportOrderService {
    //订单的明细报表
    public ResultVO<Page<ReportOrderResp>> getReportOrderList1(ReportOrderDaoReq req);
    //导出订单的明细报表
    public ResultVO<List<ReportOrderResp>> getReportOrderList1dc(ReportOrderDaoReq req);
    //订单的明细报表详情
    public ResultVO<Page<ReportOrderNbrResp>> getReportOrderList3(ReportOrderNbrDaoReq req);
}
