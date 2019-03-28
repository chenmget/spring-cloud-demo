package com.iwhalecloud.retail.oms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.oms.manager.DefaultContentManager;
import com.iwhalecloud.retail.oms.service.DefaultContentService;


@Service
public class DefaultContentServiceImpl implements DefaultContentService {

    @Autowired
    private DefaultContentManager defaultContentManager;

    
    
    
    
}