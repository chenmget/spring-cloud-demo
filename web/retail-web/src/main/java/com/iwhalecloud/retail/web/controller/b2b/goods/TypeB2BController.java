package com.iwhalecloud.retail.web.controller.b2b.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.common.GlobalConsts;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.TypeDTO;
import com.iwhalecloud.retail.goods2b.dto.req.TypeDeleteByIdReq;
import com.iwhalecloud.retail.goods2b.dto.req.TypeIsUsedQueryByIdReq;
import com.iwhalecloud.retail.goods2b.dto.req.TypeListByNameReq;
import com.iwhalecloud.retail.goods2b.dto.req.TypeSelectByIdReq;
import com.iwhalecloud.retail.goods2b.dto.resp.TypeDetailResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.TypeService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/b2b/type")
public class TypeB2BController {

    @Reference
    private TypeService typeService;

    @ApiOperation(value = "插入产品类型", notes = "")
    @RequestMapping(value = "/saveType", method = RequestMethod.POST)
    public ResultVO saveType(@RequestBody @ApiParam(value = "插入产品类型", required = true) TypeDTO request) {
        ResultVO saveTypeResponse = new ResultVO();
        request.setSourceFrom(GlobalConsts.SOURCE_FROM);
        return typeService.saveType(request);
    }

    @ApiOperation(value = "修改产品类型", notes = "")
    @RequestMapping(value = "/updateType", method = RequestMethod.POST)
    public ResultVO updateType(@RequestBody @ApiParam(value = "修改产品类型", required = true) TypeDTO request) {
        return typeService.updateType(request);
    }

    @ApiOperation(value = "删除产品类型", notes = "")
    @RequestMapping(value = "/deleteType/{typeId}", method = RequestMethod.POST)
    public ResultVO deleteType(@PathVariable("typeId") String typeId) {
        TypeDeleteByIdReq req = new TypeDeleteByIdReq();
        req.setTypeId(typeId);
        return typeService.deleteType(req);
    }

    @ApiOperation(value = "查询产品列表", notes = "")
    @GetMapping(value = "/listType")
    public ResultVO listType(@RequestParam(value = "typeName", required = false) String typeName){
        ResultVO listTypeResp = new ResultVO();
        TypeListByNameReq req = new TypeListByNameReq();
        req.setTypeName(typeName);
        ResultVO list = typeService.listType(req);
        listTypeResp.setResultCode(list.getResultCode());
        listTypeResp.setResultMsg(list.getResultMsg());
        listTypeResp.setResultData(list.getResultData());
        return listTypeResp;
    }

    @ApiOperation(value = "查询产品列表", notes = "")
    @GetMapping(value = "/selectById/{typeId}")
    public ResultVO selectById(@PathVariable("typeId") String typeId){
        ResultVO listTypeResp = new ResultVO();
        TypeSelectByIdReq req = new TypeSelectByIdReq();
        req.setTypeId(typeId);
        ResultVO list = typeService.selectById(req);
        listTypeResp.setResultCode(list.getResultCode());
        listTypeResp.setResultMsg(list.getResultMsg());
        listTypeResp.setResultData(list.getResultData());
        return listTypeResp;
    }

    @ApiOperation(value = "校验类型是否关联产品", notes = "传入typeId进行查询操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "typeId", value = "类型id", paramType = "query", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "/typeIsUsed")
    public ResultVO<Boolean> typeIsUsed(@RequestParam String typeId) {
        TypeIsUsedQueryByIdReq req = new TypeIsUsedQueryByIdReq();
        req.setTypeId(typeId);
        return typeService.typeIsUsed(req);
    }

    @ApiOperation(value = "产品类型详细分类", notes = "传入typeId进行查询操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "typeId", value = "类型id", paramType = "query", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "/getDetailType")
    public ResultVO<TypeDetailResp> getDetailType(@RequestParam String typeId) {
        TypeSelectByIdReq req = new TypeSelectByIdReq();
        req.setTypeId(typeId);
        return typeService.getDetailType(req);
    }

}
