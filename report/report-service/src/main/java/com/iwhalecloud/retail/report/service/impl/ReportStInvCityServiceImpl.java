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
@Service(parameters={"addActSup.timeout","30000"})
public class ReportStInvCityServiceImpl implements ReportStInvCityService {

    @Autowired
    private ReportStInvCityManager reportStInvCityManager;

    @Override
    public ResultVO<Page<RptPartnerOperatingDay>> getReportStInvCityList(ReportStInvCityDaoReq req) {
        Page<RptPartnerOperatingDay> pageRptPartnerOperatingDay = reportStInvCityManager.listStInvCity(req);
        return ResultVO.success(pageRptPartnerOperatingDay);

    }

    @Override
    public ResultVO<List<RptPartnerOperatingDay>> getReportStInvCityListExport(ReportStInvCityDaoReq req) {
        
        List<RptPartnerOperatingDay> listRptPartnerOperatingDay = reportStInvCityManager.listStInvCityExport(req);
        return ResultVO.success(listRptPartnerOperatingDay);
    }

    

}
