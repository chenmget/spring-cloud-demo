package com.iwhalecloud.retail.goods.manager;

import com.iwhalecloud.retail.goods.mapper.GoodsExtMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class GoodsExtManager {
    @Resource
    private GoodsExtMapper goodsExtMapper;
    
    
    
}
