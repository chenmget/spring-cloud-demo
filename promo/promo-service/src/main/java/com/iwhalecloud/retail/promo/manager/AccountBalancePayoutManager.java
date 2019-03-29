package com.iwhalecloud.retail.promo.manager;

import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.promo.mapper.AccountBalancePayoutMapper;


@Component
public class AccountBalancePayoutManager{
    @Resource
    private AccountBalancePayoutMapper accountBalancePayoutMapper;
    
    
    
}
