package com.iwhalecloud.retail.member;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.dto.SourceFromContext;
import com.iwhalecloud.retail.member.dto.request.*;
import com.iwhalecloud.retail.member.dto.response.GroupMerchantQueryResp;
import com.iwhalecloud.retail.member.dto.response.GroupQueryResp;
import com.iwhalecloud.retail.member.service.GroupMerchantService;
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
public class GroupMerchantServiceTest {

    @Autowired
    private GroupMerchantService groupMerchantService;

    @Test
    public void addGroupMerchantTest(){
        SourceFromContext.setSourceFrom("hnyhj_b2b");
        GroupMerchantAddReq req = new GroupMerchantAddReq();
        req.setGroupId("1102842995499716609");
        req.setMerchId("1234567890");
        req.setUpdateStaff("张三");
        ResultVO<Boolean> resultVO = groupMerchantService.addGroupMerchant(req);
        SourceFromContext.removeSourceFrom();
        log.info("addGroupMerchantTest result={}",resultVO.getResultData());
    }

    @Test
    public void updateGroupMerchantTest(){
        GroupMerchantUpdateReq req = new GroupMerchantUpdateReq();
        req.setGroupId("1102849388210343937");
        req.setMerchId("1234567890");
        req.setUpdateStaff("李四");
        req.setStatus("0");
        ResultVO<Boolean> resultVO = groupMerchantService.updateGroupMerchant(req);
        log.info("updateGroupMerchantTest result={}",resultVO.getResultData());
    }

    @Test
    public void deleteGroupMerchantTest(){
        GroupMerchantDeleteReq req = new GroupMerchantDeleteReq();
        req.setGroupId("1102842995499716609");
        req.setMerchId("1234567890");
        req.setUpdateStaff("王五");
        ResultVO<Boolean> resultVO = groupMerchantService.deleteGroupMerchant(req);
        log.info("deleteGroupMerchantTest result={}",resultVO.getResultData());
    }

    @Test
    public void queryGroupMerchantForPageTest(){
        GroupMerchantQueryReq req = new GroupMerchantQueryReq();
        req.setPageNo("1");
        req.setPageSize("10");
        req.setGroupId("");
        req.setMerchId("");
        req.setStatus("1");
        ResultVO<Page<GroupMerchantQueryResp>> resultVO = groupMerchantService.queryGroupMerchantForPage(req);
        log.info("queryGroupMerchantForPageTest result={}",resultVO.getResultData().getRecords());
    }
}
