package com.iwhalecloud.retail.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.UserDetailDTO;
import com.iwhalecloud.retail.system.dto.request.UserListReq;
import com.iwhalecloud.retail.system.dto.request.UserPageReq;
import com.iwhalecloud.retail.system.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    UserDetailDTO getUserDetail(@Param("userId") String userId);

    Page<UserDTO> pageUser(Page<UserDTO> page, @Param("req") UserPageReq req);

    List<UserDTO> listUser(@Param("req") UserListReq req);

}