package com.iwhalecloud.retail.promo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.iwhalecloud.retail.promo.dto.ActivityParticipantDTO;
import com.iwhalecloud.retail.promo.entity.ActivityParticipant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: ActivityParticipantMapper
 * @author autoCreate
 */
@Mapper
public interface ActivityParticipantMapper extends BaseMapper<ActivityParticipant>{
    /**
     * 获取主键
     *
     * @return
     */
    String getPrimaryKey();

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


    /**
     * 根据活动id和状态查询活动参与对象
     * @param activityId
     * @param status
     * @return
     */
    List<ActivityParticipantDTO> queryActivityParticipantByMktIdAndStatus(@Param("activityId")String activityId, @Param("status")String status);

}