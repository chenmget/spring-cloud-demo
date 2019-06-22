package com.iwhalecloud.retail.partner.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.partner.dto.SupplyRelDTO;
import com.iwhalecloud.retail.partner.dto.req.SupplyRelAddReq;
import com.iwhalecloud.retail.partner.dto.req.SupplyRelDeleteReq;
import com.iwhalecloud.retail.partner.manager.SupplyRelManager;
import com.iwhalecloud.retail.partner.service.SupplyRelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component("supplyRelService")
@Service
public class SupplyRelServiceImpl implements SupplyRelService {

    @Autowired
    private SupplyRelManager supplyRelManager;


    @Override
    @Transactional
    public int deleteSupplyRel(SupplyRelDeleteReq supplyRelDeleteReq) {
        return supplyRelManager.deleteSupplyRel(supplyRelDeleteReq);
    }

    @Override
    @Transactional
    public SupplyRelDTO addSupplyRel(SupplyRelAddReq req) {
        return supplyRelManager.addSupplyRel(req);
    }

}