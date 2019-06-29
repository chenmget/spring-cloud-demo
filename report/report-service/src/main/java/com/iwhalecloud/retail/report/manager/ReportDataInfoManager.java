package com.iwhalecloud.retail.report.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.report.dto.request.ReportStorePurchaserReq;
import com.iwhalecloud.retail.report.dto.response.ReportStorePurchaserResq;
import com.iwhalecloud.retail.report.mapper.ReportDataInfoMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

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
	
	public String getMyMktResStoreId(String relCode) {
		return reportDataInfoMapper.getMyMktResStoreId(relCode);
	}
}

