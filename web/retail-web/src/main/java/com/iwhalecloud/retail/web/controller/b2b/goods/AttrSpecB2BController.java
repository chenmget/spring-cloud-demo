package com.iwhalecloud.retail.web.controller.b2b.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.goods2b.dto.AttrSpecDTO;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.AttrSpecAddReq;
import com.iwhalecloud.retail.goods2b.dto.req.AttrSpecUpdateReq;
import com.iwhalecloud.retail.goods2b.service.AttrSpecService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/b2b/attrSpec")
@Slf4j
public class AttrSpecB2BController {
    @Reference
    private AttrSpecService attrSpecService;

    @ApiOperation(value = "新增属性规格", notes = "单个新增")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/addAttrSpec")
    @UserLoginToken
    public ResultVO addAttrSpec(@RequestBody AttrSpecAddReq req){
        return attrSpecService.addAttrSpec(req, UserContext.getUserId());
    }

    @ApiOperation(value = "删除属性规格", notes = "根据id，进行删除操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", paramType = "path", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @DeleteMapping(value = "/deleteAttrSpec/{id}")
    @UserLoginToken
    public ResultVO deleteAttrSpec(@PathVariable String id){
        return attrSpecService.deleteAttrSpec(id);
    }

    @ApiOperation(value = "修改属性规格", notes = "单个更新")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PutMapping(value = "/updateAttrSpec")
    @UserLoginToken
    public ResultVO updateAttrSpec(@RequestBody AttrSpecUpdateReq req){
        return attrSpecService.updateAttrSpec(req, UserContext.getUserId());
    }

    /*@ApiOperation(value = "查询类别属性与设置的实例值", notes = "查询列表")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "/queryAttrSpecWithInstValue")
    public ResultVO<List<AttrSpecDTO>> queryAttrSpecWithInstValue(
            @RequestParam( value = "typeId", required = true )String typeId,
            @RequestParam( value = "goodsId", required = true )String goodsId) {
        return attrSpecService.queryAttrSpecWithInstValue(typeId,goodsId);
    }

    @ApiOperation(value = "根据类别获取属性规格配置", notes = "查询列表")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "/queryAttrSpecList")
    public ResultVO<List<AttrSpecDTO>> queryAttrSpecList(
            @RequestParam( value = "typeId", required = true )String typeId) {
        return attrSpecService.queryAttrSpecList(typeId);
    }*/
}
