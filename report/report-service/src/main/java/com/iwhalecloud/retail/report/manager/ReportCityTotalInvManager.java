package com.iwhalecloud.retail.report.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.report.dto.request.ReportCityTotalInvReq;
import com.iwhalecloud.retail.report.dto.request.ReportDeSaleDaoReq;
import com.iwhalecloud.retail.report.dto.response.ProductListAllResp;
import com.iwhalecloud.retail.report.dto.response.ReportCityTotalInvResp;
import com.iwhalecloud.retail.report.dto.response.ReportDeSaleDaoResq;
import com.iwhalecloud.retail.report.mapper.ReportCityTotalInvMapper;
import com.iwhalecloud.retail.report.mapper.ReportMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lipeng
 * @date 2019-06-10
 * 地市总进销存报表数据管理类
 */
@Component
public class ReportCityTotalInvManager {
    @Resource
    private ReportCityTotalInvMapper reportCityTotalInvMapper;

	public Page<ReportCityTotalInvResp> getCityTotalInvList(ReportCityTotalInvReq req) {
		Page<ReportCityTotalInvResp> page=new Page<>(req.getPageNo(),req.getPageSize());
		Page<ReportCityTotalInvResp> pageReport =reportCityTotalInvMapper.getCityTotalInvList(page,req);
	        return pageReport;
	}

	public List<ReportCityTotalInvResp> exportCityTotalInvListReport(ReportCityTotalInvReq req) {
		List<ReportCityTotalInvResp> pageReport =reportCityTotalInvMapper.getCityTotalInvList(req);
	        return pageReport;
	}
	
	
    
}
