package com.iwhalecloud.retail.oms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.oms.dto.EventDTO;
import com.iwhalecloud.retail.oms.dto.EventInteractionTimeDTO;
import com.iwhalecloud.retail.oms.dto.RankDTO;
import com.iwhalecloud.retail.oms.dto.resquest.CountGoodsReq;
import com.iwhalecloud.retail.oms.dto.resquest.CountKeyWordReq;
import com.iwhalecloud.retail.oms.dto.resquest.EventInteractionTimeReq;
import com.iwhalecloud.retail.oms.entity.EventInteractionTime;
import com.iwhalecloud.retail.oms.manager.CSInteractionTimeManager;
import com.iwhalecloud.retail.oms.service.CSInteractionTimeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Auther: lin.wh
 * @Date: 2018/11/6 09:28
 * @Description:
 */

@Service
public class CSInteractionTimeServiceImpl implements CSInteractionTimeService {

    @Autowired
    private CSInteractionTimeManager csInteractionTimeManager;

    @Override
    public int buryingPointEvent(EventDTO dto) {
        return csInteractionTimeManager.buryingPointEvent(dto);
    }

    @Override
    public int countInteractionTime(EventInteractionTimeDTO dto) {
        return csInteractionTimeManager.countInteractionTime(dto);
    }

    @Override
    public List<EventInteractionTimeDTO> queryInteractionTime(EventInteractionTimeReq dto) {
        return csInteractionTimeManager.queryInteractionTime(dto);
    }

    @Override
    public List<EventDTO> queryLastInteractionTime(EventDTO dto) {
        return csInteractionTimeManager.queryLastInteractionTime(dto);
    }

    @Override
    public List<EventDTO> queryBeforeInteractionTime(EventDTO dto) {
        return csInteractionTimeManager.queryBeforeInteractionTime(dto);
    }

    @Override
    public List<EventDTO> queryEvent() {
        return csInteractionTimeManager.queryEvent();
    }

    @Override
    public List<RankDTO> countKeyWord(CountKeyWordReq req) {
        return csInteractionTimeManager.countKeyWord(req);
    }

    @Override
    public List<RankDTO> countGoods(CountGoodsReq req) {
        return csInteractionTimeManager.countGoods(req);
    }

    @Override
    public EventDTO getLastUpdateRecord(String devideNum) {
        return csInteractionTimeManager.getLastUpdateRecord(devideNum);
    }

    @Override
    public EventInteractionTimeDTO getLastUpdateInteracRecord(String deviceNum) {
        return csInteractionTimeManager.getLastUpdateInteracRecord(deviceNum);
    }

    @Override
    public int updateOrAddEventInterac(EventInteractionTimeDTO eventInterac) {
        int effectRownum = 0;
        if (null != eventInterac.getId()){
            effectRownum = csInteractionTimeManager.updateEventInteracObject(eventInterac);
        } else {
            effectRownum = csInteractionTimeManager.addEventInterac(eventInterac);
        }
        return effectRownum;
    }
}

