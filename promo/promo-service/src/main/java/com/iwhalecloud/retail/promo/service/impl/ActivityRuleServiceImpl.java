package com.iwhalecloud.retail.promo.service.impl;

import com.iwhalecloud.retail.promo.dto.ActivityRuleDTO;
import com.iwhalecloud.retail.promo.entity.ActivityRule;
import com.iwhalecloud.retail.promo.utils.ReflectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.promo.manager.ActivityRuleManager;
import com.iwhalecloud.retail.promo.service.ActivityRuleService;
import org.springframework.util.CollectionUtils;

import java.util.List;


@Service
public class ActivityRuleServiceImpl implements ActivityRuleService {

    @Autowired
    private ActivityRuleManager activityRuleManager;


    @Override
    public List<ActivityRuleDTO> queryActivityRuleByCondition(String marketingActivityId) {
        // 根据活动编码查询活动规则
        List<ActivityRule> activityRuleList = activityRuleManager.queryActivityRuleByCondition(marketingActivityId);
        if (!CollectionUtils.isEmpty(activityRuleList)) {
            List<ActivityRuleDTO> activityRuleDTOList = ReflectUtils.batchAssign(activityRuleList, ActivityRuleDTO.class);
            return activityRuleDTOList;
        }
        return null;
    }
}