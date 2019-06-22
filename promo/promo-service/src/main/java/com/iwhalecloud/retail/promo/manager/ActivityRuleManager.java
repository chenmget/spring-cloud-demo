package com.iwhalecloud.retail.promo.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.entity.ActivityRule;
import com.iwhalecloud.retail.promo.mapper.ActivityRuleMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Component
public class ActivityRuleManager{
    @Resource
    private ActivityRuleMapper activityRuleMapper;

    /**
     * 添加活动规则
     * @param activityRuleList 活动规则
     * @return
     */
    public void addActivityRule(List<ActivityRule> activityRuleList) {
        if (!CollectionUtils.isEmpty(activityRuleList)) {
            for (ActivityRule activityRule : activityRuleList) {
                if (StringUtils.isEmpty(activityRule.getId())) {
                    Date date = new Date();
                    activityRule.setGmtCreate(date);
                    activityRule.setGmtModified(date);
                    activityRule.setIsDeleted("0");
                    activityRuleMapper.insert(activityRule);
                }
            }
        }
    }
    /**
     * 删除活动规则
     * @param marketingActivityId 活动规则
     * @return
     */
    public void deleteActivityRule(String marketingActivityId) {

        activityRuleMapper.deleteActivityRule(marketingActivityId);

    }
    /**
     * 根据活动id查询活动规则
     * @param marketingActivityId 根据活动id查询参与活动规则
     * @return
     */
    public List<ActivityRule> queryActivityRuleByCondition(String marketingActivityId) {
        QueryWrapper<ActivityRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ActivityRule.FieldNames.isDeleted.getTableFieldName(), PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
        queryWrapper.eq(ActivityRule.FieldNames.marketingActivityId.getTableFieldName(), marketingActivityId);
        return activityRuleMapper.selectList(queryWrapper);
    }
}
