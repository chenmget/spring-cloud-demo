package com.iwhalecloud.retail.system.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.RoleMenuDTO;
import com.iwhalecloud.retail.system.entity.RoleMenu;
import com.iwhalecloud.retail.system.manager.RoleMenuManager;
import com.iwhalecloud.retail.system.service.RoleMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class RoleMenuServiceImpl implements RoleMenuService {

    @Autowired
    private RoleMenuManager roleMenuManager;


    @Override
    public ResultVO saveRoleMenu(RoleMenuDTO roleMenuDTO) {
        roleMenuDTO.setCreateDate(new Date());
        int ret = roleMenuManager.saveRoleMenu(roleMenuDTO);
        if(ret > 0){
            return ResultVO.errorEnum(ResultCodeEnum.SUCCESS);
        }else{
            return ResultVO.errorEnum(ResultCodeEnum.ERROR);
        }
    }

    @Override
    public ResultVO<List<RoleMenuDTO>> listRoleMenu() {
        List<RoleMenu> list = roleMenuManager.listRoleMenu();
        List<RoleMenuDTO> roleMenuDTOList = new ArrayList<>();
        for(RoleMenu rm : list){
            RoleMenuDTO dto = new RoleMenuDTO();
            BeanUtils.copyProperties(rm, dto);
            roleMenuDTOList.add(dto);
        }
        return ResultVO.success(roleMenuDTOList);
    }

    @Override
    public ResultVO<List<RoleMenuDTO>> listRoleMenuByRoleId(String roleId) {
        List<RoleMenu> list = roleMenuManager.listRoleMenuByRoleId(roleId);
        List<RoleMenuDTO> roleMenuDTOList = new ArrayList<>();
        for(RoleMenu rm : list){
            RoleMenuDTO dto = new RoleMenuDTO();
            BeanUtils.copyProperties(rm, dto);
            roleMenuDTOList.add(dto);
        }
        return ResultVO.success(roleMenuDTOList);
    }

    @Override
    public ResultVO deleteRoleMenu(String id) {
        int ret = roleMenuManager.deleteRoleMenu(id);
        if(ret > 0){
            return ResultVO.errorEnum(ResultCodeEnum.SUCCESS);
        }else{
            return ResultVO.errorEnum(ResultCodeEnum.ERROR);
        }
    }

    @Override
    public ResultVO updateRoleMenu(RoleMenuDTO roleMenuDTO) {
        int ret = roleMenuManager.updateRoleMenu(roleMenuDTO);
        if(ret > 0){
            return ResultVO.errorEnum(ResultCodeEnum.SUCCESS);
        }else{
            return ResultVO.errorEnum(ResultCodeEnum.ERROR);
        }
    }


}