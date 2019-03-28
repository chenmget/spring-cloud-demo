package com.iwhalecloud.retail.workflow.manager;

import com.iwhalecloud.retail.workflow.mapper.MatchMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class MatchManager{
    @Resource
    private MatchMapper matchMapper;
    
    
    
}
