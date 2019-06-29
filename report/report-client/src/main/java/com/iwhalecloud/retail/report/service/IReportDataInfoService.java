package com.iwhalecloud.retail.report.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.request.ReportStorePurchaserReq;
import com.iwhalecloud.retail.report.dto.response.ReportStorePurchaserResq;

import java.util.List;

public interface IReportDataInfoService {
	//供应商只能看自己的仓库
	public String getMyMktResStoreId(String relCode);

	//门店进销存机型报表
    public ResultVO<Page<ReportStorePurchaserResq>> getStorePurchaserReport(ReportStorePurchaserReq req);
    //导出
    public ResultVO<List<ReportStorePurchaserResq>> getStorePurchaserReportdc(ReportStorePurchaserReq req);

}
