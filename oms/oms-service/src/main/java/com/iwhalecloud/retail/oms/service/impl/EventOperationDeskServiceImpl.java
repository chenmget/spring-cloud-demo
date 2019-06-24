package com.iwhalecloud.retail.oms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.oms.dto.ReportEventShopRankDTO;
import com.iwhalecloud.retail.oms.dto.ReportEventTimeIntervalDTO;
import com.iwhalecloud.retail.oms.dto.resquest.report.ReportOperationDeskReq;
import com.iwhalecloud.retail.oms.manager.EventOperationDeskManager;
import com.iwhalecloud.retail.oms.service.EventOperationDeskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
@Service
public class EventOperationDeskServiceImpl implements EventOperationDeskService {

    @Autowired
    private EventOperationDeskManager eventOperationDeskManager;

    @Override
    public int getTodayEventCountByPartnerId(String partnerId, String eventCode) {
        return eventOperationDeskManager.getTodayEventCountByPartnerId(partnerId, eventCode);
    }

    @Override
    public int getEventCountByArea(ReportOperationDeskReq req){
        return eventOperationDeskManager.getEventCountByArea(req);
    }

    @Override
    public List<ReportEventShopRankDTO> getShopVisitVolumeRankByArea(ReportOperationDeskReq req){
        return eventOperationDeskManager.getShopVisitVolumeRankByArea(req);
    }

    @Override
    public  List<ReportEventShopRankDTO> getShopWorkTimeRankByArea(ReportOperationDeskReq req) {
        return eventOperationDeskManager.getShopWorkTimeRankByArea(req);
    }

    @Override
    public List<ReportEventTimeIntervalDTO> getTimeIntervalVisitByArea(ReportOperationDeskReq req) throws Exception {
        return eventOperationDeskManager.getTimeIntervalVisitByArea(req);
    }

    @Override
    public List<ReportEventTimeIntervalDTO> getOnOffLineTimeAboutDevice(ReportOperationDeskReq req) throws Exception{
        return eventOperationDeskManager.getOnOffLineTimeAboutDevice(req);
    }
}
