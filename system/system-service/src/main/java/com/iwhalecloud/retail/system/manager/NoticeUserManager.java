package com.iwhalecloud.retail.system.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.system.dto.NoticeUserDTO;
import com.iwhalecloud.retail.system.dto.request.*;
import com.iwhalecloud.retail.system.entity.NoticeUser;
import com.iwhalecloud.retail.system.mapper.NoticeUserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;


@Component
public class NoticeUserManager{

    @Resource
    private NoticeUserMapper noticeUserMapper;

    @Resource
    private UserManager userManager;

    /**
     * 添加一个 公告通知
     * @param noticeUser
     * @return
     */
    public NoticeUserDTO insert(NoticeUser noticeUser){
        int resultInt = noticeUserMapper.insert(noticeUser);
        if(resultInt > 0){
            NoticeUserDTO noticeUserDTO = new NoticeUserDTO();
            BeanUtils.copyProperties(noticeUser, noticeUserDTO);
            return noticeUserDTO;
        }
        return null;
    }

    /**
     * 根据条件（精确）查找单个 商家
     * @param req
     * @return
     */
    public NoticeUser getNoticeUser(NoticeUserGetReq req){
        QueryWrapper<NoticeUser> queryWrapper = new QueryWrapper<NoticeUser>();
        Boolean hasParam = false;
        if(!StringUtils.isEmpty(req.getId())){
            hasParam = true;
            queryWrapper.eq(NoticeUser.FieldNames.id.getTableFieldName(), req.getId());
        }
        if(!StringUtils.isEmpty(req.getUserId())){
            hasParam = true;
            queryWrapper.eq(NoticeUser.FieldNames.userId.getTableFieldName(), req.getUserId());
        }
        if(!StringUtils.isEmpty(req.getNoticeId())){
            hasParam = true;
            queryWrapper.eq(NoticeUser.FieldNames.noticeId.getTableFieldName(), req.getNoticeId());
        }
        if (!hasParam) {
            return null;
        }
        queryWrapper.last(" limit 1 "); // 限定查询条数(避免没参数的查出整表）
        List<NoticeUser> noticeUserList = noticeUserMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(noticeUserList)) {
            return null;
        }
        return noticeUserList.get(0);
    }

//    /**
//     * 根据id查找单个 公告通知
//     * @param relId
//     * @return
//     */
//    public NoticeUserDTO getNoticeUserById(String relId) {
//        NoticeUser noticeUser = noticeUserMapper.selectById(relId);
//        if(noticeUser == null) {
//            return null;
//        }
//        NoticeUserDTO noticeUserDTO = new NoticeUserDTO();
//        BeanUtils.copyProperties(noticeUser, noticeUserDTO);
//        return noticeUserDTO;
//    }

    /**
     * 更新
     * @param noticeUser
     * @return
     */
    public int updateNoticeUser(NoticeUser noticeUser){
        return noticeUserMapper.updateById(noticeUser);
    }

    /**
     * 删除
     * @param req
     * @return
     */
    public int deleteNoticeUserById(NoticeUserDeleteReq req){
        QueryWrapper<NoticeUser> queryWrapper = new QueryWrapper<>();
        boolean hasParam = false;
        if(!StringUtils.isEmpty(req.getUserId())){
            queryWrapper.eq(NoticeUser.FieldNames.userId.getTableFieldName(), req.getUserId());
            hasParam = true;
        }
        if(!StringUtils.isEmpty(req.getNoticeId())){
            queryWrapper.eq(NoticeUser.FieldNames.noticeId.getTableFieldName(), req.getNoticeId());
            hasParam = true;
        }
        if(!StringUtils.isEmpty(req.getId())){
            queryWrapper.eq(NoticeUser.FieldNames.id.getTableFieldName(), req.getId());
            hasParam = true;
        }
        if (!hasParam) {
            return 0;
        }
        return noticeUserMapper.delete(queryWrapper);
    }

    /**
     * 列表查询
     * @param req
     * @return
     */
    public List<NoticeUser> listNoticeUser(NoticeUserListReq req){
        QueryWrapper<NoticeUser> queryWrapper = new QueryWrapper<>();
        boolean hasParam = false;
        if(!StringUtils.isEmpty(req.getUserId())){
            queryWrapper.eq(NoticeUser.FieldNames.userId.getTableFieldName(), req.getUserId());
            hasParam = true;
        }
        if(!StringUtils.isEmpty(req.getNoticeId())){
            queryWrapper.eq(NoticeUser.FieldNames.noticeId.getTableFieldName(), req.getNoticeId());
            hasParam = true;
        }
        if(!StringUtils.isEmpty(req.getStatus())){
            queryWrapper.eq(NoticeUser.FieldNames.status.getTableFieldName(), req.getStatus());
            hasParam = true;
        }
        if (!hasParam) {
            return Lists.newArrayList();
        }
        return noticeUserMapper.selectList(queryWrapper);
    }

    /**
     * 分页查询
     * @param req
     * @return
     */
    public Page<NoticeUserDTO> pageNoticeUser(NoticeUserPageReq req) {
        Page<NoticeUserDTO> page = new Page<>(req.getPageNo(), req.getPageSize());
        Page<NoticeUserDTO> businessEntityDTOPage = noticeUserMapper.pageNoticeUser(page, req);
        return businessEntityDTOPage;
    }

    /**
     * 获取用户消息数量
     * @return
     */
    public int countNoticeUser(NoticeUserCountReq req){
        QueryWrapper<NoticeUser> queryWrapper = new QueryWrapper<>();

        if(!StringUtils.isEmpty(req.getUserId())){
            queryWrapper.eq(NoticeUser.FieldNames.userId.getTableFieldName(), req.getUserId());
        }
        if(!StringUtils.isEmpty(req.getNoticeId())){
            queryWrapper.eq(NoticeUser.FieldNames.noticeId.getTableFieldName(), req.getNoticeId());
        }
        if(!StringUtils.isEmpty(req.getStatus())){
            queryWrapper.eq(NoticeUser.FieldNames.status.getTableFieldName(), req.getStatus());
        }
        return noticeUserMapper.selectCount(queryWrapper);
    }

    /**
     * 获取未读消息数量
     * @param userId
     * @param statusList
     * @return
     */
    public int getNotReadNoticeCount(String userId, List<String> statusList){
        return noticeUserMapper.getNotReadNoticeCount(userId, statusList);
    }
    
}
