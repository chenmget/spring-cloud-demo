package com.iwhalecloud.retail.member;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.dto.SourceFromContext;
import com.iwhalecloud.retail.member.dto.request.MemberMerchantAddReq;
import com.iwhalecloud.retail.member.dto.request.MemberMerchantDeleteReq;
import com.iwhalecloud.retail.member.dto.request.MemberMerchantQueryForPageReq;
import com.iwhalecloud.retail.member.dto.request.MemberMerchantUpdateReq;
import com.iwhalecloud.retail.member.dto.response.MemberMerchantQueryForPageResp;
import com.iwhalecloud.retail.member.service.MemberMerchantService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @Author: wang.jiaxin
 * @Date: 2019年03月05日
 * @Description:
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MemberServiceApplication.class)
@Slf4j
public class MemberMerchantServiceTest {

    @Autowired
    private MemberMerchantService memberMerchantService;

    @Test
    public void addMemberMerchantTest(){
        SourceFromContext.setSourceFrom("hnyhj_b2b");
        MemberMerchantAddReq req  = new MemberMerchantAddReq();
        req.setMerchId("1234567890");
        req.setMemId("150714920000161980");
        req.setLvId(1);
        req.setUpdateStaff("张三");
        ResultVO<Boolean> resultVO = memberMerchantService.addMemberMerchant(req);
        SourceFromContext.removeSourceFrom();
        log.info("addMemberMerchantTest result={}",resultVO.getResultData());
    }

    @Test
    public void updateMemberMerchantByIdTest(){
        MemberMerchantUpdateReq req = new MemberMerchantUpdateReq();
        req.setMerchId("1234567890");
        req.setMemId("150722226400163390");
        req.setLvId(2);
        req.setUpdateStaff("李四");
        ResultVO<Boolean> resultVO = memberMerchantService.updateMemberMerchant(req);
        log.info("updateMemberMerchantByIdTest result={}",resultVO.getResultData());
    }

    @Test
    public void deleteMemberMerchantTest(){
        MemberMerchantDeleteReq req = new MemberMerchantDeleteReq();
        req.setMerchId("1234567890");
        req.setMemId("150722226400163390");
        req.setUpdateStaff("王五");
        ResultVO<Boolean> resultVO = memberMerchantService.deleteMemberMerchant(req);
        log.info("deleteMemberMerchantTest result={}",resultVO.getResultData());
    }

    @Test
    public void queryGroupByMemberForPageTest(){
        MemberMerchantQueryForPageReq req = new MemberMerchantQueryForPageReq();
        req.setPageNo("1");
        req.setPageSize("10");
        req.setMerchId("");
        req.setMemId("");
//        req.setLvId(1);
        req.setUpdateEndDate(new Date());
        ResultVO<Page<MemberMerchantQueryForPageResp>> resultVO = memberMerchantService.queryMemberMerchantForPage(req);
        log.info("queryGroupByMemberForPageTest result={}",resultVO.getResultData());
    }
}
