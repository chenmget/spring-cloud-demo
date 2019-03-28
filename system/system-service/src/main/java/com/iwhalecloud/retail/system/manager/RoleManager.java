package com.iwhalecloud.retail.system.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.system.dto.RoleDTO;
import com.iwhalecloud.retail.system.dto.request.RoleGetReq;
import com.iwhalecloud.retail.system.dto.request.RolePageReq;
import com.iwhalecloud.retail.system.entity.Role;
import com.iwhalecloud.retail.system.entity.RoleMenu;
import com.iwhalecloud.retail.system.entity.UserRole;
import com.iwhalecloud.retail.system.mapper.RoleMapper;
import com.iwhalecloud.retail.system.mapper.RoleMenuMapper;
import com.iwhalecloud.retail.system.mapper.UserRoleMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Component
public class RoleManager {
    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    /**
     * 根据条件（精确）查找单个角色
     * @param req
     * @return
     */
    public Role getRole(RoleGetReq req) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<Role>();
        Boolean hasParam = false;
        if(!StringUtils.isEmpty(req.getRoleName())){
            hasParam = true;
            queryWrapper.eq("role_name", req.getRoleName());
        }
        if(!StringUtils.isEmpty(req.getRoleId())){
            hasParam = true;
            queryWrapper.eq("role_id", req.getRoleId());
        }
        if (!hasParam) {
            return null;
        }
        queryWrapper.last(" limit 1 "); // 限定查询条数(避免没参数的查出整表）

        List<Role> roleList = roleMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(roleList)){
            return null;
        }
        return roleList.get(0);
    }

    public int saveRole(RoleDTO roleDTO){
        Role role = new Role();
        BeanUtils.copyProperties(roleDTO, role);
        role.setCreateDate(new Date());
        return  roleMapper.insert(role);
    }

    public List<Role> listRole(){
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        return roleMapper.selectList(queryWrapper);
    }

    // a) 删除角色菜单
    // b) 删除用户角色
    public int deleteRole(String roleId){
        int ret = -1;
        QueryWrapper<RoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
        roleMenuQueryWrapper.eq("role_id", roleId);
        int i  = roleMenuMapper.delete(roleMenuQueryWrapper);
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("role_id", roleId);
        int j = userRoleMapper.delete(userRoleQueryWrapper);
        return roleMapper.deleteById(roleId);
    }

    public int updateRole(RoleDTO roleDTO){
        Role role = new Role();
        BeanUtils.copyProperties(roleDTO, role);
        return roleMapper.updateById(role);
    }

    public Page<RoleDTO> queryRolePage(RolePageReq req){
        Page<RoleDTO> page = new Page<>(req.getPageNo(), req.getPageSize());
        Page<RoleDTO> roleDTOPage = roleMapper.queryRolePage(page, req);
        return roleDTOPage;
    }
}
