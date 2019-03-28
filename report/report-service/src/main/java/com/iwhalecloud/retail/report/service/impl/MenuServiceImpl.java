package com.iwhalecloud.retail.report.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.MenuDTO;
import com.iwhalecloud.retail.report.entity.Menu;
import com.iwhalecloud.retail.report.manager.MenuManager;
import com.iwhalecloud.retail.report.service.MenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuManager menuManager;

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
    public ResultVO listMenu() {
        List<Menu> list = menuManager.listMenu();
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
}
