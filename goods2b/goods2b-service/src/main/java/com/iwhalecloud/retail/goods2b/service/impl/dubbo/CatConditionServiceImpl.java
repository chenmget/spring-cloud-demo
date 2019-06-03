package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.goods2b.manager.CatConditionManager;
import com.iwhalecloud.retail.goods2b.service.dubbo.CatConditionService;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class CatConditionServiceImpl implements CatConditionService {

    @Autowired
    private CatConditionManager catConditionManager;

    
    
    
    
}