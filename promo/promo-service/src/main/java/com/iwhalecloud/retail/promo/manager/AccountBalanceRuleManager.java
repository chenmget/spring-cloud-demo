package com.iwhalecloud.retail.promo.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.promo.dto.req.AccountBalanceRuleReq;
import com.iwhalecloud.retail.promo.dto.resp.AccountBalanceRuleResp;
import com.iwhalecloud.retail.promo.entity.Account;
import com.iwhalecloud.retail.promo.entity.AccountBalanceRule;
import com.iwhalecloud.retail.promo.mapper.AccountMapper;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.promo.mapper.AccountBalanceRuleMapper;

import java.util.List;


@Component
public class AccountBalanceRuleManager extends ServiceImpl<AccountBalanceRuleMapper, AccountBalanceRule> {
    @Resource
    private AccountBalanceRuleMapper accountBalanceRuleMapper;

    public List<AccountBalanceRuleResp> queryAccountBalanceRuleList(AccountBalanceRuleReq req){
        return accountBalanceRuleMapper.queryAccountBalanceRuleList(req);
    }
    
    
}
