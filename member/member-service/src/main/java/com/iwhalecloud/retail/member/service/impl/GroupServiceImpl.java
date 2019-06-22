package com.iwhalecloud.retail.member.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.member.dto.GroupDTO;
import com.iwhalecloud.retail.member.dto.request.*;
import com.iwhalecloud.retail.member.dto.response.GroupQueryResp;
import com.iwhalecloud.retail.member.manager.GroupManager;
import com.iwhalecloud.retail.member.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Service
@Component("groupService")
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupManager groupManager;


    @Override
    public ResultVO<Boolean> addGroup(GroupAddReq req) {
        log.info("GroupServiceImpl addGroup req={} ", JSON.toJSONString(req));
        GroupDTO group = new GroupDTO();
        BeanUtils.copyProperties(req, group);
        try {
            int rspNum = groupManager.addGroup(group);
            if(rspNum > 0){
                return ResultVO.success(true);
            }
        }catch (Exception e){
            log.error("GroupServiceImpl addGroup error={}",e.getMessage());
            return ResultVO.error();
        }
        return ResultVO.success(false);
    }

    @Override
    public ResultVO<Boolean> updateGroupById(GroupUpdateReq req) {
        log.info("GroupServiceImpl updateGroup req={} ", JSON.toJSONString(req));
        GroupDTO group = new GroupDTO();
        BeanUtils.copyProperties(req, group);
        try {
            int rspNum = groupManager.updateGroup(group);
            if(rspNum > 0){
                return ResultVO.success(true);
            }
        }catch (Exception e){
            log.error("GroupServiceImpl updateGroup error={}",e.getMessage());
            return ResultVO.error();
        }
        return ResultVO.success(false);
    }

    @Override
    public ResultVO<Boolean> deleteGroup(GroupDeleteReq req) {
        log.info("GroupServiceImpl deleteGroup req={} ", JSON.toJSONString(req));
        try {
            GroupDTO group = new GroupDTO();
            BeanUtils.copyProperties(req, group);
            int rspNum = groupManager.deleteGroup(group);
            if(rspNum > 0){
                return ResultVO.success(true);
            }
        }catch (Exception e){
            log.error("GroupServiceImpl deleteGroup error={}",e.getMessage());
            return ResultVO.error();
        }
        return ResultVO.success(false);
    }

    @Override
    public ResultVO<GroupQueryResp> queryGroupById(GroupQueryDetailReq req) {
        log.info("GroupServiceImpl queryGroupById req={} ", JSON.toJSONString(req));
        GroupQueryResp groupQueryResp = new GroupQueryResp();
        GroupDTO groupDTO = groupManager.queryGroupById(req.getGroupId());
        BeanUtils.copyProperties(groupDTO, groupQueryResp);
        log.info("GroupServiceImpl queryGroupById resp={} ", JSON.toJSONString(groupQueryResp));
        return ResultVO.success(groupQueryResp);
    }

    @Override
    public ResultVO<Page<GroupQueryResp>> queryGroupForPage(GroupQueryForPageReq req) {
        log.info("GroupServiceImpl queryGroupForPage req={} ", JSON.toJSONString(req));
        Page<GroupQueryResp> page = groupManager.queryGroupForPage(req);
        log.info("GroupServiceImpl queryGroupForPage resp={} ", JSON.toJSONString(page.getRecords()));
        return ResultVO.success(page);
    }
}