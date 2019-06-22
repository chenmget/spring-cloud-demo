package com.iwhalecloud.retail.system.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.system.dto.PublicDictDTO;
import com.iwhalecloud.retail.system.manager.PublicDictManager;
import com.iwhalecloud.retail.system.service.PublicDictService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
public class PublicDictServiceImpl implements PublicDictService {

    @Autowired
    private PublicDictManager publicDictManager;


    @Override
    public List<PublicDictDTO> queryPublicDictListByType(String type) {
        return publicDictManager.queryPublicDictListByType(type);
    }
}