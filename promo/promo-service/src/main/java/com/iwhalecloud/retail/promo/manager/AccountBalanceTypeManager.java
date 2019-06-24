package com.iwhalecloud.retail.promo.manager;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.promo.common.RebateConst;
import com.iwhalecloud.retail.promo.entity.AccountBalanceType;
import com.iwhalecloud.retail.promo.mapper.AccountBalanceTypeMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;


@Component
@Slf4j
public class AccountBalanceTypeManager extends ServiceImpl<AccountBalanceTypeMapper, AccountBalanceType> {
    @Resource
    private AccountBalanceTypeMapper accountBalanceTypeMapper;

    public int addAccountBalanceType(AccountBalanceType accountBalanceType){
        if(StringUtils.isEmpty(accountBalanceType.getBalanceTypeId())){
            accountBalanceType.setStatusCd(RebateConst.Const.STATUS_USE.getValue());
            accountBalanceType.setStatusDate(new Date());
            accountBalanceType.setCreateDate(new Date());
            accountBalanceType.setUpdateDate(new Date());
            return accountBalanceTypeMapper.insert(accountBalanceType);
        } else{
            log.info("AccountBalanceTypeManager.addAccountBalanceType accountBalanceTypeDTO{}", JSON.toJSON(accountBalanceType));
            accountBalanceType.setStatusCd(RebateConst.Const.STATUS_USE.getValue());
            accountBalanceType.setStatusDate(new Date());
            accountBalanceType.setCreateDate(new Date());
            accountBalanceType.setUpdateDate(new Date());
            return accountBalanceTypeMapper.updateById(accountBalanceType);
        }
    }

}
