package com.iwhalecloud.retail.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.member.dto.MemberGroupDTO;
import com.iwhalecloud.retail.member.dto.MemberMerchantDTO;
import com.iwhalecloud.retail.member.dto.request.MemberMerchantAddReq;
import com.iwhalecloud.retail.member.dto.request.MemberMerchantDeleteReq;
import com.iwhalecloud.retail.member.dto.request.MemberMerchantQueryForPageReq;
import com.iwhalecloud.retail.member.dto.request.MemberMerchantUpdateReq;
import com.iwhalecloud.retail.member.dto.response.GroupQueryResp;
import com.iwhalecloud.retail.member.dto.response.MemberMerchantQueryForPageResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.member.manager.MemberMerchantManager;
import com.iwhalecloud.retail.member.service.MemberMerchantService;
import org.springframework.stereotype.Component;

@Slf4j
@Service
@Component("memberMerchantService")
public class MemberMerchantServiceImpl implements MemberMerchantService {

    @Autowired
    private MemberMerchantManager memberMerchantManager;


    @Override
    public ResultVO<Boolean> addMemberMerchant(MemberMerchantAddReq req) {
        log.info("MemberMerchantServiceImpl addMemberMerchant req={}", JSON.toJSONString(req));
        MemberMerchantDTO memberMerchantDTO = new MemberMerchantDTO();
        BeanUtils.copyProperties(req, memberMerchantDTO);
        try {
            int rspNum = memberMerchantManager.addMemberMerchant(memberMerchantDTO);
            if (rspNum > 0) {
                return ResultVO.success(true);
            }
        } catch (Exception e) {
            log.error("MemberMerchantServiceImpl addMemberMerchant error={}", e.getMessage());
            return ResultVO.error();
        }
        return ResultVO.success(false);
    }

    @Override
    public ResultVO<Boolean> updateMemberMerchant(MemberMerchantUpdateReq req) {
        log.info("MemberMerchantServiceImpl updateMemberMerchant req={} ", JSON.toJSONString(req));
        MemberMerchantDTO memberMerchantDTO = new MemberMerchantDTO();
        BeanUtils.copyProperties(req, memberMerchantDTO);
        try {
            int rspNum = memberMerchantManager.updateMemberMerchant(memberMerchantDTO);
            if (rspNum > 0) {
                return ResultVO.success(true);
            }
        } catch (Exception e) {
            log.error("MemberMerchantServiceImpl updateMemberMerchant error={}", e.getMessage());
            return ResultVO.error();
        }
        return ResultVO.success(false);
    }

    @Override
    public ResultVO<Boolean> deleteMemberMerchant(MemberMerchantDeleteReq req) {
        log.info("MemberMerchantServiceImpl deleteMemberMerchant req={} ", JSON.toJSONString(req));
        MemberMerchantDTO memberMerchantDTO = new MemberMerchantDTO();
        BeanUtils.copyProperties(req, memberMerchantDTO);
        try {
            int rspNum = memberMerchantManager.deleteMemberMerchant(memberMerchantDTO);
            if (rspNum > 0) {
                return ResultVO.success(true);
            }
        } catch (Exception e) {
            log.error("MemberMerchantServiceImpl deleteMemberMerchant error={}", e.getMessage());
            return ResultVO.error();
        }
        return ResultVO.success(false);
    }

    @Override
    public ResultVO<Page<MemberMerchantQueryForPageResp>> queryMemberMerchantForPage(MemberMerchantQueryForPageReq req) {
        log.info("MemberGroupServiceImpl queryMemberMerchantForPage req={} ", JSON.toJSONString(req));
        Page<MemberMerchantQueryForPageResp> page = memberMerchantManager.queryMemberMerchantForPage(req);
        log.info("MemberGroupServiceImpl queryMemberMerchantForPage resp={} ", JSON.toJSONString(page.getRecords()));
        return ResultVO.success(page);
    }
}