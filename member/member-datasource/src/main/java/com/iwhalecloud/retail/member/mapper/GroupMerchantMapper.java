package com.iwhalecloud.retail.member.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.member.dto.request.GroupMerchantQueryReq;
import com.iwhalecloud.retail.member.dto.response.GroupMerchantQueryResp;
import com.iwhalecloud.retail.member.entity.GroupMerchant  ;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: GroupMerchant  Mapper
 * @author autoCreate
 */
@Mapper
public interface GroupMerchantMapper extends BaseMapper<GroupMerchant>{

    Page<GroupMerchantQueryResp> queryGroupMerchantForPage(Page<GroupMerchantQueryResp> page, @Param("req") GroupMerchantQueryReq req);
}