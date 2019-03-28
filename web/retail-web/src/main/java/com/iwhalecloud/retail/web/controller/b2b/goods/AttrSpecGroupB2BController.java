package com.iwhalecloud.retail.web.controller.b2b.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.AttrSpecGroupDTO;
import com.iwhalecloud.retail.goods2b.service.AttrSpecGroupService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/b2b/attrSpecGroup")
@Slf4j
public class AttrSpecGroupB2BController {
    @Reference
    private AttrSpecGroupService attrSpecGroupService;

    @ApiOperation(value = "新增属性组", notes = "单个新增")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/addAttrSpecGroup")
    public ResultVO addAttrSpecGroup(@RequestBody AttrSpecGroupDTO entity){
        return attrSpecGroupService.addAttrSpecGroup(entity);
    }

    @ApiOperation(value = "删除属性组", notes = "根据id，进行删除操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", paramType = "path", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @DeleteMapping(value = "/deleteAttrSpecGroup/{id}")
    public ResultVO deleteAttrSpecGroup(@PathVariable String id){
        return attrSpecGroupService.deleteAttrSpecGroup(id);
    }

    @ApiOperation(value = "修改属性组", notes = "单个更新")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PutMapping(value = "/updateAttrSpecGroup")
    public ResultVO updateAttrSpec(@RequestBody AttrSpecGroupDTO entity){
        return attrSpecGroupService.updateAttrSpecGroup(entity);
    }

    @ApiOperation(value = "根据条件查询属性组", notes = "查询列表，条件为空查询所有")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/listAttrSpecGroupByCondition")
    public ResultVO<List<AttrSpecGroupDTO>> queryAttrSpecWithInstValue(@RequestBody AttrSpecGroupDTO condition) {
        return attrSpecGroupService.listAttrSpecGroupByCondition(condition);
    }

}
