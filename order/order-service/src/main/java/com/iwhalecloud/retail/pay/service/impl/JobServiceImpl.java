package com.iwhalecloud.retail.pay.service.impl;

import com.iwhalecloud.retail.pay.service.JobService;
import com.alibaba.dubbo.config.annotation.Service;

@Service
public class JobServiceImpl implements JobService {

    @Override
    public int myJob() {
        System.out.println("1234");
        return 0;
    }
}
