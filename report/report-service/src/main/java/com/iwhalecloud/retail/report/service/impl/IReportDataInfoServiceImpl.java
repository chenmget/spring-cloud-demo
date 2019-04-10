package com.iwhalecloud.retail.report.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.request.ReportDeSaleDaoReq;
import com.iwhalecloud.retail.report.dto.request.ReportStorePurchaserReq;
import com.iwhalecloud.retail.report.dto.response.ProductListAllResp;
import com.iwhalecloud.retail.report.dto.response.ReportDeSaleDaoResq;
import com.iwhalecloud.retail.report.dto.response.ReportStorePurchaserResq;
import com.iwhalecloud.retail.report.manager.ReportDataInfoManager;
import com.iwhalecloud.retail.report.manager.ReportManager;
import com.iwhalecloud.retail.report.service.IReportDataInfoService;

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
//		List<ReportStorePurchaserResq> list2 = new ArrayList<ReportStorePurchaserResq>();
//		for(ReportStorePurchaserResq rr : list){
//			
//			list2.add(rr);
//		}
		
        return ResultVO.success(list);	
	}
	
	@Override
	public ResultVO<List<ReportStorePurchaserResq>> getUerRoleForView(ReportStorePurchaserReq req) {
		List<ReportStorePurchaserResq> list = (List<ReportStorePurchaserResq>) reportDataInfoManager.getUerRoleForView(req);
	    
        return ResultVO.success(list);	
	}
	
}
