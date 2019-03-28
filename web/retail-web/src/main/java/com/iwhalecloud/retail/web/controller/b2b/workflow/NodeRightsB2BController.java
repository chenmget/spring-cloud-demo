package com.iwhalecloud.retail.web.controller.b2b.workflow;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.dto.NodeRightsDTO;
import com.iwhalecloud.retail.workflow.service.NodeRightsService;
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
@RequestMapping("/api/b2b/nodeRights")
@Slf4j
@Api(value="环节权限增删改查",tags={"环节权限增删改查"})
public class NodeRightsB2BController {
    
    @Reference
    private NodeRightsService nodeRightsService;

    @ApiOperation(value = "添加环节权限", notes = "添加环节权限")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/addNodeRights")
    public ResultVO<Boolean> addNodeRights(@RequestBody NodeRightsDTO req){
        log.info("NodeRightsB2BController addNodeRights req={} ", JSON.toJSON(req));
        return nodeRightsService.addNodeRights(req);
    }

    @ApiOperation(value = "编辑环节权限", notes = "编辑环节权限")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/editNodeRights")
    public ResultVO<Boolean> editNodeRights(@RequestBody NodeRightsDTO req){
        log.info("NodeRightsB2BController editNodeRights req={} ", JSON.toJSON(req));
        return nodeRightsService.editNodeRights(req);
    }

    @ApiOperation(value = "删除环节权限", notes = "删除环节权限")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/delNodeRights")
    public ResultVO<Boolean> delNodeRights(@RequestParam(value = "rightsId") String rightsId){
        log.info("NodeRightsB2BController delNodeRights rightsId={} ", rightsId);
        return nodeRightsService.delNodeRights(rightsId);
    }

    @ApiOperation(value = "根据条件进行环节权限分页查询", notes = "条件分页查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="/listNodeRightsByCondition")
    public ResultVO<List<NodeRightsDTO>> listNodeRightsByCondition(
            @RequestParam(value = "nodeId", required = false) String nodeId
    ){
        return nodeRightsService.listNodeRightsByCondition(nodeId);
    }
}
