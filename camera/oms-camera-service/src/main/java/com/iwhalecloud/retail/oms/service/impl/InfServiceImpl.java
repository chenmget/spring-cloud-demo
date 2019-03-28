package com.iwhalecloud.retail.oms.service.impl;

import com.iwhalecloud.retail.camera.service.IInfService;
import com.iwhalecloud.retail.param.req.RevaDailyReportReq;
import com.iwhalecloud.retail.param.req.RevaWeekReportReq;
import com.iwhalecloud.retail.param.resp.RevaDailyReportResp;
import com.iwhalecloud.retail.param.resp.RevaRepeatDetailsResp;
import com.iwhalecloud.retail.param.resp.RevaVipInfoResp;
import com.iwhalecloud.retail.param.resp.VisitHistoryResp;
import com.iwhalecloud.retail.util.HttpClientUtilReVa;
import com.iwhalecloud.retail.web.common.InfCameraProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service("infServiceService")
@EnableConfigurationProperties({ InfCameraProperties.class})
public class InfServiceImpl implements IInfService {

	@Autowired
    InfCameraProperties infCameraProperties;

	@Override
	public RevaDailyReportResp customersDailyReport(RevaDailyReportReq req) throws Exception {
		String addr=infCameraProperties.getServerIP() + infCameraProperties.getAddress().get("customersDailyReport");
		
		Map<String, String> map = new HashMap<>();
		map.put("date", req.getDate());
		map.put("lastDate", req.getLastDate());
		if (req.getProvince()!=null) {
			map.put("province", req.getProvince());
		}
		if (req.getCity()!=null) {
			map.put("city", req.getCity());
		}
		
		map.put("areacode", infCameraProperties.getMallCode());
		
		
		
		RevaDailyReportResp result = HttpClientUtilReVa.doPost(addr, map, RevaDailyReportResp.class,infCameraProperties,null);// http
		return result;
	
	}

	@Override
	public RevaDailyReportResp customersWeekReport(RevaWeekReportReq req)throws Exception {
		String addr=infCameraProperties.getServerIP() + infCameraProperties.getAddress().get("customersWeekReport");

		Map<String, String> map = new HashMap<>();
		
		map.put("week", req.getWeek()+"");
		map.put("year", req.getYear()+"");
		map.put("lastWeek", req.getLastWeek()+"");
		map.put("lastYear", req.getLastYear()+"");
		map.put("startDate", req.getStartDate());
		map.put("endDate", req.getEndDate());
		if (req.getProvince()!=null) {
			map.put("province", req.getProvince());
		}
		if (req.getCity()!=null) {
			map.put("city", req.getCity());
		}
		map.put("areacode", req.getAreacode());
	
		
		RevaDailyReportResp result = HttpClientUtilReVa.doPost(addr, map, RevaDailyReportResp.class,infCameraProperties,null);// http
		return result;
	
	}

	@Override
	public RevaVipInfoResp vipInfo(String personCode) throws Exception {
		String addr=infCameraProperties.getServerIP() + infCameraProperties.getAddress().get("vipInfo");

		Map<String, String> map = new HashMap<>();
		map.put("personCode", personCode+"");
		
		RevaVipInfoResp result = HttpClientUtilReVa.doPost(addr, map, RevaVipInfoResp.class,infCameraProperties,null);// http
		return result;	
		}

	@Override
	public RevaRepeatDetailsResp repeatCustomerDetails(String repeatId, String startDate, String parentcode) throws Exception {
		String addr=infCameraProperties.getServerIP() + infCameraProperties.getAddress().get("repeatCustomerDetails");

		Map<String, String> map = new HashMap<>();
		map.put("repeatId", repeatId);
		if (!StringUtils.isEmpty(startDate)) {
			map.put("startDate",startDate);
		}
		//if (!StringUtil.isEmpty(parentcode)) {
			map.put("parentcode",infCameraProperties.getMallCode());
		//}
		
		RevaRepeatDetailsResp result = HttpClientUtilReVa.doPost(addr, map, RevaRepeatDetailsResp.class,infCameraProperties,null);// http
		return result;		}

	@Override
	public VisitHistoryResp visitHistory(String personCode) throws Exception {

		String addr=infCameraProperties.getServerIP() + infCameraProperties.getAddress().get("visitHistory");

		Map<String, String> map = new HashMap<>();
		map.put("personCode", personCode);
		map.put("areacode",infCameraProperties.getMallCode());
		
		
		VisitHistoryResp result = HttpClientUtilReVa.doPost(addr, map, VisitHistoryResp.class,infCameraProperties,null);// http
		return result;		
	}
	
	
	

}
