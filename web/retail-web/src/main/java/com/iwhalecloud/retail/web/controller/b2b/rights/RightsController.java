package com.iwhalecloud.retail.web.controller.b2b.rights;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.rights.common.ResultCodeEnum;
import com.iwhalecloud.retail.rights.dto.request.*;
import com.iwhalecloud.retail.rights.dto.response.QueryCouponInstRespDTO;
import com.iwhalecloud.retail.rights.dto.response.QueryRightsRespDTO;
import com.iwhalecloud.retail.rights.service.CouponInstService;
import com.iwhalecloud.retail.rights.service.MktResCouponService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.b2b.rights.request.*;
import com.iwhalecloud.retail.web.interceptor.MemberContext;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.text.ParseException;

@RestController
@RequestMapping("/api/rights")
@Slf4j
public class RightsController {

	@Reference
	private CouponInstService couponInstService;
	@Reference
	private MktResCouponService mktResCouponService;
	
	@ApiOperation(value = "权益查询", notes = "传入mktResName、mktResTypeId，进行查询操作")
	@RequestMapping(value = "/queryRights", method = RequestMethod.POST)
	public ResultVO queryRights(@RequestBody @ApiParam(value = "查询条件", required = true) QueryRightsReqDTO request ) {
		ResultVO resultVO = new ResultVO();
		log.info("request parameter request {}",request);
		Page<QueryRightsRespDTO> queryRights = mktResCouponService.queryRights(request);
		resultVO.setResultMsg("成功");
		resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		resultVO.setResultData(queryRights);
		return resultVO;
	}
	
	@ApiOperation(value = "权益实例查询", notes = "传入custNum，进行查询操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/queryCouponinst", method = RequestMethod.POST)
	@UserLoginToken
	public ResultVO queryCouponinst(@RequestBody @ApiParam(value = "查询条件", required = true) QueryCouponInstReq req ) {
        ResultVO resultVO = new ResultVO();
        //获取memberId
        String memberId = MemberContext.getMemberId();
        if(StringUtils.isEmpty(memberId)){
        	resultVO.setResultMsg("memberId can not be null");
            resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
            return resultVO;
        }
        
        QueryCouponInstPageReq request = new QueryCouponInstPageReq();
        BeanUtils.copyProperties(req, request);
        request.setCustNum(memberId);
        Page<QueryCouponInstRespDTO> queryCouponinst = couponInstService.queryCouponinst(request);
        resultVO.setResultMsg("成功");
        resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        resultVO.setResultData(queryCouponinst);
        return resultVO;
    }
	
	
	@ApiOperation(value = "实例入库", notes = "传入InputRightsRequestDTO")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
	@RequestMapping(value = "/inputRights", method = RequestMethod.POST)
    public ResultVO inputRights(@RequestBody @Valid InputRightsReq req) throws ParseException {
        log.info("request parameter orderId {}",req);
        //获取memberId
        String memberId = MemberContext.getMemberId();
        if(StringUtils.isEmpty(memberId)){
        	ResultVO resultVO = new ResultVO();
        	resultVO.setResultMsg("memberId can not be null");
            resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
            return resultVO;
        }
        InputRightsRequestDTO request = new InputRightsRequestDTO();
        BeanUtils.copyProperties(req, request);
        request.setGmtCreate(memberId);
        return couponInstService.inputRights(request);
    }
	
	@ApiOperation(value = "权益领取", notes = "传入InputRightsRequestDTO")
	@ApiResponses({
		@ApiResponse(code=400,message="请求参数没填好"),
		@ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
	})
	@RequestMapping(value = "/saveRights", method = RequestMethod.POST)
	@UserLoginToken
	public ResultVO saveRights(@RequestBody SaveRightsReq req) {
		log.info("request parameter orderId {}",req);
		//获取memberId
        String memberId = UserContext.getUserId();
        if(StringUtils.isEmpty(memberId)){
        	ResultVO resultVO = new ResultVO();
        	resultVO.setResultMsg("memberId can not be null");
            resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
            return resultVO;
        }
        SaveRightsRequestDTO request = new SaveRightsRequestDTO();
        BeanUtils.copyProperties(req, request);
        request.setCustNum(memberId);
		return couponInstService.saveRights(request);
	}
	
	@ApiOperation(value = "权益校验", notes = "传入CheckRightsRequestDTO")
	@ApiResponses({
		@ApiResponse(code=400,message="请求参数没填好"),
		@ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
	})
	@RequestMapping(value = "/checkRights", method = RequestMethod.POST)
	@UserLoginToken
	public ResultVO checkRights(@RequestBody CheckRightsReq req) {
		log.info("request parameter orderId {}",req);
		//获取memberId
        String memberId = MemberContext.getMemberId();
        if(StringUtils.isEmpty(memberId)){
        	ResultVO resultVO = new ResultVO();
        	resultVO.setResultMsg("memberId can not be null");
            resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
            return resultVO;
        }
        CheckRightsRequestDTO request = new CheckRightsRequestDTO();
        BeanUtils.copyProperties(req, request);
        request.setCustNum(memberId);
		return couponInstService.checkRights(request);
	}
	
	@ApiOperation(value = "权益权益核销", notes = "传入UserightsRequestDTO")
	@ApiResponses({
		@ApiResponse(code=400,message="请求参数没填好"),
		@ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
	})
	@RequestMapping(value = "/userights", method = RequestMethod.POST)
	@UserLoginToken
	public ResultVO userights(@RequestBody UserightsReq req) {
		log.info("request parameter orderId {}",req);
		
        //获取memberId
		String memberId = MemberContext.getMemberId();
        if(StringUtils.isEmpty(memberId)){
        	ResultVO resultVO = new ResultVO();
        	resultVO.setResultMsg("memberId can not be null");
            resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
            return resultVO;
        }
        UserightsRequestDTO request = new UserightsRequestDTO();
        BeanUtils.copyProperties(req, request);
        request.setCustNum(memberId);
		return couponInstService.userights(request);
	}

	@ApiOperation(value = "权益领取", notes = "领取的时候创建权益实例")
	@ApiResponses({
			@ApiResponse(code=400,message="请求参数没填好"),
			@ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
	})
	@RequestMapping(value = "/receiveRights", method = RequestMethod.POST)
	@UserLoginToken
	public ResultVO receiveRights(@RequestBody SaveRightsReq req) {
		log.info("RightsController receiveRights req={}", JSON.toJSON(req));
		String merchantId = UserContext.getMerchantId();
		if(StringUtils.isEmpty(merchantId)){
			return ResultVO.error("商家id不能为空");
		}
		SaveRightsRequestDTO request = new SaveRightsRequestDTO();
		BeanUtils.copyProperties(req, request);
		request.setCustNum(merchantId);
		return couponInstService.receiveRights(request);
	}

}
