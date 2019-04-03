package com.iwhalecloud.retail.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.member.dto.GroupDTO;
import com.iwhalecloud.retail.member.dto.MemberGroupDTO;
import com.iwhalecloud.retail.member.dto.request.*;
import com.iwhalecloud.retail.member.dto.response.GroupQueryResp;
import com.iwhalecloud.retail.member.dto.response.MemberGroupQueryResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.member.manager.MemberGroupManager;
import com.iwhalecloud.retail.member.service.MemberGroupService;
import org.springframework.stereotype.Component;

@Slf4j
@Service
@Component("memberGroupService")
public class MemberGroupServiceImpl implements MemberGroupService {

    @Autowired
    private MemberGroupManager memberGroupManager;


    @Override
    public ResultVO<Boolean> addMemberGroup(MemberGroupAddReq req) {
        log.info("MemberGroupServiceImpl addMemberGroup req={} ", JSON.toJSONString(req));
        MemberGroupDTO memberGroupDTO = new MemberGroupDTO();
        BeanUtils.copyProperties(req, memberGroupDTO);
        try {
            int rspNum = memberGroupManager.addMemberGroup(memberGroupDTO);
            if(rspNum > 0){
                return ResultVO.success(true);
            }
        }catch (Exception e){
            log.error("MemberGroupServiceImpl addMemberGroup error={}",e.getMessage());
            return ResultVO.error();
        }
        return ResultVO.success(false);
    }

    @Override
    public ResultVO<Boolean> updateMemberGroupById(MemberGroupUpdateReq req) {
        log.info("MemberGroupServiceImpl updateMemberGroupById req={} ", JSON.toJSONString(req));
        MemberGroupDTO memberGroupDTO = new MemberGroupDTO();
        BeanUtils.copyProperties(req, memberGroupDTO);
        try {
            int rspNum = memberGroupManager.updateMemberGroupById(memberGroupDTO);
            if(rspNum > 0){
                return ResultVO.success(true);
            }
        }catch (Exception e){
            log.error("MemberGroupServiceImpl updateMemberGroupById error={}",e.getMessage());
            return ResultVO.error();
        }
        return ResultVO.success(false);
    }

    @Override
    public ResultVO<Boolean> deleteMemberGroup(MemberGroupDeleteReq req) {
        log.info("v deleteGroup req={} ", JSON.toJSONString(req));
        try {
            MemberGroupDTO memberGroupDTO = new MemberGroupDTO();
            BeanUtils.copyProperties(req, memberGroupDTO);
            int rspNum = memberGroupManager.deleteMemberGroup(memberGroupDTO);
            if(rspNum > 0){
                return ResultVO.success(true);
            }
        }catch (Exception e){
            log.error("MemberGroupServiceImpl deleteGroup error={}",e.getMessage());
            return ResultVO.error();
        }
        return ResultVO.success(false);
    }

    @Override
    public ResultVO<Page<GroupQueryResp>> queryGroupByMemberForPage(MemberGroupQueryGroupReq req) {
        log.info("MemberGroupServiceImpl queryGroupByMemberForPage req={} ", JSON.toJSONString(req));
        Page<GroupQueryResp> page = memberGroupManager.queryGroupByMemberForPage(req);
        log.info("MemberGroupServiceImpl queryGroupByMemberForPage resp={} ", JSON.toJSONString(page.getRecords()));
        return ResultVO.success(page);
    }

    @Override
    public ResultVO<Page<MemberGroupQueryResp>> queryMemberByGroupForPage(MemberGroupQueryMemberReq req) {
        log.info("MemberGroupServiceImpl queryMemberByGroupForPage req={} ", JSON.toJSONString(req));
        Page<MemberGroupQueryResp> page = memberGroupManager.queryMemberByGroupForPage(req);
        log.info("MemberGroupServiceImpl queryMemberByGroupForPage resp={} ", JSON.toJSONString(page.getRecords()));
        return ResultVO.success(page);
    }
}