package com.iwhalecloud.retail.web.controller.order;

import com.iwhalecloud.retail.dto.ResultVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.order.common.ResultCodeEnum;
import com.iwhalecloud.retail.order.dto.response.OrderRecommenderPageResp;
import com.iwhalecloud.retail.order.dto.response.OrderRecommenderRankResp;
import com.iwhalecloud.retail.order.dto.response.OrderRecommenderResp;
import com.iwhalecloud.retail.order.dto.resquest.AddOrderRecommenderReqDTO;
import com.iwhalecloud.retail.order.dto.resquest.ListOrderRecommenderReq;
import com.iwhalecloud.retail.order.service.OrderRecommenderService;
import com.iwhalecloud.retail.web.controller.BaseController;

@RestController
@RequestMapping("/api/oderRecommender")
@Slf4j
public class OrderRecommenderController extends BaseController {

	@Reference
	private OrderRecommenderService orderRecommenderService;
	
	@ApiOperation(value = "查看用户揽装记录", notes = "传入userId，进行查询操作")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "staffId", value = "staffId", paramType = "query", required = true, dataType = "String"),
        @ApiImplicitParam(name = "pageNo", value = "pageNo", paramType = "query", required = true, dataType = "Integer"),
        @ApiImplicitParam(name = "pageSize", value = "pageSize", paramType = "query", required = true, dataType = "Integer")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "/listOrderRecommender")
    public ResultVO listOrderRecommender(@RequestParam String staffId,
                                         @RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        ResultVO resultVO = new ResultVO();
        log.info("request parameter staffId {}",staffId);
    	if(null == staffId){
    		return ResultVO.error(OmsCommonConsts.RESULE_CODE_FAIL, "staffId不能为空");
        }
    	ListOrderRecommenderReq request = new ListOrderRecommenderReq();
    	request.setStaffId(staffId);
    	request.setPageNo(pageNo);
    	request.setPageSize(pageSize);
        Page<OrderRecommenderPageResp> resp = orderRecommenderService.listOrderRecommender(request);
        resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        resultVO.setResultData(resp);
        resultVO.setResultMsg("成功");
        return resultVO;
    }
	
	@ApiOperation(value = "查看订单揽装记录", notes = "传入orderId，进行查询操作")
    @ApiImplicitParam(name = "orderId", value = "orderId", paramType = "query", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "/getUserOrderRecommender")
    public ResultVO getUserOrderRecommender(@RequestParam String orderId) {
        ResultVO resultVO = new ResultVO();
        log.info("request parameter orderId {}",orderId);
        
    	List<OrderRecommenderResp> resp = orderRecommenderService.getOrderRecommender(orderId);
        resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        resultVO.setResultData(resp);
        resultVO.setResultMsg("成功");
        
        return resultVO;
    }
	
	@ApiOperation(value = "揽装关系录入", notes = "传入AddOrderRecommenderReqDTO，进行保存操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/addOrderRecommender", method = RequestMethod.POST)
    public ResultVO addOrderRecommender(@RequestBody @ApiParam( value = "AddOrderRecommenderReq", required = true )AddOrderRecommenderReqDTO request) {
        ResultVO resultVO = new ResultVO();
        log.info("request parameter {}",request);
        
        Integer resp = orderRecommenderService.addOrderRecommender(request);
        if (null == resp || 1 > resp) {
        	return failResultVO();
		}
        resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        resultVO.setResultData(resp);
        resultVO.setResultMsg("成功");
            
        return resultVO;
    }
	
	@ApiOperation(value = "揽装排行榜", notes = "查看揽装排行榜")
    @ApiImplicitParam(name = "shopId", value = "shopId", paramType = "query", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "/listOrderRecommenderRank")
    public ResultVO listOrderRecommenderRank(@RequestParam String shopId) {
        ResultVO resultVO = new ResultVO();
        log.info("request shopId {}",shopId);
        
        List<OrderRecommenderRankResp> resp = orderRecommenderService.listOrderRecommenderRank(shopId);
        resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        resultVO.setResultData(resp); 
        resultVO.setResultMsg("成功");
            
        return resultVO;
    }
	
}
