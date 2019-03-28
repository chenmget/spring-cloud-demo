package com.iwhalecloud.retail.system.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.RoleMenuDTO;
import com.iwhalecloud.retail.system.entity.RoleMenu;
import com.iwhalecloud.retail.system.mapper.RoleMenuMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class RoleMenuManager {
    @Resource
    private RoleMenuMapper roleMenuMapper;

    /**
     * 保存菜单--角色
     * @param roleMenuDTO
     * @return
     */
    @CacheEvict(value = SystemConst.CACHE_NAME_SYS_ROLE_MENU, key = "#roleMenuDTO.roleId")
    public int saveRoleMenu(RoleMenuDTO roleMenuDTO){
        RoleMenu roleMenu = new RoleMenu();
        BeanUtils.copyProperties(roleMenuDTO, roleMenu);
        roleMenu.setCreateDate(new Date());
        return roleMenuMapper.insert(roleMenu);
    }

    public List<RoleMenu> listRoleMenu(){
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        return roleMenuMapper.selectList(queryWrapper);
    }

    /**
     * 根据角色ID获取 角色菜单关联关系列表
     * @param roleId
     * @return
     */
    @Cacheable(value = SystemConst.CACHE_NAME_SYS_ROLE_MENU, key = "#roleId")
    public List<RoleMenu> listRoleMenuByRoleId(String roleId){
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(RoleMenu.FieldNames.roleId.getTableFieldName(), roleId);
        queryWrapper.orderByAsc(RoleMenu.FieldNames.menuName.getTableFieldName());
        return roleMenuMapper.selectList(queryWrapper);
    }

    /**
     * 根据关联ID删除 一条记录
     *
     * 要清除所有的 缓存
     *
     * @param id
     * @return
     */
    @CacheEvict(value = SystemConst.CACHE_NAME_SYS_ROLE_MENU, allEntries = true)
    public int deleteRoleMenu(String id){
        return roleMenuMapper.deleteById(id);
    }

    /**
     * 根据关联ID 更新 一条记录
     *
     * 要清除所有的 缓存
     *
     * @param roleMenuDTO
     * @return
     */
    @CacheEvict(value = SystemConst.CACHE_NAME_SYS_ROLE_MENU, allEntries = true)
    public int updateRoleMenu(RoleMenuDTO roleMenuDTO){
        RoleMenu roleMenu = new RoleMenu();
        BeanUtils.copyProperties(roleMenuDTO, roleMenu);
        return roleMenuMapper.updateById(roleMenu);
    }

    /**
     * 根据角色ID删除 该角色关联的所有 角色菜单关联关系
     * @param roleId
     * @return
     */
    @CacheEvict(value = SystemConst.CACHE_NAME_SYS_ROLE_MENU, key = "#roleId")
    public int deleteByRoleId(String roleId){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(RoleMenu.FieldNames.roleId.getTableFieldName(), roleId);
        return roleMenuMapper.deleteByMap(paramMap);
    }

    /**
     * 根据菜单ID删除 该菜单关联的所有 角色-菜单关联关系
     * 要清除所有的 缓存
     * @param menuId
     * @return
     */
    @CacheEvict(value = SystemConst.CACHE_NAME_SYS_ROLE_MENU, allEntries = true)
    public int deleteByMenuId(String menuId){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(RoleMenu.FieldNames.menuId.getTableFieldName(), menuId);
        return roleMenuMapper.deleteByMap(paramMap);
    }

}
