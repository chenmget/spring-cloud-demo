package com.iwhalecloud.retail.member.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.member.dto.MemberDTO;
import com.iwhalecloud.retail.member.dto.request.MemberPageReq;
import com.iwhalecloud.retail.member.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: smsSendMapper
 * @author autoCreate
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member>{

	Page<MemberDTO> pageMember(Page<MemberDTO> page, @Param("pageReq")MemberPageReq req);
	
	int getParMerchantAccountByMerchantId(String merchantId);
}