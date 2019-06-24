package com.iwhalecloud.retail.system.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.MenuDTO;
import com.iwhalecloud.retail.system.dto.request.MenuListReq;
import com.iwhalecloud.retail.system.entity.Menu;
import com.iwhalecloud.retail.system.manager.MenuManager;
import com.iwhalecloud.retail.system.manager.RoleMenuManager;
import com.iwhalecloud.retail.system.service.MenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuManager menuManager;

    @Autowired
    private RoleMenuManager roleMenuManager;

    @Override
    public ResultVO saveMenu(MenuDTO menuDTO) {
        menuDTO.setStatusCd("1");
        menuDTO.setCreateDate(new Date());
        menuDTO.setUpdateDate(new Date());
        int ret = menuManager.saveMenu(menuDTO);
        if(ret > 0){
            return ResultVO.errorEnum(ResultCodeEnum.SUCCESS);
        }else{
            return ResultVO.errorEnum(ResultCodeEnum.ERROR);
        }
    }

    @Override
    public ResultVO<List<MenuDTO>> listMenu(MenuListReq req) {
        List<Menu> list = menuManager.listMenu(req);
        List<MenuDTO> menuDTOList = new ArrayList<>();
        for(Menu m : list){
            MenuDTO dto = new MenuDTO();
            BeanUtils.copyProperties(m, dto);
            menuDTOList.add(dto);
        }
        return ResultVO.success(menuDTOList);
    }

    @Override
    public ResultVO<MenuDTO> getMenuByMenuId(String menuId) {
        Menu menu = menuManager.getMenuByMenuId(menuId);
        if(menu == null){
            return ResultVO.errorEnum(ResultCodeEnum.ERROR);
        }
        MenuDTO dto = new MenuDTO();
        BeanUtils.copyProperties(menu, dto);
        return ResultVO.success(dto);
    }

    @Override
    public ResultVO deleteMenu(String menuId) {
        int ret = menuManager.deleteMenu(menuId);
        int delRoleMenuCount = roleMenuManager.deleteByMenuId(menuId);
        if(ret > 0){
            return ResultVO.errorEnum(ResultCodeEnum.SUCCESS);
        }else{
            return ResultVO.errorEnum(ResultCodeEnum.ERROR);
        }
    }

    @Override
    public ResultVO updateMenu(MenuDTO menuDTO) {
        menuDTO.setUpdateDate(new Date());
        int ret = menuManager.updateMenu(menuDTO);
        if(ret > 0){
            return ResultVO.errorEnum(ResultCodeEnum.SUCCESS);
        }else{
            return ResultVO.errorEnum(ResultCodeEnum.ERROR);
        }
    }

    @Override
    public List<MenuDTO> getMenuByRoleId(String usrId) {
        return menuManager.getMenuByRoleId(usrId);
    }
}