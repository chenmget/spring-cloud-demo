package com.iwhalecloud.retail.web.controller.b2b.workflow;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.dto.ProcessDTO;
import com.iwhalecloud.retail.workflow.service.ProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author mzl
 * @date 2019/1/16
 */
@RestController
@RequestMapping("/api/b2b/process")
@Slf4j
@Api(value="流程增删改查",tags={"流程增删改查"})
public class ProcessB2BController {
    
    @Reference
    private ProcessService processService;

    @ApiOperation(value = "添加流程", notes = "添加流程")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/addProcess")
    public ResultVO<Boolean> addProcess(@RequestBody ProcessDTO req){
        log.info("ProcessB2BController addProcess req={} ", JSON.toJSON(req));
        return processService.addProcess(req);
    }

    @ApiOperation(value = "编辑流程", notes = "编辑流程")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/editProcess")
    public ResultVO<Boolean> editProcess(@RequestBody ProcessDTO req){
        log.info("ProcessB2BController editProcess req={} ", JSON.toJSON(req));
        return processService.editProcess(req);
    }

    @ApiOperation(value = "删除流程", notes = "删除流程")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/delProcess")
    public ResultVO<Boolean> delProcess(@RequestParam(value = "processId") String processId){
        log.info("ProcessB2BController delProcess req={} ", processId);
        return processService.delProcess(processId);
    }

    @ApiOperation(value = "根据条件进行流程分页查询", notes = "条件分页查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="/listProcessByCondition")
    public ResultVO<IPage<ProcessDTO>> listProcessByCondition(
            @RequestParam(value = "pageNo") Integer pageNo,
            @RequestParam(value = "pageSize") Integer pageSize,
            @RequestParam(value = "processName", required = false) String processName
    ){
        return processService.listProcessByCondition(pageNo, pageSize, processName);
    }
}
