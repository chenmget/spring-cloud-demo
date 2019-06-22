package com.iwhalecloud.retail.member.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.member.dto.MemberDTO;
import com.iwhalecloud.retail.member.dto.request.*;
import com.iwhalecloud.retail.member.dto.response.MemberIsExistsResp;
import com.iwhalecloud.retail.member.dto.response.MemberLoginResp;
import com.iwhalecloud.retail.member.dto.response.MemberResp;


public interface MemberService{

	/**
	 * 获取会员分页列表
	 * @param req 分页参数
	 * @return
	 */
	Page<MemberDTO> pageMember(MemberPageReq req);

    /**
     * 根据条件获取会员信息
     * @param req
     * @return
     */
    ResultVO<MemberDTO> getMember(MemberGetReq req);

    /**
     * 会员登陆
     * @param req
     * @return MemberLoginResp
     */
    MemberLoginResp login(MemberLoginReq req);

    /**
	 * 根据会员ID获取会员信息
	 * @param memberId 会员ID
	 * @return
	 */
    MemberResp getMember(String memberId);
	
	/**
	 * 通过手机号查询会员是否存在
	 * @param req (传 mobile 字段）
	 * @return  里面有个exists字段，为true存在， false 不存在
	 */
	MemberIsExistsResp isExists(MemberIsExistsReq req);
    
    /**
	 * 会员注册
	 * @param member
	 * @return
	 */
    ResultVO register(MemberAddReq member);

    /**
	 * 判断是否拥有翼支付账号
	 * @param merchantId
	 * @return
	 */
	int checkPayAccount(String merchantId);
}