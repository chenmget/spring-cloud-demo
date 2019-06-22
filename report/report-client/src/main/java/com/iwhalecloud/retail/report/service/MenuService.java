package com.iwhalecloud.retail.report.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.MenuDTO;

public interface MenuService {

    public ResultVO saveMenu(MenuDTO menuDTO);

    public ResultVO listMenu();

    public ResultVO<MenuDTO> getMenuByMenuId(String menuId);

    public ResultVO deleteMenu(String menuId);

    public ResultVO updateMenu(MenuDTO menuDTO);
}
