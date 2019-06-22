package com.iwhalecloud.retail.report.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.request.ReportCityTotalInvReq;
import com.iwhalecloud.retail.report.dto.response.ReportCityTotalInvResp;
import com.iwhalecloud.retail.report.manager.ReportCityTotalInvManager;
import com.iwhalecloud.retail.report.service.ReportCityTotalInvService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lipeng
 * @date 2019-06-10
 * 地市总进销存报表服务
 */
@Service
public class ReportCityTotalInvServiceImpl implements ReportCityTotalInvService {
	 
	@Autowired
	private ReportCityTotalInvManager reportCityTotalInvManager;


	/**
	 * 查询地市总进销存报表
	 * @param req
	 * @return
	 */
	@Override
	public ResultVO<Page<ReportCityTotalInvResp>> getCityTotalInvList(ReportCityTotalInvReq req){
		Page<ReportCityTotalInvResp> list = reportCityTotalInvManager.getCityTotalInvList(req);
		return ResultVO.success(list);
	}

	/**
	 * 报表导出
	 * @param req
	 * @return
	 */
	@Override
	public ResultVO<List<ReportCityTotalInvResp>> exportCityTotalInvListReport(ReportCityTotalInvReq req) {
		List<ReportCityTotalInvResp> list =reportCityTotalInvManager.exportCityTotalInvListReport(req);
		List<ReportCityTotalInvResp> list2=new ArrayList<>();

		for (ReportCityTotalInvResp rr : list) {
			String priceLevel = rr.getPriceLevel();
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

			list2.add(rr);
		}
		return ResultVO.success(list2);
	}

}