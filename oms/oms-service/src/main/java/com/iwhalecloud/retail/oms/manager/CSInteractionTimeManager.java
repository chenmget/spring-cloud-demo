package com.iwhalecloud.retail.oms.manager;

import com.iwhalecloud.retail.oms.dto.EventDTO;
import com.iwhalecloud.retail.oms.dto.EventInteractionTimeDTO;
import com.iwhalecloud.retail.oms.dto.RankDTO;
import com.iwhalecloud.retail.oms.dto.resquest.CountGoodsReq;
import com.iwhalecloud.retail.oms.dto.resquest.CountKeyWordReq;
import com.iwhalecloud.retail.oms.dto.resquest.EventInteractionTimeReq;
import com.iwhalecloud.retail.oms.entity.Event;
import com.iwhalecloud.retail.oms.entity.EventInteractionTime;
import com.iwhalecloud.retail.oms.mapper.CSInteractionEventMapper;
import com.iwhalecloud.retail.oms.mapper.CSInteractionTimeMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: lin.wh
 * @Date: 2018/11/6 09:34
 * @Description:
 */

@Component
public class CSInteractionTimeManager {

    @Resource
    private CSInteractionTimeMapper csInteractionTimeMapper;

    @Resource
    private CSInteractionEventMapper csInteractionEventMapper;

    public int buryingPointEvent(EventDTO dto) {
        dto.setId(null);
        Event event = new Event();
        BeanUtils.copyProperties(dto, event);
        return csInteractionEventMapper.insert(event);
    }

    public int countInteractionTime(EventInteractionTimeDTO dto) {
        dto.setId(null);
        EventInteractionTime eventInteractionTime = new EventInteractionTime();
        BeanUtils.copyProperties(dto, eventInteractionTime);
        return csInteractionTimeMapper.insert(eventInteractionTime);
    }

    public List<EventInteractionTimeDTO> queryInteractionTime(EventInteractionTimeReq dto) {
        return csInteractionTimeMapper.queryInteractionTime(dto);
    }

    public List<EventDTO> queryLastInteractionTime(EventDTO dto) {
        return csInteractionTimeMapper.queryLastInteractionTime(dto);
    }

    public List<EventDTO> queryBeforeInteractionTime(EventDTO dto) {
        return csInteractionTimeMapper.queryBeforeInteractionTime(dto);
    }

    public List<EventDTO> queryEvent() {
        return csInteractionTimeMapper.queryEvent();
    }

    public List<RankDTO> countKeyWord(CountKeyWordReq req) {
        return csInteractionTimeMapper.countKeyWord(req);
    }

    public List<RankDTO> countGoods(CountGoodsReq req) {
        return csInteractionTimeMapper.countGoods(req);
    }

    public EventDTO getLastUpdateRecord(String devideNum){
        return csInteractionTimeMapper.getLastUpdateRecord(devideNum);
    }

    public EventInteractionTimeDTO getLastUpdateInteracRecord(String devideNum){
        return csInteractionTimeMapper.getLastUpdateInteracRecord(devideNum);
    }

    public int updateEventInteracObject(EventInteractionTimeDTO eventInterac){
        EventInteractionTime domain = new EventInteractionTime();
        BeanUtils.copyProperties(eventInterac, domain);
        return csInteractionTimeMapper.updateById(domain);
    }

    public int addEventInterac(EventInteractionTimeDTO eventInterac){
        EventInteractionTime domain = new EventInteractionTime();
        BeanUtils.copyProperties(eventInterac, domain);
        return csInteractionTimeMapper.insert(domain);
    }
}

