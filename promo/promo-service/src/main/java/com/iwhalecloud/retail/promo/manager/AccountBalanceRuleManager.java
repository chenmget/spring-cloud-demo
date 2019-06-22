package com.iwhalecloud.retail.promo.manager;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.promo.common.RebateConst;
import com.iwhalecloud.retail.promo.dto.req.AccountBalanceRuleReq;
import com.iwhalecloud.retail.promo.dto.resp.AccountBalanceRuleResp;
import com.iwhalecloud.retail.promo.entity.AccountBalanceRule;
import com.iwhalecloud.retail.promo.mapper.AccountBalanceRuleMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Component
@Slf4j
public class AccountBalanceRuleManager extends ServiceImpl<AccountBalanceRuleMapper, AccountBalanceRule> {
    @Resource
    private AccountBalanceRuleMapper accountBalanceRuleMapper;

    public int addAccountBalanceRule(AccountBalanceRule balanceRule){
        if(StringUtils.isEmpty(balanceRule.getRuleId())){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            balanceRule.setRuleType(RebateConst.Const.RULE_TYPE_REBATE.getValue());
            balanceRule.setObjType(RebateConst.Const.ACCOUNT_BALANCE_TYPE_MERCHANT.getValue());
            balanceRule.setStatusCd(RebateConst.Const.STATUS_USE.getValue());
            balanceRule.setStatusDate(new Date());
            balanceRule.setCreateDate(new Date());
            balanceRule.setUpdateDate(new Date());
            balanceRule.setEffDate(new Date());
            try {
                balanceRule.setExpDate(simpleDateFormat.parse(RebateConst.EXP_DATE_DEF));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return accountBalanceRuleMapper.insert(balanceRule);
        } else{
            log.info("AccountBalanceTypeManager.addAccountBalanceType accountBalanceTypeDTO{}", JSON.toJSON(balanceRule));
            balanceRule.setStatusCd(RebateConst.Const.STATUS_USE.getValue());
            balanceRule.setStatusDate(new Date());
            balanceRule.setCreateDate(new Date());
            balanceRule.setUpdateDate(new Date());
            return accountBalanceRuleMapper.updateById(balanceRule);
        }
    }

    public List<AccountBalanceRuleResp> queryAccountBalanceRuleList(AccountBalanceRuleReq req){
        return accountBalanceRuleMapper.queryAccountBalanceRuleList(req);
    }


}
