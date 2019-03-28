package com.iwhalecloud.retail.goods.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.goods.manager.ProdTagRelManager;
import com.iwhalecloud.retail.goods.service.dubbo.ProdTagRelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component("prodTagRelService")
@Service
public class ProdTagRelServiceImpl implements ProdTagRelService {

    @Autowired
    private ProdTagRelManager prodTagRelManager;

    
    
    
    
}