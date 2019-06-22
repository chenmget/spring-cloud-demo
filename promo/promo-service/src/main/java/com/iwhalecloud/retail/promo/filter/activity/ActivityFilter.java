package com.iwhalecloud.retail.promo.filter.activity;

import com.iwhalecloud.retail.promo.filter.activity.model.ActivityAuthModel;

/**
 * 营销活动过滤器
 * Created by z on 2019/3/4.
 */
public interface ActivityFilter {


    /**
     * 校验是否有权限参与活动
     * @param activityAuthModel
     * @return
     */
    boolean doFilter(ActivityAuthModel activityAuthModel);

}
