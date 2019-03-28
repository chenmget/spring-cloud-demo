package com.iwhalecloud.retail.partner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.partner.manager.MerchantTempManager;
import com.iwhalecloud.retail.partner.service.MemchantTempService;


@Service
public class MemchantTempServiceImpl implements MemchantTempService {

    @Autowired
    private MerchantTempManager merchantTempManager;

    
    
    
    
}