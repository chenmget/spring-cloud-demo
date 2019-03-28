package com.iwhalecloud.retail.oms.service;

import com.iwhalecloud.retail.oms.dto.ReportEventShopRankDTO;
import com.iwhalecloud.retail.oms.dto.ReportEventTimeIntervalDTO;
import com.iwhalecloud.retail.oms.dto.resquest.report.ReportOperationDeskReq;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface EventOperationDeskService {

    /**
     * 获取分销商当天的商品加购数统计
     * @param partnerId
     * @param eventCode
     * @return
     */
    int getTodayEventCountByPartnerId(String partnerId,String eventCode);

    int getEventCountByArea(ReportOperationDeskReq req);

    List<ReportEventShopRankDTO> getShopVisitVolumeRankByArea(ReportOperationDeskReq req);

    List<ReportEventShopRankDTO> getShopWorkTimeRankByArea(ReportOperationDeskReq req);

    List<ReportEventTimeIntervalDTO> getTimeIntervalVisitByArea(ReportOperationDeskReq req) throws Exception;

    List<ReportEventTimeIntervalDTO> getOnOffLineTimeAboutDevice(ReportOperationDeskReq req) throws Exception;
}
