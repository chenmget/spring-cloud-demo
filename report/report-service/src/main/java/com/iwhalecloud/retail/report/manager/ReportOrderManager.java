package com.iwhalecloud.retail.report.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.report.dto.request.ReportOrderDaoReq;
import com.iwhalecloud.retail.report.dto.request.ReportOrderNbrDaoReq;
import com.iwhalecloud.retail.report.dto.response.ReportOrderNbrResp;
import com.iwhalecloud.retail.report.dto.response.ReportOrderResp;
import com.iwhalecloud.retail.report.mapper.ReportOrderMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class ReportOrderManager {
    @Resource
    private ReportOrderMapper reportOrderMapper;

	public Page<ReportOrderResp> listReportOrder(ReportOrderDaoReq req) {
		Page<ReportOrderResp> page=new Page<>(req.getPageNo(),req.getPageSize());
		Page<ReportOrderResp> pageReport =reportOrderMapper.ListReportOrder(page,req);
	        return pageReport;
	}
	
	public List<ReportOrderResp> ListReportOrder1dc(ReportOrderDaoReq req) {
		List<ReportOrderResp> pageReport =reportOrderMapper.ListReportOrder(req);
	        return pageReport;
	}
	
	public List<ReportOrderNbrResp> listReportOrderNbr(String orderId) {
		List<ReportOrderNbrResp> pageReport =reportOrderMapper.ListReportOrderNbr(orderId);
	        return pageReport;
	}
    
	public Page<ReportOrderNbrResp> listReportOrderNbr3(ReportOrderNbrDaoReq req) {
		Page<ReportOrderNbrResp> page=new Page<>(req.getPageNo(),req.getPageSize());
		Page<ReportOrderNbrResp> pageReport =reportOrderMapper.ListReportOrderNbr3(page,req);
	        return pageReport;
	}
	
}
