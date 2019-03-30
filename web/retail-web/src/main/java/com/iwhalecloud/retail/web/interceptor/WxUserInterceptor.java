package com.iwhalecloud.retail.web.interceptor;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.member.dto.request.BindingQueryReq;
import com.iwhalecloud.retail.member.dto.request.MemberLoginReq;
import com.iwhalecloud.retail.member.dto.response.BindingQueryResp;
import com.iwhalecloud.retail.member.dto.response.MemberLoginResp;
import com.iwhalecloud.retail.member.service.BindingService;
import com.iwhalecloud.retail.member.service.MemberService;
import com.iwhalecloud.retail.oms.dto.CodeSessionDTO;
import com.iwhalecloud.retail.web.consts.WebConst;
import com.iwhalecloud.retail.web.utils.JWTTokenUtil;
import com.iwhalecloud.retail.web.utils.UserAgentUtil;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;


public class WxUserInterceptor implements HandlerInterceptor {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${wechat.mp.notifyServer}")
	private String notifyServer;
	
	@Value("${wechat.mp.appId}")
    private String appId;
	
	@Value("${wechat.mp.secret}")
    private String secret;
	
	
	@Autowired
    private WxMpService wxService;

	@Reference
    private MemberService memberService;

	@Reference
	private BindingService bindingService;

    /**小程序获取appId地址*/
    private final static String code2SessionUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}
	
	/**
	 * 是白名单，直接放过的URL
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 */
	private boolean isWhiteNames(HttpServletRequest request, HttpServletResponse response, Object handler) {
//		if (request.getServletPath().indexOf("/api/wechat/dispatch") >= 0) { 
//			return true;
//		}
//		
//		if (request.getServletPath().indexOf("/api/rop/member/getMember") >= 0) {
//			return true;
//		}
		
		return true;
	}
	
	
	private void loginByWxCode(HttpServletRequest req) {
		try {
			
			logger.info("----模拟登录sessionId-->  " + req.getSession().getId());
			//如果已经登录
			if (req.getSession().getAttribute(WebConst.SESSION_TOKEN) != null) {
				return;
			}
			
			String openId = (String)req.getSession().getAttribute(WebConst.SESSION_WX_OPEN_ID);

			if (StringUtils.isEmpty(openId)) {
				openId = getOpenId(req);

				// 写死测试
//				openId = "oZs6bs2YFklYRqWLvAsXyePX_4iY";

				req.getSession().setAttribute(WebConst.SESSION_WX_OPEN_ID, openId);
			}
			
			if (StringUtils.isEmpty(openId)) {
				logger.info("openid为空");
				return;
			}

//			MemberBindingDTO bindingMember = memberService.getMemberBinding(openId);
			BindingQueryReq bindingQueryReq = new BindingQueryReq();
			bindingQueryReq.setTargetId(openId);
			List<BindingQueryResp> bindingQueryRespList = bindingService.queryeBindingCodition(bindingQueryReq).getResultData();
			if (CollectionUtils.isEmpty(bindingQueryRespList)) {
				//[1550525]【安全漏洞修复】明文密码、日志记录、硬编码、密钥明文存储、敏感信息泄露等
				logger.info("openId未关联会员===>");
				//查出微信昵称，存入缓存
//				WxMpUserService mpUserService = new WxMpUserServiceImpl(wxService);
//				WxMpUser wxMpUser = mpUserService.userInfo(openId);
//				logger.info("openId ===>" + openId + "昵称===>" + wxMpUser.getNickname());
//				req.getSession().setAttribute(OmsConst.SESSION_WX_NICK_NAME, wxMpUser.getNickname());
			} else {
                //[1550525]【安全漏洞修复】明文密码、日志记录、硬编码、密钥明文存储、敏感信息泄露等
				logger.info("openId已关联会员===>关联会员===>" + bindingQueryRespList.get(0).getUname());
				
				//模拟登录
				BindingQueryResp bindingQueryResp = bindingQueryRespList.get(0);
				MemberLoginReq memberLoginReq = new MemberLoginReq();
				memberLoginReq.setUserName(bindingQueryResp.getUname());

				final String sessionId = req.getSession().getId();
				MemberLoginResp memberLoginResp = memberService.login(memberLoginReq);
				if (memberLoginResp.getIsLoginSuccess()
						&& memberLoginResp.getMemberDTO() != null) {
					MemberContext.setMember(memberLoginResp.getMemberDTO());
					String token = JWTTokenUtil.getMemberToken(memberLoginResp.getMemberDTO().getMemberId(), sessionId);
					req.getSession().setAttribute(WebConst.SESSION_TOKEN, token);
					logger.info("关联会员登录成功===>" + bindingQueryResp.getUname());
				} else {
					logger.info("关联会员登录失败===>" + bindingQueryResp.getUname());
				}
			}
		} catch (Exception e) {
			logger.error("微信登录异常", e);
		}
	}

	/**
	 * 获取openId
	 * @param req
	 * @return
	 * @throws WxErrorException
	 */
	private String getOpenId(HttpServletRequest req) throws WxErrorException {
		
		String authCode = req.getParameter("wx_code");
		if (StringUtils.isEmpty(authCode)) {
			logger.info("wx_code为空");
			return null;
		} else {
			logger.info("wx_code=" + authCode);
		}
		String reqUrl = String.format(code2SessionUrl,appId,secret,authCode);
		String resp = this.wxService.get(reqUrl, "");
		CodeSessionDTO codeSession = JSON.parseObject(resp, CodeSessionDTO.class);
		if (codeSession == null) {
			return null;
		}
		if (codeSession!=null && 
				(codeSession.getErrcode()==null || "0".equals(codeSession.getErrcode()))) {
			return codeSession.getOpenid();
		}
		logger.error("获取openId失败==>" + JSON.toJSONString(codeSession));
		return null;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String reqUrl = request.getServletPath();
		String qryStr = request.getQueryString();
		
		String host = request.getRemoteHost(); 
		int port = request.getRemotePort();
		
		logger.info("当前请求参数 qryStr=>" + qryStr);
		logger.info("当前当前参数 path:host:port=>" + reqUrl + " : " + host + " : " + port);
		
		if (request.getServletPath().indexOf("/api/member/getMember") >= 0) {
			loginByWxCode(request);
		}
		
		//1、如果是白名单，则直接跳过
		if (isWhiteNames(request, response, handler)) {
			return true;
		}
		
		//2、如果是微信浏览器
		if (UserAgentUtil.isWechat(request)) {
			String wxOpenId = (String)request.getSession().getAttribute(WebConst.SESSION_WX_OPEN_ID);
			logger.info("session  openid = " + wxOpenId);
			if (StringUtils.isEmpty(wxOpenId)) {
				String redirectUrl = request.getServletPath();
				final String queryString = request.getQueryString();
				if (!StringUtils.isEmpty(queryString)) {
					redirectUrl += "?" + request.getQueryString();
				} 
				
				final String encodeRedirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
				
				String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId
						+"&redirect_uri="+URLEncoder.encode(
						notifyServer
						+"/api/wechat/dispatch?redirectUrl="+URLEncoder.encode(encodeRedirectUrl,"utf-8"),"utf-8") + "&response_type=code&scope=snsapi_base&state=REDIRECT"
						+"#wechat_redirect";
				
				logger.info("去微信授权的url===>" + url);
				response.sendRedirect(url);
				return false;
			}
		}
		
		return true;
		
	}

}
