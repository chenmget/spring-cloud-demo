package com.iwhalecloud.retail.goods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.goods.manager.GoodsExtManager;
import com.iwhalecloud.retail.goods.service.GoodsExtService;


@Service
public class GoodsExtServiceImpl implements GoodsExtService {

    @Autowired
    private GoodsExtManager goodsExtManager;

    
    
    
    
}