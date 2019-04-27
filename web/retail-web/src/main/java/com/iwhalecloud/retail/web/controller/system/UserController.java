package com.iwhalecloud.retail.web.controller.system;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.BusinessEntityDTO;
import com.iwhalecloud.retail.partner.dto.req.BusinessEntityGetReq;
import com.iwhalecloud.retail.partner.service.*;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.*;
import com.iwhalecloud.retail.system.dto.request.*;
import com.iwhalecloud.retail.system.dto.response.UserLoginResp;
import com.iwhalecloud.retail.system.service.*;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.consts.WebConst;
import com.iwhalecloud.retail.web.controller.BaseController;
import com.iwhalecloud.retail.web.controller.system.request.AddUserReq;
import com.iwhalecloud.retail.web.controller.system.request.EditUserReq;
import com.iwhalecloud.retail.web.controller.system.response.GetUserDetailResp;
import com.iwhalecloud.retail.web.controller.system.response.LoginResp;
import com.iwhalecloud.retail.web.dto.UserOtherMsgDTO;
import com.iwhalecloud.retail.web.exception.UserNotLoginException;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import com.iwhalecloud.retail.web.utils.AESUtils;
import com.iwhalecloud.retail.web.utils.JWTTokenUtil;
import com.iwhalecloud.retail.web.utils.ZopClientUtil;
import com.twmacinta.util.MD5;
import com.ztesoft.zop.common.message.ResponseResult;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/user")
@Api(value="用户模块：登录、增、改、查等", tags={"用户模块：登录、增、改、查等"})
public class UserController extends BaseController {

    @Value("${jwt.secret}")
    private String SECRET;

    @Reference
    private PartnerStaffService partnerStaffService;

    @Reference
    private PartnerService partnerService;

    @Reference
    private PartnerShopService partnerShopService;

    @Reference
    private SupplierService supplierService;

    @Reference
    private BusinessEntityService businessEntityService;

    @Reference
    private UserService userService;

    @Reference
    private MerchantService merchantService;

    @Reference
    OrganizationService organizationService;

    @Reference
    MenuService menuService;

    @Reference
    RoleService roleService;

    @Reference
    RoleMenuService roleMenuService;

    @Reference
    UserRoleService userRoleService;

    @Reference
    ConfigInfoService configInfoService;

    /**
     * 云货架
     */
    public static final String YHJ_CODE = "yhj";

    /**
     * 统一门户
     */
    public static final String PORTAL_CODE = "portal";


/*******   登录部分 start ********/


    /**
     * 系统用户登陆
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "系统员工登陆", notes = "传入UserLoginReq对象，进行系统用户登陆操作")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultVO<LoginResp> userLogin(HttpServletRequest request, @RequestBody @ApiParam(value = "UserLoginReq", required = true) UserLoginReq req) throws Exception {

        // 先密码还原成普通字符串
        req.setLoginPwd(decodePassword(req.getLoginPwd()));

        ResultVO resultVO = new ResultVO();
        String loginType = req.getLoginType();
        if (PORTAL_CODE.equals(loginType)) {
            String loginName = req.getLoginName();
            String loginPwd = req.getLoginPwd();
            Map<String, String> params = new HashMap<>();
            //入参密码加密
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            String loginPwdNew = base64en.encode(md5.digest(loginPwd.getBytes("utf-8")));
            params.put("sign", "");
            params.put("ip_addr", "127.0.0.1");
            params.put("user_code", loginName);
            params.put("req_time", "");
            params.put("password", loginPwdNew);
            params.put("transaction_id", "");
            params.put("version", "1.0");
            //查询能开地址配置信息
            ConfigInfoDTO configInfoDTO = configInfoService.getConfigInfoById("zop_address");
            if(Objects.isNull(configInfoDTO)){
                return ResultVO.error("未查询到能开地址配置信息");
            }
            String zopAddress = configInfoDTO.getCfValue();
            //调用能开提供的"渠道登录鉴权"能力
            ResponseResult responseResult = ZopClientUtil.callRest("ODA1NzM5Zjg1ZDVmNDBiNGVjYzVkNzVmOGJmZTRlYmM=", zopAddress, "chk.auth.ChkChannelLogin", "1.0", params);
            String resCode = "00000";
            String resultCodeCom = "0";
            if (resCode.equals(responseResult.getRes_code())) {
                Object result = responseResult.getResult();
                JSONObject jsonObject = JSON.parseObject(String.valueOf(result));
                JSONObject contractRoot = jsonObject.getJSONObject("contractRoot");
                JSONObject svcCont = contractRoot.getJSONObject("svcCont");
                String resultCode = svcCont.getString("resultCode");
                String resultMsg = svcCont.getString("resultMsg");
                JSONObject resultObject = svcCont.getJSONObject("resultObject");
                JSONArray ruleEvents = resultObject.getJSONArray("ruleEvents");
                String ruleMsg = "";
                for (int i = 0; i < 1; i++) {
                    JSONObject job = ruleEvents.getJSONObject(i);
                    ruleMsg = String.valueOf(job.get("ruleMsg"));
                }
                if (!resultCodeCom.equals(resultCode)) {
                    resultVO.setResultCode(resultCode);
                    resultVO.setResultMsg(resultMsg + ":" + ruleMsg);
                    return resultVO;
                }
            } else {
                resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
                resultVO.setResultMsg(responseResult.getRes_message());
                return resultVO;
            }
        }
        UserLoginResp resp = userService.login(req);

        // 失败 返回错误信息
        if (!resp.getIsLoginSuccess() || resp.getUserDTO() == null) {
            return failResultVO(resp.getErrorMessage());
        }

        request.getSession().invalidate();//清空session
        Cookie[] cookies = request.getCookies();
        if (Objects.nonNull(cookies) && cookies.length > 0) {
            Cookie cookie = request.getCookies()[0];//获取cookie
            cookie.setMaxAge(0);//让cookie过期
        }

        // 获取其他信息  并保存
        UserOtherMsgDTO userOtherMsgDTO = saveUserOtherMsg(resp.getUserDTO());

        LoginResp loginResp = new LoginResp();
        loginResp.setAdminUser(resp.getUserDTO());
        loginResp.setUserOtherMsg(userOtherMsgDTO);
        // b2c部分
//        loginResp.setShopInfo(userOtherMsgDTO.getPartnerShop());
        loginResp.setToken(JWTTokenUtil.getUserToken(resp.getUserDTO().getUserId(), request.getSession().getId()));
        loginResp.setUserMenu(getUserMenu(resp.getUserDTO().getUserId()));
        loginResp.setLoginStatusCode(WebConst.loginStatusEnum.HAVE_LOGIN.getCode());
        loginResp.setLoginStatusMsg(WebConst.loginStatusEnum.HAVE_LOGIN.getValue());

        return successResultVO(loginResp);
    }

    /**
     * 把前端传过来的ASE加密后的密码 还原成普通字符串
     * @param base64StrPW
     * @return
     */
    private String decodePassword(String base64StrPW) {
        // 1、先获取密钥
        ConfigInfoDTO configInfoDTO = configInfoService.getConfigInfoById(WebConst.AES_KEY_CONFIG_ID);
        if (Objects.isNull(configInfoDTO)) {
            return null;
        }
        String key = configInfoDTO.getCfValue();
        // 2、解密
        return AESUtils.decrypt(base64StrPW, key);
    }



    /**
     * 系统员工免密登陆
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "系统员工免密登陆", notes = "传入UserLoginReq对象，进行系统用户登陆操作")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/loginWithoutPwd", method = RequestMethod.POST)
    public ResultVO<LoginResp> userLoginWithoutPwd(HttpServletRequest request, @RequestBody @ApiParam(value = "UserLoginReq", required = true) UserLoginWithoutPwdReq req) {
        UserLoginResp resp = userService.loginWithoutPwd(req);

        // 失败 返回错误信息
        if (!resp.getIsLoginSuccess() || resp.getUserDTO() == null) {
            return failResultVO(resp.getErrorMessage());
        }

        // 获取其他信息  并保存
        UserOtherMsgDTO userOtherMsgDTO = saveUserOtherMsg(resp.getUserDTO());

        LoginResp loginResp = new LoginResp();
        loginResp.setAdminUser(resp.getUserDTO());
        loginResp.setUserOtherMsg(userOtherMsgDTO);
        // b2c部分
//        loginResp.setShopInfo(userOtherMsgDTO.getPartnerShop());
        loginResp.setToken(JWTTokenUtil.getUserToken(resp.getUserDTO().getUserId(), request.getSession().getId()));
        loginResp.setUserMenu(getUserMenu(resp.getUserDTO().getUserId()));
        loginResp.setLoginStatusCode(WebConst.loginStatusEnum.HAVE_LOGIN.getCode());
        loginResp.setLoginStatusMsg(WebConst.loginStatusEnum.HAVE_LOGIN.getValue());


        return successResultVO(loginResp);
    }

    /**
     * 系统用户登出
     * @return
     */
    @ApiOperation(value = "系统员工登出", notes = "")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResultVO userLogout(HttpServletRequest request) {

        //清空session里面的用户信息
        request.getSession().removeAttribute(WebConst.SESSION_TOKEN);
        request.getSession().removeAttribute(WebConst.SESSION_USER_OTHER_MSG);
        request.getSession().removeAttribute(WebConst.SESSION_USER);

        return ResultVO.success();
    }

    /**
     * 根据用户ID  获取用户的菜单
     * @param userId
     * @return
     */
    private List<MenuDTO> getUserMenu(String userId){
        List<MenuDTO> menuList = new ArrayList();
        //1、先找  用户-角色 关联
        List<UserRoleDTO> userRoleDTOList = userRoleService.listUserRoleByUserId(userId).getResultData();
        List<String> menuIdList = new ArrayList<>();

        for(UserRoleDTO userRoleDTO : userRoleDTOList){
            //2、找 角色-菜单 关联
            List<RoleMenuDTO> roleMenuDTOList = roleMenuService.listRoleMenuByRoleId(userRoleDTO.getRoleId()).getResultData();
            // 存menuID
            for (RoleMenuDTO roleMenuDTO : roleMenuDTOList){
                menuIdList.add(roleMenuDTO.getMenuId());
            }
        }
        // menuID 去重
        HashSet h = new HashSet(menuIdList);
        menuIdList.clear();
        menuIdList.addAll(h);
        for (String menuId : menuIdList){
            MenuDTO menuDTO = menuService.getMenuByMenuId(menuId).getResultData();
            if(menuDTO != null){
                menuList.add(menuDTO);
            }
        }
        return menuList;
    }

    /**
     * 获取系统用户信息（外层都返回成功，是否登陆在resultData字段里面返回）
     * 不用token拦截啦，这里本身要做token校验啦（方便统一返回）
     * @param request
     * @return
     */
    @ApiOperation(value = "根据token获取系统用户信息", notes = "把token放进请求头部，键值对 token: token字符串 ")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @ApiImplicitParam(name = "platformFlag", value = "平台标识：0交易平台；1管理平台", paramType = "query", required = true, dataType = "String")
    @RequestMapping(value="/getUser",method = RequestMethod.GET)
    @UserLoginToken
    public ResultVO<LoginResp> getUser(HttpServletRequest request, @RequestParam String platformFlag) {
        ResultVO checkUserLoginResult = checkUserLogin(request);

        LoginResp resp = new LoginResp();
        if (!checkUserLoginResult.isSuccess()) {
            // 验证失败返回
            resp.setLoginStatusCode(WebConst.loginStatusEnum.NOT_LOGIN.getCode());
            resp.setLoginStatusMsg(WebConst.loginStatusEnum.NOT_LOGIN.getValue());
            return successResultVO(resp);
        }
        // 获取用户信息
        UserDTO userDTO = UserContext.getUser();
        if (userDTO == null) {
            String userId = (String) checkUserLoginResult.getResultData();
            UserGetReq req = new UserGetReq();
            req.setUserId(userId);
            userDTO = userService.getUser(req);
            UserContext.setUser(userDTO);
        }

        UserOtherMsgDTO userOtherMsgDTO = UserContext.getUserOtherMsg();
        if (userOtherMsgDTO == null) {
            userOtherMsgDTO = saveUserOtherMsg(userDTO);
        }

        Boolean isAdminType = UserContext.isAdminType();
        String tradePlaltform = "0";
        // 平台不对返回
        if (isAdminType && tradePlaltform.equals(platformFlag)) {
            resp.setLoginStatusCode(WebConst.loginStatusEnum.LOGIN_WRONG_PLATFORM.getCode());
            resp.setLoginStatusMsg(WebConst.loginStatusEnum.LOGIN_WRONG_PLATFORM.getValue());
            return successResultVO(resp);
        }else if(!isAdminType && !tradePlaltform.equals(platformFlag)){
            resp.setLoginStatusCode(WebConst.loginStatusEnum.LOGIN_WRONG_PLATFORM.getCode());
            resp.setLoginStatusMsg(WebConst.loginStatusEnum.LOGIN_WRONG_PLATFORM.getValue());
            return successResultVO(resp);
        }


        resp.setAdminUser(userDTO);
        resp.setUserOtherMsg(userOtherMsgDTO);
        // b2c部分
//        resp.setShopInfo(userOtherMsgDTO.getPartnerShop());
        resp.setUserMenu(getUserMenu(userDTO.getUserId()));
        resp.setToken(getToken(request));
        resp.setLoginStatusCode(WebConst.loginStatusEnum.HAVE_LOGIN.getCode());
        resp.setLoginStatusMsg(WebConst.loginStatusEnum.HAVE_LOGIN.getValue());
        return successResultVO(resp);
    }


    /**
     * 取token
     * @param request
     * @return
     */
    private String getToken(HttpServletRequest request){
        // 从 http 请求头中取出 token
        String token = request.getHeader("token");
        // 如果http请求头没有token则尝试从session中获取
        if (StringUtils.isEmpty(token)) {
            token = (String)request.getSession().getAttribute(WebConst.SESSION_TOKEN);
        }
        return token;
    }

    /**
     * 判断用户是否登陆  登陆：返回 用户ID： userId
     * @param request
     * @return
     */
    private ResultVO<String> checkUserLogin(HttpServletRequest request) {
        // 取出  token
        String token = getToken(request);
        // 判空
        if (StringUtils.isEmpty(token)) {
            return ResultVO.errorEnum(ResultCodeEnum.NOT_LOGIN);
        }
        if (token == null || token.equals("")) {
            return ResultVO.errorEnum(ResultCodeEnum.NOT_LOGIN);
        }
        // 执行认证
        String id;
        try {
            Map<String, Claim> claims = JWT.decode(token).getClaims();
            id = claims.get("id").asString();
            if (id == null) {
                return ResultVO.errorEnum(ResultCodeEnum.NOT_LOGIN);
            }
            String sessionId = claims.get("sessionId").asString();
            int exp = claims.get("exp").asInt();
            int iat = claims.get("iat").asInt();
            String selfToken = claims.get("selfToken").asString();

            String hash = "id="+id+"&sessionId="+sessionId+"&secret="+SECRET+"&exp="+String.valueOf(exp)+"&iat="+String.valueOf(iat);
            MD5 md5 = new MD5();
            md5.Update(hash, null);
            if(!selfToken.equals(md5.asHex())){
//            if(!selfToken.equals(MD5Util.compute("id="+id+"&sessionId="+sessionId+"&secret="+SECRET+"&exp="+String.valueOf(exp)+"&iat="+String.valueOf(iat)))){
                return ResultVO.errorEnum(ResultCodeEnum.NOT_LOGIN);
            }

            // 验证 token 有效时间
            if (JWTTokenUtil.isTokenEffect(sessionId)) {
                // 有效  更新token有效时间
                if (!JWTTokenUtil.updateTokenExpireTime(sessionId)) {
                    log.info("更新token 有效时间 失败");
                }
            } else {
                log.info("token失效，请重新登录");
                throw new UserNotLoginException("token失效，请重新登录");
            }
        } catch (Exception ex) {
            log.error("MemberController.getMemberToken Exception token="+token,ex);
            return ResultVO.errorEnum(ResultCodeEnum.NOT_LOGIN);
        }

        return ResultVO.success(id);
    }


    /**
     * 保存用户的 其他信息
     * 同时存到session
     * @param userDTO
     * @return
     */
    private UserOtherMsgDTO saveUserOtherMsg(UserDTO userDTO){

        UserOtherMsgDTO userOtherMsgDTO = new UserOtherMsgDTO();
        // 找  用户-角色 关联
//        List<UserRoleDTO> userRoleDTOList = userRoleService.listUserRoleByUserId(userDTO.getUserId()).getResultData();
//        userOtherMsgDTO.setUserRoleList(userRoleDTOList);

        // 获取商家信息
        userOtherMsgDTO.setMerchant(merchantService.getMerchantById(userDTO.getRelCode()).getResultData());

        // b2c部分
//        if(userDTO.getUserFounder() == SystemConst.USER_FOUNDER_3
//                || userDTO.getUserFounder() == SystemConst.USER_FOUNDER_6) {
//
//            // 如果founder=3或6 去获取厅店信息
//            // 获取厅店
//            // 获取店员信息
//            if(!StringUtils.isEmpty(userDTO.getRelNo())){
//                PartnerShopDTO partnerShopDTO = null;
//                PartnerStaffDTO partnerStaffDTO = partnerStaffService.getPartnerStaff(userDTO.getRelNo());
//                // 获取店铺信息
//                if(partnerStaffDTO != null){
//                    partnerShopDTO = partnerShopService.getPartnerShop(partnerStaffDTO.getPartnerShopId());
//                }
//                userOtherMsgDTO.setPartnerShop(partnerShopDTO);
//            }
//            // 获取分销商信息
////            if(!StringUtils.isEmpty(userDTO.getRelCode())){
////                PartnerDTO partnerDTO = partnerService.getPartnerById(userDTO.getRelCode());
////                userOtherMsgDTO.setPartner(partnerDTO);
////            }
//
//        } else if(userDTO.getUserFounder() == SystemConst.USER_FOUNDER_4
//                || userDTO.getUserFounder() == SystemConst.USER_FOUNDER_5) {
//
//            // 如果founder=4或5 去获取供应商信息
//            if(!StringUtils.isEmpty(userDTO.getRelCode())){
//                SupplierGetReq supplierGetReq = new SupplierGetReq();
//                supplierGetReq.setSupplierId(userDTO.getRelCode());
//                SupplierDTO supplierDTO = supplierService.getSupplier(supplierGetReq).getResultData();
//                userOtherMsgDTO.setSupplier(supplierDTO);
//            }
//
//        } else

            if(Objects.nonNull(userDTO.getUserFounder())
                    && userDTO.getUserFounder() == SystemConst.USER_FOUNDER_7) {

            // 如果founder=7 去获取分销商经营主体信息
            if(!StringUtils.isEmpty(userDTO.getRelCode())){
                BusinessEntityGetReq req = new BusinessEntityGetReq();
                req.setBusinessEntityId(userDTO.getRelCode());
                BusinessEntityDTO businessEntityDTO = businessEntityService.getBusinessEntity(req).getResultData();
                userOtherMsgDTO.setBusinessEntity(businessEntityDTO);
            }

        }

        userOtherMsgDTO.setUser(userDTO);
        UserContext.setUserOtherMsg(userOtherMsgDTO);
        UserContext.setUser(userDTO);
        return userOtherMsgDTO;
    }






/*******   登录部分 end ********/




    /**
     * 获取用户列表
     * @param req
     * @return
     */
    @ApiOperation(value = "获取用户列表", notes = "可以根据用户账号、姓名条件进行筛选查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/getUserPage", method = RequestMethod.POST)
    public ResultVO<Page<UserDTO>> getAdminListWithRole(@RequestBody UserPageReq req) {
        // 判空
        Page<UserDTO> resp = userService.pageUser(req);
        // 设置组织名称
        if(!CollectionUtils.isEmpty(resp.getRecords())){
            for (UserDTO userDTO : resp.getRecords()){
                userDTO.setOrgName(getOrgNameByOrgId(userDTO.getOrgId()));
            }
        }
        return successResultVO(resp);
    }

    /**
     * 获取用户列表
     * @param userId
     * @return
     */
    @ApiOperation(value = "获取用户详情", notes = "可以根据用户ID获取用户详情")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/getUserDetail", method = RequestMethod.GET)
    public ResultVO<GetUserDetailResp> getUserDetail(@RequestParam(value = "userId", required = true) String userId) {
        // 判空
        if(StringUtils.isEmpty(userId)){
            return failResultVO("用户ID不能为空");
        }
        // 用户信息
        UserDTO userDTO = userService.getUserByUserId(userId);
        if (userDTO == null){
            return failResultVO("获取用户详情失败");
        }
        // 获取组织名称
        userDTO.setOrgName(getOrgNameByOrgId(userDTO.getOrgId()));

        // 角色信息
        //1、先找  用户-角色 关联
        List<UserRoleDTO> userRoleDTOList = userRoleService.listUserRoleByUserId(userId).getResultData();

        GetUserDetailResp resp = new GetUserDetailResp();
        resp.setUser(userDTO);
        resp.setUserRoleList(userRoleDTOList);
        return successResultVO(resp);
    }

    /**
     * 获取组织名称
     * @param orgId
     * @return
     */
    private String getOrgNameByOrgId(String orgId){
        if(StringUtils.isEmpty(orgId)){
            return "";
        }
        String orgName = "";
        com.iwhalecloud.retail.dto.ResultVO resultVO =organizationService.getOrganization(orgId);
        if(resultVO.getResultData() != null ){
            orgName =((OrganizationDTO)resultVO.getResultData()).getOrgName();
        }
        return orgName;
    }

    /**
     * 添加用户(先根据userName字段去查询有没有这个字段值的账号，没有才能注册）
     * @param req
     * @return
     */
    @ApiOperation(value = "添加系统员工", notes = "传入UserAddReq对象，进行添加操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultVO<UserDTO> addUser(@RequestBody AddUserReq req) {

        if (StringUtils.isEmpty(req.getLoginName())) {
            return failResultVO("账号不能为空");
        }
        if (StringUtils.isEmpty(req.getLoginPwd())) {
            return failResultVO("密码不能为空");
        }

        UserDTO loginUser = UserContext.getUser();
        if (loginUser == null){
            return failResultVO("用户未登录！");
        }

        // 检查是否存在同名账号
        UserGetReq userGetReq = new UserGetReq();
        userGetReq.setLoginName(req.getLoginName());
        UserDTO userDTO = userService.getUser(userGetReq);
        if(userDTO != null){
            return failResultVO("该账号已经存在，请换一个账号名");
        }
        // 添加账号信息
        UserAddReq userAddReq = new UserAddReq();
        BeanUtils.copyProperties(req, userAddReq);


//        userDTO = userService.addUser(userAddReq);
//        // 失败统一返回
//        if (userDTO == null) {
//            return failResultVO("新增失败!");
//        }
        ResultVO<UserDTO> addUserResultVO = userService.addUser(userAddReq);
        userDTO = addUserResultVO.getResultData();
        // 失败统一返回
        if (!addUserResultVO.isSuccess() || userDTO == null) {
            return addUserResultVO;
        }

        // 判断是否需要增加角色关联
        if(!CollectionUtils.isEmpty(req.getRoleIdList())){
            for (String roleId : req.getRoleIdList()){
                // 获取角色
                RoleGetReq roleGetReq = new RoleGetReq();
                roleGetReq.setRoleId(roleId);
                RoleDTO roleDTO = roleService.getRole(roleGetReq);
                if(roleDTO == null){
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
        return successResultVO(userDTO);
    }

    /**
     * 设置用户状态
     * @param req
     * @return
     */
    @ApiOperation(value = "设置用户状态", notes = "传入UserSetStatusReq对象，进行添加操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/setStatus", method = RequestMethod.POST)
    public ResultVO setAdminStatus(@RequestBody UserSetStatusReq req) {
        if (StringUtils.isEmpty(req.getUserId())) {
            return failResultVO("用户ID不能为空");
        }
        if (req.getStatusCd() == null) {
            return failResultVO("状态不能为空");
        }
        if (req.getStatusCd() != SystemConst.USER_STATUS_INVALID
                && req.getStatusCd() != SystemConst.USER_STATUS_VALID
                && req.getStatusCd() != SystemConst.USER_STATUS_DELETE
                && req.getStatusCd() != SystemConst.USER_STATUS_LOCK
        ) {
            return failResultVO("状态值有误，请确认");
        }
        int result = userService.setUserStatus(req);
        // 失败统一返回
        if (result == 0) {
            if (req.getStatusCd() == SystemConst.USER_STATUS_INVALID ) {
                return failResultVO("禁用用户失败");
            }else if ( req.getStatusCd() == SystemConst.USER_STATUS_VALID ) {
                return failResultVO("启用用户失败");
            }else if (req.getStatusCd() == SystemConst.USER_STATUS_DELETE) {
                return failResultVO("删除用户失败");
            }
            return failResultVO("修改用户状态失败");
        }
        return successResultVO(result);
    }


    /**
     * 设置用户密码
     * @param req
     * @return
     */
    @ApiOperation(value = "设置用户密码", notes = "传入UserSetStatusReq对象，进行添加操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO updateUserPassword(@RequestBody UserUpdatePasswordReq req) {
//        if (StringUtils.isEmpty(req.getUserId())) {
//            return failResultVO("用户ID不能为空");
//        }
        if (StringUtils.isEmpty(req.getOldPassword())) {
            return failResultVO("原密码不能为空");
        }
        if (StringUtils.isEmpty(req.getNewPassword())) {
            return failResultVO("新密码不能为空");
        }
        UserDTO userDTO = UserContext.getUser();
        if(userDTO == null) {
            return failResultVO("用户未登录！");
        }
        req.setUserId(userDTO.getUserId());

        // 先密码还原成普通字符串
        req.setOldPassword(decodePassword(req.getOldPassword()));
        req.setNewPassword(decodePassword(req.getNewPassword()));

        return userService.updatePassword(req);
    }

    /**
     * 编辑用户
     * @para requestDTO
     * @return
     */
    @ApiOperation(value = "编辑系统用户信息", notes = "传入AdminUserEditRequestDTO对象，进行编辑操作")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @Transactional
    @UserLoginToken
    public ResultVO editUser(@RequestBody EditUserReq req) {

        if (StringUtils.isEmpty(req.getUserId())) {
            return failResultVO("用户ID不能为空");
        }
//        if (requestDTO.getRoleIds() == null) {
//            return resultVO(OmsCommonConsts.RESULE_CODE_FAIL, "角色参数缺失", null);
//        }

        UserDTO loginUser = UserContext.getUser();
        if (loginUser == null){
            return failResultVO("用户未登录！");
        }

        // 账号信息
        UserEditReq userEditReq = new UserEditReq();
        BeanUtils.copyProperties(req, userEditReq);
        userEditReq.setUpdateStaff(loginUser.getUserId());
        ResultVO result = userService.editUser(userEditReq);
        // 失败统一返回
        if (!result.isSuccess()) {
            return failResultVO("编辑用户失败!");
        }

        // 获取更新后的  数据
        UserDTO userDTO = userService.getUserByUserId(req.getUserId());
        if (userDTO == null) {
            return failResultVO("编辑用户失败!");
        }

        //先删除已有的 角色关联关系
        userRoleService.deleteUserRoleByUserId(req.getUserId());

        // 判断是否需要增加角色关联
        if(!CollectionUtils.isEmpty(req.getRoleIdList())){
            for (String roleId : req.getRoleIdList()){
                // 获取角色
                RoleGetReq roleGetReq = new RoleGetReq();
                roleGetReq.setRoleId(roleId);
                RoleDTO roleDTO = roleService.getRole(roleGetReq);
                if(roleDTO == null){
                    continue;
                }
                // 增加关联关系
                UserRoleDTO userRoleDTO = new UserRoleDTO();
                userRoleDTO.setRoleId(roleId);
                userRoleDTO.setRoleName(roleDTO.getRoleName());
                userRoleDTO.setUserId(req.getUserId());
                userRoleDTO.setUserName(userDTO.getUserName());
                userRoleDTO.setCreateStaff(loginUser.getUserId());
                userRoleService.saveUserRole(userRoleDTO);
            }
        }
        return ResultVO.success(null);
    }

    /**
     * 测试重定向到另外的一个系统
     *
     * @param response
     * @throws Exception
     */
    @RequestMapping("/ssoLogin")
    public void ssoLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String verifyKey = "YHJ@20190306#zte";
        //3.0门户菜单编码
        String businessCode = "YUN1";
        String managerCode = "YUN2";
        //平台标识
        String platformFlag = "";

        response.setCharacterEncoding("gbk");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");
        String staffCode = request.getParameter("staffCode");
        String bss3SessionId = request.getParameter("bss3SessionId");
        String menuId = request.getParameter("menuId");
        String menuCode = request.getParameter("menuCode");
        String signString = request.getParameter("signString");
        String signTimestamp = request.getParameter("signTimestamp");

        if(businessCode.equals(menuCode)){
            platformFlag = "0";
        }else{
            platformFlag = "1";
        }

        if (StringUtils.isEmpty(action)) {
            out.print("单点登录失败，参数action不能为空 ");
            return;
        }
        if (StringUtils.isEmpty(staffCode)) {
            out.print("单点登录失败，参数staffCode不能为空 ");
            return;
        }
        if (StringUtils.isEmpty(bss3SessionId)) {
            out.print("单点登录失败，参数bss3SessionId不能为空 ");
            return;
        }
        if (StringUtils.isEmpty(menuId)) {
            out.print("单点登录失败，参数menuId不能为空 ");
            return;
        }
        if (StringUtils.isEmpty(signString)) {
            out.print("单点登录失败，参数signString不能为空 ");
            return;
        }
        if (StringUtils.isEmpty(signTimestamp)) {
            out.print("单点登录失败，参数signTimestamp不能为空 ");
            return;
        }
        if (StringUtils.isEmpty(menuCode)) {
            out.print("单点登录失败，参数menuCode不能为空 ");
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("action", action);
        map.put("staffCode", staffCode);
        map.put("bss3SessionId", bss3SessionId);
        map.put("signTimestamp", signTimestamp);
        map.put("menuId", menuId);
        map.put("menuCode", menuCode);

        ConfigInfoDTO configInfoDTO = configInfoService.getConfigInfoById("BUSINESS_PLATFORM");
        ConfigInfoDTO configInfoDTO1 = configInfoService.getConfigInfoById("MANAGER_PLATFORM");
        if (Objects.isNull(configInfoDTO)) {
            out.print("单点登录失败，未查询到交易平台地址配置信息 ");
        }
        String businessPlatform = configInfoDTO.getCfValue();
        if (Objects.isNull(configInfoDTO1)) {
            out.print("单点登录失败，未查询到管理平台地址配置信息 ");
        }
        String managerPlatform = configInfoDTO1.getCfValue();


        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        long result = getDatePoor(df.parse(signTimestamp));
        //时间差相差120秒
        long flag = 120;
        if (0 <= result && result <= flag) {
            String signStringLocal = analysis(map, verifyKey);
            if (signString.equals(signStringLocal)) {
                UserGetReq userGetReq = new UserGetReq();
                userGetReq.setLoginName(staffCode);
                //校验传过来的用户名是否有存在云货架系统
                UserDTO userDTO = userService.getUser(userGetReq);
                if (userDTO != null) {
                    UserLoginWithoutPwdReq req = new UserLoginWithoutPwdReq();
                    req.setLoginName(staffCode);
                    req.setPlatformFlag(platformFlag);
                    req.setLoginType("yhj");
                    //免密登录
                    ResultVO<LoginResp> rv = this.userLoginWithoutPwd(request, req);
                    log.info("UserController userLoginWithoutPwd resp:{}" + JSON.toJSON(rv.getResultData()));
                    if (rv.isSuccess()) {
                        if (businessCode.equals(menuCode)) {
                            Cookie businessCookie = new Cookie("tokenTp", rv.getResultData().getToken());
                            businessCookie.setPath("/");
                            //跳转交易平台
                            response.addCookie(businessCookie);
                            response.sendRedirect(businessPlatform);
                        } else if (managerCode.equals(menuCode)) {
                            Cookie managerCookie = new Cookie("token", rv.getResultData().getToken());
                            managerCookie.setPath("/");
                            //跳转管理平台
                            response.addCookie(managerCookie);
                            response.sendRedirect(managerPlatform);
                        }
                        return;
                    } else {
                        out.print("单点登录失败，"+rv.getResultMsg());
                        return;
                    }
                } else {
                    out.print("单点登录失败，当前工号不存在云货架系统 ");
                    return;
                }
            } else {
                out.print("单点登录失败，加密串值不匹配 ");
                return;
            }
        } else {
            out.print("单点登录失败，时间校验失败 ");
            return;
        }
    }

    /**
     * 参数加密
     *
     * @param params
     * @param verifyKey
     * @return
     */
    private String analysis(HashMap<String, String> params, String verifyKey) {
        //按key排序
        Set<String> keys = params.keySet();
        TreeSet<String> sortKeys = new TreeSet<>();
        sortKeys.addAll(keys);
        Iterator<String> iterator = sortKeys.iterator();
        StringBuilder sbSignValue = new StringBuilder();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = params.get(key);
            if (StringUtils.isBlank(value)) {
                continue;
            }
            sbSignValue.append(key).append("=").append(value).append("&");
        }
        //拼接上
        sbSignValue.append("verifyKey=").append(verifyKey);
        String signValue = sbSignValue.toString();
        log.info("signValue={},verifyKey={}", signValue, verifyKey);
        String signString = DigestUtils.md5Hex(sbSignValue.toString());
        return signString;
    }

    /**
     * 计算时间差
     *
     * @param date
     * @return
     */
    private long getDatePoor(Date date) {
        //获取系统当前时间
        Date nowDate = new Date();
        //计算时间差
        long diff = Math.abs(date.getTime() - nowDate.getTime());
        long seconds = diff / 1000;
        return seconds;
    }

    /**
     * 重置用户密码
     *
     * @return
     */
    @ApiOperation(value = "重置用户密码", notes = "重置用户密码")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO resetPassword() {
        UserEditReq userEditReq = new UserEditReq();
        userEditReq.setUserId(UserContext.getUserId());
        //设置初始密码并加密
        userEditReq.setLoginPwd(new MD5("123").asHex());
        ResultVO<Integer> integerResultVO = userService.editUser((userEditReq));
        if(!integerResultVO.isSuccess()){
            return ResultVO.error("初始化密码失败");
        }
        return ResultVO.successMessage("初始化密码成功");
    }
}
