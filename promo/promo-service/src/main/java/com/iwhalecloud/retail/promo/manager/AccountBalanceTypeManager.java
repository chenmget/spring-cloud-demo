package com.iwhalecloud.retail.promo.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.promo.dto.AccountBalanceTypeDTO;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountBalanceReq;
import com.iwhalecloud.retail.promo.entity.AccountBalanceRule;
import com.iwhalecloud.retail.promo.entity.AccountBalanceType;
import com.iwhalecloud.retail.promo.mapper.AccountBalanceRuleMapper;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.promo.mapper.AccountBalanceTypeMapper;

import java.util.List;


@Component
public class AccountBalanceTypeManager extends ServiceImpl<AccountBalanceTypeMapper, AccountBalanceType> {
    @Resource
    private AccountBalanceTypeMapper accountBalanceTypeMapper;


}
