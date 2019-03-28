package com.iwhalecloud.retail.promo.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.dto.ActSupRecordDTO;
import com.iwhalecloud.retail.promo.dto.req.QueryActSupRecordReq;
import com.iwhalecloud.retail.promo.dto.resp.ActSupRecodeListResp;
import com.iwhalecloud.retail.promo.entity.ActSupRecord;
import com.iwhalecloud.retail.promo.mapper.ActSupRecordMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Date;


@Component
public class ActSupRecordManager {
    @Resource
    private ActSupRecordMapper actSupRecordMapper;

    /**
     * 分页查询前置活动补录记录
     * @param queryActSupRecordReq
     * @return
     */
    public Page<ActSupRecodeListResp> queryActSupRecord(QueryActSupRecordReq queryActSupRecordReq){
        Page<ActSupRecodeListResp> page = new Page<>(queryActSupRecordReq.getPageNo(), queryActSupRecordReq.getPageSize());
        return actSupRecordMapper.queryActSupRecord(page,queryActSupRecordReq);
    }

    /**
     * 删除前置活动补录记录
     * @param recordId
     * @return
     */
    public Integer deleteActSupRecord(String recordId){
        ActSupRecord actSupRecord = new ActSupRecord();
        actSupRecord.setRecordId(recordId);
        actSupRecord.setStatus(PromoConst.ActivitySupStatus.ACTIVITY_SUP_STATUS_CANCEL.getCode());
        return actSupRecordMapper.updateById(actSupRecord);
    }

    /**
     * 新增前置记录补录
     * @param actSupRecordDTO
     * @return
     */
    public String addActSupRecord(ActSupRecordDTO actSupRecordDTO){
        ActSupRecord actSupRecord = new ActSupRecord();
        BeanUtils.copyProperties(actSupRecordDTO, actSupRecord);
        actSupRecord.setStatus(PromoConst.ActivitySupStatus.ACTIVITY_SUP_STATUS_PEDING.getCode());
        actSupRecord.setGmtCreate(new Date());
        actSupRecord.setGmtModified(new Date());
        actSupRecord.setModifier(actSupRecordDTO.getCreator());
        actSupRecordMapper.insert(actSupRecord);
        return actSupRecord.getRecordId();
    }

    /**
     * 更新前置活动补录记录状态
     * @param recordId
     * @param status
     * @return
     */
    public Integer updateActSupRecordStatus(String recordId, String status) {
        ActSupRecord actSupRecord = new ActSupRecord();
        actSupRecord.setRecordId(recordId);
        actSupRecord.setStatus(status);
        return actSupRecordMapper.updateById(actSupRecord);
    }
}
