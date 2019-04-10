package com.iwhalecloud.retail.report.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.MenuDTO;
import com.iwhalecloud.retail.report.dto.request.ReportDeSaleDaoReq;
import com.iwhalecloud.retail.report.dto.request.ReportStSaleDaoReq;
import com.iwhalecloud.retail.report.dto.response.ReportStSaleDaoResp;
import com.iwhalecloud.retail.report.entity.Menu;
import com.iwhalecloud.retail.report.manager.MenuManager;
import com.iwhalecloud.retail.report.manager.ReportManager;
import com.iwhalecloud.retail.report.manager.ReportStoreManager;
import com.iwhalecloud.retail.report.service.ReportService;
import com.iwhalecloud.retail.report.service.ReportStoreService;
@Service
public class ReportStoreServiceImpl implements ReportStoreService{
	 
	@Autowired
	    private ReportStoreManager reportStoreManager;

	@Override
	public ResultVO<Page<ReportStSaleDaoResp>> getReportStSaleList(ReportStSaleDaoReq req) {
		Page<ReportStSaleDaoResp> list = (Page<ReportStSaleDaoResp>) reportStoreManager.ListReportStSale(req);
	    
        return ResultVO.success(list);	
	}
	
	@Override
	public ResultVO<List<ReportStSaleDaoResp>> getReportStSaleListdc(ReportStSaleDaoReq req) {
		List<ReportStSaleDaoResp> list = (List<ReportStSaleDaoResp>) reportStoreManager.ListReportStSaledc(req);
	    List<ReportStSaleDaoResp> list2 = new ArrayList<ReportStSaleDaoResp>();
	    for(ReportStSaleDaoResp rr : list){
	    	String cityId = rr.getCityId();//所属城市
	    	//String inventoryWarning = rr.getInventoryWarning();//库存预警
	    	if(cityId != null){
				if("730".equals(cityId)){
					rr.setCityId("岳阳市");
				}else if("731".equals(cityId)){
					rr.setCityId("长沙市");
				}else if("732".equals(cityId)){
					rr.setCityId("湘潭市");
				}else if("733".equals(cityId)){
					rr.setCityId("株洲市");
				}else if("734".equals(cityId)){
					rr.setCityId("衡阳市");
				}else if("735".equals(cityId)){
					rr.setCityId("郴州市");
				}else if("736".equals(cityId)){
					rr.setCityId("常德市");
				}else if("737".equals(cityId)){
					rr.setCityId("益阳市");
				}else if("738".equals(cityId)){
					rr.setCityId("娄底市");
				}else if("739".equals(cityId)){
					rr.setCityId("邵阳市");
				}else if("743".equals(cityId)){
					rr.setCityId("湘西本地网");
				}else if("744".equals(cityId)){
					rr.setCityId("张家界本地网");
				}
			}
	    	
//	    	if(inventoryWarning != null){
//	    		if("1".equals(inventoryWarning)){
//		    		rr.setInventoryWarning("充裕");
//		    	}else if("2".equals(inventoryWarning)){
//		    		rr.setInventoryWarning("缺货");
//		    	}else if("3".equals(inventoryWarning)){
//		    		rr.setInventoryWarning("严重缺货");
//		    	}
//	    	}
	    	
	    	list2.add(rr);
	    }
	    
        return ResultVO.success(list2);	
	}
}
