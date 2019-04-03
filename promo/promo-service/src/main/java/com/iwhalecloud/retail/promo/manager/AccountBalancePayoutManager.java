package com.iwhalecloud.retail.promo.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountBalancePayoutReq;
import com.iwhalecloud.retail.promo.dto.resp.QueryAccountBalancePayoutResp;
import com.iwhalecloud.retail.promo.mapper.AccountBalancePayoutMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class AccountBalancePayoutManager{
    @Resource
    private AccountBalancePayoutMapper accountBalancePayoutMapper;

    public Page<QueryAccountBalancePayoutResp> queryAccountBalancePayoutForPage(QueryAccountBalancePayoutReq req){
        Page<QueryAccountBalancePayoutResp> page = new Page<>(req.getPageNo(),req.getPageSize());
        Page<QueryAccountBalancePayoutResp> respPage = accountBalancePayoutMapper.queryAccountBalancePayoutForPage(page, req);
        return respPage;
    }

}
