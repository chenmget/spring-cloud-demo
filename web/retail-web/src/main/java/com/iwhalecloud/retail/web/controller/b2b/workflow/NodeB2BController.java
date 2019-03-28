package com.iwhalecloud.retail.web.controller.b2b.workflow;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.dto.NodeDTO;
import com.iwhalecloud.retail.workflow.service.NodeService;
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
@RequestMapping("/api/b2b/node")
@Slf4j
@Api(value="环节增删改查",tags={"环节增删改查"})
public class NodeB2BController {
    
    @Reference
    private NodeService nodeService;

    @ApiOperation(value = "添加环节", notes = "添加环节")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/addNode")
    public ResultVO<Boolean> addNode(@RequestBody NodeDTO req){
        log.info("NodeB2BController addNode req={} ", JSON.toJSON(req));
        return nodeService.addNode(req);
    }

    @ApiOperation(value = "编辑环节", notes = "编辑环节")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/editNode")
    public ResultVO<Boolean> editNode(@RequestBody NodeDTO req){
        log.info("NodeB2BController editNode req={} ", JSON.toJSON(req));
        return nodeService.editNode(req);
    }

    @ApiOperation(value = "删除环节", notes = "删除环节")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/delNode")
    public ResultVO<Boolean> delNode(@RequestParam(value = "nodeId") String nodeId){
        log.info("NodeB2BController delNode nodeId={} ", nodeId);
        return nodeService.delNode(nodeId);
    }

    @ApiOperation(value = "根据条件进行环节分页查询", notes = "条件分页查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="/listNodeByCondition")
    public ResultVO<IPage<NodeDTO>> listNodeByCondition(
            @RequestParam(value = "pageNo") Integer pageNo,
            @RequestParam(value = "pageSize") Integer pageSize,
            @RequestParam(value = "nodeName", required = false) String nodeName
    ){
        return nodeService.listNodeByCondition(pageNo, pageSize, nodeName);
    }
}
