package com.iwhalecloud.retail.partner.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.dto.SupplyProductRelDTO;
import com.iwhalecloud.retail.partner.dto.resp.SupplyProductQryResp;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.partner.manager.SupplyProductRelManager;
import com.iwhalecloud.retail.partner.service.SupplyProductRelService;


@Service
public class SupplyProductRelServiceImpl implements SupplyProductRelService {

    @Autowired
    private SupplyProductRelManager supplyProductRelManager;


    @Override
    public Page<SupplyProductQryResp> querySupplyProductRel(SupplyProductRelDTO dto) {
        return supplyProductRelManager.querySupplyProduct(dto);
    }

    @Override
    public int bindSupplyProductRel(SupplyProductRelDTO dto) {
        return supplyProductRelManager.bindSupplyProduct(dto);
    }

    @Override
    public int unBindSupplyProductRel(SupplyProductRelDTO dto) {
        return supplyProductRelManager.unBindSupplyProduct(dto);
    }
}