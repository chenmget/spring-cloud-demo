package com.iwhalecloud.retail.promo.manager;

import com.iwhalecloud.retail.promo.mapper.AccountItemMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class AccountItemManager{
    @Resource
    private AccountItemMapper accountItemMapper;
    
    
    
}
