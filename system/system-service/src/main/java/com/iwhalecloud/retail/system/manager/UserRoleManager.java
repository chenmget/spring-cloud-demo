package com.iwhalecloud.retail.system.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.UserRoleDTO;
import com.iwhalecloud.retail.system.entity.UserRole;
import com.iwhalecloud.retail.system.mapper.UserRoleMapper;
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
public class UserRoleManager {
    @Resource
    private UserRoleMapper userRoleMapper;

    /**
     * 保存用户--角色
     * 要清理缓存
     * @param userRoleDTO
     * @return
     */
    @CacheEvict(value = SystemConst.CACHE_NAME_SYS_USER_ROLE, key = "#userRoleDTO.userId")
    public int saveUserRole(UserRoleDTO userRoleDTO){
        UserRole userRole = new UserRole();
        BeanUtils.copyProperties(userRoleDTO, userRole);
        userRole.setCreateDate(new Date());
        return userRoleMapper.insert(userRole);
    }

    /**
     * 根据用户ID获取 用户角色关联关系列表
     * @param userId
     * @return
     */
    @Cacheable(value = SystemConst.CACHE_NAME_SYS_USER_ROLE, key = "#userId")
    public List<UserRole> listUserRoleByUserId(String userId){
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(UserRole.FieldNames.userId.getTableFieldName(), userId);
        return userRoleMapper.selectList(queryWrapper);
    }

    /**
     * 根据角色ID删除 该角色关联的所有 用户角色关联关系
     * 要清除所有的 缓存
     * @param roleId
     * @return
     */
    @CacheEvict(value = SystemConst.CACHE_NAME_SYS_USER_ROLE, allEntries = true)
    public int deleteByRoleId(String roleId){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(UserRole.FieldNames.roleId.getTableFieldName(), roleId);
        int count = userRoleMapper.deleteByMap(paramMap);
        return count;
    }

    /**
     * 根据用户ID删除 该用户关联的所有 用户角色关联关系
     * @param userId
     * @return
     */
    @CacheEvict(value = SystemConst.CACHE_NAME_SYS_USER_ROLE, key = "#userId")
    public int deleteByUserId(String userId){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(UserRole.FieldNames.userId.getTableFieldName(), userId);
        int count = userRoleMapper.deleteByMap(paramMap);
        return count;
    }
    
}
