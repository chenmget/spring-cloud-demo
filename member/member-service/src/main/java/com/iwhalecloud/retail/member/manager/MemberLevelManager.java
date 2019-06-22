package com.iwhalecloud.retail.member.manager;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.member.common.MemberConst;
import com.iwhalecloud.retail.member.dto.MemberLevelDTO;
import com.iwhalecloud.retail.member.dto.request.MemberLevelQueryReq;
import com.iwhalecloud.retail.member.dto.response.MemberLevelQueryResp;
import com.iwhalecloud.retail.member.entity.MemberLevel;
import com.iwhalecloud.retail.member.mapper.MemberLevelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class MemberLevelManager{
    @Resource
    private MemberLevelMapper memberLevelMapper;


    public int addMemberLevel(MemberLevelDTO memberLevelDTO) {
        MemberLevel memberLevel = new MemberLevel();
        BeanUtils.copyProperties(memberLevelDTO, memberLevel);
        memberLevel.setIsDefault(MemberConst.IsDefaultLevel.NO.getCode());
        memberLevel.setStatus(MemberConst.CommonState.VALID.getCode());
        return memberLevelMapper.insert(memberLevel);
    }

    public int updateMemberLevel(MemberLevelDTO memberLevelDTO) {
        UpdateWrapper<MemberLevel> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(MemberLevel.FieldNames.lvId.getTableFieldName(),memberLevelDTO.getLvId());
        MemberLevel memberLevel = new MemberLevel();
        BeanUtils.copyProperties(memberLevelDTO, memberLevel);
        return memberLevelMapper.update(memberLevel, updateWrapper);
    }

    public int deleteMemberLevel(MemberLevelDTO memberLevelDTO) {
        UpdateWrapper<MemberLevel> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(MemberLevel.FieldNames.lvId.getTableFieldName(),memberLevelDTO.getLvId());
        MemberLevel memberLevel = new MemberLevel();
        memberLevel.setStatus(MemberConst.CommonState.INVALID.getCode());
        return memberLevelMapper.update(memberLevel, updateWrapper);
    }

    public Page<MemberLevelQueryResp> queryGroupLevelForPage(MemberLevelQueryReq req) {
        Page<MemberLevelQueryResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        return memberLevelMapper.queryGroupLevelForPage(page, req);
    }
}
