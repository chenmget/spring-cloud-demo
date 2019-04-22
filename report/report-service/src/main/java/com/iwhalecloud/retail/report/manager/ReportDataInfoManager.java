package com.iwhalecloud.retail.report.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.report.dto.request.ReportDeSaleDaoReq;
import com.iwhalecloud.retail.report.dto.request.ReportStorePurchaserReq;
import com.iwhalecloud.retail.report.dto.response.ProductListAllResp;
import com.iwhalecloud.retail.report.dto.response.ReportDeSaleDaoResq;
import com.iwhalecloud.retail.report.dto.response.ReportStorePurchaserResq;
import com.iwhalecloud.retail.report.mapper.ReportDataInfoMapper;
import com.iwhalecloud.retail.report.mapper.ReportMapper;

@Component
public class ReportDataInfoManager {

	@Resource
    private ReportDataInfoMapper reportDataInfoMapper;

	public Page<ReportStorePurchaserResq> getStorePurchaserReport(ReportStorePurchaserReq req) {
		Page<ReportStorePurchaserResq> page=new Page<>(req.getPageNo(),req.getPageSize());
		Page<ReportStorePurchaserResq> pageReport =reportDataInfoMapper.getStorePurchaserReport(page,req);
	        return pageReport;
	}
	
	public List<ReportStorePurchaserResq> getStorePurchaserReportdc(ReportStorePurchaserReq req) {
		List<ReportStorePurchaserResq> pageReport =reportDataInfoMapper.getStorePurchaserReport(req);
	        return pageReport;
	}
	
	public List<ReportStorePurchaserResq> getUerRoleForView(ReportStorePurchaserReq req) {
		List<ReportStorePurchaserResq> pageReport =reportDataInfoMapper.getUerRoleForView(req);
	        return pageReport;
	}
	
	public String retailerCodeBylegacy(String legacyAccount) {
		return reportDataInfoMapper.retailerCodeBylegacy(legacyAccount);
	}
}
