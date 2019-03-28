package com.iwhalecloud.retail.system.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.NoticeUserDTO;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.request.*;
import com.iwhalecloud.retail.system.entity.NoticeUser;
import com.iwhalecloud.retail.system.manager.NoticeUserManager;
import com.iwhalecloud.retail.system.service.NoticeUserService;
import com.iwhalecloud.retail.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class NoticeUserServiceImpl implements NoticeUserService {

    @Autowired
    private NoticeUserManager noticeUserManager;

    @Autowired
    private UserService userService;

    /**
     * 添加一个 通知用户关联
     * @param req
     * @return
     */
    @Override
    public ResultVO<NoticeUserDTO> saveNoticeUser(NoticeUserSaveReq req){
        log.info("NoticeUserServiceImpl.saveNoticeUser(), 入参req={} ", req);
        NoticeUser noticeUser = new NoticeUser();
        BeanUtils.copyProperties(req, noticeUser);
        noticeUser.setStatus(SystemConst.NoticeUserReadStatusEnum.NOT_READ.getCode());
        NoticeUserDTO noticeUserDTO = noticeUserManager.insert(noticeUser);
        log.info("NoticeUserServiceImpl.saveNoticeUser(), 出参NoticeUserDTO={} ", noticeUserDTO);
        if (noticeUserDTO == null){
            return ResultVO.error("新增公通知用户关联信息失败");
        }
        return ResultVO.success(noticeUserDTO);
    }

    /**
     * 添加一组 通知用户关联
     * @param noticeId
     * @param userIdList
     * @return
     */
    @Override
    public ResultVO<NoticeUserDTO> saveNoticeUserWithUserIds(String noticeId, List<String> userIdList) {
        log.info("NoticeUserServiceImpl.saveNoticeUserWithUserIds(), InputParameter: noticeId={} userIdList={}  ", noticeId, JSON.toJSONString(userIdList));
        // 定向类型 要绑定指定用户
        UserListReq userListReq = new UserListReq();
        userListReq.setUserIdList(userIdList);
        List<UserDTO> userDTOList = userService.getUserList(userListReq);

        for (String userId : userIdList) {
            NoticeUserSaveReq noticeUserSaveReq = new NoticeUserSaveReq();
            noticeUserSaveReq.setNoticeId(noticeId);
            noticeUserSaveReq.setUserId(userId);
            // 设置用户名
            for (UserDTO userDTO : userDTOList) {
                if (StringUtils.equals(userId, userDTO.getUserId())) {
                    noticeUserSaveReq.setUserName(userDTO.getUserName());
                    break;
                }
            }
            saveNoticeUser(noticeUserSaveReq);
        }

        return ResultVO.success();
    }

    /**
     * 定向类通知： 获取可以选择的用户分页列表
     * @param req
     * @return
     */
    @Override
    public ResultVO<Page<UserDTO>> pageSelectableUser(NoticeSelectUserPageReq req) {
        UserPageReq userPageReq = new UserPageReq();

//        if (!StringUtils.isEmpty(noticeId)) {
//            // 获取已经关联的 userids
//            NoticeUserListReq noticeUserListReq = new NoticeUserListReq();
//            noticeUserListReq.setNoticeId(noticeId);
//            List<NoticeUser> noticeUserList = noticeUserManager.listNoticeUser(noticeUserListReq);
//            List<String> userIdList = Lists.newArrayList();
//            if (!CollectionUtils.isEmpty(noticeUserList)) {
//                for (NoticeUser noticeUser : noticeUserList) {
//                    userIdList.add(noticeUser.getUserId());
//                }
//                userPageReq.setNotInUserIdList(userIdList); // 过滤 已经关联的 userid
//            }
//        }

        BeanUtils.copyProperties(req, userPageReq);
        // 设置有效状态
        userPageReq.setStatusList(Lists.newArrayList(SystemConst.USER_STATUS_VALID));

//        userPageReq.setPageSize(pageSize);
//        userPageReq.setPageNo(pageNo);
        Page<UserDTO> page = userService.pageUser(userPageReq);
        log.info("NoticeUserServiceImpl.pageSelectableUser()," +
                " \n InputParameter: NoticeSelectUserPageReq = {}" +
                " \n OutputParameter: page = {} ", JSON.toJSONString(req), JSON.toJSONString(page));
        return ResultVO.success(page);
    }

    /**
     * 获取一个 通知用户关联
     * @param relId
     * @return
     */
    @Override
    public ResultVO<NoticeUserDTO> getNoticeUserById(String relId) {
        log.info("NoticeUserServiceImpl.getNoticeUserById(), 入参relId={} ", relId);
        NoticeUserGetReq req = new NoticeUserGetReq();
        req.setId(relId);
        NoticeUser noticeUser = noticeUserManager.getNoticeUser(req);
        NoticeUserDTO noticeUserDTO = null;
        if(noticeUser != null) {
            noticeUserDTO = new NoticeUserDTO();
            BeanUtils.copyProperties(noticeUser, noticeUserDTO);
        }
        log.info("NoticeUserServiceImpl.getNoticeUserById(), 出参对象noticeUserDTO={} ", noticeUserDTO);
        return ResultVO.success(noticeUserDTO);
    }

    /**
     * 编辑 通知用户关联知 信息
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> updateNoticeUser(NoticeUserUpdateReq req) {
        log.info("NoticeUserServiceImpl.updateNoticeUser(), 入参NoticeUserUpdateReq={} ", req);
        NoticeUser noticeUser = new NoticeUser();
        BeanUtils.copyProperties(req, noticeUser);
        int result = noticeUserManager.updateNoticeUser(noticeUser);
        log.info("NoticeUserServiceImpl.updateNoticeUser(), 出参对象(更新影响数据条数）={} ", result);
        if (result <= 0){
            return ResultVO.error("编辑通知用户关联信息失败");
        }
        return ResultVO.success(result);
    }

    /**
     * 设置已读
     * @param noticeId
     * @param userId
     * @return
     */
    @Override
    public ResultVO<Integer> setHasReadStatus(String noticeId, String userId) {
        if (StringUtils.isEmpty(noticeId) && StringUtils.isEmpty(userId)) {
            return ResultVO.error("通知ID或用户ID不能为空");
        }
        NoticeUserGetReq noticeUserGetReq = new NoticeUserGetReq();
        noticeUserGetReq.setNoticeId(noticeId);
        noticeUserGetReq.setUserId(userId);
        NoticeUser noticeUser = noticeUserManager.getNoticeUser(noticeUserGetReq);
        if (noticeUser == null){
            return ResultVO.error("没有该记录");
        }
        // 设置已读
        noticeUser.setStatus(SystemConst.NoticeUserReadStatusEnum.HAS_READ.getCode());
        int result = noticeUserManager.updateNoticeUser(noticeUser);
        log.info("NoticeUserServiceImpl.setHasReadStatus()," +
                " \n InputParameter: noticeId = {} userId = {} " +
                " \n OutputParameter: effect lows: result = {} ", noticeId, userId, result);

        if (result <= 0){
            return ResultVO.error("设置已读状态失败");
        }
        return ResultVO.success(result);
    }


    /**
     * 删除 通知用户关联知 信息
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> deleteNoticeUser(NoticeUserDeleteReq req) {
        log.info("NoticeUserServiceImpl.deleteNoticeUserById(), 入参id={} ", req);
        int result = noticeUserManager.deleteNoticeUserById(req);
        log.info("NoticeUserServiceImpl.deleteNoticeUserById(), 出参对象(更新影响数据条数）={} ", result);
        if (result <= 0){
            return ResultVO.error("删除通知用户关联信息失败");
        }
        return ResultVO.success(result);
    }

    /**
     * 通知用户关联 信息列表分页
     * @param pageReq
     * @return
     */
    @Override
    public ResultVO<Page<NoticeUserDTO>> pageNoticeUser(NoticeUserPageReq pageReq) {
        log.info("NoticeUserServiceImpl.pageNoticeUser(), 入参NoticeUserPageReq={} ", pageReq);
        Page<NoticeUserDTO> page = noticeUserManager.pageNoticeUser(pageReq);
        log.info("NoticeUserServiceImpl.pageNoticeUser(), 出参page={} ", page);
        return ResultVO.success(page);
    }

    /**
     * 通知用户关联 信息列表
     * @param req
     * @return
     */
    @Override
    public ResultVO<List<NoticeUserDTO>> listNoticeUser(NoticeUserListReq req) {
        List<NoticeUser> noticeUserList = noticeUserManager.listNoticeUser(req);
        List<NoticeUserDTO> noticeUserDTOList = new ArrayList<>();
        for (NoticeUser noticeUser : noticeUserList) {
            NoticeUserDTO noticeUserDTO = new NoticeUserDTO();
            BeanUtils.copyProperties(noticeUser, noticeUserDTO);
            noticeUserDTOList.add(noticeUserDTO);
        }
        log.info("NoticeUserServiceImpl.listNoticeUser()," +
                " \n InputParameter: merchantId = {}" +
                " \n OutputParameter: list = {} ", JSON.toJSONString(req), JSON.toJSONString(noticeUserDTOList));
        return ResultVO.success(noticeUserDTOList);
    }

    @Override
    public ResultVO<Integer> getNotReadNoticeCount(String userId){
        log.info("NoticeUserServiceImpl.getNotReadNoticeCount(), 入参userId ", userId);
//        List<String> statusList = new ArrayList<>();
//        statusList.add(SystemConst.NoticeUserReadStatusEnum.NOT_READ.getCode());
//        int count = noticeUserManager.getNotReadNoticeCount(userId, statusList);

        NoticeUserCountReq req = new NoticeUserCountReq();
        req.setUserId(userId);
        req.setStatus(SystemConst.NoticeUserReadStatusEnum.NOT_READ.getCode());
        int count = noticeUserManager.countNoticeUser(req);

        log.info("NoticeUserServiceImpl.getNotReadNoticeCount(), 出参int ", count);
        return ResultVO.success(count);
    }
}