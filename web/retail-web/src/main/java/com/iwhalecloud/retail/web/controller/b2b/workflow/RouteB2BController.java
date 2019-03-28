package com.iwhalecloud.retail.web.controller.b2b.workflow;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.dto.RouteDTO;
import com.iwhalecloud.retail.workflow.service.RouteService;
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
@RequestMapping("/api/b2b/route")
@Slf4j
@Api(value="路由增删改查",tags={"路由增删改查"})
public class RouteB2BController {
    
    @Reference
    private RouteService routeService;

    @ApiOperation(value = "添加路由", notes = "添加路由")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/addRoute")
    public ResultVO<Boolean> addRoute(@RequestBody RouteDTO req){
        log.info("RouteB2BController addRoute req={} ", JSON.toJSON(req));
        return routeService.addRoute(req);
    }

    @ApiOperation(value = "编辑路由", notes = "编辑路由")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/editRoute")
    public ResultVO<Boolean> editRoute(@RequestBody RouteDTO req){
        log.info("RouteB2BController editRoute req={} ", JSON.toJSON(req));
        return routeService.editRoute(req);
    }

    @ApiOperation(value = "删除路由", notes = "删除路由")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/delRoute")
    public ResultVO<Boolean> delRoute(@RequestParam(value = "routeId") String routeId){
        log.info("RouteB2BController delRoute routeId={} ", routeId);
        return routeService.delRoute(routeId);
    }

    @ApiOperation(value = "根据条件进行路由列表查询", notes = "条件列表查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="/listRouteByCondition")
    public ResultVO<List<RouteDTO>> listRouteByCondition(
            @RequestParam(value = "routeName", required = false) String routeName
    ){
        return routeService.listRouteByCondition(routeName);
    }
}
