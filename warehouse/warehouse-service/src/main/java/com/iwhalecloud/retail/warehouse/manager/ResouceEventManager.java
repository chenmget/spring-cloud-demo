package com.iwhalecloud.retail.warehouse.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.warehouse.common.GenerateCodeUtil;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.ResouceEventDTO;
import com.iwhalecloud.retail.warehouse.dto.request.ResouceEventUpdateReq;
import com.iwhalecloud.retail.warehouse.entity.ResouceEvent;
import com.iwhalecloud.retail.warehouse.mapper.ResouceEventMapper;
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
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(ResouceEvent.FieldNames.objId.getTableFieldName(),resouceEventDTO.getObjId());
        queryWrapper.eq(ResouceEvent.FieldNames.objType.getTableFieldName(),resouceEventDTO.getObjType());
        queryWrapper.eq(ResouceEvent.FieldNames.mktResId.getTableFieldName(),resouceEventDTO.getMktResId());
        ResouceEvent event = resouceEventMapper.selectOne(queryWrapper);
        if(null != event){
            ResouceEvent updateEvent = new ResouceEvent();
            updateEvent.setStatusCd(ResourceConst.EVENTSTATE.DONE.getCode());
            updateEvent.setUpdateDate(new Date());
            resouceEventMapper.update(updateEvent,queryWrapper);
            return event.getMktResEventId();
        }else {
            BeanUtils.copyProperties(resouceEventDTO, resouceEvent);
            resouceEvent.setCreateDate(Calendar.getInstance().getTime());
            resouceEvent.setStatusDate(Calendar.getInstance().getTime());
            resouceEvent.setAcceptDate(Calendar.getInstance().getTime());
            resouceEvent.setStatusCd(ResourceConst.EVENTSTATE.PROCESSING.getCode());
            resouceEvent.setMktResEventNbr(GenerateCodeUtil.generateCode());
            resouceEvent.setUpdateDate(new Date());
            resouceEventMapper.insert(resouceEvent);
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
