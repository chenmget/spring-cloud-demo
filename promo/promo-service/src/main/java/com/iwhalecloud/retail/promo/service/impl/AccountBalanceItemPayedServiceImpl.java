package com.iwhalecloud.retail.promo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.promo.manager.AccountBalanceItemPayedManager;
import com.iwhalecloud.retail.promo.service.AccountBalanceItemPayedService;


@Service
public class AccountBalanceItemPayedServiceImpl implements AccountBalanceItemPayedService {

    @Autowired
    private AccountBalanceItemPayedManager accountBalanceItemPayedManager;


}