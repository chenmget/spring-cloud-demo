package com.iwhalecloud.retail.promo.manager;

import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.promo.mapper.ActivityChangeMapper;


@Component
public class ActivityChangeManager{
    @Resource
    private ActivityChangeMapper activityChangeMapper;
    
    
    
}
