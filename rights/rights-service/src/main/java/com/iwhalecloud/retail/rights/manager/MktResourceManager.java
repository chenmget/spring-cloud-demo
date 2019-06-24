package com.iwhalecloud.retail.rights.manager;

import com.iwhalecloud.retail.rights.mapper.MktResourceMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class MktResourceManager{
    @Resource
    private MktResourceMapper mktResourceMapper;
    
 
    
}
