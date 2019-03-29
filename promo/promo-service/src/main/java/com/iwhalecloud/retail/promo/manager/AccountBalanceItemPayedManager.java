package com.iwhalecloud.retail.promo.manager;

import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.promo.mapper.AccountBalanceItemPayedMapper;


@Component
public class AccountBalanceItemPayedManager{
    @Resource
    private AccountBalanceItemPayedMapper accountBalanceItemPayedMapper;
    
    
    
}
