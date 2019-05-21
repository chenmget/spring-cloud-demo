package com.iwhalecloud.retail.web.controller.member;

import com.alibaba.dubbo.config.annotation.Reference;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.member.dto.MemberDTO;
import com.iwhalecloud.retail.member.dto.request.*;
import com.iwhalecloud.retail.member.dto.response.BindingQueryResp;
import com.iwhalecloud.retail.member.dto.response.MemberIsExistsResp;
import com.iwhalecloud.retail.member.dto.response.MemberLoginResp;
import com.iwhalecloud.retail.member.service.BindingService;
import com.iwhalecloud.retail.member.service.MemberService;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.common.ResultCodeEnum;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.request.RandomLogAddReq;
import com.iwhalecloud.retail.system.dto.request.RandomLogGetReq;
import com.iwhalecloud.retail.system.dto.request.RandomLogUpdateReq;
import com.iwhalecloud.retail.system.dto.request.UserGetReq;
import com.iwhalecloud.retail.system.dto.response.RandomLogGetResp;
import com.iwhalecloud.retail.system.service.RandomLogService;
import com.iwhalecloud.retail.system.service.UserService;
import com.iwhalecloud.retail.web.annotation.PassToken;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.consts.WebConst;
import com.iwhalecloud.retail.web.controller.BaseController;
import com.iwhalecloud.retail.web.controller.member.requst.LoginReq;
import com.iwhalecloud.retail.web.controller.member.requst.VerificationCodeReq;
import com.iwhalecloud.retail.web.controller.member.response.LoginResp;
import com.iwhalecloud.retail.web.interceptor.MemberContext;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import com.iwhalecloud.retail.web.utils.JWTTokenUtil;
import com.twmacinta.util.MD5;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 * @author
 * @date 2018/12/01
 */
@RestController
@RequestMapping("/api/member")
@Slf4j
public class MemberController extends BaseController {

    @Value("${jwt.secret}")
    private String SECRET;

    @Reference
	private MemberService memberService;
    @Reference
    private BindingService bindingService;
    @Reference
	private RandomLogService randomLogService;
    @Reference
	private UserService userService;

	/**
	 * 会员登陆或注册
	 * @param loginReq
	 * @return
	 */
	@RequestMapping(value="/loginOrRegister",method = RequestMethod.POST)
	@PassToken
	public ResultVO<LoginResp> memberLoginOrRegister(HttpServletRequest request,@RequestBody LoginReq loginReq) {

		if (StringUtils.isEmpty(loginReq.getMobile())) {
			return ResultVO.error(OmsCommonConsts.RESULE_CODE_FAIL, "用户名或手机号不能为空");
		}
		if (StringUtils.isEmpty(loginReq.getPwd())) {
			return ResultVO.error(OmsCommonConsts.RESULE_CODE_FAIL, "密码或验证码不能为空");
		}
		if (StringUtils.isEmpty(loginReq.getLoginType())) {
			return ResultVO.error(OmsCommonConsts.RESULE_CODE_FAIL, "登陆类型不能为空");
		}

		// 1、校验验证码
		if(!checkVerificationCode(loginReq)){
			return ResultVO.error( "校验验证码失败");
		}
		// 2、先判断是否存在账号
		if(!isExistsByMobile(loginReq.getMobile())){
			// 注册
			if(!registerByMobile(loginReq.getMobile())){
				return ResultVO.error("注册失败");
			}
		}
		//3、登录
		return login(request, loginReq);
	}

	/**
	 * 获取会员信息（外层都返回成功，是否登陆在resultData字段里面返回）
	 * 不用token拦截啦，这里本身要做token校验啦（方便统一返回）
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getMember",method = RequestMethod.GET)
	@PassToken
	public ResultVO<LoginResp> getMember(HttpServletRequest request) {
		LoginResp loginResp = new LoginResp();

		// 获取openId（拦截器里面会先判断是否微信小程序登录）
		String openId = (String)request.getSession().getAttribute(WebConst.SESSION_WX_OPEN_ID);

		if (!StringUtils.isEmpty(openId)) {
			// 微信登录   先判断是否绑定过
			BindingQueryReq req = new BindingQueryReq();
			req.setTargetId(openId);
			com.iwhalecloud.retail.dto.ResultVO<List<BindingQueryResp>> resultData = bindingService.queryeBindingCodition(req);
			List<BindingQueryResp> resultList = resultData.getResultData();
			//未绑定
			if(resultList == null || resultList.isEmpty()){
				loginResp.setMember(null);
				loginResp.setLoginStatusCode(ResultCodeEnum.NOT_BINDING_WX.getCode());
				loginResp.setLoginStatusMsg(ResultCodeEnum.NOT_BINDING_WX.getDesc());
				return ResultVO.success(loginResp);
			}

			// 取会员信息
			MemberDTO memberDTO = MemberContext.getMember();
			if(memberDTO == null){
				MemberGetReq memberGetReq = new MemberGetReq();
				memberGetReq.setMemberId(resultList.get(0).getMemberId());
				memberDTO = memberService.getMember(memberGetReq).getResultData();
				MemberContext.setMember(memberDTO);
			}

			//模拟登录
			loginResp.setMember(memberDTO);
			loginResp.setToken(getToken(request));
			loginResp.setLoginStatusCode(WebConst.loginStatusEnum.HAVE_LOGIN.getCode());
			loginResp.setLoginStatusMsg(WebConst.loginStatusEnum.HAVE_LOGIN.getValue());
			return ResultVO.success(loginResp);
		}

		ResultVO checkUserLoginResult = checkUserLogin(request);
		if (!checkUserLoginResult.isSuccess()) {
			// 验证失败返回
			loginResp.setMember(null);
			loginResp.setLoginStatusCode(checkUserLoginResult.getResultCode());
			loginResp.setLoginStatusMsg(checkUserLoginResult.getResultMsg());
			return ResultVO.success(loginResp);
		}
		MemberDTO memberDTO = MemberContext.getMember();
		if (memberDTO == null) {
			String memberId = (String) checkUserLoginResult.getResultData();
			MemberGetReq memberGetReq = new MemberGetReq();
			memberGetReq.setMemberId(memberId);
			memberDTO = memberService.getMember(memberGetReq).getResultData();
			MemberContext.setMember(memberDTO);
		}

		loginResp.setMember(memberDTO);
		loginResp.setToken(getToken(request));
		loginResp.setLoginStatusCode(WebConst.loginStatusEnum.HAVE_LOGIN.getCode());
		loginResp.setLoginStatusMsg(WebConst.loginStatusEnum.HAVE_LOGIN.getValue());
		return ResultVO.success(loginResp);
	}

	/**
	 * 会员登陆
	 * @param loginReq
	 * @return
	 */
	private ResultVO<LoginResp> login(HttpServletRequest request,@RequestBody LoginReq loginReq) {
		if (StringUtils.isEmpty(loginReq.getMobile())) {
			return ResultVO.error(OmsCommonConsts.RESULE_CODE_FAIL, "用户名或手机号不能为空");
		}
		if (StringUtils.isEmpty(loginReq.getPwd())) {
			return ResultVO.error(OmsCommonConsts.RESULE_CODE_FAIL, "密码或验证码不能为空");
		}
		if (StringUtils.isEmpty(loginReq.getLoginType())) {
			return ResultVO.error(OmsCommonConsts.RESULE_CODE_FAIL, "登陆类型不能为空");
		}

		MemberLoginResp memberLoginResp;
		MemberLoginReq req = new MemberLoginReq();

//只用登录名登录（手机号码可能会修改）
//		req.setMobile(loginReq.getMobile());
		req.setUserName(loginReq.getMobile());

		if(WebConst.LoginTypeEnum.PASSWORD.getCode().equals(loginReq.getLoginType())){
			// 密码登陆（暂时屏蔽该功能）
//			req.setPassword(loginReq.getPwd());
//            memberLoginResp = memberService.login(req);
			return ResultVO.error(OmsCommonConsts.RESULE_CODE_FAIL, "登陆类型错误,暂时只支持验证码登录");
		} else if (WebConst.LoginTypeEnum.VERIFICATION_CODE.getCode().equals(loginReq.getLoginType())){
			req.setUserName(loginReq.getMobile());
			memberLoginResp = memberService.login(req);
		} else {
			return ResultVO.error(OmsCommonConsts.RESULE_CODE_FAIL, "登陆类型错误");
		}
		if(!memberLoginResp.getIsLoginSuccess()
				&& memberLoginResp.getMemberDTO() != null){
			return ResultVO.error("登陆失败！");
		}

		// 保存到会员信息到 session
		MemberContext.setMember(memberLoginResp.getMemberDTO());

		//绑定openId
		bindingOpenId(request, memberLoginResp.getMemberDTO());

		//设置返回对象
		LoginResp loginResp = new LoginResp();
		loginResp.setMember(memberLoginResp.getMemberDTO());
		loginResp.setLoginStatusCode(WebConst.loginStatusEnum.HAVE_LOGIN.getCode());
		loginResp.setLoginStatusMsg(WebConst.loginStatusEnum.HAVE_LOGIN.getValue());
		try {
			loginResp.setToken(JWTTokenUtil.getMemberToken(memberLoginResp.getMemberDTO().getMemberId(), request.getSession().getId()));
		}catch (Exception e){
			return ResultVO.error("获取token异常");
		}
		return ResultVO.success(loginResp);
	}

	/**
	 * 根据手机号注册
	 * @param mobile
	 * @return
	 */
	private boolean registerByMobile(String mobile) {
		// 会员对象 (设置一些默认值）
		MemberAddReq memberAddReq = new MemberAddReq();
		memberAddReq.setMobile(mobile);
		// 生成随机的名字 平台简称(翼码购）+时间戳
		memberAddReq.setName("YMG_"+ System.currentTimeMillis());
		// 先设置为mobile 随机生成password
		memberAddReq.setUname(mobile);
		// 后面会员列表查询根据手机号码的查询的是查这个字段
		memberAddReq.setTel(mobile);
		memberAddReq.setPassword(UUID.randomUUID().toString().replace("-", ""));
		com.iwhalecloud.retail.dto.ResultVO rigistResult = memberService.register(memberAddReq);
		// 注册失败
		if(!rigistResult.getResultCode().equals(OmsCommonConsts.RESULE_CODE_SUCCESS)){
			return false;
		}
		return true;
	}

	/**
	 * 判断会员是否存在  根据手机号
	 * @param mobile
	 * @return
	 */
	private boolean isExistsByMobile(String mobile) {
		if (StringUtils.isEmpty(mobile)) {
			return false;
		}
		MemberIsExistsReq req = new MemberIsExistsReq();
//		req.setMobile(mobile); //待确认能不能修改手机号
		req.setUname(mobile);
		MemberIsExistsResp resp = memberService.isExists(req);

		if (null != resp && !resp.isExists()) {
			return false; // 不存在
		}
		return true;
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
     * 判断用户是否登陆
     * @param request
     * @return  会员ID
     */
    private ResultVO<String> checkUserLogin(HttpServletRequest request) {
    	
    	ResultVO resultVO = new ResultVO();
		resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        // 取出 token
        String token = getToken(request);
        if (StringUtils.isEmpty(token)) {

        	resultVO.setResultCode(ResultCodeEnum.LOST_TOKEN.getCode());
        	resultVO.setResultMsg(ResultCodeEnum.LOST_TOKEN.getDesc());
            return resultVO;
        }
        // 执行认证
        String id;
        try {
            Map<String, Claim> claims = JWT.decode(token).getClaims();
            id = claims.get("id").asString();
            if (id == null) {
            	resultVO.setResultCode("100");
            	resultVO.setResultMsg("用户未登录");
                return resultVO;
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
            	resultVO.setResultCode("101");
            	resultVO.setResultMsg("TOKEN无效");
                return resultVO;
            }
        } catch (Exception ex) {
            log.error("MemberController.getMemberToken Exception ex={}",ex);
            resultVO.setResultCode("101");
        	resultVO.setResultMsg("TOKEN无效");
            return resultVO;
        }
        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
        	resultVO.setResultCode("101");
        	resultVO.setResultMsg("TOKEN无效");
            return resultVO;
        }
        return ResultVO.success(id);
    }


	/**
	 * 绑定openId, 先判断是否绑定过
	 * 没绑定：直接添加绑定
	 * 绑定过：判断是否需要更新
	 * @param request
	 * @param memberDTO
	 */
	private void bindingOpenId(HttpServletRequest request, MemberDTO memberDTO) {

		// 拦截器类 WxUserInterceptor 会判断是否微信小程序登录（会塞openID到session 里面）
		String openId = (String)request.getSession().getAttribute(WebConst.SESSION_WX_OPEN_ID);

		// 写死测试
//		openId = "oZs6bs2YFklYRqWLvAsXyePX_4iY";

		if (!StringUtils.isEmpty(openId) && memberDTO != null) {

			BindingQueryReq req = new BindingQueryReq();
			req.setTargetId(openId);
        	//先判断是否绑定过
            com.iwhalecloud.retail.dto.ResultVO<List<BindingQueryResp>> result = bindingService.queryeBindingCodition(req);
            List<BindingQueryResp> bindingList = result.getResultData();
			if(bindingList != null && !bindingList.isEmpty()){
				BindingQueryResp memberBinding = bindingList.get(0);
			    //判断会员名称和ID  是否相同   否：记录更新
                if(!StringUtils.equals(memberDTO.getMemberId(), memberBinding.getMemberId())
                        || !StringUtils.equals(memberDTO.getUname(), memberDTO.getUname())){
                	BindingUpdateReq updateReq = new BindingUpdateReq();
                    memberBinding.setMemberId(memberDTO.getMemberId());
                    memberBinding.setUname(memberDTO.getUname());
                    BeanUtils.copyProperties(memberBinding, updateReq);
                    bindingService.updateBindingCodition(updateReq);
                }
                return; // 绑定过直接返回
			}
			//新增
			BindingAddReq dto = new BindingAddReq();
			dto.setMemberId(memberDTO.getMemberId());
			dto.setTargetId(openId);
			dto.setTargetType(1);
			dto.setUname(memberDTO.getUname());
            bindingService.insertBinding(dto);
        }
	}


	@ApiOperation(value = "获取验证码", notes = "传入VerificationCodeReq")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
	@PostMapping(value="/getVerificationCode")
	public ResultVO getVerificationCode(HttpServletRequest request, @RequestBody VerificationCodeReq req){
		String sessionId = request.getSession().getId();
		log.info("MemberController.getVerificationCode sessionId={}, req={}", sessionId, req);
		RandomLogAddReq randomLogAddReq = new RandomLogAddReq();
		BeanUtils.copyProperties(req, randomLogAddReq);

        randomLogAddReq.setSessionId(sessionId);
		randomLogAddReq.setBusiId(sessionId);
		return randomLogService.insertSelective(randomLogAddReq);
	}

	/**
	 * 校验 验证码
	 * @param loginReq
	 * @return
	 */
	private boolean checkVerificationCode(LoginReq loginReq){
		if(WebConst.LoginTypeEnum.PASSWORD.getCode().equals(loginReq.getLoginType())){
			// 密码登陆（跳过验证码）
			return true;
		} else if (WebConst.LoginTypeEnum.VERIFICATION_CODE.getCode().equals(loginReq.getLoginType())) {
			// 验证码登录
			RandomLogGetReq randomLogQueryReq = new RandomLogGetReq();
			randomLogQueryReq.setReceviNo(loginReq.getMobile());
			randomLogQueryReq.setRandomCode(loginReq.getPwd());
			ResultVO<RandomLogGetResp> resultVO = randomLogService.selectLogIdByRandomCode(randomLogQueryReq);
			RandomLogGetResp randomLogGetResp = resultVO.getResultData();
			// 校验成功
			if (randomLogGetResp != null) {
				// 更新验证状态
				RandomLogUpdateReq logUpdateReq = new RandomLogUpdateReq();
				logUpdateReq.setLogId(randomLogGetResp.getLogId());
				logUpdateReq.setValidStatus(SystemConst.ValidStatusEnum.HAVE_VALID.getCode());
				logUpdateReq.setValidTime(new Date());
				randomLogService.updateByPrimaryKey(logUpdateReq);
			} else {
				return false;
			}
		}
		return true;
	}

    @ApiOperation(value = "查询会员分页列表", notes = "传入MemberPageReq对象，进行查询操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/qryMemberPage", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<Page<MemberDTO>> memberList(HttpServletRequest request, @RequestBody MemberPageReq req) {

        Page<MemberDTO> memberPage = memberService.pageMember(req);
        return ResultVO.success(memberPage);
    }
    
    @ApiOperation(value = "判断商家是否拥有翼支付账号,没有则弹窗提示", notes = "传入商家MERCHANT_ID，进行查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/checkPayAccount", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<Map<String, String>> checkPayAccount() {
    	
    	String userId = UserContext.getUserId();
    	UserDTO userDTO = userService.getUserByUserId(userId);
    	String merchantId = userDTO.getRelCode();
    	if(StringUtils.isBlank(userId)){
    		ResultVO.error("账号不能为空");
    	}
    	//resultCode 1、账号存在  2、账号不存在
    	Map<String, String> resultCode = new TreeMap<String, String>();
    	resultCode.put("resultCode", "2");
    	int flag = memberService.checkPayAccount(merchantId);
    	if(flag > 0){
    		resultCode.put("resultCode", "1");
    	}
    	return ResultVO.success(resultCode);
    }
    
}
