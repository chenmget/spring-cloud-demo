package com.iwhalecloud.retail.system.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.UserRoleDTO;

import java.util.List;

public interface UserRoleService {

    /**
     * 保存用户角色关系
     * @param userRoleDTO
     * @return
     */
    ResultVO saveUserRole(UserRoleDTO userRoleDTO);

    /**
     * 根据用户ID获取角色列表
     * @param userId
     * @return
     */
    ResultVO<List<UserRoleDTO>> listUserRoleByUserId(String userId);

    ResultVO deleteUserRoleByUserId(String userId);
}