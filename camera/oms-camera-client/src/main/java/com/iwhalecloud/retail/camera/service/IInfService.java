package com.iwhalecloud.retail.camera.service;

import com.iwhalecloud.retail.param.req.RevaDailyReportReq;
import com.iwhalecloud.retail.param.req.RevaWeekReportReq;
import com.iwhalecloud.retail.param.resp.RevaDailyReportResp;
import com.iwhalecloud.retail.param.resp.RevaRepeatDetailsResp;
import com.iwhalecloud.retail.param.resp.RevaVipInfoResp;
import com.iwhalecloud.retail.param.resp.VisitHistoryResp;

public interface IInfService {
	
	/**
	 * 
	 */
	public RevaDailyReportResp customersDailyReport(RevaDailyReportReq req) throws Exception;
	
	public RevaDailyReportResp customersWeekReport(RevaWeekReportReq req)throws Exception;
	
	public RevaVipInfoResp vipInfo(String personCode) throws Exception;
	
	public RevaRepeatDetailsResp repeatCustomerDetails(String repeatId, String startDate, String parentcode) throws Exception;
	
	
	public VisitHistoryResp visitHistory(String personCode) throws Exception;

}
