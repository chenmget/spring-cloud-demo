package com.iwhalecloud.retail.web.controller.report;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.consts.GoodsCountRankConstants;
import com.iwhalecloud.retail.oms.service.EventOperationDeskService;
import com.iwhalecloud.retail.order.dto.response.OrderAmountCategoryRespDTO;
import com.iwhalecloud.retail.order.dto.response.PastWeekOrderAmountRespDTO;
import com.iwhalecloud.retail.order.dto.resquest.*;
import com.iwhalecloud.retail.order.service.OrderSummaryService;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.BaseController;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author zwl
 * @date 2018-11-09
 * 订单统计相关 (分销商 供货商）
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/orderSummary")
public class OrderSummaryController extends BaseController {

    @Reference
    private OrderSummaryService orderSummaryService;
    @Reference
    private EventOperationDeskService eventOperationDeskService;

    /**
     * 获取 分销商或供货商的今日数据：待处理订单、订单数量 、销售额、 加购数
     * @return
     */
    @ApiOperation(value = "获取供货商或分销商的今日数据：待处理订单、订单数量 、销售额、 加购数", notes = "后端会区分角色类型查出对应的数据")
    @ApiImplicitParams({
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping("/getTodayData")
    @UserLoginToken
    public ResultVO getTodayData() {
        UserDTO userDTO = UserContext.getUser();
        if(userDTO == null){
            return ResultVO.errorEnum(ResultCodeEnum.NOT_LOGIN);
        }

        TodayTodoOrderCountReq todoCountReq = new TodayTodoOrderCountReq();
        TodayOrderCountReq countReq = new TodayOrderCountReq();
        TodayOrderAmountReq amountReq = new TodayOrderAmountReq();
        //  加购数
        Map objMap = Maps.newConcurrentMap();
        if(userDTO.getUserFounder() == SystemConst.USER_FOUNDER_3
                && !StringUtils.isEmpty(userDTO.getRelCode())){
            // 分销商
            todoCountReq.setPartnerId(userDTO.getRelCode());
            countReq.setPartnerId(userDTO.getRelCode());
            amountReq.setPartnerId(userDTO.getRelCode());
            //  加购数
            objMap.put("cartCount", eventOperationDeskService.getTodayEventCountByPartnerId(userDTO.getRelCode(), GoodsCountRankConstants.GOODS_CART_EVENT));
        } else if(userDTO.getUserFounder() == SystemConst.USER_FOUNDER_4
                && !StringUtils.isEmpty(userDTO.getRelCode())){
            // 供货商
            todoCountReq.setSupplierId(userDTO.getRelCode());
            countReq.setSupplierId(userDTO.getRelCode());
            amountReq.setSupplierId(userDTO.getRelCode());
            objMap.put("cartCount", 0);
        } else {
            return failResultVO("用户信息有误，未能查到数据！");
        }

        objMap.put("todoOrderCount", orderSummaryService.getTodayTodoOrderCount(todoCountReq));
        objMap.put("orderCount", orderSummaryService.getTodayOrderCount(countReq));
        objMap.put("orderAmount", orderSummaryService.getTodayOrderAmount(amountReq));
        return successResultVO(objMap);
    }

    /**
     * 获取 分销商或供货商的 过去一周的订单销售额
     * @return
     */
    @ApiOperation(value = "获取供货商或分销商的过去一周的订单销售额", notes = "后端会区分角色类型查出对应的数据")
    @ApiImplicitParams({
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping("/getPastWeekOrderAmount")
//    @UserLoginToken
    public ResultVO getPastWeekOrderAmount() {
//        UserDTO userDTO = UserContext.getUser();
//        if(userDTO == null){
//            return ResultVO.errorEnum(ResultCodeEnum.NOT_LOGIN);
//        }
        PastWeekOrderAmountReq req = new PastWeekOrderAmountReq();
//        if(userDTO.getUserFounder() == SystemConst.USER_FOUNDER_3
//                && !StringUtils.isEmpty(userDTO.getRelCode())){
//            // 分销商
//            req.setPartnerId(userDTO.getRelCode());
//        } else if(userDTO.getUserFounder() == SystemConst.USER_FOUNDER_4
//                && !StringUtils.isEmpty(userDTO.getRelCode())){
//            // 供货商
//            req.setSupplierId(userDTO.getRelCode());
//        } else {
//            return failResultVO("用户信息有误，未能查到数据！");
//        }
        req.setSupplierId("4301811022885");
        List<PastWeekOrderAmountRespDTO> result = orderSummaryService.getPastWeekOrderAmount(req);
        // 计算总数 和均值
        float total = 0;
        float average = 0;
        for (PastWeekOrderAmountRespDTO item : result){
            total = total + item.getOrderAmount();
        }
        average = total/result.size();
        Map objMap = Maps.newConcurrentMap();
        objMap.put("total", total);
        objMap.put("average", average);
        objMap.put("list", result);
        return successResultVO(objMap);
    }

    /**
     * 获取 分销商或供货商的 订单分类统计
     * @return
     */
    @ApiOperation(value = "获取供货商或分销商的订单分类统计", notes = "后端会区分角色类型查出对应的数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryType", value = "queryType 0：今天  1：本月  2：本年  默认0", paramType = "path", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping("/getOrderAmountCategory")
    @UserLoginToken
    public ResultVO getOrderAmountCategory(
            @RequestParam(value = "queryType", required = true) int queryType
            ) {
        UserDTO userDTO = UserContext.getUser();
        if(userDTO == null){
            return ResultVO.errorEnum(ResultCodeEnum.NOT_LOGIN);
        }
        OrderAmountCategoryReq req = new OrderAmountCategoryReq();
        if(userDTO.getUserFounder() == SystemConst.USER_FOUNDER_3
                && !StringUtils.isEmpty(userDTO.getRelCode())){
            // 分销商
            req.setPartnerId(userDTO.getRelCode());
        } else if(userDTO.getUserFounder() == SystemConst.USER_FOUNDER_4
                && !StringUtils.isEmpty(userDTO.getRelCode())){
            // 供货商
            req.setSupplierId(userDTO.getRelCode());
        } else {
            return failResultVO("用户信息有误，未能查到数据！");
        }
        req.setQueryType(queryType);
        List<OrderAmountCategoryRespDTO> resultList = orderSummaryService.getOrderAmountCategory(req);
        //处理结果列表 计算总数
        float total = 0;
        for (OrderAmountCategoryRespDTO item : resultList){
            total = total + item.getOrderAmount();
        }
        Map objMap = Maps.newConcurrentMap();
        objMap.put("total", total);
        objMap.put("list", resultList);
        return successResultVO(objMap);
    }

}
