package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.promo.manager.AccountItemManager;
import com.iwhalecloud.retail.promo.service.AccountItemService;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class AccountItemServiceImpl implements AccountItemService {

    @Autowired
    private AccountItemManager accountItemManager;


}