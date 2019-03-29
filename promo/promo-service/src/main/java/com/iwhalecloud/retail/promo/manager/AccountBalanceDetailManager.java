package com.iwhalecloud.retail.promo.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.promo.dto.AccountBalanceDetailDTO;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountBalanceDetailAllReq;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountBalanceDetailForPageReq;
import com.iwhalecloud.retail.promo.dto.resp.QueryAccountBalanceDetailAllResp;
import com.iwhalecloud.retail.promo.entity.AccountBalanceDetail;
import com.iwhalecloud.retail.promo.mapper.AccountBalanceDetailMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class AccountBalanceDetailManager extends ServiceImpl<AccountBalanceDetailMapper, AccountBalanceDetail> {
    @Resource
    private AccountBalanceDetailMapper accountBalanceDetailMapper;

    public Page<AccountBalanceDetailDTO> queryAccountBalanceDetailForPage(QueryAccountBalanceDetailForPageReq req) {
        Page<AccountBalanceDetailDTO> page = new Page<AccountBalanceDetailDTO>(req.getPageNo(),req.getPageSize());
        Page<AccountBalanceDetailDTO> respPage = accountBalanceDetailMapper.queryAccountBalanceDetailForPage(page, req);
        return respPage;


    }
    public Page<QueryAccountBalanceDetailAllResp> queryAccountBalanceDetailAllForPage(QueryAccountBalanceDetailAllReq req){
        Page<QueryAccountBalanceDetailAllResp> page = new Page<>(req.getPageNo(),req.getPageSize());
        Page<QueryAccountBalanceDetailAllResp> respPage = accountBalanceDetailMapper.queryAccountBalanceDetailAllForPage(page, req);
        return respPage;
    }
}
