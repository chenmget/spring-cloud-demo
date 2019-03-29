package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.dto.AccountDTO;
import com.iwhalecloud.retail.promo.dto.req.*;
import com.iwhalecloud.retail.promo.dto.resp.AccountBalanceStResp;
import com.iwhalecloud.retail.promo.dto.resp.QueryAccountForPageResp;
import com.iwhalecloud.retail.promo.dto.resp.QueryTotalAccountResp;
import com.iwhalecloud.retail.promo.entity.Account;
import com.iwhalecloud.retail.promo.manager.AccountManager;
import com.iwhalecloud.retail.promo.service.AccountBalanceLogService;
import com.iwhalecloud.retail.promo.service.AccountBalanceService;
import com.iwhalecloud.retail.promo.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Component("accountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountManager accountManager;

    @Autowired
    private AccountBalanceService accountBalanceService;
    @Reference
    private AccountBalanceLogService accountBalanceLogService;


    @Override
    public ResultVO addAccount(AddAccountReq req) {
        Account account = new Account();
        String custId = req.getCustId();
        ResultVO<AccountDTO> accountDTOResultVO = this.getAccountByCustId(custId,req.getAcctType());
        if(accountDTOResultVO.getResultData()!=null){
            return ResultVO.error("商家对应的已经账户存在");
        }

        BeanUtils.copyProperties(req, account);
        account.setCreateDate(new Date());
        account.setStatusDate(new Date());
        int n = accountManager.addAccount(account);
        if(n>0){
            return ResultVO.success(account.getAcctId());
        }
        return ResultVO.error("插入失败");

    }
    @Override
    public ResultVO updateAccount(UpdateAccountReq req){
        ResultVO<AccountDTO> data = this.getAccountByAcctId(req.getAcctId());
        if(data.getResultData()==null){
            return ResultVO.error("修改失败,账户已不存在");
        }
        AccountDTO accountDTO = new AccountDTO();
        BeanUtils.copyProperties(req,accountDTO);
        int n = accountManager.updateAccount(accountDTO);
        if(n>0){
            return ResultVO.success(accountDTO.getAcctId());
        }
        return ResultVO.error("修改失败");

    }
    @Override
    public ResultVO<AccountDTO> getAccountByAcctId(String acctId){
        Account account = accountManager.getAccountByAcctId(acctId);
        AccountDTO dto = null;
        if(account!=null){
            dto = new AccountDTO();
            BeanUtils.copyProperties(account,dto);
        }

        return ResultVO.success(dto);
    }
    @Override
    public ResultVO<AccountDTO> getAccountByCustId(String custId,String acctType){
        Account account = accountManager.getAccountByCustId(custId,acctType);
        if(account!=null){
            AccountDTO accountDTO = new AccountDTO();
            BeanUtils.copyProperties(account, accountDTO);
            return ResultVO.success(accountDTO);
        }
        return ResultVO.success();
    }
    @Override
    public ResultVO<Page<QueryTotalAccountResp>> queryTotalAccount(QueryTotalAccountReq req){

        QueryAccountForPageReq pageReq = new QueryAccountForPageReq();

        pageReq.setCustId(req.getCustId());
        pageReq.setAcctType(req.getAcctType());
        pageReq.setPageNo(req.getPageNo());
        pageReq.setPageSize(req.getPageSize());
        ResultVO<Page<QueryAccountForPageResp>> pageResultVO = this.queryAccountForPage(pageReq);
        ResultVO<Page<QueryTotalAccountResp>> resultVO = new ResultVO<Page<QueryTotalAccountResp>>();
        resultVO.setResultCode(pageResultVO.getResultCode());
        resultVO.setResultMsg(pageResultVO.getResultMsg());
        Page<QueryTotalAccountResp> respPage = new  Page<QueryTotalAccountResp>();
        if(pageResultVO.getResultData()!=null){
            Page<QueryAccountForPageResp> page = pageResultVO.getResultData();

            if(page!=null){
                BeanUtils.copyProperties(page, respPage);
                List<QueryTotalAccountResp> list = new ArrayList<QueryTotalAccountResp>();
                List<QueryAccountForPageResp> accountList = page.getRecords();
                if(accountList!=null&&!accountList.isEmpty()){
                    for (QueryAccountForPageResp queryAccountForPageResp : accountList) {
                        QueryTotalAccountResp totalAccountResp = new QueryTotalAccountResp();
                        BeanUtils.copyProperties(queryAccountForPageResp, totalAccountResp);
                        this.initOtherSt(totalAccountResp,queryAccountForPageResp);
                        list.add(totalAccountResp);
                    }
                }

                respPage.setRecords(list);


            }
        }
        resultVO.setResultData(respPage);
        return resultVO;

    }
    private void initOtherSt(QueryTotalAccountResp totalAccountResp,QueryAccountForPageResp queryAccountForPageResp){
        //其他统计数据
        AccountBalanceStReq stReq = new AccountBalanceStReq();
        stReq.setAcctId(queryAccountForPageResp.getAcctId());
        stReq.setAcctType(queryAccountForPageResp.getAcctType());
        //获取账本统计信息
        AccountBalanceStResp stResp = accountBalanceService.getBalanceSt(stReq);
        int accountBalanceSum = accountBalanceService.getAccountBalanceSum(stReq);
        totalAccountResp.setBalanceNum(String.valueOf(accountBalanceSum));
        totalAccountResp.setTotalAmount("0");
        totalAccountResp.setTotalUneffAmount("0");
        totalAccountResp.setTotalInvaildAmount("0");
        if(stResp!=null){
            totalAccountResp.setTotalAmount(stResp.getTotalAmount());
            totalAccountResp.setTotalUneffAmount(stResp.getTotalUneffAmount());
            totalAccountResp.setTotalInvaildAmount(stResp.getTotalInvaildAmount());
            //统计收支情况
            AccountBalanceLogStReq logStReq = new AccountBalanceLogStReq();
            logStReq.setAcctId(queryAccountForPageResp.getAcctId());
            Long addSum = accountBalanceLogService.getAccountBalanceAddSum(logStReq);
            totalAccountResp.setTotalIncome(addSum==null?"0":String.valueOf(addSum));
            Long reduceSum = accountBalanceLogService.getAccountBalanceReduceSum(logStReq);
            totalAccountResp.setTotalExpenses(reduceSum==null?"0":String.valueOf(reduceSum));
        }
    }


    @Override
    public ResultVO<Page<QueryAccountForPageResp>> queryAccountForPage(QueryAccountForPageReq req) {
        return ResultVO.success(accountManager.queryAccountForPage(req));

    }
}