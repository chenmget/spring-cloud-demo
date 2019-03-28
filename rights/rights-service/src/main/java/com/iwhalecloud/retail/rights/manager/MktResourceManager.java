package com.iwhalecloud.retail.rights.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.iwhalecloud.retail.rights.mapper.MktResourceMapper;


@Component
public class MktResourceManager{
    @Resource
    private MktResourceMapper mktResourceMapper;
    
 
    
}
