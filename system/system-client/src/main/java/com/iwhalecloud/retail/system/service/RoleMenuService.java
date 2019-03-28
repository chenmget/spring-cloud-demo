package com.iwhalecloud.retail.system.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.RoleMenuDTO;

import java.util.List;

public interface RoleMenuService {

    ResultVO saveRoleMenu(RoleMenuDTO roleMenuDTO);

    ResultVO listRoleMenu();

    ResultVO<List<RoleMenuDTO>> listRoleMenuByRoleId(String roleId);

    ResultVO deleteRoleMenu(String id);

    ResultVO updateRoleMenu(RoleMenuDTO roleMenuDTO);
}