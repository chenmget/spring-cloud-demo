package com.iwhalecloud.retail.report.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.request.ReportDeSaleDaoReq;
import com.iwhalecloud.retail.report.dto.response.ProductListAllResp;
import com.iwhalecloud.retail.report.dto.response.ReportDeSaleDaoResq;
import com.iwhalecloud.retail.report.manager.ReportManager;
import com.iwhalecloud.retail.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
	 
	@Autowired
	private ReportManager reportManager;

	@Override
	public ResultVO<Page<ReportDeSaleDaoResq>> getReportDeSaleList(ReportDeSaleDaoReq req) {
		Page<ReportDeSaleDaoResq> list = (Page<ReportDeSaleDaoResq>) reportManager.ListReportDeSale(req);
	    
        return ResultVO.success(list);	
	}
	
	@Override
    public ResultVO<List<ProductListAllResp>> listProductAll(String brandId){
	List<ProductListAllResp> list = (List<ProductListAllResp>) reportManager.listProductAll(brandId);
	    
        return ResultVO.success(list);	
	}

	@Override
	public ResultVO<List<ReportDeSaleDaoResq>> reportDeSaleExport(ReportDeSaleDaoReq req) {
		List<ReportDeSaleDaoResq> list =reportManager.reportDeSaleExport(req);
		List<ReportDeSaleDaoResq> list2=new ArrayList<ReportDeSaleDaoResq>();	

		for (ReportDeSaleDaoResq rr : list) {
			String priceLevel = rr.getPriceLevel();
			String redStatus = rr.getRedStatus();
			if (priceLevel != null) {
				if ("1".equals(priceLevel)) {
					rr.setPriceLevel("400以下");
				} else if ("2".equals(priceLevel)) {
					rr.setPriceLevel("400-700");
				} else if ("3".equals(priceLevel)) {
					rr.setPriceLevel("700-1000");
				} else if ("4".equals(priceLevel)) {
					rr.setPriceLevel("1000-2000");
				} else if ("5".equals(priceLevel)) {
					rr.setPriceLevel("2000-3000");
				} else if (priceLevel.equals("6")) {
					rr.setPriceLevel("3000以上");
				}
			}
			if (redStatus != null) {
				if ("1".equals(redStatus)) {
					rr.setRedStatus("充裕");
				} else if ("2".equals(redStatus)) {
					rr.setRedStatus("缺货");
				} else if ("3".equals(redStatus)) {
					rr.setRedStatus("严重缺货");
				}
			}
			String typeId = rr.getTypeId();
			if("201903142030001".equals(typeId)){
				rr.setTypeId("手机");
			}
			list2.add(rr);
		}
		// TODO Auto-generated method stub
		return ResultVO.success(list2);
	}

}