package com.iwhalecloud.retail.goods.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.goods.manager.ProdCatComplexManager;
import com.iwhalecloud.retail.goods.service.dubbo.ProdCatComplexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component("prodCatComplexService")
@Service
public class ProdCatComplexServiceImpl implements ProdCatComplexService {

    @Autowired
    private ProdCatComplexManager prodCatComplexManager;

    
    
    
    
}