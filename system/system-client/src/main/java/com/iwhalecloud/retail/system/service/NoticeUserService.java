package com.iwhalecloud.retail.system.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.NoticeUserDTO;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.request.*;

import java.util.List;

public interface NoticeUserService{

    /**
     * 添加一个 通知用户关联
     * @param req
     * @return
     */
    ResultVO<NoticeUserDTO> saveNoticeUser(NoticeUserSaveReq req);

    /**
     * 添加一组 通知用户关联
     * @param noticeId
     * @param userIdList
     * @return
     */
    ResultVO<NoticeUserDTO> saveNoticeUserWithUserIds(String noticeId, List<String> userIdList);

    /**
     * 定向类通知： 获取可以选择的用户分页列表
     * @param req
     * @return
     */
    ResultVO<Page<UserDTO>> pageSelectableUser(NoticeSelectUserPageReq req);


    /**
     * 获取一个 通知用户关联
     * @param id
     * @return
     */
    ResultVO<NoticeUserDTO> getNoticeUserById(String id);

    /**
     * 编辑 通知用户关联 信息
     * @param req
     * @return
     */
    ResultVO<Integer> updateNoticeUser(NoticeUserUpdateReq req);

    /**
     * 设置已读
     * @param noticeId
     * @param userId
     * @return
     */
    ResultVO<Integer> setHasReadStatus(String noticeId, String userId);


    /**
     * 删除 通知用户关联 信息
     * @param req
     * @return
     */
    ResultVO<Integer> deleteNoticeUser(NoticeUserDeleteReq req);

    /**
     * 通知用户关联 信息列表分页
     * @param pageReq
     * @return
     */
    ResultVO<Page<NoticeUserDTO>> pageNoticeUser(NoticeUserPageReq pageReq);

    /**
     * 通知用户关联 信息列表
     * @param req
     * @return
     */
    ResultVO<List<NoticeUserDTO>> listNoticeUser(NoticeUserListReq req);


    /**
     * 获取未读消息数量
     * @param userId
     * @return
     */
    ResultVO<Integer> getNotReadNoticeCount(String userId);

}