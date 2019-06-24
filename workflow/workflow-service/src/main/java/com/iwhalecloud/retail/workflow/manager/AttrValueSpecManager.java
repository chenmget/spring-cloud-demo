package com.iwhalecloud.retail.workflow.manager;

import com.iwhalecloud.retail.workflow.mapper.AttrValueSpecMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class AttrValueSpecManager{
    @Resource
    private AttrValueSpecMapper attrValueSpecMapper;
    
    
    
}
