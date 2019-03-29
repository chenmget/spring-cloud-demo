package com.iwhalecloud.retail.promo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.promo.manager.AccountBalancePayoutManager;
import com.iwhalecloud.retail.promo.service.AccountBalancePayoutService;


@Service
public class AccountBalancePayoutServiceImpl implements AccountBalancePayoutService {

    @Autowired
    private AccountBalancePayoutManager accountBalancePayoutManager;

    
    
    
    
}