package com.iwhalecloud.retail.system.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.req.FactoryMerchantSaveReq;
import com.iwhalecloud.retail.partner.dto.req.SupplierResistReq;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.system.common.DateUtils;
import com.iwhalecloud.retail.system.common.SysUserLoginConst;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.*;
import com.iwhalecloud.retail.system.dto.request.*;
import com.iwhalecloud.retail.system.dto.response.UserLoginResp;
import com.iwhalecloud.retail.system.entity.CommonRegion;
import com.iwhalecloud.retail.system.entity.User;
import com.iwhalecloud.retail.system.manager.CommonRegionManager;
import com.iwhalecloud.retail.system.manager.OrganizationManager;
import com.iwhalecloud.retail.system.manager.UserManager;
import com.iwhalecloud.retail.system.service.RoleService;
import com.iwhalecloud.retail.system.service.ZopMessageService;
import com.iwhalecloud.retail.system.service.UserRoleService;
import com.iwhalecloud.retail.system.service.UserService;
import com.iwhalecloud.retail.system.utils.SysUserCacheUtils;
import com.twmacinta.util.MD5;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.transaction.annotation.Propagation;

import java.util.Calendar;
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

    @Autowired
    private SysUserCacheUtils sysUserCacheUtils;

    @Reference
    private MerchantService merchantService;


    @Value("${retailer.login}")
    private String retailerLogin;

    @Reference
    UserRoleService userRoleService;

    @Reference
    RoleService roleService;

    @Autowired
    ZopMessageService zopMessageService;

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
        if(SystemConst.loginTypeEnum.YHJ.getType().equals(req.getLoginType())){
            if ( StringUtils.isEmpty(req.getLoginPwd())
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
        }
        MerchantDTO merchantDTO = null;
        if(StringUtils.isNotBlank(user.getRelCode())){
          ResultVO merchantRt = merchantService.getMerchantById(user.getRelNo());
          if(merchantRt.isSuccess()) merchantDTO = (MerchantDTO) merchantRt.getResultData();
        }
    /*    //6 管理平台账户超过90天未登入强制要求修改密码
        if(req.getPlatformFlag().equals(SysUserLoginConst.platformFlag.MANAGEMENT_PLATFORM.getPlaformCode())){
            int intervalDate = DateUtils.differentDays(user.getLastLoginTime(),user.getCurLoginTime());
            if(intervalDate >= 90 ){
                resp.setFailCode(SysUserLoginConst.loginFail_TRADING.NEED_RESETPASSWD.getCode());
                resp.setErrorMessage(SysUserLoginConst.loginFail_TRADING.NEED_RESETPASSWD.getMsg());
                return resp;
            }
        }*/
        //超过九十天强制修改密码
        if(user.getLastLoginTime() == null)  user.setLastLoginTime(new Date());
        if(user.getCurLoginTime() == null)  user.setCurLoginTime(new Date());
        int intervalDate = DateUtils.differentDays(user.getLastLoginTime(),user.getCurLoginTime());
        if(intervalDate >= 90 ){
            resp.setFailCode(SysUserLoginConst.loginFail_TRADING.NEED_RESETPASSWD.getCode());
            resp.setErrorMessage(SysUserLoginConst.loginFail_TRADING.NEED_RESETPASSWD.getMsg());
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
            resp.setUserDTO(userDTO);
            return resp;
        }
        //操作成功后的逻辑，修改当前登录时间，和上次登录时间 ，登录次数1+  将 failLoginCnt 清零
        user.setFailLoginCnt(0);
        Integer successCnt = user.getSuccessLoginCnt() != null ? user.getSuccessLoginCnt() : 0;
        // 登录失败次数大于0 才更新  不用每次成功都更新
/*        if (Objects.nonNull(user.getFailLoginCnt()) && user.getFailLoginCnt() > 0) {
            User updateUser = new User();
            updateUser.setUserId(user.getUserId());
            updateUser.setSuccessLoginCnt(successCnt + 1);
            updateUser.setFailLoginCnt(0);
            updateUser.setLastLoginTime(user.getCurLoginTime());
            updateUser.setCurLoginTime(new Date());
            userManager.updateUser(updateUser);
        }*/
        //先更新user表的登入时间在更新缓存
        User updateUser = new User();
        updateUser.setUserId(user.getUserId());
        updateUser.setSuccessLoginCnt(successCnt + 1);
        updateUser.setFailLoginCnt(0);
        updateUser.setLastLoginTime(user.getCurLoginTime());
        updateUser.setCurLoginTime(new Date());
        userManager.updateUser(updateUser);
        sysUserCacheUtils.put(user.getUserId(),user);

        resp.setErrorMessage("登录成功");
        resp.setFailCode(0);
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
        if (Objects.nonNull(user.getFailLoginCnt()) && user.getFailLoginCnt() >= SystemConst.MAX_FAIL_LOGIN_COUNT) {
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
            sysUserCacheUtils.put(user.getUserId(),user);
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

        // 检查是否存在同名账号
        UserGetReq userGetReq = new UserGetReq();
        userGetReq.setLoginName(req.getLoginName());
        UserDTO userDTO = getUser(userGetReq);
        if (userDTO != null) {
            return ResultVO.error("该账号已经存在，请换一个账号名");
        }

        // 密码格式校验(改成前端验证密码合法性)
//        if (!checkPassword(req.getLoginPwd())) {
//            return ResultVO.error("密码格式不对，密码应为包含大小写字母、数字、特殊字符的8到20位字符串");
//        }
        /**前端加密后传给后台**/
//        user.setLoginPwd(new MD5(user.getLoginPwd()).asHex());
//        user.setStatusCd(SystemConst.USER_STATUS_VALID); //状态
        user.setCreateDate(new Date());
        user.setFailLoginCnt(0);
        user.setSuccessLoginCnt(0);
        userDTO = userManager.addAdminUser(user);
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
        updateLoginTime(user);
        return ResultVO.success(result);
    }

    private void updateLoginTime(User user) {
        User updateUser = new User();
        updateUser.setUserId(user.getUserId());
        updateUser.setFailLoginCnt(0);
        updateUser.setLastLoginTime(user.getCurLoginTime());
        updateUser.setCurLoginTime(new Date());
        userManager.updateUser(updateUser);
        sysUserCacheUtils.put(user.getUserId(),user);
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

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultVO registLandSupplier(UserRegisterReq resistReq) {
        ResultVO codeRt = zopMessageService.checkVerifyCode(resistReq.getPhoneNo(),resistReq.getCode());
        if(!codeRt.isSuccess())return codeRt;
        //add user and get id into req
        SupplierResistReq req = new SupplierResistReq();
        BeanUtils.copyProperties(resistReq,req);
        UserAddReq userAddReq = new UserAddReq();
        BeanUtils.copyProperties(req,userAddReq);
        ResultVO<UserDTO> resultVO = addUser(userAddReq);
        if(!resultVO.isSuccess())return resultVO;
        String userId = resultVO.getResultData().getUserId();
        req.setUserId(userId);
        log.info("userId" + userId);
        // use remote service
        if(!resultVO.isSuccess()) return ResultVO.error(resultVO.getResultMsg());
        ResultVO rt = merchantService.registLandSupplier(req);
        log.info(rt.getResultCode()+" ---" + rt.getResultMsg());
        if(!rt.isSuccess())
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.info("地包商注册失败");
            return ResultVO.error("地包商注册失败");
        }
        return ResultVO.success();
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultVO registProvinceSupplier(UserRegisterReq resistReq) {
        //verifyCode
        ResultVO codeRt = zopMessageService.checkVerifyCode(resistReq.getPhoneNo(),resistReq.getCode());
        if(!codeRt.isSuccess())return codeRt;
        //add user and get id into req
        SupplierResistReq req = new SupplierResistReq();
        BeanUtils.copyProperties(resistReq,req);
        UserAddReq userAddReq = new UserAddReq();
        BeanUtils.copyProperties(req,userAddReq);
        ResultVO<UserDTO> resultVO = addUser(userAddReq);
        if(!resultVO.isSuccess())return resultVO;
        String userId = resultVO.getResultData().getUserId();
        req.setUserId(userId);
        log.info("userId" + userId);
        if(!resultVO.isSuccess()) return ResultVO.error(resultVO.getResultMsg());
        ResultVO rt = merchantService.registProvinceSupplier(req);
        if(!rt.isSuccess())
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.info("省包商注册失败");
            return ResultVO.error("省包商注册失败");
        }
        return ResultVO.success();
    }


    /**
     * 更改系统用户状态
     * @param userId
     * @param state
     * @return
     */
    @Override
    public ResultVO UpSysUserState(String userId, int state) {
        User user = new User();
        user.setUserId(userId);
        user.setStatusCd(state);
        if(userManager.updateUser(user)==1)return ResultVO.success();
        return ResultVO.error();
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

    /**
     * 自建厂商
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO registerFactoryMerchant(UserFactoryMerchantReq req) {
        if (!zopMessageService.checkVerifyCode(req.getPhoneNo(), req.getCode()).isSuccess()){
            return ResultVO.error("短信验证码不一致");
        }
        //注册用户
        UserAddReq userAddReq = new UserAddReq();
        BeanUtils.copyProperties(req, userAddReq);
        //默认禁用字段
        userAddReq.setStatusCd(SystemConst.USER_STATUS_INVALID);
        //用户类型为厂商
        userAddReq.setUserFounder(SystemConst.USER_FOUNDER_8);
        ResultVO<UserDTO> addUserResultVO = addUser(userAddReq);
        UserDTO userDTO = addUserResultVO.getResultData();
        if (!addUserResultVO.isSuccess() || userDTO == null) {
            return addUserResultVO;
        }
        //组装厂商信息
        req.setUserId(userDTO.getUserId());
        FactoryMerchantSaveReq factoryMerchantSaveReq=new FactoryMerchantSaveReq();
        BeanUtils.copyProperties(req, factoryMerchantSaveReq);
        factoryMerchantSaveReq.setCreateStaff(userDTO.getUserId());
        factoryMerchantSaveReq.setCreateStaffName(userDTO.getUserName());
        //调用自注册服务
        ResultVO<String> merchantResult = merchantService.registerFactoryMerchant(factoryMerchantSaveReq);
        if (!merchantResult.isSuccess() || null == merchantResult.getResultData()) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return merchantResult;
        } else {
            //修改用户的关联信息
            String merchantId = merchantResult.getResultData();
            User user = new User();
            BeanUtils.copyProperties(userDTO, user);
            user.setRelCode(merchantId);
            userManager.updateUser(user);
        }
        return ResultVO.success();
    }


    @Override
    public ResultVO<UserDTO> addSysUser(UserDTO loginUser,UserSaveReq req) {
        // 添加账号信息
        ResultVO<UserDTO> addUserResultVO=initUser(req);
        UserDTO userDTO = addUserResultVO.getResultData();
        if (!addUserResultVO.isSuccess() || userDTO == null) {
            return addUserResultVO;
        }
        // 判断是否需要增加角色关联
        addUserRole(req,userDTO,loginUser);
        return ResultVO.success(userDTO);
    }

    /**
     * 初始化用户信息
     * @param req
     * @return
     */
    private  ResultVO<UserDTO> initUser(Object req){
        UserAddReq userAddReq = new UserAddReq();
        BeanUtils.copyProperties(req, userAddReq);
        ResultVO<UserDTO> addUserResultVO = addUser(userAddReq);
        return addUserResultVO;
    }

    /**
     *  是否新增权限
     * @param req
     * @param userDTO
     * @param loginUser
     */
    private void addUserRole(UserSaveReq req,UserDTO userDTO,UserDTO loginUser) {
        if (!CollectionUtils.isEmpty(req.getRoleIdList())) {
            for (String roleId : req.getRoleIdList()) {
                // 获取角色
                RoleGetReq roleGetReq = new RoleGetReq();
                roleGetReq.setRoleId(roleId);
                RoleDTO roleDTO = roleService.getRole(roleGetReq);
                if (roleDTO == null) {
                    continue;
                }
                // 增加关联关系
                UserRoleDTO userRoleDTO = new UserRoleDTO();
                userRoleDTO.setRoleId(roleId);
                userRoleDTO.setRoleName(roleDTO.getRoleName());
                userRoleDTO.setUserId(userDTO.getUserId());
                userRoleDTO.setUserName(userDTO.getLoginName());
                userRoleDTO.setCreateStaff(loginUser.getUserId());
                userRoleService.saveUserRole(userRoleDTO);
            }
        }
    }
}