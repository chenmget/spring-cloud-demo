package com.iwhalecloud.retail.goods.manager;

import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.goods.mapper.GoodsExtMapper;


@Component
public class GoodsExtManager {
    @Resource
    private GoodsExtMapper goodsExtMapper;
    
    
    
}
