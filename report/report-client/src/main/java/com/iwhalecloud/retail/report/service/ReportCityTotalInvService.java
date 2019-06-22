package com.iwhalecloud.retail.report.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.request.ReportCityTotalInvReq;
import com.iwhalecloud.retail.report.dto.response.ReportCityTotalInvResp;

import java.util.List;
/**
 * @author lipeng
 * @date 2019-06-10
 * 地市总进销存报表服务接口
 */
public interface ReportCityTotalInvService {

    /**
     * 查询地市总进销存报表
     * @param req
     * @return
     */
    ResultVO<Page<ReportCityTotalInvResp>> getCityTotalInvList(ReportCityTotalInvReq req);

    /**
     * 报表导出
     * @param req
     * @return
     */
	ResultVO<List<ReportCityTotalInvResp>> exportCityTotalInvListReport(ReportCityTotalInvReq req);
}
