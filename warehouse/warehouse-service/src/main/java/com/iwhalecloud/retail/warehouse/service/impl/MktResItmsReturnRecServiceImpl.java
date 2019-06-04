package com.iwhalecloud.retail.warehouse.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.warehouse.manager.MktResItmsReturnRecManager;
import com.iwhalecloud.retail.warehouse.service.MktResItmsReturnRecService;


@Service
public class MktResItmsReturnRecServiceImpl implements MktResItmsReturnRecService {

    @Autowired
    private MktResItmsReturnRecManager mktResItmsReturnRecManager;

    
    
    
    
}