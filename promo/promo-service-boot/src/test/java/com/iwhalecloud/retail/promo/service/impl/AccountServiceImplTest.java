package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.PromoServiceApplication;
import com.iwhalecloud.retail.promo.common.RebateConst;
import com.iwhalecloud.retail.promo.dto.req.*;
import com.iwhalecloud.retail.promo.dto.resp.*;
import com.iwhalecloud.retail.promo.service.AccountBalanceDetailService;
import com.iwhalecloud.retail.promo.service.AccountBalancePayoutService;
import com.iwhalecloud.retail.promo.service.AccountBalanceService;
import com.iwhalecloud.retail.promo.service.AccountService;
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
    @Reference
    private AccountBalancePayoutService accountBalancePayoutService;



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
        req.setPageSize(10);
        req.setPageNo(1);
        req.setCustId("4300001063072");
        ResultVO<Page<QueryAccountForPageResp>> resultVO = accountService.queryAccountForPage(req);
        System.out.println(resultVO);
    }

    @Test
    public void queryTotalAccount(){
        //测试通过
        QueryTotalAccountReq req = new QueryTotalAccountReq();
        req.setCustId("4300001063072");
        req.setAcctType("20");
        req.setPageNo(1);
        req.setPageSize(10);
        ResultVO<Page<QueryTotalAccountResp>> pageResultVO = accountService.queryTotalAccount(req);
        System.out.println(pageResultVO);
    }
    @Test
    public void queryAccountBalanceDetailAllForPage(){
        //测试通过
        QueryAccountBalanceDetailAllReq req = new QueryAccountBalanceDetailAllReq();
//        req.setAcctId("1");
        req.setAcctType("20");
        req.setCustId("4300001063072");
        req.setPageNo(1);
        req.setPageSize(10);
//        req.setActName("优惠券");
//        req.setSupplierName("三星投资有限公司");
//        req.setEffDateEnd("2019-10-12");

        ResultVO<Page<QueryAccountBalanceDetailAllResp>> pageResultVO = accountBalanceDetailService.queryAccountBalanceDetailAllForPage(req);
        System.out.println(pageResultVO);
    }
    @Test
    public void queryAccountBalanceAllForPage(){
        //已改未测试
        QueryAccountBalanceAllReq req = new QueryAccountBalanceAllReq();
        req.setCustId("4300001063072");
        req.setAcctType("20");
        req.setPageNo(1);
        req.setPageSize(10);
        req.setEffDateEnd("2019-10-12");
        ResultVO<Page<QueryAccountBalanceAllResp>> pageResultVO = accountBalanceService.queryAccountBalanceAllForPage(req);
        System.out.println(pageResultVO);

    }
    @Test
    public void queryAccountBalancePayoutForPage(){
        QueryAccountBalancePayoutReq req = new QueryAccountBalancePayoutReq();
        req.setPageNo(1);
        req.setPageSize(10);
        req.setCustId("4300001063072");
        req.setAcctType("20");
//        req.setBrandName("华为");
//        req.setSupplierLoginName("111");
//        req.setSupplierName("ly");
//        req.setOperDateStart("1987-10-10");
//        req.setOperDateEnd("2020-10-10");
        ResultVO<Page<QueryAccountBalancePayoutResp>> pageResultVO = accountBalancePayoutService.queryAccountBalancePayoutForPage(req);

        System.out.println(pageResultVO);

    }

    /**
     * 返利计算
     */
    @Test
    public void calculation(){
        //测试通过，需要改造
        AccountBalanceCalculationReq req = new AccountBalanceCalculationReq();
        req.setBalanceSourceType(RebateConst.Const.ACCOUNT_BALANCE_DETAIL_BALANCE_SOURCE_TYPE_ID.getValue());
//        req.setCustId("4300001063072");
        req.setCustId("10511269");
        req.setUserId("1082191485979451394");
        List<AccountBalanceCalculationOrderItemReq> orderItemList = new ArrayList<AccountBalanceCalculationOrderItemReq>();
        AccountBalanceCalculationOrderItemReq itemReq = new AccountBalanceCalculationOrderItemReq();
        itemReq.setActId("10008987");
        itemReq.setActName("20190402lytest1");
        itemReq.setActNum("30");
        itemReq.setOrderId("201903223810003591");
        itemReq.setProductId("100000092");
//        itemReq.setProductId("11111");
        itemReq.setSupplierId("10000578");
//        itemReq.setOrderItemId("201903210910000050");
        itemReq.setOrderItemId("201903223810003592");
        orderItemList.add(itemReq);

        req.setOrderItemList(orderItemList);

        ResultVO resultVO = accountBalanceService.calculation(req);
        System.out.println(resultVO);

    }
}
