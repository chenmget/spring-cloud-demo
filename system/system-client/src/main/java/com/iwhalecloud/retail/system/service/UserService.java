package com.iwhalecloud.retail.system.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.UserDetailDTO;
import com.iwhalecloud.retail.system.dto.request.*;
import com.iwhalecloud.retail.system.dto.response.UserLoginResp;

import java.util.List;

public interface UserService {

    /**
     * 系统用户登陆
     * @param req
     * @return
     */
    UserLoginResp login(UserLoginReq req);

    /**
     * 系统用户免密登陆
     * @param req
     * @return
     */
    UserLoginResp loginWithoutPwd(UserLoginWithoutPwdReq req);

    /**
     * 根据分销商ids 获取可用的系统用户（店员关联工号时 调用）
     * 条件：
     * state = 1
     * relCode 是分销商ids中的一个
     * relNo 是空的
     */
    List<UserDTO> getEnableUserWithPartnerIds(List<String> partnerIds);

    /**
     * 根据条件（登录名、昵称 模糊查询）获取系统用户列表
     */
    List<UserDTO> getUserList(UserListReq req);

    /**
     * 更新系统用户 关联店员信息
     * rel_no  存 par_partner_staff表的主键staff_id
     * @param req
     * @return
     */
    int updateUserWithShopStaff(UserUpdateWithShopStaffReq req);

    /**
     * 新增系统用户 信息
     * @param req
     * @return 新增记录的userId
     */
//    UserDTO addUser(UserAddReq req);
    ResultVO<UserDTO> addUser(UserAddReq req);

    /**
     * 根据条件（精确）查找单个用户
     * @param req
     * @return
     */
    UserDTO getUser(UserGetReq req);

    /**
     * 根据userId查找用户详情(跨表）
     * @param userId
     * @return
     */
    ResultVO<UserDetailDTO> getUserDetailByUserId(String userId);

    /**
     * 根据条件userId查找单个用户
     * @param useId
     * @return
     */
    UserDTO getUserByUserId(String useId);

    /**
     * 设置用户状态
     * @param req
     * @return
     */
    int setUserStatus(UserSetStatusReq req);

    /**
     * 更新用户密码
     * @param req
     * @return
     */
    ResultVO<Integer> updatePassword(UserUpdatePasswordReq req);

    /**
     * 编辑系统用户信息
     * @param req
     * @return
     */
    ResultVO<Integer> editUser(UserEditReq req);

    /**
     * 获取用户列表
     * @param req
     * @return
     */
    Page<UserDTO> pageUser(UserPageReq req);

    /**
     * 重置用户密码
     * @param req
     * @return
     */
    ResultVO<Integer> resetPassword(UserResetPasswordReq req);


    /**  对外提供的服务 star **/

    /**
     * 根据条件查询用户信息列表
     * @param roleId  角色ID
     * @param orgId  组织ID
     * @return
     */
    ResultVO<List<UserDTO>> listUserByCondition(String roleId, String orgId);


    /**  对外提供的服务 end  **/

}