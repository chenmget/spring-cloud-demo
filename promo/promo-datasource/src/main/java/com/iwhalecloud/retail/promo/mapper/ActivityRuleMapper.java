package com.iwhalecloud.retail.promo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.iwhalecloud.retail.promo.entity.ActivityRule;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Class: ActivityRuleMapper
 * @author autoCreate
 */
@Mapper
public interface ActivityRuleMapper extends BaseMapper<ActivityRule>{
    /**
     * 删除活动规则
     * @param marketingActivityId
     */
    void deleteActivityRule(String marketingActivityId);
}