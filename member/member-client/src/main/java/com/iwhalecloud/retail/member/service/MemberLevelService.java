package com.iwhalecloud.retail.member.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.member.dto.request.MemberLevelAddReq;
import com.iwhalecloud.retail.member.dto.request.MemberLevelDeleteReq;
import com.iwhalecloud.retail.member.dto.request.MemberLevelQueryReq;
import com.iwhalecloud.retail.member.dto.request.MemberLevelUpdateReq;
import com.iwhalecloud.retail.member.dto.response.MemberLevelQueryResp;

public interface MemberLevelService{
    /**
     * 新增会员等级
     * @param req
     * @return
     */
    ResultVO<Boolean> addMemberLevel(MemberLevelAddReq req);

    /**
     * 更新会员等级信息
     * @param req
     * @return
     */
    ResultVO<Boolean> updateMemberLevel(MemberLevelUpdateReq req);

    /**
     * 删除会员等级信息
     * @param req
     * @return
     */
    ResultVO<Boolean> deleteMemberLevel(MemberLevelDeleteReq req);

    /**
     * 分页查询会员等级
     * @param req
     * @return
     */
    ResultVO<Page<MemberLevelQueryResp>> queryGroupLevelForPage(MemberLevelQueryReq req);
}