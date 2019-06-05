package com.iwhalecloud.retail.warehouse.manager;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.ResouceEventDTO;
import com.iwhalecloud.retail.warehouse.entity.ResouceEvent;
import com.iwhalecloud.retail.warehouse.mapper.ResouceEventMapper;
import com.iwhalecloud.retail.warehouse.mapper.ResourceChngEvtDetailMapper;
import com.iwhalecloud.retail.warehouse.mapper.ResourceInstMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Component
public class ResouceEventManager {
    @Resource
    private ResouceEventMapper resouceEventMapper;

    @Resource
    private ResourceChngEvtDetailMapper resourceChngEvtDetailMapper;

    @Resource
    private ResourceInstMapper resourceInstMapper;

    /**
     * 新增变动事件
     *
     * @param resouceEventDTO
     * @return
     */
    public String insertResouceEvent(ResouceEventDTO resouceEventDTO) {
        log.info("ResouceEventManager.insertResouceEvent req={}", JSON.toJSONString(resouceEventDTO));
        String eventId = "";
        Date now = new Date();
        QueryWrapper queryWrapper = new QueryWrapper();
        boolean exist = true;
        ResouceEvent event = null;
        if (StringUtils.isEmpty(resouceEventDTO.getObjId()) || StringUtils.isEmpty(resouceEventDTO.getObjType())) {
            exist = false;
        } else {
            queryWrapper.eq(ResouceEvent.FieldNames.objId.getTableFieldName(), resouceEventDTO.getObjId());
            queryWrapper.eq(ResouceEvent.FieldNames.objType.getTableFieldName(), resouceEventDTO.getObjType());
            queryWrapper.eq(ResouceEvent.FieldNames.mktResId.getTableFieldName(), resouceEventDTO.getMktResId());
            queryWrapper.eq(ResouceEvent.FieldNames.eventType.getTableFieldName(), resouceEventDTO.getEventType());
            event = resouceEventMapper.selectOne(queryWrapper);
            if (null == event) {
                exist = false;
            } else {
                exist = true;
            }

        }
        if (!exist) {
            ResouceEvent resouceEvent = new ResouceEvent();
            BeanUtils.copyProperties(resouceEventDTO, resouceEvent);
            resouceEvent.setCreateDate(now);
            resouceEvent.setStatusDate(now);
            resouceEvent.setAcceptDate(now);
            if (StringUtils.isEmpty(resouceEventDTO.getStatusCd())) {
                resouceEvent.setStatusCd(ResourceConst.EVENTSTATE.PROCESSING.getCode());
            }
            resouceEvent.setMktResEventNbr(resourceInstMapper.getPrimaryKey());
            resouceEvent.setUpdateDate(now);
            resouceEventMapper.insert(resouceEvent);
            eventId = resouceEvent.getMktResEventId();
        } else {
            ResouceEvent updateEvent = new ResouceEvent();
            updateEvent.setStatusCd(ResourceConst.EVENTSTATE.DONE.getCode());
            if (StringUtils.isNotEmpty(resouceEventDTO.getStatusCd())) {
                updateEvent.setStatusCd(resouceEventDTO.getStatusCd());
            }
            updateEvent.setUpdateDate(now);
            int i = resouceEventMapper.update(updateEvent, queryWrapper);
            eventId = event.getMktResEventId();
        }
        return eventId;
    }


}
