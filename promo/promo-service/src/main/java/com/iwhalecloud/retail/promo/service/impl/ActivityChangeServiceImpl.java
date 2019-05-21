package com.iwhalecloud.retail.promo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.promo.manager.ActivityChangeManager;
import com.iwhalecloud.retail.promo.service.ActivityChangeService;


@Service
public class ActivityChangeServiceImpl implements ActivityChangeService {

    @Autowired
    private ActivityChangeManager activityChangeManager;

    
    
    
    
}