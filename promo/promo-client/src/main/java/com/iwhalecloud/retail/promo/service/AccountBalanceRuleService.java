package com.iwhalecloud.retail.promo.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.dto.AccountBalanceRuleDTO;
import com.iwhalecloud.retail.promo.dto.req.AccountBalanceRuleReq;
import com.iwhalecloud.retail.promo.dto.resp.AccountBalanceRuleResp;

import java.util.List;

public interface AccountBalanceRuleService{

    String addAccountBalanceRule(AccountBalanceRuleDTO accountBalanceRule);

    ResultVO<List<AccountBalanceRuleResp>> queryAccountBalanceRuleList(AccountBalanceRuleReq req);
}