package com.iwhalecloud.retail.report.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.report.dto.request.ReportStSaleDaoReq;
import com.iwhalecloud.retail.report.dto.response.ReportStSaleDaoResp;
import com.iwhalecloud.retail.report.mapper.ReportStoreMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class ReportStoreManager {
	
    @Resource
    private ReportStoreMapper reportStoreMapper;
    
	public Page<ReportStSaleDaoResp> ListReportStSale(ReportStSaleDaoReq req) {
		Page<ReportStSaleDaoResp> page=new Page<>(req.getPageNo(),req.getPageSize());
		Page<ReportStSaleDaoResp> pageReport =reportStoreMapper.ListReportStSale(page,req);
	        return pageReport;
	}
	
	public List<ReportStSaleDaoResp> ListReportStSaledc(ReportStSaleDaoReq req) {
		List<ReportStSaleDaoResp> pageReport =reportStoreMapper.ListReportStSale(req);
	        return pageReport;
	}
}
