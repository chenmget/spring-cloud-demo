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

        handleResult(listRptPartnerOperatingDay);

        return ResultVO.success(pageRptPartnerOperatingDay);

    }

    @Override
    public ResultVO<List<RptPartnerOperatingDay>> getReportStInvCityListExport(ReportStInvCityDaoReq req) {
        
        List<RptPartnerOperatingDay> listRptPartnerOperatingDay = reportStInvCityManager.listStInvCityExport(req);

        handleResult(listRptPartnerOperatingDay);

        return ResultVO.success(listRptPartnerOperatingDay);
    }

    private void handleResult(List<RptPartnerOperatingDay> listRptPartnerOperatingDay){
        for (RptPartnerOperatingDay rsic : listRptPartnerOperatingDay) {
            if(rsic.getWarnStatus() == null){
                rsic.setWarnStatus("严重缺货");
                continue;
            }
            Double warnStatus = Double.valueOf(rsic.getWarnStatus());
            if (warnStatus >= 10) {
                rsic.setWarnStatus("充裕");
            } else if (warnStatus <= 5) {
                rsic.setWarnStatus("严重缺货");
            } else {
                rsic.setWarnStatus("缺货");
            }
        }
    }

}
