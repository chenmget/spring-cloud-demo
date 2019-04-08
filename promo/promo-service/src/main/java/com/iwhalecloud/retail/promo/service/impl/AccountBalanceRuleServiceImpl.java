package com.iwhalecloud.retail.promo.service.impl;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.common.RebateConst;
import com.iwhalecloud.retail.promo.dto.AccountBalanceRuleDTO;
import com.iwhalecloud.retail.promo.dto.req.AccountBalanceRuleReq;
import com.iwhalecloud.retail.promo.dto.resp.AccountBalanceRuleResp;
import com.iwhalecloud.retail.promo.entity.AccountBalanceRule;
import com.iwhalecloud.retail.system.common.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.promo.manager.AccountBalanceRuleManager;
import com.iwhalecloud.retail.promo.service.AccountBalanceRuleService;

import java.util.Date;
import java.util.List;


@Service
public class AccountBalanceRuleServiceImpl implements AccountBalanceRuleService {

    @Autowired
    private AccountBalanceRuleManager accountBalanceRuleManager;


    @Override
    public String addAccountBalanceRule(AccountBalanceRuleDTO accountBalanceRule) {
        AccountBalanceRule rule = new AccountBalanceRule();
        BeanUtils.copyProperties(accountBalanceRule, rule);
        rule.setCreateDate(new Date());
        rule.setStatusCd(String.valueOf(RebateConst.Const.STATUS_USE.getValue()));
        rule.setEffDate(new Date());
        //失效时间
        rule.setExpDate(DateUtils.strToUtilDate(RebateConst.EXP_DATE_DEF));
        rule.setStatusDate(new Date());
        boolean isSuc = accountBalanceRuleManager.save(rule);
        if (isSuc) {
            return rule.getRuleId();
        }
        return null;
    }

    @Override
    public ResultVO<List<AccountBalanceRuleResp>> queryAccountBalanceRuleList(AccountBalanceRuleReq req) {
        return ResultVO.success(accountBalanceRuleManager.queryAccountBalanceRuleList(req));
    }
}