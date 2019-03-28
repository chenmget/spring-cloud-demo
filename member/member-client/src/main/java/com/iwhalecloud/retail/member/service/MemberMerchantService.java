package com.iwhalecloud.retail.member.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.member.dto.request.MemberMerchantAddReq;
import com.iwhalecloud.retail.member.dto.request.MemberMerchantDeleteReq;
import com.iwhalecloud.retail.member.dto.request.MemberMerchantQueryForPageReq;
import com.iwhalecloud.retail.member.dto.request.MemberMerchantUpdateReq;
import com.iwhalecloud.retail.member.dto.response.MemberMerchantQueryForPageResp;

public interface MemberMerchantService{

    /**
     * 新增商户与会员关系
     * @param req
     * @return
     */
    ResultVO<Boolean> addMemberMerchant(MemberMerchantAddReq req);

    /**
     * 更新商户与会员关系
     * @param req
     * @return
     */
    ResultVO<Boolean> updateMemberMerchant(MemberMerchantUpdateReq req);

    /**
     * 删除商户和会员关系
     * @param req
     * @return
     */
    ResultVO<Boolean> deleteMemberMerchant(MemberMerchantDeleteReq req);

    /**
     * 查询商户会员关系列表
     * @param req
     * @return
     */
    ResultVO<Page<MemberMerchantQueryForPageResp>> queryMemberMerchantForPage(MemberMerchantQueryForPageReq req);

}