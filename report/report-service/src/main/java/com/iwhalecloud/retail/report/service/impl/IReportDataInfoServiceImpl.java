package com.iwhalecloud.retail.report.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.request.ReportStorePurchaserReq;
import com.iwhalecloud.retail.report.dto.response.ReportStorePurchaserResq;
import com.iwhalecloud.retail.report.manager.ReportDataInfoManager;
import com.iwhalecloud.retail.report.service.IReportDataInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class IReportDataInfoServiceImpl implements IReportDataInfoService {

	@Autowired
	private ReportDataInfoManager reportDataInfoManager;

	@Override
	public ResultVO<Page<ReportStorePurchaserResq>> getStorePurchaserReport(ReportStorePurchaserReq req) {
		Page<ReportStorePurchaserResq> list = (Page<ReportStorePurchaserResq>) reportDataInfoManager.getStorePurchaserReport(req);

        return ResultVO.success(list);
	}

	@Override
	public ResultVO<List<ReportStorePurchaserResq>> getStorePurchaserReportdc(ReportStorePurchaserReq req) {
		List<ReportStorePurchaserResq> list = (List<ReportStorePurchaserResq>) reportDataInfoManager.getStorePurchaserReportdc(req);
        return ResultVO.success(list);
	}

	@Override
	public String getMyMktResStoreId(String relCode) {
		return reportDataInfoManager.getMyMktResStoreId(relCode);
	}
}