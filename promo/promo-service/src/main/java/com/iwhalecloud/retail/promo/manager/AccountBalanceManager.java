package com.iwhalecloud.retail.promo.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.promo.dto.AccountBalanceDTO;
import com.iwhalecloud.retail.promo.dto.req.AccountBalanceStReq;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountBalanceAllReq;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountBalanceReq;
import com.iwhalecloud.retail.promo.dto.resp.AccountBalanceStResp;
import com.iwhalecloud.retail.promo.dto.resp.QueryAccountBalanceAllResp;
import com.iwhalecloud.retail.promo.entity.AccountBalance;
import com.iwhalecloud.retail.promo.mapper.AccountBalanceMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class AccountBalanceManager extends ServiceImpl<AccountBalanceMapper, AccountBalance> {
    @Resource
    private AccountBalanceMapper accountBalanceMapper;

    public List<AccountBalanceDTO> queryAccountBalanceList(QueryAccountBalanceReq req){
        return accountBalanceMapper.queryAccountBalanceList(req);
    }
    public AccountBalanceStResp getBalanceSt(AccountBalanceStReq req){

        return accountBalanceMapper.getBalanceSt(req);

    }
    public int getAccountBalanceSum(AccountBalanceStReq req){
        return accountBalanceMapper.getAccountBalanceSum(req);
    }
    
    public Page<QueryAccountBalanceAllResp> queryAccountBalanceAllForPage(QueryAccountBalanceAllReq req){
        Page<QueryAccountBalanceAllResp> page = new Page<>(req.getPageNo(),req.getPageSize());
        Page<QueryAccountBalanceAllResp> respPage = accountBalanceMapper.queryAccountBalanceAllForPage(page, req);
        return respPage;
    }
}
