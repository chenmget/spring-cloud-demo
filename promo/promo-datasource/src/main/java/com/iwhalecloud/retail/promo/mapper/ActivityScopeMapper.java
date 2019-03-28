package com.iwhalecloud.retail.promo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.iwhalecloud.retail.promo.dto.ActivityScopeDTO;
import com.iwhalecloud.retail.promo.entity.ActivityScope;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Class: ActivityScopeMapper
 * @author autoCreate
 */
@Mapper
public interface ActivityScopeMapper extends BaseMapper<ActivityScope>{
    /**
     * 删除活动参与范围
     * @param marketingActivityId
     */
    void deleteActivityScopeBatch(String marketingActivityId);

    /**
     * 查询活动范围
     * @param marketingActivityId
     * @return
     */
    List<ActivityScopeDTO> queryActivityScopeByMktId(String marketingActivityId);
}