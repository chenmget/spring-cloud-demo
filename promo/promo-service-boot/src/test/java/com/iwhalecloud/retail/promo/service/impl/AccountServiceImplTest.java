package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.PromoServiceApplication;
import com.iwhalecloud.retail.promo.common.RebateConst;
import com.iwhalecloud.retail.promo.dto.req.*;
import com.iwhalecloud.retail.promo.dto.resp.QueryAccountBalanceAllResp;
import com.iwhalecloud.retail.promo.dto.resp.QueryAccountBalanceDetailAllResp;
import com.iwhalecloud.retail.promo.dto.resp.QueryAccountForPageResp;
import com.iwhalecloud.retail.promo.dto.resp.QueryTotalAccountResp;
import com.iwhalecloud.retail.promo.service.AccountBalanceDetailService;
import com.iwhalecloud.retail.promo.service.AccountBalanceService;
import com.iwhalecloud.retail.promo.service.AccountService;
import com.iwhalecloud.retail.promo.service.MarketingActivityService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 吴良勇
 * @date 2019/3/26 17:22
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromoServiceApplication.class)
public class AccountServiceImplTest {

    @Reference
    private AccountService accountService;
    @Reference
    private AccountBalanceDetailService accountBalanceDetailService;

    @Reference
    private AccountBalanceService accountBalanceService;


    @Test
    public void addAccount(){
        AddAccountReq  req = new AddAccountReq();
        req.setCreateStaff("1082191485979451394");
        req.setAcctName("中兴通讯股份有限公司");
        req.setAcctType(RebateConst.Const.ACCOUNT_BALANCE_DETAIL_ACCT_TYPE_REBATE.getValue());
        req.setCustId("10000577");
        ResultVO resultVO = accountService.addAccount(req);
        System.out.println(resultVO);
    }
    @Test
    public void updateAccount(){
        UpdateAccountReq  req = new UpdateAccountReq();
        req.setAcctType("20");
        req.setAcctId("10116518");
        req.setAcctName("中兴通讯股份有限公司lytest");
        ResultVO resultVO = accountService.updateAccount(req);
        System.out.println(resultVO);
    }
    @Test
    public void queryAccountForPage(){
        QueryAccountForPageReq  req = new QueryAccountForPageReq();
        req.setAcctType("20");
        req.setPageSize(1);
        req.setPageNo(10);
        ResultVO<Page<QueryAccountForPageResp>> resultVO = accountService.queryAccountForPage(req);
        System.out.println(resultVO);
    }

    @Test
    public void queryTotalAccount(){
        QueryTotalAccountReq req = new QueryTotalAccountReq();
//        req.setCustId("123");
        req.setAcctType("20");
        req.setPageNo(1);
        req.setPageSize(10);
        ResultVO<Page<QueryTotalAccountResp>> pageResultVO = accountService.queryTotalAccount(req);
        System.out.println(pageResultVO);
    }
    @Test
    public void queryAccountBalanceDetailAllForPage(){
        QueryAccountBalanceDetailAllReq req = new QueryAccountBalanceDetailAllReq();
//        req.setAcctId("1");
        req.setAcctType("20");
        req.setPageNo(1);
        req.setPageSize(10);
//        req.setEffDateEnd("2019-10-12");

        ResultVO<Page<QueryAccountBalanceDetailAllResp>> pageResultVO = accountBalanceDetailService.queryAccountBalanceDetailAllForPage(req);
        System.out.println(pageResultVO);
    }
    @Test
    public void queryAccountBalanceAllForPage(){
        QueryAccountBalanceAllReq req = new QueryAccountBalanceAllReq();
        req.setAcctId("1");
        req.setPageNo(1);
        req.setPageSize(10);
        req.setEffDateEnd("2019-10-12");
        ResultVO<Page<QueryAccountBalanceAllResp>> pageResultVO = accountBalanceService.queryAccountBalanceAllForPage(req);
        System.out.println(pageResultVO);

    }
    @Test
    public void calculation(){
        AccountBalanceCalculationReq req = new AccountBalanceCalculationReq();
        req.setBalanceSourceType(RebateConst.Const.ACCOUNT_BALANCE_DETAIL_BALANCE_SOURCE_TYPE_ID.getValue());
        req.setCustId("4300001063072");
        req.setUserId("1082191485979451394");
        List<AccountBalanceCalculationOrderItemReq> orderItemList = new ArrayList<AccountBalanceCalculationOrderItemReq>();
        AccountBalanceCalculationOrderItemReq itemReq = new AccountBalanceCalculationOrderItemReq();
        itemReq.setActId("10008987");
        itemReq.setActName("111");
        itemReq.setActNum("20");
        itemReq.setOrderId("1");
        itemReq.setProductId("123456");
        itemReq.setSupplierId("1111");
        itemReq.setOrderItemId("12312");

        orderItemList.add(itemReq);
        req.setOrderItemList(orderItemList);

        ResultVO resultVO = accountBalanceService.calculation(req);
        System.out.println(resultVO);

    }
}
