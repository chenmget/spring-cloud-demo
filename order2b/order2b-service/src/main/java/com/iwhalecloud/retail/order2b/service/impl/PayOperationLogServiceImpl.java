package com.iwhalecloud.retail.order2b.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.order2b.manager.PayOperationLogManager;
import com.iwhalecloud.retail.order2b.service.PayOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class PayOperationLogServiceImpl implements PayOperationLogService {

    @Autowired
    private PayOperationLogManager payOperationLogManager;

    
    
    
    
}