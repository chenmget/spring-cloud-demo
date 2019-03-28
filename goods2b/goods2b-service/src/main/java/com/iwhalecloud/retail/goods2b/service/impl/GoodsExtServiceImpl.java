package com.iwhalecloud.retail.goods2b.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.goods2b.manager.GoodsExtManager;
import com.iwhalecloud.retail.goods2b.service.GoodsExtService;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class GoodsExtServiceImpl implements GoodsExtService {

    @Autowired
    private GoodsExtManager goodsExtManager;

    
    
    
    
}