package com.iwhalecloud.retail.system.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.RoleDTO;
import com.iwhalecloud.retail.system.dto.request.RoleGetReq;
import com.iwhalecloud.retail.system.dto.request.RolePageReq;

public interface RoleService {

    /**
     * 精确查找单个角色
     * @param req
     * @return
     */
    RoleDTO getRole(RoleGetReq req);

    ResultVO saveRole(RoleDTO roleDTO);

    ResultVO listRole();

    ResultVO updateRole(RoleDTO roleDTO);

    ResultVO deleteRole(String roleId);

    ResultVO queryRolePage(RolePageReq req);

}