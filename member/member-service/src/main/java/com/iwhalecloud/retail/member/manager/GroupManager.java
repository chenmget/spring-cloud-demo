package com.iwhalecloud.retail.member.manager;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.member.common.MemberConst;
import com.iwhalecloud.retail.member.dto.GroupDTO;
import com.iwhalecloud.retail.member.dto.request.GroupQueryForPageReq;
import com.iwhalecloud.retail.member.dto.response.GroupQueryResp;
import com.iwhalecloud.retail.member.entity.Group;
import com.iwhalecloud.retail.member.mapper.GroupMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class GroupManager{
    @Resource
    private GroupMapper groupMapper;

    public int addGroup(GroupDTO group) {
        Group t = new Group();
        BeanUtils.copyProperties(group, t);
        t.setStatus(MemberConst.CommonState.VALID.getCode());
        t.setCreateDate(new Date());
        t.setUpdateStaff(group.getCreateStaff());
        t.setUpdateDate(new Date());
        return groupMapper.insert(t);
    }

    public int updateGroup(GroupDTO group) {
        UpdateWrapper<Group> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(Group.FieldNames.groupId.getTableFieldName(),group.getGroupId());
        Group t = new Group();
        BeanUtils.copyProperties(group, t);
        t.setUpdateDate(new Date());
        return groupMapper.update(t, updateWrapper);
    }

    public int deleteGroup(GroupDTO group) {
        UpdateWrapper<Group> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(Group.FieldNames.groupId.getTableFieldName(),group.getGroupId());
        Group t = new Group();
        t.setStatus(MemberConst.CommonState.INVALID.getCode());
        t.setUpdateStaff(group.getUpdateStaff());
        t.setUpdateDate(new Date());
        return groupMapper.update(t, updateWrapper);
    }

    public GroupDTO queryGroupById(String groupId) {
        Group group = groupMapper.selectById(groupId);
        GroupDTO groupDTO = new GroupDTO();
        BeanUtils.copyProperties(group, groupDTO);
        return groupDTO;
    }

    public Page<GroupQueryResp> queryGroupForPage(GroupQueryForPageReq req) {
        Page<GroupQueryResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        return groupMapper.queryGroupForPage(page, req);
    }
}
