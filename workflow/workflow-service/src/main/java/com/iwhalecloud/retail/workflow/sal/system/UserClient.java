package com.iwhalecloud.retail.workflow.sal.system;

import com.iwhalecloud.retail.system.dto.UserDetailDTO;
import com.iwhalecloud.retail.workflow.dto.req.HandlerUser;

import java.util.List;

/**
 * @author mzl
 * @date 2019/1/10
 */
public interface UserClient {

    /**
     * 根据角色ID和部门ID查询用户
     *
     * @param roleId
     * * @param deptId
     * @return
     */
    List<HandlerUser> listUserByCondition(String roleId, String deptId);

    /**
     * 根据用户ID查询用户
     *
     * @param userId
     * @return
     */
    HandlerUser queryUserByUserId(String userId);


    /**
     * 获取用户详细信息
     * @param userId 用户ID
     * @return
     */
    UserDetailDTO getUserDetail(String userId);

}
