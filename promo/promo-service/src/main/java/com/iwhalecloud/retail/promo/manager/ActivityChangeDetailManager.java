package com.iwhalecloud.retail.promo.manager;

import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.promo.mapper.ActivityChangeDetailMapper;


@Component
public class ActivityChangeDetailManager{
    @Resource
    private ActivityChangeDetailMapper activityChangeDetailMapper;
    
    
    
}
