package com.iwhalecloud.retail.report.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.report.dto.MenuDTO;
import com.iwhalecloud.retail.report.entity.Menu;
import com.iwhalecloud.retail.report.mapper.MenuMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class MenuManager {
    @Resource
    private MenuMapper menuMapper;


    public int saveMenu(MenuDTO menuDTO){
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuDTO, menu);
        return  menuMapper.insert(menu);
    }

    public List<Menu> listMenu(){
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("deleteflag", "0"); // 有效的
        return menuMapper.selectList(queryWrapper);
    }

    public Menu getMenuByMenuId(String menuId){
        return menuMapper.selectById(menuId);
    }

    // 同时删除角色菜单
    public int deleteMenu(String menuId){
        return menuMapper.deleteById(menuId);
    }

    public int updateMenu(MenuDTO menuDTO){
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuDTO, menu);
        return menuMapper.updateById(menu);
    }
}
