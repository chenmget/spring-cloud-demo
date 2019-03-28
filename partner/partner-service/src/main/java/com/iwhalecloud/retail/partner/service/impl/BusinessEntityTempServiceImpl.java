package com.iwhalecloud.retail.partner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.partner.manager.BusinessEntityTempManager;
import com.iwhalecloud.retail.partner.service.BusinessEntityTempService;


@Service
public class BusinessEntityTempServiceImpl implements BusinessEntityTempService {

    @Autowired
    private BusinessEntityTempManager businessEntityTempManager;

}