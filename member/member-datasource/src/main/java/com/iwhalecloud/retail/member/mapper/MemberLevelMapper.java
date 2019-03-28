package com.iwhalecloud.retail.member.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.member.dto.request.MemberLevelQueryReq;
import com.iwhalecloud.retail.member.dto.response.MemberLevelQueryResp;
import com.iwhalecloud.retail.member.entity.MemberLevel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: MemberLevelMapper
 * @author autoCreate
 */
@Mapper
public interface MemberLevelMapper extends BaseMapper<MemberLevel>{

    Page<MemberLevelQueryResp> queryGroupLevelForPage(Page<MemberLevelQueryResp> page, @Param("req") MemberLevelQueryReq req);
}