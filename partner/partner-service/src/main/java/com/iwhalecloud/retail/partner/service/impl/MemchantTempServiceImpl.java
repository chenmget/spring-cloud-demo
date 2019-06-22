package com.iwhalecloud.retail.partner.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.partner.manager.MerchantTempManager;
import com.iwhalecloud.retail.partner.service.MemchantTempService;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class MemchantTempServiceImpl implements MemchantTempService {

    @Autowired
    private MerchantTempManager merchantTempManager;

    
    
    
    
}