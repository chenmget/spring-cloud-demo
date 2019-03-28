package com.iwhalecloud.retail.report.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.request.ReportDeSaleDaoReq;
import com.iwhalecloud.retail.report.dto.response.ProductListAllResp;
import com.iwhalecloud.retail.report.dto.response.ReportDeSaleDaoResq;
import com.iwhalecloud.retail.report.mapper.ReportMapper;


@Component
public class ReportManager {
    @Resource
    private ReportMapper reportMapper;

	public Page<ReportDeSaleDaoResq> ListReportDeSale(ReportDeSaleDaoReq req) {
		Page<ReportDeSaleDaoResq> page=new Page<>(req.getPageNo(),req.getPageSize());
		Page<ReportDeSaleDaoResq> pageReport =reportMapper.ListReportDeSale(page,req);
	        return pageReport;
	}
	public List<ProductListAllResp> listProductAll(String brandId) {
	List<ProductListAllResp> pageReport =reportMapper.listProductAll(brandId);
	        return pageReport;
	}
	public List<ReportDeSaleDaoResq> reportDeSaleExport(ReportDeSaleDaoReq req) {
		List<ReportDeSaleDaoResq> pageReport =reportMapper.reportDeSaleExport(req);
	        return pageReport;
	}
	
	
    
}
