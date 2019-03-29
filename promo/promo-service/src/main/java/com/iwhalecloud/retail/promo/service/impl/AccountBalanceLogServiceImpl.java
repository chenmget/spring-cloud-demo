package com.iwhalecloud.retail.promo.service.impl;

import com.iwhalecloud.retail.promo.common.RebateConst;
import com.iwhalecloud.retail.promo.dto.AccountBalanceLogDTO;
import com.iwhalecloud.retail.promo.dto.req.AccountBalanceLogStReq;
import com.iwhalecloud.retail.promo.entity.AccountBalanceLog;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.promo.manager.AccountBalanceLogManager;
import com.iwhalecloud.retail.promo.service.AccountBalanceLogService;

import java.util.Date;


@Service
public class AccountBalanceLogServiceImpl implements AccountBalanceLogService {

    @Autowired
    private AccountBalanceLogManager accountBalanceLogManager;



    @Override
    public String addAccountBalanceLog(AccountBalanceLogDTO accountBalanceLogDTO) {
        AccountBalanceLog log = new AccountBalanceLog();

        log.setStatusCd(String.valueOf(RebateConst.STATUS_USE));
        log.setStatusDate(new Date());
        boolean isSuc = accountBalanceLogManager.save(log);
        if(isSuc){
            return log.getBalanceLogId();
        }

        return null;
    }

    @Override
    public Long getAccountBalanceAddSum(AccountBalanceLogStReq req ) {
        return accountBalanceLogManager.getAccountBalanceAddSum(req);
    }

    @Override
    public Long getAccountBalanceReduceSum(AccountBalanceLogStReq req ) {
        return accountBalanceLogManager.getAccountBalanceReduceSum(req);
    }

}