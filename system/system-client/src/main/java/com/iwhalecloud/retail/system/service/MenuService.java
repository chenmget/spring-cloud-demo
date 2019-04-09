package com.iwhalecloud.retail.system.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.MenuDTO;
import com.iwhalecloud.retail.system.dto.request.MenuListReq;

import java.util.List;

public interface MenuService {

    ResultVO saveMenu(MenuDTO menuDTO);

    ResultVO<List<MenuDTO>> listMenu(MenuListReq req);

    ResultVO<MenuDTO> getMenuByMenuId(String menuId);

    ResultVO deleteMenu(String menuId);

    ResultVO updateMenu(MenuDTO menuDTO);
}