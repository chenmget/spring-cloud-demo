package com.iwhalecloud.retail.promo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.iwhalecloud.retail.promo.dto.ActivityScopeDTO;
import com.iwhalecloud.retail.promo.entity.ActivityScope;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: ActivityScopeMapper
 * @author autoCreate
 */
@Mapper
public interface ActivityScopeMapper extends BaseMapper<ActivityScope>{
    /**
     * 获取主键
     *
     * @return
     */
    String getPrimaryKey();

    /**
     * 删除活动参与范围
     * @param marketingActivityId
     */
    void deleteActivityScopeBatch(String marketingActivityId);

    /**
     * 根据活动id查询活动范围
     * @param marketingActivityId
     * @return
     */
    List<ActivityScopeDTO> queryActivityScopeByMktId(String marketingActivityId);


    /**
     * 根据活动id和状态查询活动范围
     * @param activityId
     * @param status
     * @return
     */
    List<ActivityScopeDTO> queryActivityScopeByMktIdAndStatus(@Param("activityId")String activityId, @Param("status")String status);

}