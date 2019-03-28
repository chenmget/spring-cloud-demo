package com.iwhalecloud.retail.goods2b.manager;

import com.iwhalecloud.retail.goods2b.mapper.GoodsExtMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class GoodsExtManager {
    @Resource
    private GoodsExtMapper goodsExtMapper;
    
    
    
}
