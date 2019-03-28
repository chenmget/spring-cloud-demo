package com.iwhalecloud.retail.member.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.member.dto.request.*;
import com.iwhalecloud.retail.member.dto.response.GroupQueryResp;

public interface GroupService{

    /**
     * 新增群组
     * @param req
     * @return
     */
    ResultVO<Boolean> addGroup(GroupAddReq req);

    /**
     * 更新群组信息
     * @param req
     * @return
     */
    ResultVO<Boolean> updateGroupById(GroupUpdateReq req);

    /**
     * 删除群组
     * @param req
     * @return
     */
    ResultVO<Boolean> deleteGroup(GroupDeleteReq req);

    /**
     * 根据Id查询群组信息
     * @param req
     * @return
     */
    ResultVO<GroupQueryResp> queryGroupById(GroupQueryDetailReq req);

    /**
     * 分页查询群组列表
     * @param req
     * @return
     */
    ResultVO<Page<GroupQueryResp>> queryGroupForPage(GroupQueryForPageReq req);

}