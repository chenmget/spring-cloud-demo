package com.iwhalecloud.retail.report.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.report.dto.request.ReportStInvCityDaoReq;
import com.iwhalecloud.retail.report.dto.request.RptPartnerOperatingDay;
import com.iwhalecloud.retail.report.mapper.ReportStInvCityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ReportStInvCityManager {

    @Autowired
    private ReportStInvCityMapper reportStInvCityMapper;

    public Page<RptPartnerOperatingDay> listStInvCity(ReportStInvCityDaoReq req) {
        Page<ReportStInvCityDaoReq> page = new Page<>(req.getPageNo(), req.getPageSize());
        Page<RptPartnerOperatingDay> pageReport = reportStInvCityMapper.listStInvCity(page, req);
        return pageReport;
    }

    public List<RptPartnerOperatingDay> listStInvCityExport(ReportStInvCityDaoReq req) {
        List<RptPartnerOperatingDay> pageReport = reportStInvCityMapper.listStInvCity(req);
        return pageReport;
    }

}
