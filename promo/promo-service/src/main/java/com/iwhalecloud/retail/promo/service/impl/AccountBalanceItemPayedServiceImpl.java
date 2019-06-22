package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.promo.manager.AccountBalanceItemPayedManager;
import com.iwhalecloud.retail.promo.service.AccountBalanceItemPayedService;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class AccountBalanceItemPayedServiceImpl implements AccountBalanceItemPayedService {

    @Autowired
    private AccountBalanceItemPayedManager accountBalanceItemPayedManager;


}