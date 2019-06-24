package com.iwhalecloud.retail.promo.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.promo.common.RebateConst;
import com.iwhalecloud.retail.promo.dto.AccountDTO;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountForPageReq;
import com.iwhalecloud.retail.promo.dto.resp.QueryAccountForPageResp;
import com.iwhalecloud.retail.promo.entity.Account;
import com.iwhalecloud.retail.promo.mapper.AccountMapper;
import com.iwhalecloud.retail.system.common.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Component
public class AccountManager extends ServiceImpl<AccountMapper, Account> {
    @Resource
    private AccountMapper accountMapper;


    public int addAccount(Account account){
        if(StringUtils.isEmpty(account.getStatusCd())){
            account.setStatusCd(RebateConst.Const.STATUS_USE.getValue());
        }
        if(StringUtils.isEmpty(account.getEffDate())){
            account.setEffDate(new Date());
        }
        if(StringUtils.isEmpty(account.getExpDate())){
            account.setExpDate(DateUtils.strToUtilDate(RebateConst.EXP_DATE_DEF));
        }
        return accountMapper.insert(account);

    }
    public Account getAccountByCustId(String  custId,String acctType){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(Account.FieldNames.custId.getTableFieldName(),custId);
        queryWrapper.eq(Account.FieldNames.statusCd.getTableFieldName(), RebateConst.Const.STATUS_USE.getValue());
        queryWrapper.eq(Account.FieldNames.acctType.getTableFieldName(), acctType);
        queryWrapper.le(Account.FieldNames.effDate.getTableFieldName(),new Date());
        queryWrapper.ge(Account.FieldNames.expDate.getTableFieldName(),new Date());
        List<Account> list = accountMapper.selectList(queryWrapper);
        if(list!=null&&!list.isEmpty()){
            return list.get(0);
        }
        return null;

    }

    public Page<QueryAccountForPageResp> queryAccountForPage(QueryAccountForPageReq req){
        Page<QueryAccountForPageResp> page = new Page<>(req.getPageNo(),req.getPageSize());
        Page<QueryAccountForPageResp> respPage = accountMapper.queryAccountForPage(page, req);
        return respPage;
    }
    public Account getAccountByAcctId(String acctId){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(Account.FieldNames.acctId.getTableFieldName(),acctId);
        queryWrapper.eq(Account.FieldNames.statusCd.getTableFieldName(), RebateConst.Const.STATUS_USE.getValue());

        queryWrapper.le(Account.FieldNames.effDate.getTableFieldName(),new Date());
        queryWrapper.ge(Account.FieldNames.expDate.getTableFieldName(),new Date());
        return accountMapper.selectOne(queryWrapper);

    }
    public int updateAccount(AccountDTO account){
        Account accountData = new Account();
        BeanUtils.copyProperties(account,accountData);
        return accountMapper.updateById(accountData);

    }
    public String getRebateNextId(){
        return accountMapper.getRebateNextId();
    }

    /**
     * 获取accountId
     * @param custId
     * @param acctType
     * @return
     */
    public String  getAccountId(String custId,String acctType){
        String acctId = RebateConst.QUERY_NULL;
        Account account = this.getAccountByCustId(custId,acctType);
        if(account!=null){
            acctId =  account.getAcctId();
        }
        return acctId;
    }

}
