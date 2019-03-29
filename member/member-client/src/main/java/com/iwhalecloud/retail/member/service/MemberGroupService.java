package com.iwhalecloud.retail.member.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.member.dto.request.*;
import com.iwhalecloud.retail.member.dto.response.GroupQueryResp;
import com.iwhalecloud.retail.member.dto.response.MemberGroupQueryResp;

public interface MemberGroupService{
    /**
     * 新增群组会员
     * @param req
     * @return
     */
    ResultVO<Boolean> addMemberGroup(MemberGroupAddReq req);

    /**
     * 更新群组会员信息
     * @param req
     * @return
     */
    ResultVO<Boolean> updateMemberGroupById(MemberGroupUpdateReq req);

    /**
     * 删除群组会员
     * @param req
     * @return
     */
    ResultVO<Boolean> deleteMemberGroup(MemberGroupDeleteReq req);

    /**
     * 根据会员分页查询群组列表
     * @param req
     * @return
     */
    ResultVO<Page<GroupQueryResp>> queryGroupByMemberForPage(MemberGroupQueryGroupReq req);

    /**
     * 根据群组分页查询会员列表
     * @param req
     * @return
     */
    ResultVO<Page<MemberGroupQueryResp>> queryMemberByGroupForPage(MemberGroupQueryMemberReq req);

}