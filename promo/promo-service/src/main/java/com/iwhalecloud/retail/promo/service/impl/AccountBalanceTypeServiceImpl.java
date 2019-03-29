package com.iwhalecloud.retail.promo.service.impl;

import com.iwhalecloud.retail.promo.common.RebateConst;
import com.iwhalecloud.retail.promo.dto.AccountBalanceTypeDTO;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountBalanceReq;
import com.iwhalecloud.retail.promo.entity.AccountBalanceType;
import com.iwhalecloud.retail.system.common.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.promo.manager.AccountBalanceTypeManager;
import com.iwhalecloud.retail.promo.service.AccountBalanceTypeService;

import java.util.Date;
import java.util.List;


@Service
public class AccountBalanceTypeServiceImpl implements AccountBalanceTypeService {

    @Autowired
    private AccountBalanceTypeManager accountBalanceTypeManager;
    @Override
    public String addAccountBalanceType(AccountBalanceTypeDTO accountBalanceType){
        AccountBalanceType type = new AccountBalanceType();
        BeanUtils.copyProperties(accountBalanceType, type);
        type.setCreateDate(new Date());
        type.setStatusCd(String.valueOf(RebateConst.STATUS_USE));
        type.setStatusDate(new Date());
        boolean isSuc = accountBalanceTypeManager.save(type);
        if(isSuc){
            return type.getBalanceTypeId();
        }
        return null;
    }

}