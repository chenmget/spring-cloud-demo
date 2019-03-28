package com.iwhalecloud.retail.web.controller.b2b.workflow;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.dto.ServiceDTO;
import com.iwhalecloud.retail.workflow.service.ServiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author mzl
 * @date 2019/1/14
 */
@RestController
@RequestMapping("/api/b2b/service")
@Slf4j
@Api(value="服务增删改查",tags={"服务增删改查"})
public class ServiceB2BController  {

    @Reference
    private ServiceService serviceService;

    @ApiOperation(value = "添加服务", notes = "添加服务")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/addService")
    public ResultVO<Boolean> addService(@RequestBody ServiceDTO req){
        log.info("ServiceB2BController addService req={} ", JSON.toJSON(req));
        return serviceService.addService(req);
    }

    @ApiOperation(value = "编辑服务", notes = "编辑服务")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/editService")
    public ResultVO<Boolean> editService(@RequestBody ServiceDTO req){
        log.info("ServiceB2BController editService req={} ", JSON.toJSON(req));
        return serviceService.editService(req);
    }

    @ApiOperation(value = "删除服务", notes = "删除服务")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/delService")
    public ResultVO<Boolean> delService(@RequestParam(value = "serviceId") String serviceId){
        log.info("ServiceB2BController delService req={} ", serviceId);
        return serviceService.delService(serviceId);
    }

    @ApiOperation(value = "根据条件进行服务分页查询", notes = "条件分页查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="/listServiceByCondition")
    public ResultVO<IPage<ServiceDTO>> listServiceByCondition(
            @RequestParam(value = "pageNo") Integer pageNo,
            @RequestParam(value = "pageSize") Integer pageSize,
            @RequestParam(value = "serviceName", required = false) String serviceName
    ){
        return serviceService.listServiceByCondition(pageNo, pageSize, serviceName);
    }
}
