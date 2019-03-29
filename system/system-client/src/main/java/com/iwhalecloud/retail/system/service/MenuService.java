package com.iwhalecloud.retail.system.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.MenuDTO;

public interface MenuService {

    ResultVO saveMenu(MenuDTO menuDTO);

    ResultVO listMenu(String platform, String menuName);

    ResultVO<MenuDTO> getMenuByMenuId(String menuId);

    ResultVO deleteMenu(String menuId);

    ResultVO updateMenu(MenuDTO menuDTO);
}