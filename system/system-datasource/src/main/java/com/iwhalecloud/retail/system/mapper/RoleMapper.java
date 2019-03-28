package com.iwhalecloud.retail.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.system.dto.RoleDTO;
import com.iwhalecloud.retail.system.dto.request.RolePageReq;
import com.iwhalecloud.retail.system.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    Page<RoleDTO> queryRolePage(Page<RoleDTO> page, @Param("req") RolePageReq req);

}