package com.iwhalecloud.retail.warehouse.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.warehouse.common.GenerateCodeUtil;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.ResouceEventDTO;
import com.iwhalecloud.retail.warehouse.dto.request.ResouceEventUpdateReq;
import com.iwhalecloud.retail.warehouse.entity.ResouceEvent;
import com.iwhalecloud.retail.warehouse.mapper.ResouceEventMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;


@Component
public class ResouceEventManager{
    @Resource
    private ResouceEventMapper resouceEventMapper;

    /**
     * 新增变动事件
     * @param resouceEventDTO
     * @return
     */
    public String insertResouceEvent(ResouceEventDTO resouceEventDTO) {
        ResouceEvent resouceEvent = new ResouceEvent();
        Date now = new Date();
        if (StringUtils.isEmpty(resouceEventDTO.getObjId())) {
            BeanUtils.copyProperties(resouceEventDTO, resouceEvent);
            resouceEvent.setCreateDate(now);
            resouceEvent.setStatusDate(now);
            resouceEvent.setAcceptDate(now);
            resouceEvent.setStatusCd(ResourceConst.EVENTSTATE.PROCESSING.getCode());
            resouceEvent.setMktResEventNbr(GenerateCodeUtil.generateCode());
            resouceEvent.setUpdateDate(new Date());
            resouceEventMapper.insert(resouceEvent);
        } else {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq(ResouceEvent.FieldNames.objId.getTableFieldName(), resouceEventDTO.getObjId());
            queryWrapper.eq(ResouceEvent.FieldNames.objType.getTableFieldName(), resouceEventDTO.getObjType());
            queryWrapper.eq(ResouceEvent.FieldNames.mktResId.getTableFieldName(), resouceEventDTO.getMktResId());
            queryWrapper.eq(ResouceEvent.FieldNames.eventType.getTableFieldName(), resouceEventDTO.getEventType());
            ResouceEvent event = resouceEventMapper.selectOne(queryWrapper);
            ResouceEvent updateEvent = new ResouceEvent();
            updateEvent.setStatusCd(ResourceConst.EVENTSTATE.DONE.getCode());
            updateEvent.setUpdateDate(now);
            resouceEventMapper.update(updateEvent, queryWrapper);
            return event.getMktResEventId();
        }
        return resouceEvent.getMktResEventId();
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
