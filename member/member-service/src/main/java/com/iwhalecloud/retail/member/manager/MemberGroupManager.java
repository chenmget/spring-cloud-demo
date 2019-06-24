package com.iwhalecloud.retail.member.manager;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.member.common.MemberConst;
import com.iwhalecloud.retail.member.dto.MemberGroupDTO;
import com.iwhalecloud.retail.member.dto.request.MemberGroupQueryGroupReq;
import com.iwhalecloud.retail.member.dto.request.MemberGroupQueryMemberReq;
import com.iwhalecloud.retail.member.dto.response.GroupQueryResp;
import com.iwhalecloud.retail.member.dto.response.MemberGroupQueryResp;
import com.iwhalecloud.retail.member.entity.MemberGroup;
import com.iwhalecloud.retail.member.mapper.MemberGroupMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;


@Component
public class MemberGroupManager{
    @Resource
    private MemberGroupMapper memberGroupMapper;

    public int addMemberGroup(MemberGroupDTO memberGroupDTO) {
        MemberGroup memberGroup = new MemberGroup();
        BeanUtils.copyProperties(memberGroupDTO, memberGroup);
        memberGroup.setStatus(MemberConst.CommonState.VALID.getCode());
        memberGroup.setCreateDate(new Date());
        memberGroup.setUpdateStaff(memberGroupDTO.getCreateStaff());
        memberGroup.setUpdateDate(new Date());
        return memberGroupMapper.insert(memberGroup);
    }

    public int updateMemberGroupById(MemberGroupDTO memberGroupDTO) {
        UpdateWrapper<MemberGroup> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(MemberGroup.FieldNames.groupId.getTableFieldName(),memberGroupDTO.getGroupId());
        updateWrapper.eq(MemberGroup.FieldNames.memId.getTableFieldName(),memberGroupDTO.getMemId());
        MemberGroup memberGroup = new MemberGroup();
        BeanUtils.copyProperties(memberGroupDTO, memberGroup);
        memberGroup.setUpdateDate(new Date());
        memberGroup.setMemId(null);
        return memberGroupMapper.update(memberGroup, updateWrapper);
    }

    public int deleteMemberGroup(MemberGroupDTO memberGroupDTO) {
        UpdateWrapper<MemberGroup> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(MemberGroup.FieldNames.groupId.getTableFieldName(),memberGroupDTO.getGroupId());
        updateWrapper.eq(MemberGroup.FieldNames.memId.getTableFieldName(),memberGroupDTO.getMemId());
        MemberGroup memberGroup = new MemberGroup();
        memberGroup.setStatus(MemberConst.CommonState.INVALID.getCode());
        memberGroup.setUpdateDate(new Date());
        memberGroup.setUpdateStaff(memberGroupDTO.getUpdateStaff());
        memberGroup.setMemId(null);
        return memberGroupMapper.update(memberGroup, updateWrapper);
    }

    public Page<GroupQueryResp> queryGroupByMemberForPage(MemberGroupQueryGroupReq req) {
        Page<GroupQueryResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        return memberGroupMapper.queryGroupByMemberForPage(page, req);
    }

    public Page<MemberGroupQueryResp> queryMemberByGroupForPage(MemberGroupQueryMemberReq req) {
        Page<MemberGroupQueryResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        return memberGroupMapper.queryMemberByGroupForPage(page, req);
    }
}
