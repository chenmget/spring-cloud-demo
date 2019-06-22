package com.iwhalecloud.retail.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.pay.service.JobService;

@Service
public class JobServiceImpl implements JobService {

    @Override
    public int myJob() {
        System.out.println("1234");
        return 0;
    }
}
