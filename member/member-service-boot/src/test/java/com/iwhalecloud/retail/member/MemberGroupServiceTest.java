package com.iwhalecloud.retail.member;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.dto.SourceFromContext;
import com.iwhalecloud.retail.member.dto.request.*;
import com.iwhalecloud.retail.member.dto.response.GroupQueryResp;
import com.iwhalecloud.retail.member.dto.response.MemberGroupQueryResp;
import com.iwhalecloud.retail.member.service.GroupService;
import com.iwhalecloud.retail.member.service.MemberGroupService;
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
public class MemberGroupServiceTest {

    @Autowired
    private MemberGroupService memberGroupService;

    @Test
    public void addMemberGroupTest(){
        SourceFromContext.setSourceFrom("hnyhj_b2b");
        MemberGroupAddReq req  = new MemberGroupAddReq();
        req.setCreateStaff("张三");
        req.setGroupId("1102849388210343937");
        req.setMemId("150722226400163390");
        ResultVO<Boolean> resultVO = memberGroupService.addMemberGroup(req);
        SourceFromContext.removeSourceFrom();
        log.info("addMemberGroupTest result={}",resultVO.getResultData());
    }

    @Test
    public void updateMemberGroupByIdTest(){
        MemberGroupUpdateReq req = new MemberGroupUpdateReq();
        req.setGroupId("1102849388210343937");
        req.setMemId("150714920000161980");
        req.setStatus("0");
        req.setUpdateStaff("李四");
        ResultVO<Boolean> resultVO = memberGroupService.updateMemberGroupById(req);
        log.info("updateMemberGroupByIdTest result={}",resultVO.getResultData());
    }

    @Test
    public void deleteMemberGroupTest(){
        MemberGroupDeleteReq req = new MemberGroupDeleteReq();
        req.setGroupId("1102849388210343937");
        req.setMemId("150722226400163390");
        req.setUpdateStaff("王五");
        ResultVO<Boolean> resultVO = memberGroupService.deleteMemberGroup(req);
        log.info("deleteMemberGroupTest result={}",resultVO.getResultData());
    }

    @Test
    public void queryGroupByMemberForPageTest(){
        MemberGroupQueryGroupReq req = new MemberGroupQueryGroupReq();
        req.setPageNo("1");
        req.setPageSize("10");
        req.setMemId("150714920000161980");
        ResultVO<Page<MemberGroupQueryResp>> resultVO = memberGroupService.queryGroupByMemberForPage(req);
        log.info("queryGroupByMemberForPageTest result={}",resultVO.getResultData());
    }

    @Test
    public void queryMemberByGroupForPageTest(){
        MemberGroupQueryMemberReq req = new MemberGroupQueryMemberReq();
        req.setPageNo("1");
        req.setPageSize("10");
        req.setGroupId("1102849388210343937");
        ResultVO<Page<GroupQueryResp>> resultVO = memberGroupService.queryMemberByGroupForPage(req);
        log.info("queryMemberByGroupForPageTest result={}",resultVO.getResultData().getRecords());
    }
}
