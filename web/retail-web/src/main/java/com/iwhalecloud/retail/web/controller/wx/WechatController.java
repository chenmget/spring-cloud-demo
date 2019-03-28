package com.iwhalecloud.retail.web.controller.wx;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.member.dto.request.BindingQueryReq;
import com.iwhalecloud.retail.member.dto.request.MemberLoginReq;
import com.iwhalecloud.retail.member.dto.response.BindingQueryResp;
import com.iwhalecloud.retail.member.dto.response.MemberLoginResp;
import com.iwhalecloud.retail.member.service.BindingService;
import com.iwhalecloud.retail.member.service.MemberService;
//import com.iwhalecloud.retail.oms.consts.OmsConst;
import com.iwhalecloud.retail.web.consts.WebConst;
import com.iwhalecloud.retail.web.controller.BaseController;
import com.iwhalecloud.retail.web.interceptor.MemberContext;
import com.iwhalecloud.retail.web.utils.JWTTokenUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.api.impl.WxMpUserServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.List;

/**
 * 微信服务控制器
 *
 */
@RestController
@RequestMapping("/api/wechat")
public class WechatController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxMpService wxService;
    
    @Reference
    private MemberService memberService;

	@Reference
	private BindingService bindingService;

	/**
     * 微信校验token接口
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @param resp
     */
    @GetMapping(produces = "text/plain;charset=utf-8")
    public void authGet(
            @RequestParam(name = "signature",
                    required = false) String signature,
            @RequestParam(name = "timestamp",
                    required = false) String timestamp,
            @RequestParam(name = "nonce", required = false) String nonce,
            @RequestParam(name = "echostr", required = false) String echostr, 
            HttpServletResponse resp) {

        this.logger.info("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature,
                timestamp, nonce, echostr);
        
        System.out.println("微信get请求参数=================Start");
        System.out.println("signature=>" + signature + ", timestamp=>" + timestamp + ", nonce=>" + nonce + ", echostr=>" + echostr);
        System.out.println("微信get请求参数=================End");

        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
        	System.out.println("请求参数非法，请核实!");
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }
        
        String result = "非法请求";

        if (this.wxService.checkSignature(timestamp, nonce, signature)) {
        	System.out.println("checkSignature验证通过");
//            return echostr;
        	result = echostr;
        } else {
        	
        	System.out.println("checkSignature验证不通过....");
        }
        
//        return "非法请求";
    	try {
			resp.getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}        
    }


    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/dispatch", method = RequestMethod.GET)
    public void dispatch(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException, Exception{
    	
		Enumeration enu = req.getParameterNames();
		logger.info("############################");
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			System.out.println(paraName + "==>" + req.getParameter(paraName));
		}
		logger.info("############################");
    	
    	
    	String authCode = req.getParameter("code");
		String redirectUrl = URLDecoder.decode(req.getParameter("redirectUrl"), "UTF-8");
		
		WxMpOAuth2AccessToken at = this.wxService.oauth2getAccessToken(authCode);
		
		logger.info("微信授权结果 at===>" + at);
		
		String openId = at.getOpenId();		
		
		req.getSession().setAttribute(WebConst.SESSION_WX_OPEN_ID, openId);
		
		BindingQueryReq bindingQueryReq = new BindingQueryReq();
		bindingQueryReq.setTargetId(openId);
		List<BindingQueryResp> bindingQueryRespList = bindingService.queryeBindingCodition(bindingQueryReq).getResultData();
		if (CollectionUtils.isEmpty(bindingQueryRespList)) {
//		if (bindingMember == null) {
			logger.info("openId未关联会员===>" + openId);
			//查出微信昵称，存入缓存
			WxMpUserService mpUserService = new WxMpUserServiceImpl(wxService);
			WxMpUser wxMpUser = mpUserService.userInfo(openId);
			logger.info("openId ===>" + openId + "昵称===>" + wxMpUser.getNickname());
			req.getSession().setAttribute(WebConst.SESSION_WX_NICK_NAME, wxMpUser.getNickname());
		} else {
			logger.info("openId已关联会员===>" + openId + "--关联会员===>" + bindingQueryRespList.get(0).getUname());
			
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
		
		resp.sendRedirect(redirectUrl);
    }
    
    @SuppressWarnings({"unchecked" })
	@GetMapping(value = "/createJsapiSignature")
    @ApiOperation(value = "创建调用jsapi时所需要的签名", notes = "创建调用jsapi时所需要的签名")
    public ResultVO<WxJsapiSignature> createJsapiSignature(@RequestParam(value = "url", required = false) @ApiParam(value = "url") String url)
    		throws WxErrorException {
    	WxJsapiSignature jsapiSignature = this.wxService.createJsapiSignature(url);
    	
    	return successResultVO(jsapiSignature);
    }
}
