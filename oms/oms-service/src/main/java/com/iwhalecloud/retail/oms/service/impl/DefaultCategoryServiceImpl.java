package com.iwhalecloud.retail.oms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.oms.manager.DefaultCategoryManager;
import com.iwhalecloud.retail.oms.service.DefaultCategoryService;


@Service
public class DefaultCategoryServiceImpl implements DefaultCategoryService {

    @Autowired
    private DefaultCategoryManager defaultCategoryManager;

    
    
    
    
}