package com.iwhalecloud.retail.promo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.promo.dto.req.AccountBalanceRuleReq;
import com.iwhalecloud.retail.promo.dto.resp.AccountBalanceRuleResp;
import com.iwhalecloud.retail.promo.entity.AccountBalanceRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: AccountBalanceRuleMapper
 * @author autoCreate
 */
@Mapper
public interface AccountBalanceRuleMapper extends BaseMapper<AccountBalanceRule>{
    List<AccountBalanceRuleResp> queryAccountBalanceRuleList(@Param("req") AccountBalanceRuleReq req);
}