package com.iwhalecloud.retail.system.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.OrganizationDTO;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.UserDetailDTO;
import com.iwhalecloud.retail.system.dto.request.*;
import com.iwhalecloud.retail.system.dto.response.UserLoginResp;
import com.iwhalecloud.retail.system.entity.CommonRegion;
import com.iwhalecloud.retail.system.entity.User;
import com.iwhalecloud.retail.system.manager.CommonRegionManager;
import com.iwhalecloud.retail.system.manager.OrganizationManager;
import com.iwhalecloud.retail.system.manager.UserManager;
import com.iwhalecloud.retail.system.service.UserService;
import com.twmacinta.util.MD5;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserManager userManager;

    @Autowired
    private CommonRegionManager commonRegionManager;

    @Autowired
    private OrganizationManager organizationManager;

    @Reference
    private MerchantService merchantService;

    @Value("${retailer.login}")
    private String retailerLogin;


    @Override
    public UserLoginResp login(UserLoginReq req) {
        log.info("UserServiceImpl.login 入参：UserLoginReq-{}", JSON.toJSON(req));

        // 错误统一提示
        String errorMsg = "用户名或密码错误";

        UserLoginResp resp = new UserLoginResp();
        //1、根据登录账号 取用户信息
//        UserGetReq userGetReq = new UserGetReq();
//        userGetReq.setLoginName(req.getLoginName());
        User user = userManager.login(req.getLoginName());
        //2、是否存在
        if(user == null){
//            resp.setErrorMessage("用户不存在，请确认输入账号是否正确");
            resp.setErrorMessage(errorMsg);
            log.info("UserServiceImpl.login 出参：UserLoginResp-{}", JSON.toJSON(resp));
            return resp;
        }

        //3、判断用户登陆平台是否正确
        Boolean isAdminType = this.isAdminType(user.getUserFounder());
        if(isAdminType && SystemConst.PlatformFlag.TRADE.getCode().equals(req.getPlatformFlag())){
            resp.setErrorMessage("管理人员只能登陆管理平台！");
            log.info("UserServiceImpl.login 出参：UserLoginResp-{}", JSON.toJSON(resp));
            return resp;
        }
        if(!isAdminType && SystemConst.PlatformFlag.ADMIN.getCode().equals(req.getPlatformFlag())){
            resp.setErrorMessage("非管理人员不能登陆管理平台！");
            log.info("UserServiceImpl.login 出参：UserLoginResp-{}", JSON.toJSON(resp));
            return resp;
        }
        String retailerLoginPass = "0";
        if(!retailerLoginPass.equals(retailerLogin) && SystemConst.USER_FOUNDER_3 == user.getUserFounder()){
            resp.setErrorMessage("零售商请通过BSS3.0平台登陆！");
            log.info("UserServiceImpl.login 出参：UserLoginResp-{}", JSON.toJSON(resp));
            return resp;
        }
        //3、判断状态是否被禁用
        if(user.getStatusCd() != SystemConst.USER_STATUS_VALID){
            resp.setErrorMessage("此用户状态为非有效，请联系管理员！");
            log.info("UserServiceImpl.login 出参：UserLoginResp-{}", JSON.toJSON(resp));
            return resp;
        }
        //4、 判断登录失败的次数是否》=5 》=5将此用户冻结 不能登录系统
        if (Objects.nonNull(user.getFailLoginCnt()) && user.getFailLoginCnt() >= SystemConst.MAX_FAIL_LOGIN_COUNT) {
            resp.setErrorMessage("您已经连续" + SystemConst.MAX_FAIL_LOGIN_COUNT
                    + "次密码错误，不可再登录此系统，请联系管理员解冻");
            log.info("UserServiceImpl.login 出参：UserLoginResp-{}", JSON.toJSON(resp));

            // 锁住 用户账号   清空登录失败次数
            // 只更部分字段
            User updateUser = new User();
            updateUser.setUserId(user.getUserId());
            updateUser.setStatusCd(SystemConst.USER_STATUS_LOCK);
            updateUser.setFailLoginCnt(0);
            userManager.updateUser(updateUser);

            return resp;
        }
        //5、 判断登录密码
        if (SystemConst.loginTypeEnum.YHJ.getType().equals(req.getLoginType()) && StringUtils.isEmpty(req.getLoginPwd())
                || !StringUtils.equals(user.getLoginPwd(), new MD5(req.getLoginPwd()).asHex() )) {
            // 密码错误 登录失败次数 failLoginCnt +1
            Integer failLoginCnt = user.getFailLoginCnt() != null ? user.getFailLoginCnt() : 0;
            // 只更部分字段
            User updateUser = new User();
            updateUser.setUserId(user.getUserId());
            updateUser.setFailLoginCnt(failLoginCnt + 1);
            userManager.updateUser(updateUser);

            resp.setErrorMessage(errorMsg);
            log.info("UserServiceImpl.login 出参：UserLoginResp-{}", JSON.toJSON(resp));
            return resp;
        }

        //操作成功后的逻辑，修改当前登录时间，和上次登录时间 ，登录次数1+  将 failLoginCnt 清零
        user.setFailLoginCnt(0);
        Integer successCnt = user.getSuccessLoginCnt() != null ? user.getSuccessLoginCnt() : 0;
        // 登录失败次数大于0 才更新  不用每次成功都更新
        if (Objects.nonNull(user.getFailLoginCnt()) && user.getFailLoginCnt() > 0) {
            User updateUser = new User();
            updateUser.setUserId(user.getUserId());
            updateUser.setSuccessLoginCnt(successCnt + 1);
            updateUser.setFailLoginCnt(0);
            updateUser.setLastLoginTime(user.getCurLoginTime());
            updateUser.setCurLoginTime(new Date());
            userManager.updateUser(updateUser);
        }

        resp.setErrorMessage("登录成功");
        resp.setIsLoginSuccess(true);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        resp.setUserDTO(userDTO);
        log.info("UserServiceImpl.login 出参：UserLoginResp-{}", JSON.toJSON(resp));
        return resp;
    }

    @Override
    public UserLoginResp loginWithoutPwd(UserLoginWithoutPwdReq req) {
        log.info("UserServiceImpl.loginWithoutPwd 入参：UserLoginWithoutPwdReq-{}", JSON.toJSON(req));

        // 错误统一提示
        String errorMsg = "用户名或密码错误";

        UserLoginResp resp = new UserLoginResp();
        //1、根据登录账号 取用户信息
//        UserGetReq userGetReq = new UserGetReq();
//        userGetReq.setLoginName(req.getLoginName());
        User user = userManager.login(req.getLoginName());
        //2、是否存在
        if(user == null){
//            resp.setErrorMessage("用户不存在，请确认输入账号是否正确");
            resp.setErrorMessage(errorMsg);
            log.info("UserServiceImpl.loginWithoutPwd 出参：UserLoginResp-{}", JSON.toJSON(resp));
            return resp;
        }

        //3、判断用户登陆平台是否正确
        Boolean isAdminType = this.isAdminType(user.getUserFounder());
        if(isAdminType && SystemConst.PlatformFlag.TRADE.getCode().equals(req.getPlatformFlag())){
            resp.setErrorMessage("管理人员只能登陆管理平台！");
            log.info("UserServiceImpl.loginWithoutPwd 出参：UserLoginResp-{}", JSON.toJSON(resp));
            return resp;
        }
        if(!isAdminType && SystemConst.PlatformFlag.ADMIN.getCode().equals(req.getPlatformFlag())){
            resp.setErrorMessage("非管理人员不能登陆管理平台！");
            log.info("UserServiceImpl.loginWithoutPwd 出参：UserLoginResp-{}", JSON.toJSON(resp));
            return resp;
        }

        //3、判断状态是否被禁用
        if(user.getStatusCd() != SystemConst.USER_STATUS_VALID){
            resp.setErrorMessage("此用户状态为非有效，请联系管理员！");
            log.info("UserServiceImpl.loginWithoutPwd 出参：UserLoginResp-{}", JSON.toJSON(resp));
            return resp;
        }
        //4、 判断登录失败的次数是否》=5 》=5将此用户冻结 不能登录系统
        if (user.getFailLoginCnt() >= SystemConst.MAX_FAIL_LOGIN_COUNT) {
            resp.setErrorMessage("您已经连续" + SystemConst.MAX_FAIL_LOGIN_COUNT
                    + "次密码错误，不可再登录此系统，请联系管理员解冻");
            log.info("UserServiceImpl.loginWithoutPwd 出参：UserLoginResp-{}", JSON.toJSON(resp));

            // 锁住 用户账号   清空登录失败次数
//            user.setStatusCd(SystemConst.USER_STATUS_LOCK);
//            user.setFailLoginCnt(0);
//            userManager.updateUser(user);
            // 只更部分字段
            User updateUser = new User();
            updateUser.setUserId(user.getUserId());
            updateUser.setStatusCd(SystemConst.USER_STATUS_LOCK);
            updateUser.setFailLoginCnt(0);
            userManager.updateUser(updateUser);

            return resp;
        }

        //操作成功后的逻辑，修改当前登录时间，和上次登录时间 ，登录次数1+  将 failLoginCnt 清零
        user.setFailLoginCnt(0);
        Integer successCnt = user.getSuccessLoginCnt() != null ? user.getSuccessLoginCnt() : 0;
//        user.setSuccessLoginCnt(successCnt + 1);
//        user.setLastLoginTime(user.getCurLoginTime());
//        user.setCurLoginTime(new Date());
//        userManager.updateUser(user);
        // 只更部分字段
        User updateUser = new User();
        updateUser.setUserId(user.getUserId());
        updateUser.setSuccessLoginCnt(successCnt + 1);
        updateUser.setLastLoginTime(user.getCurLoginTime());
        updateUser.setCurLoginTime(new Date());
        userManager.updateUser(updateUser);

        resp.setErrorMessage("登录成功");
        resp.setIsLoginSuccess(true);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        resp.setUserDTO(userDTO);
        log.info("UserServiceImpl.login 出参：UserLoginResp-{}", JSON.toJSON(resp));
        return resp;
    }

    @Override
    public List<UserDTO> getEnableUserWithPartnerIds(List<String> partnerIds) {
        return userManager.getEnableUserWithPartnerIds(partnerIds);
    }

    @Override
    public List<UserDTO> getUserList(UserListReq req){
        log.info("UserServiceImpl.getUserList(), input: UserListReq={} ", JSON.toJSONString(req));
        List<UserDTO> userDTOList = userManager.listUser(req);
        log.info("UserServiceImpl.getUserList(), input: list={} ", JSON.toJSONString(userDTOList));
        return userDTOList;
    }

    @Override
    public int updateUserWithShopStaff(UserUpdateWithShopStaffReq req) {
        return userManager.updateUserWithShopStaff(req);
    }

    @Override
    public ResultVO<UserDTO> addUser(UserAddReq req) {
        log.info("UserServiceImpl.addUser(), 入参UserAddReq={} ", req);
        User user = new User();
        BeanUtils.copyProperties(req, user);
        // 设置一些默认值

        // 密码格式校验
        if (!checkPassword(req.getLoginPwd())) {
            return ResultVO.error("密码格式不对，密码应为包含大小写字母、数字、特殊字符的8到20位字符串");
        }

        user.setLoginPwd(new MD5(user.getLoginPwd()).asHex());
//        user.setStatusCd(SystemConst.USER_STATUS_VALID); //状态
        user.setCreateDate(new Date());
        user.setFailLoginCnt(0);
        user.setSuccessLoginCnt(0);
        UserDTO userDTO = userManager.addAdminUser(user);
        if (userDTO == null) {
            return ResultVO.error("新增用户失败");
        }
        log.info("UserServiceImpl.addUser(), 出参userDTO={} ", userDTO);
        return ResultVO.success(userDTO);
    }

    @Override
    public UserDTO getUserByUserId(String useId) {
        UserDTO userDTO = new UserDTO();
        User user = userManager.getUserByUserId(useId);
        if(user == null){
            return null;
        }
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    @Override
    public UserDTO getUser(UserGetReq req) {
        UserDTO userDTO = new UserDTO();
        User user = userManager.getUser(req);
        if(user == null){
            return null;
        }
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    /**
     * 根据userId查找用户详情(跨表）
     * @param userId
     * @return
     */
    @Override
    public ResultVO<UserDetailDTO> getUserDetailByUserId(String userId) {
        // 原先逻辑
//        UserDetailDTO userDetailDTO = userManager.getUserDetail(userId);
//        if (userDetailDTO != null
//                && !StringUtils.isEmpty(userDetailDTO.getRelCode())) {
//            // 取商家名称
//            MerchantDTO merchantDTO = merchantService.getMerchantById(userDetailDTO.getRelCode()).getResultData();
//            userDetailDTO.setMerchantName(merchantDTO != null ? merchantDTO.getMerchantName() : null);
//        }

        // 改为单表查询
        User user = userManager.getUserByUserId(userId);
        if (Objects.isNull(user)) {
            return ResultVO.successMessage("没有userId为" + userId + "的用户");
        }
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        BeanUtils.copyProperties(user, userDetailDTO);
        if (!StringUtils.isEmpty(userDetailDTO.getRelCode())) {
            // 取lanName  regionName
            userDetailDTO.setLanName(getRegionNameByRegionId(userDetailDTO.getLanId()));
            userDetailDTO.setRegionName(getRegionNameByRegionId(userDetailDTO.getRegionId()));
        }
        if (!StringUtils.isEmpty(userDetailDTO.getRelCode())) {
            // 取组织名称
            OrganizationDTO organizationDTO = organizationManager.getOrganization(userDetailDTO.getOrgId());
            userDetailDTO.setOrgName(organizationDTO != null ? organizationDTO.getOrgName() : null);
        }

        if (!StringUtils.isEmpty(userDetailDTO.getRelCode())) {
            // 取商家名称
            MerchantDTO merchantDTO = merchantService.getMerchantById(userDetailDTO.getRelCode()).getResultData();
            userDetailDTO.setMerchantName(merchantDTO != null ? merchantDTO.getMerchantName() : null);
        }
        return ResultVO.success(userDetailDTO);
    }

    /**
     * 根据regionId获取 regionName
     *
     * @param regionId
     * @return
     */
    private String getRegionNameByRegionId(String regionId) {
        if (StringUtils.isEmpty(regionId)) {
            return "";
        }
        CommonRegion commonRegion = commonRegionManager.getCommonRegionById(regionId);
        if (commonRegion != null) {
            return commonRegion.getRegionName();
        }
        return "";
    }

    @Override
    public int setUserStatus(UserSetStatusReq req) {
        log.info("UserServiceImpl.setUserStatus 入参：UserSetStatusReq-{}", JSON.toJSON(req));
        User user = new User();
        user.setUserId(req.getUserId());
        user.setStatusCd(req.getStatusCd());
        int resp = userManager.updateUser(user);
        log.info("UserServiceImpl.setUserStatus 出参：int-{}", JSON.toJSON(resp));
        return resp;
    }

    /**
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> updatePassword(UserUpdatePasswordReq req) {
        log.info("UserServiceImpl.updatePassword 入参：UserUpdatePasswordReq={}", JSON.toJSON(req));
        if(StringUtils.isEmpty(req.getUserId())){
            return ResultVO.error("用户不存在");
        }
        User user = userManager.getUserByUserId(req.getUserId());
        if(user == null){
            return ResultVO.error("用户不存在");
        }

        // 比对旧密码
        if(!StringUtils.equals(new MD5(req.getOldPassword()).asHex(), user.getLoginPwd())){
            return ResultVO.error("密码错误");
        }

        // 判断密码长度（8--20）、格式（数字字符串组合） 是否符合要求
        if (!checkPassword(req.getNewPassword())) {
            return ResultVO.error("密码格式不对，密码应为包含大小写字母、数字、特殊字符的8到20位字符串");
        }
        Integer changePwdCount = user.getChangePwdCount() == null ? 0 : user.getChangePwdCount();
        changePwdCount += 1;
        user.setChangePwdCount(changePwdCount);
        user.setChangePwdTime(new Date());
        user.setLoginPwd(new MD5(req.getNewPassword()).asHex());
        int result = userManager.updateUser(user);
        log.info("UserServiceImpl.updatePassword 出参：更新密码影响的行数={}", result);
        if (result <= 0) {
            return ResultVO.error("修改密码失败");
        }
        return ResultVO.success(result);
    }

    /**
     * 校验密码  格式：8--20位，包含 大写、小写字母、数字 特殊字符
     * @param password
     * @return
     */
    private boolean checkPassword(String password) {

        // 方法1  直接匹配
        // 密码匹配  包含 大小写  数字  特殊字符  8到20位
//        String pw_test = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,20}$";
//        return = password.matches(pw_test);

        //方法2  单个匹配包含
        // Z = 大写字母  X 小写字母  S = 数字 T = 特殊字符
        String regexZ = ".*[A-Z]+.*";
        String regexX = ".*[a-z]+.*";
        String regexS = ".*\\d+.*";
       String regexT = ".*[`\\-\\=\\[\\];,./~!@#$%^*()_+}{:?]+.*";
//        String regexT = ".*\\W+.*"; // 这个不包含 _ (下划线）校验

        if (password.length() < 8 || password.length() >20) {
            log.info("密码长度不对");
            return false;
        }
        if (!password.matches(regexZ)) {
            log.info("密码不包含大写字母");
            return false;
        }
        if (!password.matches(regexX)) {
            log.info("密码不包含小写字母");
            return false;
        }
        if (!password.matches(regexS)) {
            log.info("密码不包含数字");
            return false;
        }
        if (!password.matches(regexT)) {
            log.info("密码不包含特殊字符");
            return false;
        }
        return true;
    }


    @Override
    public ResultVO<Integer> editUser(UserEditReq req) {
        log.info("UserServiceImpl.editUser 入参：UserEditReq={}", req.toString());
        User user = new User();
        BeanUtils.copyProperties(req, user);
        int result = userManager.updateUser(user);
        log.info("UserServiceImpl.editUser 出参：更新用户信息影响的行数={}", result);
        if (result <= 0) {
            return ResultVO.error("更新用户信息失败");
        }
        return ResultVO.success(result);
    }

    @Override
    public Page<UserDTO> pageUser(UserPageReq req) {
        log.info("UserServiceImpl.pageUser(), input：UserPageReq={}", req.toString());
        Page<UserDTO> page = userManager.pageUser(req);
        log.info("UserServiceImpl.pageUser(), output：Page={}", page);
        return page;
    }

    /**
     * 密码重置
     *
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> resetPassword(UserResetPasswordReq req) {
        log.info("UserServiceImpl.resetPassword req：UserResetPasswordReq={}", JSON.toJSON(req));
        User user = userManager.getUserByUserId(req.getUpdateUserId());
        if (user == null) {
            return ResultVO.error("用户不存在");
        }
        if (!checkPassword(req.getUpdatePassword())) {
            return ResultVO.error("密码校验不通过，格式必须为：8--20位，包含 大写、小写字母、数字 特殊字符");
        }
        user.setLoginPwd(new MD5(req.getUpdatePassword()).asHex());
        int result = userManager.updateUser(user);
        log.info("UserServiceImpl.resetPassword resp：result={}", result);
        if (result <= 0) {
            return ResultVO.error("重置密码失败");
        }
        return ResultVO.success(result);
    }


    /**  对外提供的服务 star **/


    /**
     * 根据条件查询用户信息列表
     * @param roleId  角色ID
     * @param orgId  组织ID
     * @return
     */
    @Override
    public ResultVO<List<UserDTO>> listUserByCondition(String roleId, String orgId) {
        if (StringUtils.isEmpty(roleId) && StringUtils.isEmpty(orgId)) {
            // 同时为空  返回空列表
            return ResultVO.success(Lists.newArrayList());
        }
        UserListReq req = new UserListReq();
        req.setRoleId(roleId);
        req.setOrgId(orgId);
        return ResultVO.success(userManager.listUser(req));
    }


    /**  对外提供的服务 end  **/


    /**
     * 是否超级管理员、终端公司管理人员、省公司市场部管理人员
     * @return
     */
    private boolean isAdminType(Integer userFounder) {
        List<Integer> list = Lists.newArrayList(
                SystemConst.USER_FOUNDER_0,
                SystemConst.USER_FOUNDER_1,
                SystemConst.USER_FOUNDER_12,
                SystemConst.USER_FOUNDER_24,
                SystemConst.USER_FOUNDER_2,
                SystemConst.USER_FOUNDER_9
        );
        return list.contains(userFounder);
    }
}