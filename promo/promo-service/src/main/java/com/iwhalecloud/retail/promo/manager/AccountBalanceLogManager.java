package com.iwhalecloud.retail.promo.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.promo.dto.req.AccountBalanceLogStReq;
import com.iwhalecloud.retail.promo.entity.AccountBalanceLog;
import com.iwhalecloud.retail.promo.mapper.AccountBalanceLogMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class AccountBalanceLogManager extends ServiceImpl<AccountBalanceLogMapper, AccountBalanceLog> {
    @Resource
    private AccountBalanceLogMapper accountBalanceLogMapper;

    public Long getAccountBalanceAddSum(AccountBalanceLogStReq req){
        return accountBalanceLogMapper.getAccountBalanceAddSum(req);
    }

    public Long getAccountBalanceReduceSum(AccountBalanceLogStReq req){
        return accountBalanceLogMapper.getAccountBalanceReduceSum(req);
    }

}
