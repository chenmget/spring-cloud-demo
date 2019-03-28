package com.iwhalecloud.retail.promo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.iwhalecloud.retail.promo.dto.ActivityParticipantDTO;
import com.iwhalecloud.retail.promo.entity.ActivityParticipant;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Class: ActivityParticipantMapper
 * @author autoCreate
 */
@Mapper
public interface ActivityParticipantMapper extends BaseMapper<ActivityParticipant>{
    /**
     * 根据活动Id删除活动参与对象
     * @param marketingActivityId
     */
    void deleteActivityParticipantBatch(String marketingActivityId);

    /**
     * 根据活动Id查询活动参与对象
     * @param marketingActivityId
     * @return
     */
    List<ActivityParticipantDTO>  queryActivityParticipantByMktId(String marketingActivityId);
}