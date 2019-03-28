package com.iwhalecloud.retail.workflow.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.workflow.manager.TaskPoolManager;
import com.iwhalecloud.retail.workflow.service.TaskPoolService;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class TaskPoolServiceImpl implements TaskPoolService {

    @Autowired
    private TaskPoolManager taskPoolManager;

    
    
    
    
}