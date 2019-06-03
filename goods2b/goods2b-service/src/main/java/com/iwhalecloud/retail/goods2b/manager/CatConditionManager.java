package com.iwhalecloud.retail.goods2b.manager;

import com.iwhalecloud.retail.goods2b.mapper.CatConditionMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class CatConditionManager {
    @Resource
    private CatConditionMapper catConditionMapper;


}
