package com.iwhalecloud.retail.member;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.dto.SourceFromContext;
import com.iwhalecloud.retail.member.dto.request.*;
import com.iwhalecloud.retail.member.dto.response.GroupQueryResp;
import com.iwhalecloud.retail.member.service.GroupService;
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
public class GroupServiceTest {

    @Autowired
    private GroupService groupService;

    @Test
    public void addGroupTest(){
        SourceFromContext.setSourceFrom("hnyhj_b2b");
        GroupAddReq req = new GroupAddReq();
        req.setGroupName("测试群组0305");
        req.setGroupType("1");
        req.setMeno("群组备注0305");
        req.setTradeName("测试商圈0305");
        req.setCreateStaff("王五");
        ResultVO<Boolean> resultVO = groupService.addGroup(req);
        SourceFromContext.removeSourceFrom();
        log.info("addGroupTest result={}",resultVO.getResultData());
    }

    @Test
    public void updateGroupByIdTest(){
        GroupUpdateReq req = new GroupUpdateReq();
        req.setGroupId("1102849388210343937");
        req.setGroupName("测试群组0305-1");
        req.setGroupType("2");
        req.setMeno("群组备注0305-1");
        req.setUpdateStaff("张三");
        ResultVO<Boolean> resultVO = groupService.updateGroupById(req);
        log.info("updateGroupByIdTest result={}",resultVO.getResultData());
    }

    @Test
    public void deleteGroupByIdTest(){
        GroupDeleteReq req = new GroupDeleteReq();
        req.setGroupId("1102849388210343937");
        req.setUpdateStaff("王五");
        ResultVO<Boolean> resultVO = groupService.deleteGroup(req);
        log.info("deleteGroupByIdTest result={}",resultVO.getResultData());
    }

    @Test
    public void queryGroupByIdTest(){
        GroupQueryDetailReq req = new GroupQueryDetailReq();
        req.setGroupId("1102830056122093570");
        ResultVO<GroupQueryResp> resultVO = groupService.queryGroupById(req);
        log.info("queryGroupByIdTest result={}",resultVO.getResultData());
    }

    @Test
    public void queryGroupForPageTest(){
        GroupQueryForPageReq req = new GroupQueryForPageReq();
        req.setPageNo("1");
        req.setPageSize("10");
        req.setGroupName("测试");
        req.setGroupType("1");
        req.setCreateStaff("张三");
        req.setMeno("备注");
        req.setStatus("0");
        req.setTradeName("商圈");
        req.setCreateEndDate(new Date());
        ResultVO<Page<GroupQueryResp>> resultVO = groupService.queryGroupForPage(req);
        log.info("queryGroupForPageTest result={}",resultVO.getResultData().getRecords());
    }
}
