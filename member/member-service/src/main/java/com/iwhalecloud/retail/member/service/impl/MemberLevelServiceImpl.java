package com.iwhalecloud.retail.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.member.dto.MemberLevelDTO;
import com.iwhalecloud.retail.member.dto.request.MemberLevelAddReq;
import com.iwhalecloud.retail.member.dto.request.MemberLevelDeleteReq;
import com.iwhalecloud.retail.member.dto.request.MemberLevelQueryReq;
import com.iwhalecloud.retail.member.dto.request.MemberLevelUpdateReq;
import com.iwhalecloud.retail.member.dto.response.MemberLevelQueryResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.member.manager.MemberLevelManager;
import com.iwhalecloud.retail.member.service.MemberLevelService;
import org.springframework.stereotype.Component;

@Slf4j
@Service
@Component("memberLevelService")
public class MemberLevelServiceImpl implements MemberLevelService {

    @Autowired
    private MemberLevelManager memberLevelManager;


    @Override
    public ResultVO<Boolean> addMemberLevel(MemberLevelAddReq req) {
        log.info("MemberLevelServiceImpl addMemberLevel req={} ", JSON.toJSONString(req));
        MemberLevelDTO memberLevelDTO = new MemberLevelDTO();
        BeanUtils.copyProperties(req, memberLevelDTO);
        try {
            int rspNum = memberLevelManager.addMemberLevel(memberLevelDTO);
            if(rspNum > 0){
                return ResultVO.success(true);
            }
        }catch (Exception e){
            log.error("MemberLevelServiceImpl addMemberLevel error={}",e.getMessage());
            return ResultVO.error();
        }
        return ResultVO.success(false);
    }

    @Override
    public ResultVO<Boolean> updateMemberLevel(MemberLevelUpdateReq req) {
        log.info("MemberLevelServiceImpl updateMemberLevel req={} ", JSON.toJSONString(req));
        MemberLevelDTO memberLevelDTO = new MemberLevelDTO();
        BeanUtils.copyProperties(req, memberLevelDTO);
        try {
            int rspNum = memberLevelManager.updateMemberLevel(memberLevelDTO);
            if(rspNum > 0){
                return ResultVO.success(true);
            }
        }catch (Exception e){
            log.error("MemberLevelServiceImpl updateMemberLevel error={}",e.getMessage());
            return ResultVO.error();
        }
        return ResultVO.success(false);
    }

    @Override
    public ResultVO<Boolean> deleteMemberLevel(MemberLevelDeleteReq req) {
        log.info("MemberLevelServiceImpl deleteMemberLevel req={} ", JSON.toJSONString(req));
        MemberLevelDTO memberLevelDTO = new MemberLevelDTO();
        BeanUtils.copyProperties(req, memberLevelDTO);
        try {
            int rspNum = memberLevelManager.deleteMemberLevel(memberLevelDTO);
            if(rspNum > 0){
                return ResultVO.success(true);
            }
        }catch (Exception e){
            log.error("MemberLevelServiceImpl deleteMemberLevel error={}",e.getMessage());
            return ResultVO.error();
        }
        return ResultVO.success(false);
    }

    @Override
    public ResultVO<Page<MemberLevelQueryResp>> queryGroupLevelForPage(MemberLevelQueryReq req) {
        log.info("MemberLevelServiceImpl queryGroupLevelForPage req={} ", JSON.toJSONString(req));
        Page<MemberLevelQueryResp> page = memberLevelManager.queryGroupLevelForPage(req);
        log.info("MemberLevelServiceImpl queryGroupLevelForPage resp={} ", JSON.toJSONString(page.getRecords()));
        return ResultVO.success(page);
    }
}