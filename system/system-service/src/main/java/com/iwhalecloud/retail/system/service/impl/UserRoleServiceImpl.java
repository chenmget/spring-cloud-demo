package com.iwhalecloud.retail.system.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.UserRoleDTO;
import com.iwhalecloud.retail.system.entity.UserRole;
import com.iwhalecloud.retail.system.manager.UserRoleManager;
import com.iwhalecloud.retail.system.service.UserRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleManager userRoleManager;

    @Override
    public ResultVO saveUserRole(UserRoleDTO userRoleDTO) {
        userRoleDTO.setCreateDate(new Date());
        int ret = userRoleManager.saveUserRole(userRoleDTO);
        if(ret > 0){
            return ResultVO.errorEnum(ResultCodeEnum.SUCCESS);
        }else{
            return ResultVO.errorEnum(ResultCodeEnum.ERROR);
        }
    }

    /**
     * 根据用户ID获取角色列表
     * @param userId
     * @return
     */
    @Override
    public ResultVO<List<UserRoleDTO>> listUserRoleByUserId(String userId) {
        List<UserRole> list = userRoleManager.listUserRoleByUserId(userId);
        List<UserRoleDTO> userRoleDTOList = new ArrayList<>();
        for(UserRole ur : list){
            UserRoleDTO dto = new UserRoleDTO();
            BeanUtils.copyProperties(ur, dto);
            userRoleDTOList.add(dto);
        }
        return ResultVO.success(userRoleDTOList);
    }

    @Override
    public ResultVO deleteUserRoleByUserId(String userId) {
        int ret = userRoleManager.deleteByUserId(userId);
        if(ret > 0){
            return ResultVO.errorEnum(ResultCodeEnum.SUCCESS);
        }else{
            return ResultVO.errorEnum(ResultCodeEnum.ERROR);
        }
    }
}