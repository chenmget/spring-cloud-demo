package com.iwhalecloud.retail.web.interceptor;

import com.alibaba.dubbo.config.annotation.Reference;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.iwhalecloud.retail.member.dto.MemberDTO;
import com.iwhalecloud.retail.member.dto.request.MemberGetReq;
import com.iwhalecloud.retail.member.service.MemberService;
import com.iwhalecloud.retail.partner.dto.BusinessEntityDTO;
import com.iwhalecloud.retail.partner.dto.req.BusinessEntityGetReq;
import com.iwhalecloud.retail.partner.service.*;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.service.UserRoleService;
import com.iwhalecloud.retail.system.service.UserService;
import com.iwhalecloud.retail.web.annotation.PassToken;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.consts.UserType;
import com.iwhalecloud.retail.web.consts.WebConst;
import com.iwhalecloud.retail.web.dto.UserOtherMsgDTO;
import com.iwhalecloud.retail.web.exception.UserNotLoginException;
import com.twmacinta.util.MD5;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Value("${jwt.secret}")
    private String SECRET;

    @Reference
    private UserService userService;

    @Reference
    private MemberService memberService;

    @Reference
    private PartnerStaffService partnerStaffService;

    @Reference
    private PartnerShopService partnerShopService;

    @Reference
    private SupplierService supplierService;

    @Reference
    private BusinessEntityService businessEntityService;

    @Reference
    private MerchantService merchantService;

    @Reference
    UserRoleService userRoleService;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {

        //每次请求清除线程变量中的用户信息
        MemberContext.removeMemberSession();
        UserContext.removeUserSession();


        // 从 session中获取，自动登录的时候会重置session中的 token
        String token = (String)httpServletRequest.getSession().getAttribute(WebConst.SESSION_TOKEN);
        log.info("session token = {}",token);
        log.info("----登录校验sessionId-->  " + httpServletRequest.getSession().getId());
        // 如果http请求头没有token则尝试从session中获取
        if (StringUtils.isEmpty(token)) {
        	token = httpServletRequest.getHeader("token");
        	log.info("header token = {}",token);
        }

        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }

        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
        	log.info("校验token = {}",token);
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null || token.equals("")) {
                    throw new UserNotLoginException("token为空，无效token，请重新登录");
                }

                String id = "";
                String sessionId = "";
                String type = "";
                try {
                    Map<String, Claim> claims = JWT.decode(token).getClaims();
                    id = claims.get("id").asString();
                    sessionId = claims.get("sessionId").asString();
                    type = claims.get("type").asString();
                    int exp = claims.get("exp").asInt();
                    int iat = claims.get("iat").asInt();
                    String selfToken = claims.get("selfToken").asString();
                    String hash = "id="+id+"&sessionId="+sessionId+"&secret="+SECRET+"&exp="+String.valueOf(exp)+"&iat="+String.valueOf(iat);
                    MD5 md5 = new MD5();
                    md5.Update(hash, null);
                    if(!selfToken.equals(md5.asHex())){
                        throw new UserNotLoginException("校验不通过，无效token，请重新登录");
                    }
                } catch (Exception j) {
                    throw new UserNotLoginException("token异常，请重新登录");
                }

                if (id == null) {
                    throw new UserNotLoginException("非法数据，请重新登录");
                }

                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                	e.printStackTrace();
                    throw new UserNotLoginException("token失效，请重新登录");
                }

                //将id放入线程变量
                if(type.equals(UserType.MEMBER.toString())){
                    MemberDTO memberDTO = (MemberDTO) httpServletRequest.getSession().getAttribute(WebConst.SESSION_MEMBER);
                    if(memberDTO == null){
                        // web 重启后  从session 取到的值可能是空
                        MemberGetReq req = new MemberGetReq();
                        req.setMemberId(id);
                        memberDTO = memberService.getMember(req).getResultData();
                    }
                    // 保存会员信息
                    MemberContext.setMember(memberDTO);
                    MemberContext.setMemberId(id);
                    MemberContext.setUserSessionId(sessionId);
                }else if(type.equals(UserType.USER.toString())){
                    UserDTO userDTO = (UserDTO) httpServletRequest.getSession().getAttribute(WebConst.SESSION_USER);
                    UserOtherMsgDTO otherMsgDTO = (UserOtherMsgDTO) httpServletRequest.getSession().getAttribute(WebConst.SESSION_USER_OTHER_MSG);

//                    log.info("token 里面的用户ID= {}", id);
//                    if (userDTO != null) {
//                        log.info("session里面的用户信息的用户ID=  {}", userDTO.getUserId());
//                    }

                    if(userDTO == null || !StringUtils.equals(id, userDTO.getUserId())){
//                        log.info("session里面的用户信息为空，或者 toke里面的用户ID和session里面的用户ID 不一样，重新获取用户信息");
                        userDTO = userService.getUserByUserId(id);
                        if(userDTO != null){
                            otherMsgDTO = saveUserOtherMsg(userDTO);
                        }
                    }
//                    if(otherMsgDTO == null
//                            && userDTO != null){
//                        otherMsgDTO = saveUserOtherMsg(userDTO);
//                    }
                    // 保存用户信息
                    UserContext.setUser(userDTO);
                    UserContext.setUserId(id);
                    UserContext.setSessionId(sessionId);
                    UserContext.setUserOtherMsg(otherMsgDTO);
                }

                return true;
            }
        }
        return true;
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

//        if(userDTO.getUserFounder() == SystemConst.USER_FOUNDER_3
//                || userDTO.getUserFounder() == SystemConst.USER_FOUNDER_6) {
//
//            // 如果founder=3或6 去获取厅店信息
//            // 获取厅店
//            // 获取店员信息
//            if(!org.apache.commons.lang.StringUtils.isEmpty(userDTO.getRelNo())){
//                PartnerShopDTO partnerShopDTO = null;
//                PartnerStaffDTO partnerStaffDTO = partnerStaffService.getPartnerStaff(userDTO.getRelNo());
//                // 获取店铺信息
//                if(partnerStaffDTO != null){
//                    partnerShopDTO = partnerShopService.getPartnerShop(partnerStaffDTO.getPartnerShopId());
//                }
//                userOtherMsgDTO.setPartnerShop(partnerShopDTO);
//            }
//        } else if(userDTO.getUserFounder() == SystemConst.USER_FOUNDER_4
//                || userDTO.getUserFounder() == SystemConst.USER_FOUNDER_5) {
//
//            // 如果founder=4或5 去获取供应商信息
//            if(!org.apache.commons.lang.StringUtils.isEmpty(userDTO.getRelCode())){
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
            if(!org.apache.commons.lang.StringUtils.isEmpty(userDTO.getRelCode())){
                BusinessEntityGetReq req = new BusinessEntityGetReq();
                req.setBusinessEntityId(userDTO.getRelCode());
                BusinessEntityDTO businessEntityDTO = businessEntityService.getBusinessEntity(req).getResultData();
                userOtherMsgDTO.setBusinessEntity(businessEntityDTO);
            }

        }

        userOtherMsgDTO.setUser(userDTO);
        return userOtherMsgDTO;
    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
