package com.iwhalecloud.retail.web.controller.b2b.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.ComplexInfoDTO;
import com.iwhalecloud.retail.goods2b.dto.req.ComplexInfoQueryReq;
import com.iwhalecloud.retail.goods2b.dto.req.ComplexInfoReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.ComplexInfoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/3/4.
 */
@RestController
@RequestMapping("/api/b2b/comlpexInfo")
@Slf4j
public class ComplexInfoB2BController {
    @Reference
    private ComplexInfoService complexInfoService;

    @ApiOperation(value = "添加商品关联推荐信息", notes = "添加商品关联推荐信息")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/add")
    public ResultVO<Boolean> add(@RequestBody ComplexInfoReq req){
        Boolean result = false;
        result = complexInfoService.batchAddComplexInfo(req);
        return ResultVO.success(result);
    }

    @ApiOperation(value = "添加单条商品关联推荐信息", notes = "添加单条商品关联推荐信息")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/addOne")
    public ResultVO<Integer> addOne(@RequestBody ComplexInfoDTO complexInfoDTO){
        return ResultVO.success(complexInfoService.insertComplexInfo(complexInfoDTO));
    }

    @ApiOperation(value = "修改商品关联推荐信息", notes = "修改商品关联推荐信息")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/updateComplexInfo")
    public ResultVO<Integer> updateComplexInfo(@RequestBody ComplexInfoDTO complexInfoDTO){
        return ResultVO.success(complexInfoService.updateComplexInfo(complexInfoDTO));
    }

    @ApiOperation(value = "删除单条商品关联推荐信息", notes = "删除单条商品关联推荐信息")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/deleteOneComplexInfo")
    public ResultVO<Integer> deleteOneComplexInfo(@RequestParam( value = "complexInfoId")String complexInfoId){
        ComplexInfoDTO complexInfoDTO = new ComplexInfoDTO();
        complexInfoDTO.setComplexInfoId(complexInfoId);
        return ResultVO.success(complexInfoService.deleteOneComplexInfo(complexInfoDTO));
    }

    @ApiOperation(value = "根据商品id删除商品关联推荐信息", notes = "根据商品id删除商品关联推荐信息")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/deleteComplexInfo")
    public ResultVO<Integer> deleteComplexInfo(@RequestParam( value = "goodsId")String goodsId){
        ComplexInfoDTO complexInfoDTO = new ComplexInfoDTO();
        complexInfoDTO.setAGoodsId(goodsId);
        return ResultVO.success(complexInfoService.deleteComplexInfoByGoodsId(complexInfoDTO));
    }

    @ApiOperation(value = "根据商品id删除商品关联推荐信息", notes = "根据商品id删除商品关联推荐信息")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/queryComplexInfo")
    public ResultVO<List<ComplexInfoDTO>> queryComplexInfo(@RequestBody ComplexInfoQueryReq complexInfoQueryReq){
        List<ComplexInfoDTO> complexInfoDTOList = new ArrayList<>();
        ResultVO<List<ComplexInfoDTO>> resultVO = complexInfoService.queryComplexInfo(complexInfoQueryReq) ;
        if(resultVO.isSuccess() && resultVO.getResultData() != null){
            complexInfoDTOList = resultVO.getResultData();
        }
        return ResultVO.success(complexInfoDTOList);
    }
}
