package com.iwhalecloud.retail.web.controller.b2b.rights;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.rights.dto.request.CommonQueryByMktResIdReqDTO;
import com.iwhalecloud.retail.rights.dto.response.*;
import com.iwhalecloud.retail.rights.service.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rule")
@Slf4j
public class RightsRuleController {

	@Reference
	private CouponEffExpRuleService couponEffExpRuleService;
	@Reference
	private CouponDiscountRuleService couponDiscountRuleService;
	@Reference
	private CouponSupplyRuleService couponSupplyRuleService;
	@Reference
	private CouponApplyObjectService couponApplyObjectService;
	@Reference
	private MktResRegionService mktResRegionService;
	
	@ApiOperation(value = "权益生失效规则查询", notes = "传入CommonQueryByMktResIdReqDTO，进行查询操作")
	@RequestMapping(value = "/queryCouponEffExpRule", method = RequestMethod.POST)
	public ResultVO queryCouponEffExpRule(@RequestBody @ApiParam(value = "查询条件", required = true) CommonQueryByMktResIdReqDTO request ) {
        ResultVO resultVO = new ResultVO();
        log.info("request parameter request {}",request);
        Page<CouponEffExpRuleRespDTO> discountRule = couponEffExpRuleService.queryCouponEffExpRule(request);
        resultVO.setResultMsg("成功");
        resultVO.setResultCode("0");
        resultVO.setResultData(discountRule);
        return resultVO;
    }
	
	@ApiOperation(value = "权益抵扣规则查询", notes = "传入CommonQueryByMktResIdReqDTO，进行查询操作")
	@RequestMapping(value = "/queryCouponDiscountRule", method = RequestMethod.POST)
	public ResultVO queryCouponDiscountRule(@RequestBody @ApiParam(value = "查询条件", required = true) CommonQueryByMktResIdReqDTO request ) {
		ResultVO resultVO = new ResultVO();
		log.info("request parameter request {}",request);
		Page<CouponDiscountRuleRespDTO> discountRule = couponDiscountRuleService.queryCouponDiscountRule(request);
		resultVO.setResultMsg("成功");
		resultVO.setResultCode("0");
		resultVO.setResultData(discountRule);
		return resultVO;
	}
	
	@ApiOperation(value = "权益领取规则查询", notes = "传入CommonQueryByMktResIdReqDTO，进行查询操作")
	@RequestMapping(value = "/queryCouponSupplyRule", method = RequestMethod.POST)
	public ResultVO queryCouponSupplyRule(@RequestBody @ApiParam(value = "查询条件", required = true) CommonQueryByMktResIdReqDTO request ) {
		ResultVO resultVO = new ResultVO();
		log.info("request parameter request {}",request);
		Page<CouponSupplyRuleRespDTO> discountRule = couponSupplyRuleService.queryCouponSupplyRule(request);
		resultVO.setResultMsg("成功");
		resultVO.setResultCode("0");
		resultVO.setResultData(discountRule);
		return resultVO;
	}
	
	@ApiOperation(value = "权益生适用对象查询", notes = "传入CommonQueryByMktResIdReqDTO，进行查询操作")
	@RequestMapping(value = "/queryCouponApplyObject", method = RequestMethod.POST)
	public ResultVO queryCouponApplyObject(@RequestBody @ApiParam(value = "查询条件", required = true) CommonQueryByMktResIdReqDTO request ) {
		ResultVO resultVO = new ResultVO();
		log.info("request parameter request {}",request);
		Page<CouponApplyObjectRespDTO> applyObject = couponApplyObjectService.queryCouponApplyObject(request);
		resultVO.setResultMsg("成功");
		resultVO.setResultCode("0");
		resultVO.setResultData(applyObject);
		return resultVO;
	}
	
	@ApiOperation(value = "权益适用地区查询", notes = "传入CommonQueryByMktResIdReqDTO，进行查询操作")
	@RequestMapping(value = "/queryMktResRegion", method = RequestMethod.POST)
	public ResultVO queryMktResRegion(@RequestBody @ApiParam(value = "查询条件", required = true) CommonQueryByMktResIdReqDTO request ) {
		ResultVO resultVO = new ResultVO();
		log.info("request parameter request {}",request);
		Page<MktResRegionRespDTO> resRegion = mktResRegionService.queryMktResRegion(request);
		resultVO.setResultMsg("成功");
		resultVO.setResultCode("0");
		resultVO.setResultData(resRegion);
		return resultVO;
	}
	
	
}
