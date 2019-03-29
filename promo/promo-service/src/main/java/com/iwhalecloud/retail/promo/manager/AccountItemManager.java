package com.iwhalecloud.retail.promo.manager;

import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.promo.mapper.AccountItemMapper;


@Component
public class AccountItemManager{
    @Resource
    private AccountItemMapper accountItemMapper;
    
    
    
}
