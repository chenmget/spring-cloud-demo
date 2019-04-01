package com.iwhalecloud.retail.promo.service;


import com.iwhalecloud.retail.promo.dto.ActivityRuleDTO;

import java.util.List;

public interface ActivityRuleService{

    List<ActivityRuleDTO> queryActivityRuleByCondition(String marketingActivityId);

}