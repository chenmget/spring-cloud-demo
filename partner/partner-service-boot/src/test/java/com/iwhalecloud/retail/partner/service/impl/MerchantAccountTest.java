package com.iwhalecloud.retail.partner.service.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.PartnerServiceApplication;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.MerchantAccountDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantAccountListReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantAccountSaveReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantAccountUpdateReq;
import com.iwhalecloud.retail.partner.service.MerchantAccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author: wang.jiaxin
 * @date: 2019年03月18日
 * @description:
 **/
@SpringBootTest(classes = PartnerServiceApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class MerchantAccountTest {
    @Autowired
    private MerchantAccountService merchantAccountService;

    @Test
    public void saveMerchantAccountTest() {
        MerchantAccountSaveReq req = new MerchantAccountSaveReq();
        req.setAccount("324234234");
        req.setAccountType(PartnerConst.MerchantAccountTypeEnum.BEST_PAY.getType());
        req.setBank("中国招商银行");
        req.setBankAccount("账户名称");
        req.setIsDefault("0");
        req.setMerchantId("23432904320984");
        ResultVO<MerchantAccountDTO> resultVO = merchantAccountService.saveMerchantAccount(req);
        log.info("MerchantAccountTest.saveMerchantAccountTest resp={}", JSON.toJSON(resultVO.getResultData()));
    }

    @Test
    public void getMerchantAccountByIdTest() {
        String accountId = "100016333";
        ResultVO<MerchantAccountDTO> resultVO = merchantAccountService.getMerchantAccountById(accountId);
        log.info("MerchantAccountTest.getMerchantAccountByIdTest resp={}", JSON.toJSON(resultVO.getResultData()));
    }

    @Test
    public void updateMerchantAccountTest() {
        MerchantAccountUpdateReq req = new MerchantAccountUpdateReq();
        req.setAccountId("100016333");
        req.setAccount("21345432423");
        req.setAccountType(PartnerConst.MerchantAccountTypeEnum.BEST_PAY.getType());
        req.setBank("中国招商银行");
        req.setBankAccount("账户名称");
        req.setIsDefault("1");
        req.setMerchantId("23432904320984");
        ResultVO<Integer> resultVO = merchantAccountService.updateMerchantAccount(req);
        log.info("MerchantAccountTest.updateMerchantAccountTest resp={}", JSON.toJSON(resultVO.getResultData()));
    }

    @Test
    public void deleteMerchantAccountByIdTest() {
        String accountId = "100016333";
        ResultVO<Integer> resultVO = merchantAccountService.deleteMerchantAccountById(accountId);
        log.info("MerchantAccountTest.deleteMerchantAccountByIdTest resp={}", JSON.toJSON(resultVO.getResultData()));
    }

    @Test
    public void listMerchantAccountTest() {
        MerchantAccountListReq req = new MerchantAccountListReq();
        req.setAccountType(PartnerConst.MerchantAccountTypeEnum.BEST_PAY.getType());
        req.setMerchantId("23432904320984");
        ResultVO<List<MerchantAccountDTO>> resultVO = merchantAccountService.listMerchantAccount(req);
        log.info("MerchantAccountTest.listMerchantAccountTest resp={}", JSON.toJSON(resultVO.getResultData()));
    }
}
