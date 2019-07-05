package com.iwhalecloud.retail.warehouse.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.warehouse.dto.request.ResouceInstItmsManualSyncRecAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResouceInstItmsManualSyncRecPageReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResouceInstItmsManualSyncRecListResp;
import com.iwhalecloud.retail.warehouse.entity.ResouceInstItmsManualSyncRec;
import com.iwhalecloud.retail.warehouse.entity.ResouceInstTrack;
import com.iwhalecloud.retail.warehouse.mapper.ResouceInstItmsManualSyncRecMapper;
import com.iwhalecloud.retail.warehouse.mapper.ResouceInstTrackMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
@Slf4j
public class ResouceInstTtmsManualSyncRecManager extends ServiceImpl<ResouceInstTrackMapper, ResouceInstTrack> {

    @Resource
    private ResouceInstItmsManualSyncRecMapper resouceInstItmsManualSyncRecMapper;

    /**
     * 条件分页查询
     * @param req
     * @return
     */
    public Page<ResouceInstItmsManualSyncRecListResp> listResourceItemsManualSyncRec(ResouceInstItmsManualSyncRecPageReq req){
        Page<ResouceInstItmsManualSyncRecListResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        return resouceInstItmsManualSyncRecMapper.listResourceItemsManualSyncRec(page, req);
    }

    /**
     * 新增
     * @param req
     * @return
     */
    public Integer addResourceItemsManualSyncRec(ResouceInstItmsManualSyncRecAddReq req){
        ResouceInstItmsManualSyncRec resouceInstItmsManualSyncRec = new ResouceInstItmsManualSyncRec();
        BeanUtils.copyProperties(req, resouceInstItmsManualSyncRec);
        Date now = new Date();
        resouceInstItmsManualSyncRec.setSyncDate(now);
        resouceInstItmsManualSyncRec.setCreateDate(now);
        resouceInstItmsManualSyncRec.setStatusDate(now);
        return resouceInstItmsManualSyncRecMapper.insert(resouceInstItmsManualSyncRec);
    }
}
