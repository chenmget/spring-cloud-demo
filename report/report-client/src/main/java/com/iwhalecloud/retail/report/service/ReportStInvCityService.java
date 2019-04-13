package com.iwhalecloud.retail.report.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.request.ReportStInvCityDaoReq;
import com.iwhalecloud.retail.report.dto.request.RptPartnerOperatingDay;

import java.util.List;

public interface ReportStInvCityService {
    // 展示列表
    public ResultVO<Page<RptPartnerOperatingDay>> getReportStInvCityList(ReportStInvCityDaoReq req);

    // 导出列表
    public ResultVO<List<RptPartnerOperatingDay>> getReportStInvCityListExport(ReportStInvCityDaoReq req);

}
