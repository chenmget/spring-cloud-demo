package com.iwhalecloud.retail.system.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.RoleDTO;
import com.iwhalecloud.retail.system.dto.request.RoleGetReq;
import com.iwhalecloud.retail.system.dto.request.RolePageReq;
import com.iwhalecloud.retail.system.entity.Role;
import com.iwhalecloud.retail.system.manager.RoleManager;
import com.iwhalecloud.retail.system.manager.RoleMenuManager;
import com.iwhalecloud.retail.system.manager.UserRoleManager;
import com.iwhalecloud.retail.system.service.RoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleManager roleManager;

    @Autowired
    private UserRoleManager userRoleManager;

    @Autowired
    private RoleMenuManager roleMenuManager;

    @Override
    public RoleDTO getRole(RoleGetReq req) {
        RoleDTO roleDTO = new RoleDTO();
        Role role = roleManager.getRole(req);
        if(role == null){
            return null;
        }
        BeanUtils.copyProperties(role, roleDTO);
        return roleDTO;
    }

    @Override
    public ResultVO saveRole(RoleDTO roleDTO) {
        // 设置默认值
        roleDTO.setCreateDate(new Date());
        roleDTO.setUpdateDate(new Date());
        if(!StringUtils.isEmpty(roleDTO.getCreateStaff())){
            roleDTO.setUpdateStaff(roleDTO.getCreateStaff());
        }
        int ret = roleManager.saveRole(roleDTO);
        if(ret > 0){
            return ResultVO.errorEnum(ResultCodeEnum.SUCCESS);
        }else{
            return ResultVO.errorEnum(ResultCodeEnum.ERROR);
        }
    }

    @Override
    public ResultVO listRole() {
        List<Role> list = roleManager.listRole();
        List<RoleDTO> roleDTOList = new ArrayList<>();
        for (Role r : list){
            RoleDTO roleDTO = new RoleDTO();
            BeanUtils.copyProperties(r, roleDTO);
            roleDTOList.add(roleDTO);
        }
        return ResultVO.success(roleDTOList);
    }

    @Override
    public ResultVO updateRole(RoleDTO roleDTO) {
        roleDTO.setUpdateDate(new Date());
        int ret = roleManager.updateRole(roleDTO);
        if(ret > 0){
            return ResultVO.errorEnum(ResultCodeEnum.SUCCESS);
        }else{
            return ResultVO.errorEnum(ResultCodeEnum.ERROR);
        }
    }

    @Override
    public ResultVO deleteRole(String roleId) {
        int ret = roleManager.deleteRole(roleId);
        int userRoleDelCount = userRoleManager.deleteByRoleId(roleId);
        int roleMenuDelCount = roleMenuManager.deleteByRoleId(roleId);
        if(ret > 0){
            return ResultVO.errorEnum(ResultCodeEnum.SUCCESS);
        }else{
            return ResultVO.errorEnum(ResultCodeEnum.ERROR);
        }
    }

    @Override
    public ResultVO queryRolePage(RolePageReq req) {
        if (req.getPageNo() == null || req.getPageSize() == null) {
            ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        Page<RoleDTO> organizationDTOPage = roleManager.queryRolePage(req);
        return ResultVO.success(organizationDTOPage);
    }
}