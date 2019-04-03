package com.iwhalecloud.retail.member.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.member.dto.request.MemberGroupQueryGroupReq;
import com.iwhalecloud.retail.member.dto.request.MemberGroupQueryMemberReq;
import com.iwhalecloud.retail.member.dto.response.GroupQueryResp;
import com.iwhalecloud.retail.member.dto.response.MemberGroupQueryResp;
import com.iwhalecloud.retail.member.entity.MemberGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: MemberGroupMapper
 * @author autoCreate
 */
@Mapper
public interface MemberGroupMapper extends BaseMapper<MemberGroup>{

    Page<GroupQueryResp> queryGroupByMemberForPage(Page<GroupQueryResp> page, @Param("req") MemberGroupQueryGroupReq req);

    Page<MemberGroupQueryResp> queryMemberByGroupForPage(Page<MemberGroupQueryResp> page, @Param("req") MemberGroupQueryMemberReq req);
}