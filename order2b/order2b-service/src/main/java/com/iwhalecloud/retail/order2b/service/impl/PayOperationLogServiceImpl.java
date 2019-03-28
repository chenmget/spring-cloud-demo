package com.iwhalecloud.retail.order2b.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.order2b.manager.PayOperationLogManager;
import com.iwhalecloud.retail.order2b.service.PayOperationLogService;


@Service
public class PayOperationLogServiceImpl implements PayOperationLogService {

    @Autowired
    private PayOperationLogManager payOperationLogManager;

    
    
    
    
}