package com.iwhalecloud.retail.promo.manager;

import com.iwhalecloud.retail.promo.mapper.AccountBalanceItemPayedMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class AccountBalanceItemPayedManager{
    @Resource
    private AccountBalanceItemPayedMapper accountBalanceItemPayedMapper;
    
    
    
}
