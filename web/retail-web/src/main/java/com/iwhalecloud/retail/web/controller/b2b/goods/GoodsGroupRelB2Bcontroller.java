package com.iwhalecloud.retail.web.controller.b2b.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.GoodsGroupRelDTO;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsGroupRelAddReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsGroupRelService;
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
@RequestMapping("/api/b2b/goodsGroupRel")
@Slf4j
public class GoodsGroupRelB2Bcontroller {
    @Reference
    private GoodsGroupRelService goodsGroupRelService;

    @ApiOperation(value = "添加商品组关联商品", notes = "添加商品组关联商品")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/add")
    public ResultVO<Integer> add(@RequestBody GoodsGroupRelAddReq req){

        return ResultVO.success(goodsGroupRelService.insertGoodsGroupRel(req));
    }

    @ApiOperation(value = "根据主键删除商品组关联商品", notes = "根据主键删除商品组关联商品")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/deleteOneGoodsGroupRel")
    public ResultVO<Integer> deleteOneGoodsGroupRel(@RequestParam( value = "groupRelId")String groupRelId){
        GoodsGroupRelDTO goodsGroupRelDTO = new GoodsGroupRelDTO();
        goodsGroupRelDTO.setGroupRelId(groupRelId);
        return ResultVO.success(goodsGroupRelService.deleteOneGoodsGroupRel(goodsGroupRelDTO));
    }

    @ApiOperation(value = "根据商品id删除商品组关联商品", notes = "根据商品id删除商品组关联商品")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/deleteGoodsGroupRelByGoodsId")
    public ResultVO<Integer> deleteGoodsGroupRelByGoodsId(@RequestParam( value = "goodsId")String goodsId){
        GoodsGroupRelDTO goodsGroupRelDTO = new GoodsGroupRelDTO();
        goodsGroupRelDTO.setGoodsId(goodsId);
        return ResultVO.success(goodsGroupRelService.deleteGoodsGroupRel(goodsGroupRelDTO));
    }

    @ApiOperation(value = "根据商品组ID删除商品组关联商品", notes = "根据商品组ID删除商品组关联商品")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/deleteGoodsGroupRelByGroupId")
    public ResultVO<Integer> deleteGoodsGroupRelByGroupId(@RequestParam( value = "groupId")String groupId){
        GoodsGroupRelDTO goodsGroupRelDTO = new GoodsGroupRelDTO();
        goodsGroupRelDTO.setGroupId(groupId);
        return ResultVO.success(goodsGroupRelService.deleteGoodsGroupRelByGroupId(goodsGroupRelDTO));
    }

    @ApiOperation(value = "修改商品组关联商品", notes = "修改商品组关联商品")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/updateGoodsGroupRel")
    public ResultVO<Integer> updateGoodsGroupRel(@RequestBody GoodsGroupRelDTO req){

        return ResultVO.success(goodsGroupRelService.updateGoodsGroupRel(req));
    }

    @ApiOperation(value = "根据商品ID商品组关联商品", notes = "根据商品ID商品组关联商品")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/queryGoodsGroupRelByGoodsId")
    public ResultVO<List<GoodsGroupRelDTO>> queryGoodsGroupRelByGoodsId(@RequestParam( value = "goodsId")String goodsId){
        List<GoodsGroupRelDTO> goodsGroupRelDTOs = new ArrayList<>();
        GoodsGroupRelDTO goodsGroupRelDTO = new GoodsGroupRelDTO();
        goodsGroupRelDTO.setGoodsId(goodsId);
        goodsGroupRelDTOs = goodsGroupRelService.queryGoodsGroupRelByGoodsId(goodsGroupRelDTO);
        if(goodsGroupRelDTOs == null)
            return ResultVO.error("根据商品ID没查到对应的商品组关联商品记录");
        return ResultVO.success(goodsGroupRelDTOs);
    }

    @ApiOperation(value = "根据商品组ID商品组关联商品", notes = "根据商品组ID商品组关联商品")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/queryGoodsGroupRelByGroupId")
    public ResultVO<List<GoodsGroupRelDTO>> queryGoodsGroupRelByGroupId(@RequestParam( value = "groupId")String groupId){
        List<GoodsGroupRelDTO> goodsGroupRelDTOs = new ArrayList<>();
        GoodsGroupRelDTO goodsGroupRelDTO = new GoodsGroupRelDTO();
        goodsGroupRelDTO.setGroupId(groupId);
        goodsGroupRelDTOs = goodsGroupRelService.queryGoodsGroupRelByGroupId(goodsGroupRelDTO);
        if(goodsGroupRelDTOs == null)
            return ResultVO.error("根据商品组ID没查到对应的商品组关联商品记录");
        return ResultVO.success(goodsGroupRelDTOs);
    }
}
