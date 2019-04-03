package com.iwhalecloud.retail.warehouse.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.ResouceEventDTO;
import com.iwhalecloud.retail.warehouse.dto.request.ResouceEventUpdateReq;
import com.iwhalecloud.retail.warehouse.entity.ResouceEvent;
import com.iwhalecloud.retail.warehouse.mapper.ResouceEventMapper;
import com.iwhalecloud.retail.warehouse.mapper.ResourceInstMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;


@Component
public class ResouceEventManager{
    @Resource
    private ResouceEventMapper resouceEventMapper;

    @Resource
    private ResourceInstMapper resourceInstMapper;

    /**
     * 新增变动事件
     * @param resouceEventDTO
     * @return
     */
    public String insertResouceEvent(ResouceEventDTO resouceEventDTO) {
        String eventId = "";
        Date now = new Date();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(ResouceEvent.FieldNames.objId.getTableFieldName(), resouceEventDTO.getObjId());
        queryWrapper.eq(ResouceEvent.FieldNames.objType.getTableFieldName(), resouceEventDTO.getObjType());
        queryWrapper.eq(ResouceEvent.FieldNames.mktResId.getTableFieldName(), resouceEventDTO.getMktResId());
        queryWrapper.eq(ResouceEvent.FieldNames.eventType.getTableFieldName(), resouceEventDTO.getEventType());
        ResouceEvent event = resouceEventMapper.selectOne(queryWrapper);
        if (null == event) {
            ResouceEvent resouceEvent = new ResouceEvent();
            BeanUtils.copyProperties(resouceEventDTO, resouceEvent);
            resouceEvent.setCreateDate(now);
            resouceEvent.setStatusDate(now);
            resouceEvent.setAcceptDate(now);
            resouceEvent.setStatusCd(ResourceConst.EVENTSTATE.PROCESSING.getCode());
            resouceEvent.setMktResEventNbr(resourceInstMapper.getPrimaryKey());
            resouceEvent.setUpdateDate(now);
            resouceEventMapper.insert(resouceEvent);
            eventId = resouceEvent.getMktResEventId();
        } else {
            ResouceEvent updateEvent = new ResouceEvent();
            updateEvent.setStatusCd(ResourceConst.EVENTSTATE.DONE.getCode());
            updateEvent.setUpdateDate(now);
            int i = resouceEventMapper.update(updateEvent, queryWrapper);
            eventId = event.getMktResEventId();
        }
        return eventId;
    }

    /**
     * 修改变动事件的状态
     * @param req
     * @return
     */
    public int updateResouceEventState(ResouceEventUpdateReq req){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(ResouceEvent.FieldNames.mktResEventId.getTableFieldName(),req.getMktResEventId());
        ResouceEvent resouceEvent = new ResouceEvent();
        resouceEvent.setStatusCd(req.getStatusCd());
        return resouceEventMapper.update(resouceEvent,queryWrapper);
    }
    
    
    
}
