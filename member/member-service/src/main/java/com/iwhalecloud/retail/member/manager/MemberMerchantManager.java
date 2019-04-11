package com.iwhalecloud.retail.member.manager;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.member.common.MemberConst;
import com.iwhalecloud.retail.member.dto.MemberMerchantDTO;
import com.iwhalecloud.retail.member.dto.request.MemberMerchantQueryForPageReq;
import com.iwhalecloud.retail.member.dto.response.MemberMerchantQueryForPageResp;
import com.iwhalecloud.retail.member.entity.MemberMerchant;
import com.iwhalecloud.retail.member.mapper.MemberMerchantMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;


@Component
public class MemberMerchantManager{
    @Resource
    private MemberMerchantMapper memberMerchantMapper;


    public int addMemberMerchant(MemberMerchantDTO memberMerchantDTO) {
        MemberMerchant memberMerchant = new MemberMerchant();
        BeanUtils.copyProperties(memberMerchantDTO, memberMerchant);
        memberMerchant.setStatus(MemberConst.CommonState.VALID.getCode());
        memberMerchant.setUpdateDate(new Date());
        return memberMerchantMapper.insert(memberMerchant);
    }

    public int updateMemberMerchant(MemberMerchantDTO memberMerchantDTO) {
        UpdateWrapper<MemberMerchant> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(MemberMerchant.FieldNames.merchId.getTableFieldName(),memberMerchantDTO.getMerchId());
        updateWrapper.eq(MemberMerchant.FieldNames.memId.getTableFieldName(),memberMerchantDTO.getMemId());
        MemberMerchant memberMerchant = new MemberMerchant();
        BeanUtils.copyProperties(memberMerchantDTO, memberMerchant);
        memberMerchant.setUpdateDate(new Date());
        memberMerchant.setMemId(null);
        return memberMerchantMapper.update(memberMerchant, updateWrapper);
    }

    public int deleteMemberMerchant(MemberMerchantDTO memberMerchantDTO) {
        UpdateWrapper<MemberMerchant> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(MemberMerchant.FieldNames.merchId.getTableFieldName(),memberMerchantDTO.getMerchId());
        updateWrapper.eq(MemberMerchant.FieldNames.memId.getTableFieldName(),memberMerchantDTO.getMemId());
        MemberMerchant memberMerchant = new MemberMerchant();
        BeanUtils.copyProperties(memberMerchantDTO, memberMerchant);
        memberMerchant.setStatus(MemberConst.CommonState.INVALID.getCode());
        memberMerchant.setUpdateDate(new Date());
        memberMerchant.setMemId(null);
        return memberMerchantMapper.update(memberMerchant, updateWrapper);
    }

    public Page<MemberMerchantQueryForPageResp> queryMemberMerchantForPage(MemberMerchantQueryForPageReq req) {
        Page<MemberMerchantQueryForPageResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        return memberMerchantMapper.queryMemberMerchantForPage(page, req);
    }
}
