package com.iwhalecloud.retail.member.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.member.dto.request.MemberMerchantQueryForPageReq;
import com.iwhalecloud.retail.member.dto.response.MemberMerchantQueryForPageResp;
import com.iwhalecloud.retail.member.entity.MemberMerchant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: MemberMerchantMapper
 * @author autoCreate
 */
@Mapper
public interface MemberMerchantMapper extends BaseMapper<MemberMerchant>{

    Page<MemberMerchantQueryForPageResp> queryMemberMerchantForPage(Page<MemberMerchantQueryForPageResp> page, @Param("req") MemberMerchantQueryForPageReq req);
}