package com.iwhalecloud.retail.web.interceptor;

import com.iwhalecloud.retail.member.dto.MemberDTO;
import com.iwhalecloud.retail.web.consts.WebConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

//import com.iwhalecloud.retail.oms.consts.OmsConst;

@Slf4j
public class MemberContext implements Serializable {

    private static ThreadLocal<String> memberIdThreadLocal = new ThreadLocal<>();

    private static ThreadLocal<String> userSessionIdThreadLocal = new ThreadLocal<>();

    private static ThreadLocal<MemberDTO> memberThreadLocal = new ThreadLocal<>();

    public static String getMemberId() {
        return memberIdThreadLocal.get();
    }

    public static void setMemberId(String memberId) {
        memberIdThreadLocal.set(memberId);
    }

    public static String getUserSessionId(){
        return userSessionIdThreadLocal.get();
    }

    public static void setUserSessionId(String userSessionId){
        userSessionIdThreadLocal.set(userSessionId);
    }

    public static void removeMemberSession() {
        memberIdThreadLocal.remove();
        userSessionIdThreadLocal.remove();
    }

    /**
     * 获取当前servlet的request对象，不在servlet环境下返回null
     *
     * @return
     * @author Ji.kai 2018-11-5
     */
    public static final HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            return null;
        }
        HttpServletRequest request = attrs.getRequest();
        return request;
    }

    /**
     * 获取当前servlet的request对象，不在servlet环境下返回null.
     *
     * @return
     * @author Ji.kai 2018-11-5
     */
    public static final HttpSession getSession() {
        HttpServletRequest req = getRequest();
        if (req == null) {
            return null;
        }
        HttpSession session = req.getSession();
        return session;
    }

    /**
     * 获取当前登录的会员信息,没有登录则返回null
     * @return
     */
    public static final MemberDTO getMember() {

        MemberDTO memberDTO = memberThreadLocal.get();
        //优先从线程变量取，取到就返回
        if(memberDTO != null){
            return memberDTO;
        }
        //从httpsession中获取
        HttpSession httpSession = getSession();
        if (httpSession != null) {
            memberDTO = (MemberDTO) httpSession.getAttribute(WebConst.SESSION_MEMBER);
            return memberDTO;
        }

        return null;
    }

    /**
     * 设置会员信息到session中.
     * @param memberDTO 登录信息
     */
    public static final void setMember(MemberDTO memberDTO) {
        HttpServletRequest req = getRequest();
        if (req == null) {
            log.warn("会员信息设置失败！");
            return;
        }
        HttpSession session = req.getSession(true);
        //清空这些信息，减轻写入session redis缓存的内容，读取的时候在增加从redis中读取
        session.setAttribute(WebConst.SESSION_MEMBER, memberDTO);
        if (memberDTO != null) {
            log.info("添加会员信息到session中，memberId={}，uName={}", memberDTO.getMemberId(), memberDTO.getUname());
        }
        //设置线程变量
        memberThreadLocal.set(memberDTO);
    }


}
