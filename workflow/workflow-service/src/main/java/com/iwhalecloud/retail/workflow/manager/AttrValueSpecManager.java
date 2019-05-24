package com.iwhalecloud.retail.workflow.manager;

import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.workflow.mapper.AttrValueSpecMapper;


@Component
public class AttrValueSpecManager{
    @Resource
    private AttrValueSpecMapper attrValueSpecMapper;
    
    
    
}
