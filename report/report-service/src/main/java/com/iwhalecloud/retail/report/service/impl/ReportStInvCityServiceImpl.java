package com.iwhalecloud.retail.report.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.request.ReportStInvCityDaoReq;
import com.iwhalecloud.retail.report.dto.request.RptPartnerOperatingDay;
import com.iwhalecloud.retail.report.manager.ReportStInvCityManager;
import com.iwhalecloud.retail.report.service.ReportStInvCityService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

//import com.iwhalecloud.retail.report.entity.RptPartnerOperatingDay;

/**
 * Created by jiyou on 2019/4/11.
 */
@Service
public class ReportStInvCityServiceImpl implements ReportStInvCityService {

    @Autowired
    private ReportStInvCityManager reportStInvCityManager;

    @Override
    public ResultVO<Page<RptPartnerOperatingDay>> getReportStInvCityList(ReportStInvCityDaoReq req) {
        Page<RptPartnerOperatingDay> pageRptPartnerOperatingDay = reportStInvCityManager.listStInvCity(req);
        List<RptPartnerOperatingDay> listRptPartnerOperatingDay = pageRptPartnerOperatingDay.getRecords();

        for (RptPartnerOperatingDay rsic : listRptPartnerOperatingDay) {
            Double warnStatus = Double.valueOf(rsic.getWarnStatus());
            if (warnStatus >= 10) {
                rsic.setWarnStatus("充裕");
            } else if (warnStatus <= 5) {
                rsic.setWarnStatus("严重缺货");
            } else {
                rsic.setWarnStatus("缺货");
            }
        }

        return ResultVO.success(pageRptPartnerOperatingDay);

    }

    @Override
    public ResultVO<List<RptPartnerOperatingDay>> getReportStInvCityListExport(ReportStInvCityDaoReq req) {
        
        List<RptPartnerOperatingDay> listRptPartnerOperatingDay = reportStInvCityManager.listStInvCityExport(req);

        for (RptPartnerOperatingDay rsic : listRptPartnerOperatingDay) {
            Double warnStatus = Double.valueOf(rsic.getWarnStatus());
            if (warnStatus >= 10) {
                rsic.setWarnStatus("充裕");
            } else if (warnStatus <= 5) {
                rsic.setWarnStatus("严重缺货");
            } else {
                rsic.setWarnStatus("缺货");
            }
        }

        return ResultVO.success(listRptPartnerOperatingDay);
    }
}
