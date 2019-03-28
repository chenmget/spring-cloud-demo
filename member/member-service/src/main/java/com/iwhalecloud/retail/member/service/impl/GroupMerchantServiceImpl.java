package com.iwhalecloud.retail.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.member.dto.GroupMerchantDTO;
import com.iwhalecloud.retail.member.dto.request.GroupMerchantAddReq;
import com.iwhalecloud.retail.member.dto.request.GroupMerchantDeleteReq;
import com.iwhalecloud.retail.member.dto.request.GroupMerchantQueryReq;
import com.iwhalecloud.retail.member.dto.request.GroupMerchantUpdateReq;
import com.iwhalecloud.retail.member.dto.response.GroupMerchantQueryResp;
import com.iwhalecloud.retail.member.dto.response.GroupQueryResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.member.manager.GroupMerchantManager;
import com.iwhalecloud.retail.member.service.GroupMerchantService;
import org.springframework.stereotype.Component;

@Slf4j
@Service
@Component("groupMerchantService")
public class GroupMerchantServiceImpl implements GroupMerchantService {

    @Autowired
    private GroupMerchantManager groupMerchantManager;


    @Override
    public ResultVO<Boolean> addGroupMerchant(GroupMerchantAddReq req) {
        log.info("GroupMerchantServiceImpl addGroupMerchant req={} ", JSON.toJSONString(req));
        GroupMerchantDTO groupMerchantDTO = new GroupMerchantDTO();
        BeanUtils.copyProperties(req, groupMerchantDTO);
        try {
            int rspNum = groupMerchantManager.addGroupMerchant(groupMerchantDTO);
            if(rspNum > 0){
                return ResultVO.success(true);
            }
        }catch (Exception e){
            log.error("GroupMerchantServiceImpl addGroupMerchant error={}",e.getMessage());
            return ResultVO.error();
        }
        return ResultVO.success(false);
    }

    @Override
    public ResultVO<Boolean> updateGroupMerchant(GroupMerchantUpdateReq req) {
        log.info("GroupMerchantServiceImpl updateGroupMerchant req={} ", JSON.toJSONString(req));
        GroupMerchantDTO groupMerchantDTO = new GroupMerchantDTO();
        BeanUtils.copyProperties(req, groupMerchantDTO);
        try {
            int rspNum = groupMerchantManager.updateGroupMerchant(groupMerchantDTO);
            if(rspNum > 0){
                return ResultVO.success(true);
            }
        }catch (Exception e){
            log.error("GroupMerchantServiceImpl updateGroupMerchant error={}",e.getMessage());
            return ResultVO.error();
        }
        return ResultVO.success(false);
    }

    @Override
    public ResultVO<Boolean> deleteGroupMerchant(GroupMerchantDeleteReq req) {
        log.info("GroupMerchantServiceImpl deleteGroupMerchant req={} ", JSON.toJSONString(req));
        GroupMerchantDTO groupMerchantDTO = new GroupMerchantDTO();
        BeanUtils.copyProperties(req, groupMerchantDTO);
        try {
            int rspNum = groupMerchantManager.deleteGroupMerchant(groupMerchantDTO);
            if(rspNum > 0){
                return ResultVO.success(true);
            }
        }catch (Exception e){
            log.error("GroupMerchantServiceImpl deleteGroupMerchant error={}",e.getMessage());
            return ResultVO.error();
        }
        return ResultVO.success(false);
    }

    @Override
    public ResultVO<Page<GroupMerchantQueryResp>> queryGroupMerchantForPage(GroupMerchantQueryReq req) {
        log.info("GroupMerchantServiceImpl queryGroupMerchantForPage req={} ", JSON.toJSONString(req));
        Page<GroupMerchantQueryResp> page = groupMerchantManager.queryGroupMerchantForPage(req);
        log.info("GroupMerchantServiceImpl queryGroupMerchantForPage resp={} ", JSON.toJSONString(page.getRecords()));
        return ResultVO.success(page);
    }
}