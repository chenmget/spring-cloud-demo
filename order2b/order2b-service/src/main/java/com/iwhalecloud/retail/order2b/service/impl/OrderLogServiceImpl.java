package com.iwhalecloud.retail.order2b.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.order2b.manager.OrderLogManager;
import com.iwhalecloud.retail.order2b.service.OrderLogService;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class OrderLogServiceImpl implements OrderLogService {

    @Autowired
    private OrderLogManager orderLogManager;

    
    
    
    
}