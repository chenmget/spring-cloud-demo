package com.iwhalecloud.retail.warehouse.busiservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iwhalecloud.retail.warehouse.manager.ResourceReqDetailManager;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceReqDetailService;


@Service
public class ResourceReqDetailServiceImpl implements ResourceReqDetailService {

    @Autowired
    private ResourceReqDetailManager resourceReqDetailManager;


}