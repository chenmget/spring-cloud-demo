package com.iwhalecloud.retail.system.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.MenuDTO;
import com.iwhalecloud.retail.system.entity.Menu;
import com.iwhalecloud.retail.system.entity.RoleMenu;
import com.iwhalecloud.retail.system.mapper.MenuMapper;
import com.iwhalecloud.retail.system.mapper.RoleMenuMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class MenuManager {
    @Resource
    private MenuMapper menuMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;
    
    public int saveMenu(MenuDTO menuDTO){
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuDTO, menu);
        return  menuMapper.insert(menu);
    }

    public List<Menu> listMenu(String platform, String menuName){
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(platform)) {
            queryWrapper.eq("PLATFORM", platform);
        }
        if (!StringUtils.isEmpty(menuName)) {
            queryWrapper.like("menu_name", menuName);
        }
//        queryWrapper.eq("deleteflag", "0"); // 有效的
        queryWrapper.orderByAsc("menu_name");
        return menuMapper.selectList(queryWrapper);
    }

    @Cacheable(value = SystemConst.CACHE_NAME_SYS_MENU, key = "#menuId")
    public Menu getMenuByMenuId(String menuId){
        return menuMapper.selectById(menuId);
    }

    // 同时删除角色菜单
    @CacheEvict(value = SystemConst.CACHE_NAME_SYS_MENU, key = "#menuId")
    public int deleteMenu(String menuId){
        QueryWrapper<RoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
        roleMenuQueryWrapper.eq("menu_id", menuId);
        int i  = roleMenuMapper.delete(roleMenuQueryWrapper);
        return menuMapper.deleteById(menuId);
    }

    @CacheEvict(value = SystemConst.CACHE_NAME_SYS_MENU, key = "#menuDTO.menuId")
    public int updateMenu(MenuDTO menuDTO){
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuDTO, menu);
        return menuMapper.updateById(menu);
    }
}
