package com.iwhalecloud.retail.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.system.dto.NoticeUserDTO;
import com.iwhalecloud.retail.system.dto.request.NoticeUserPageReq;
import com.iwhalecloud.retail.system.entity.NoticeUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: NoticeUserMapper
 * @author autoCreate
 */
@Mapper
public interface NoticeUserMapper extends BaseMapper<NoticeUser>{

    Page<NoticeUserDTO> pageNoticeUser(Page<NoticeUserDTO> page, @Param("req") NoticeUserPageReq req);

    int getNotReadNoticeCount(@Param("userId")String userId, @Param("statusList") List<String> statusList);
}