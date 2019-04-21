package com.iwhalecloud.retail.member.manager;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.member.common.MemberConst;
import com.iwhalecloud.retail.member.dto.GroupMerchantDTO;
import com.iwhalecloud.retail.member.dto.request.GroupMerchantQueryReq;
import com.iwhalecloud.retail.member.dto.response.GroupMerchantQueryResp;
import com.iwhalecloud.retail.member.entity.GroupMerchant;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.member.mapper.GroupMerchantMapper;

import java.util.Date;


@Component
public class GroupMerchantManager{
    @Resource
    private GroupMerchantMapper groupMerchantMapper;


    public int addGroupMerchant(GroupMerchantDTO groupMerchantDTO) {
        GroupMerchant groupMerchant = new GroupMerchant();
        BeanUtils.copyProperties(groupMerchantDTO, groupMerchant);
        groupMerchant.setStatus(MemberConst.CommonState.VALID.getCode());
        groupMerchant.setUpdateDate(new Date());
        return groupMerchantMapper.insert(groupMerchant);
    }

    public int updateGroupMerchant(GroupMerchantDTO groupMerchantDTO) {
        UpdateWrapper<GroupMerchant> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(GroupMerchant.FieldNames.groupId.getTableFieldName(),groupMerchantDTO.getGroupId());
        updateWrapper.eq(GroupMerchant.FieldNames.merchId.getTableFieldName(),groupMerchantDTO.getMerchId());
        GroupMerchant groupMerchant = new GroupMerchant();
        BeanUtils.copyProperties(groupMerchantDTO, groupMerchant);
        groupMerchant.setUpdateDate(new Date());
        groupMerchant.setMerchId(null);
        return groupMerchantMapper.update(groupMerchant, updateWrapper);
    }

    public int deleteGroupMerchant(GroupMerchantDTO groupMerchantDTO) {
        UpdateWrapper<GroupMerchant> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(GroupMerchant.FieldNames.groupId.getTableFieldName(),groupMerchantDTO.getGroupId());
        updateWrapper.eq(GroupMerchant.FieldNames.merchId.getTableFieldName(),groupMerchantDTO.getMerchId());
        GroupMerchant groupMerchant = new GroupMerchant();
        groupMerchant.setStatus(MemberConst.CommonState.INVALID.getCode());
        groupMerchant.setUpdateDate(new Date());
        groupMerchant.setUpdateStaff(groupMerchantDTO.getUpdateStaff());
        return groupMerchantMapper.update(groupMerchant, updateWrapper);
    }

    public Page<GroupMerchantQueryResp> queryGroupMerchantForPage(GroupMerchantQueryReq req) {
        Page<GroupMerchantQueryResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        return groupMerchantMapper.queryGroupMerchantForPage(page, req);
    }
}
