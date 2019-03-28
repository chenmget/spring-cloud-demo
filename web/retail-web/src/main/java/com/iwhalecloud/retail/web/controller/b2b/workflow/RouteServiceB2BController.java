package com.iwhalecloud.retail.web.controller.b2b.workflow;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.dto.RouteServiceDTO;
import com.iwhalecloud.retail.workflow.service.RouteServiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author mzl
 * @date 2019/1/16
 */
@RestController
@RequestMapping("/api/b2b/routeService")
@Slf4j
@Api(value="路由路由服务增删改查",tags={"路由路由服务增删改查"})
public class RouteServiceB2BController {
    
    @Reference
    private RouteServiceService routeServiceService;

    @ApiOperation(value = "添加路由服务", notes = "添加路由服务")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/addRouteService")
    public ResultVO<Boolean> addRouteService(@RequestBody RouteServiceDTO req){
        log.info("RouteServiceB2BController addRouteService req={} ", JSON.toJSON(req));
        return routeServiceService.addRouteService(req);
    }

    @ApiOperation(value = "编辑路由服务", notes = "编辑路由服务")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/editRouteService")
    public ResultVO<Boolean> editRouteService(@RequestBody RouteServiceDTO req){
        log.info("RouteServiceB2BController editRouteService req={} ", JSON.toJSON(req));
        return routeServiceService.editRouteService(req);
    }

    @ApiOperation(value = "删除路由服务", notes = "删除路由服务")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/delRouteService")
    public ResultVO<Boolean> delRouteService(@RequestParam(value = "routeServiceId") String routeServiceId){
        log.info("RouteServiceB2BController delService routeServiceId={} ", routeServiceId);
        return routeServiceService.delRouteService(routeServiceId);
    }

    @ApiOperation(value = "根据条件进行路由服务列表查询", notes = "条件列表查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="/listRouteServiceByCondition")
    public ResultVO<List<RouteServiceDTO>> listRouteServiceByCondition(
            @RequestParam(value = "routeId", required = false) String routeId
    ){
        return routeServiceService.listRouteService(routeId);
    }
}
