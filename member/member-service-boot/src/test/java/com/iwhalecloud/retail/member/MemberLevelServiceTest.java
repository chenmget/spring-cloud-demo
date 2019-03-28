package com.iwhalecloud.retail.member;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.dto.SourceFromContext;
import com.iwhalecloud.retail.member.dto.request.*;
import com.iwhalecloud.retail.member.dto.response.GroupQueryResp;
import com.iwhalecloud.retail.member.dto.response.MemberGroupQueryResp;
import com.iwhalecloud.retail.member.dto.response.MemberLevelQueryResp;
import com.iwhalecloud.retail.member.service.MemberGroupService;
import com.iwhalecloud.retail.member.service.MemberLevelService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * @Author: wang.jiaxin
 * @Date: 2019年03月05日
 * @Description:
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MemberServiceApplication.class)
@Slf4j
public class MemberLevelServiceTest {

    @Autowired
    private MemberLevelService memberLevelService;

    @Test
    public void addMemberLevelTest(){
        SourceFromContext.setSourceFrom("hnyhj_b2b");
        MemberLevelAddReq req  = new MemberLevelAddReq();
        req.setLvName("测试等级2");
        req.setMerchantId("1234567890");
        req.setPointStart(1111L);
        req.setPointEnd(2111L);
        ResultVO<Boolean> resultVO = memberLevelService.addMemberLevel(req);
        SourceFromContext.removeSourceFrom();
        log.info("addMemberLevelTest result={}",resultVO.getResultData());
    }

    @Test
    public void updateMemberLevelTest(){
        MemberLevelUpdateReq req = new MemberLevelUpdateReq();
        req.setLvId("1103136846432972801");
        req.setLvName("测试等级6");
        req.setMerchantId("1234567890");
        req.setDiscount(BigDecimal.valueOf(10L));
        req.setIsDefault("1");
        req.setPointStart(5L);
        req.setPointEnd(15L);
        req.setShowName("等级名称6");
        ResultVO<Boolean> resultVO = memberLevelService.updateMemberLevel(req);
        log.info("updateMemberLevelTest result={}",resultVO.getResultData());
    }

    @Test
    public void deleteMemberLevelTest(){
        MemberLevelDeleteReq req = new MemberLevelDeleteReq();
        req.setLvId("1103136846432972801");
        ResultVO<Boolean> resultVO = memberLevelService.deleteMemberLevel(req);
        log.info("deleteMemberLevelTest result={}",resultVO.getResultData());
    }

    @Test
    public void queryGroupLevelForPageTest(){
        MemberLevelQueryReq req = new MemberLevelQueryReq();
        req.setPageNo(1);
        req.setPageSize(10);
        req.setLvId("");
        req.setLvName("等级");
        req.setMerchantId("1234567890");
        req.setIsDefault("0");
        req.setShowName("名称");
        req.setStatus("1");
        req.setSourceFrom("hnyhj_b2b");
        ResultVO<Page<MemberLevelQueryResp>> resultVO = memberLevelService.queryGroupLevelForPage(req);
        log.info("queryGroupLevelForPageTest result={}",resultVO.getResultData());
    }
}
