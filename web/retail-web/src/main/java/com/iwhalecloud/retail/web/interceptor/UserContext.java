package com.iwhalecloud.retail.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.web.consts.WebConst;
import com.iwhalecloud.retail.web.dto.UserOtherMsgDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.List;

@Slf4j
public class UserContext implements Serializable {

    private static final long serialVersionUID = -6634783591580517814L;

    private static ThreadLocal<String> userIdThreadLocal = new ThreadLocal<>();

    private static ThreadLocal<String> sessionIdThreadLocal = new ThreadLocal<>();

    private static ThreadLocal<UserDTO> userThreadLocal = new ThreadLocal<>();

    private static ThreadLocal<UserOtherMsgDTO> userOtherMsgThreadLocal = new ThreadLocal<>();

    public static String getUserId() {
        String userId = userIdThreadLocal.get();
        if (StringUtils.isEmpty(userId)) {
            // 去session里面取
            userId = getUser() != null ? getUser().getUserId() : null;
        }
        return userId;
    }

    public static void setUserId(String userId) {
        userIdThreadLocal.set(userId);
    }

    public static String getSessionId() {
        return sessionIdThreadLocal.get();
    }

    public static void setSessionId(String sessionIdId) {
        sessionIdThreadLocal.set(sessionIdId);
    }

    public static void removeUserSession() {
        userIdThreadLocal.remove();
        sessionIdThreadLocal.remove();
        userThreadLocal.remove();
        userOtherMsgThreadLocal.remove();
    }

    /**
     * 判断用户是否登录
     *
     * @return
     * @author zhong.wenlong 2018-12-25
     */
    public static final Boolean isUserLogin() {
        // 看是否获取可以获取登录用户
        UserDTO userDTO = getUser();
        if (userDTO != null) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前servlet的session对象，不在servlet环境下返回null.
     *
     * @return
     * @author Ji.kai 2018-11-5
     */
    public static final HttpSession getSession() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            log.warn("获取当前servlet的ServletRequestAttributes对象失败，ServletRequestAttributes为空！");
            return null;
        }
        HttpServletRequest req = attrs.getRequest();
        if (req == null) {
            log.warn("获取当前servlet的request对象失败，HttpServletRequest为空！");
            return null;
        }
        HttpSession session = req.getSession(true);
        if (session == null) {
            log.warn("获取当前Session对象失败，HttpSession为空！");
        }
        return session;
    }

    /**
     * 获取当前登录的用户信息,没有登录则返回null
     *
     * @return
     * @author Ji.kai 2018-11-5
     */
    public static final UserDTO getUser() {

        UserDTO userDTO = userThreadLocal.get();
        log.info("userThreadLocal.get() resp={} ", JSON.toJSONString(userDTO));

        //优先从线程变量取，取到就返回
        if (userDTO != null) {
            return userDTO;
        }

        //从httpsession中获取
        HttpSession httpSession = getSession();
        if (httpSession != null) {
            userDTO = (UserDTO) httpSession.getAttribute(WebConst.SESSION_USER);
            log.info("httpSession.getAttribute(WebConst.SESSION_USER) resp={} ", JSON.toJSONString(userDTO));
            return userDTO;
        }

        return null;
    }

    /**
     * 设置登录信息到session中.
     *
     * @param userDTO 登录信息
     * @author Ji.kai 2018-11-5
     */
    public static final void setUser(UserDTO userDTO) {
        HttpSession session = getSession();
        if (session == null) {
            return;
        }
        // 写入session
        session.setAttribute(WebConst.SESSION_USER, userDTO);
        // 设置线程变量
        userThreadLocal.set(userDTO);
        log.info("添加用户信息到session中，userDTO={}", userDTO);
    }


    /**
     * 获取当前登录的用户的 其他信息,没有登录则返回null
     *
     * @return
     * @author zwl 2018-12-25
     */
    public static final UserOtherMsgDTO getUserOtherMsg() {
        UserOtherMsgDTO userOtherMsgDTO = userOtherMsgThreadLocal.get();
        //优先从线程变量取，取到就返回
        if (userOtherMsgDTO != null) {
            return userOtherMsgDTO;
        }
        // 从httpsession中获取
        HttpSession httpSession = getSession();
        if (httpSession != null) {
            userOtherMsgDTO = (UserOtherMsgDTO) httpSession.getAttribute(WebConst.SESSION_USER_OTHER_MSG);
            return userOtherMsgDTO;
        }
        return null;
    }

    /**
     * 设置登录用户的其他信息到session中.
     *
     * @param userOtherMsgDTO 登录用户的其他信息
     * @author zwl 2018-11-5
     */
    public static final void setUserOtherMsg(UserOtherMsgDTO userOtherMsgDTO){
        HttpSession session = getSession();
        if (session == null) {
            return;
        }
        // 写入session
        session.setAttribute(WebConst.SESSION_USER_OTHER_MSG, userOtherMsgDTO);
        //设置线程变量
        userOtherMsgThreadLocal.set(userOtherMsgDTO);
        log.info("添加用户的其他信息到session中，userOtherMsgDTO={}", userOtherMsgDTO);
    }


    /**
     * 获取厅店ID，假如工号的founder不是3或者6，返回null
     *
     * @return
     */
    public static final String getPartnerShopId() {
        UserDTO userDTO = getUser();
        if (userDTO == null) {
            log.warn("获取厅店ID失败，登录工号信息为空");
            return null;
        }
        if (SystemConst.USER_FOUNDER_3 != userDTO.getUserFounder()
                || SystemConst.USER_FOUNDER_6 != userDTO.getUserFounder()) {
            return null;

        }
        return userDTO.getRelCode();

    }


    /**
     * 获取商家ID
     *
     * @return
     */
    public static final String getMerchantId() {
        UserDTO userDTO = getUser();
        if (userDTO == null) {
            return null;
        }
        if (isMerchant()) {
            // 商家ID
            return userDTO.getRelCode();
        }
        return null;
    }


    /**
     * 是否商家：厂商  零售商  供应商
     *
     * @return
     */
    public static boolean isMerchant() {
        UserDTO userDTO = getUser();
        if (userDTO == null) {
            return false;
        }
        List<Integer> list = Lists.newArrayList(
                SystemConst.USER_FOUNDER_3,
                SystemConst.USER_FOUNDER_4,
                SystemConst.USER_FOUNDER_5,
                SystemConst.USER_FOUNDER_8
        );
        return list.contains(userDTO.getUserFounder());
    }

    /**
     * 是否分销商
     *
     * @return
     */
    public static boolean isPartner() {
        UserDTO userDTO = getUser();
        if (userDTO == null) {
            log.warn("判断是否代理商失败，登录工号信息为空");
            return false;
        }

        return SystemConst.USER_FOUNDER_3 == userDTO.getUserFounder() || SystemConst.USER_FOUNDER_6 == userDTO.getUserFounder();
    }

    /**
     * 是否超级管理员、终端公司管理人员、省公司市场部管理人员
     *
     * @return
     */
    public static boolean isAdminType() {
        UserDTO userDTO = getUser();
        if (userDTO == null) {
            return false;
        }
        List<Integer> list = Lists.newArrayList(
                SystemConst.USER_FOUNDER_1,
                SystemConst.USER_FOUNDER_2,
                SystemConst.USER_FOUNDER_9,
                SystemConst.USER_FOUNDER_12,
                SystemConst.USER_FOUNDER_24
        );
        return list.contains(userDTO.getUserFounder());
    }

    /**
     * 是否 地市管理员
     *
     * @return
     */
    public static boolean isCityAdminType() {
        UserDTO userDTO = getUser();
        if (userDTO == null) {
            return false;
        }
        List<Integer> list = Lists.newArrayList(
                SystemConst.USER_FOUNDER_9
        );
        return list.contains(userDTO.getUserFounder());
    }

    /**
     * 是否 厂商类型
     *
     * @return
     */
    public static boolean isManufacturerType() {
        UserDTO userDTO = getUser();
        if (userDTO == null) {
            return false;
        }
        List<Integer> list = Lists.newArrayList(
                SystemConst.USER_FOUNDER_9
        );
        return list.contains(userDTO.getUserFounder());
    }
}
