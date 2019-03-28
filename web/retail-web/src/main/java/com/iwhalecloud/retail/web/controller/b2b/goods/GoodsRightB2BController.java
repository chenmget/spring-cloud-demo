package com.iwhalecloud.retail.web.controller.b2b.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.GoodsConst;
import com.iwhalecloud.retail.goods2b.dto.GoodsRightDTO;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsRightAddReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsRightService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2019/3/4.
 */
@RestController
@RequestMapping("/api/b2b/goodsRight")
@Slf4j
public class GoodsRightB2BController {
    @Reference
    private GoodsRightService goodsRightService;

    @ApiOperation(value = "添加商品配置优惠卷", notes = "添加商品配置优惠卷")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/addGoodsRight")
    public ResultVO<Boolean> addGoodsRight(@RequestBody GoodsRightAddReq req){
        ResultVO<Boolean> resultVO =goodsRightService.batchInsertGoodsRight(req);
        Boolean result = false;
        if(resultVO.isSuccess()){
            result = resultVO.getResultData();
        }
        log.info("lumma");
        return ResultVO.success(result);
    }

    @ApiOperation(value = "修改商品配置优惠卷", notes = "修改商品配置优惠卷")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/updateGoodsRight")
    public ResultVO<Integer> updateGoodsRight(@RequestBody GoodsRightDTO req){
        String status = req.getStatus();
        if(StringUtils.isEmpty(status)){
            return ResultVO.error("状态不能为空");
        }else if(GoodsConst.EFFECTIVE.equals(status) || GoodsConst.NO_EFFECTIVE.equals(status)){
            int num = goodsRightService.updateGoodsRight(req);
            return ResultVO.success(num);
        }
        return ResultVO.error("状态只能填1 or 0");
    }

    @ApiOperation(value = "根据商品id删除商品配置优惠卷", notes = "根据商品id删除商品配置优惠卷")
     @ApiResponses({
             @ApiResponse(code=400,message="请求参数没填好"),
             @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
     })
     @PostMapping(value="/deleteGoodsRight")
     public ResultVO<Integer> deleteGoodsRight(@RequestParam( value = "goodsId")String goodsId){
        GoodsRightDTO goodsRightDTO = new GoodsRightDTO();
        goodsRightDTO.setGoodsId(goodsId);
        int num = goodsRightService.deleteGoodsRight(goodsRightDTO);
        return ResultVO.success(num);
    }

    @ApiOperation(value = "根据主键删除商品配置优惠卷", notes = "根据主键删除商品配置优惠卷")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/deleteOneGoodsRight")
    public ResultVO<Integer> deleteOneGoodsRight(@RequestParam( value = "goodsRightsId")String goodsRightsId){
        GoodsRightDTO goodsRightDTO = new GoodsRightDTO();
        goodsRightDTO.setGoodsRightsId(goodsRightsId);
        int num = goodsRightService.deleteOneGoodsRight(goodsRightDTO);
        return ResultVO.success(num);
    }

    @ApiOperation(value = "根据商品id删除商品配置优惠卷", notes = "根据商品id删除商品配置优惠卷")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/listGoodsRight")
    public ResultVO<List<GoodsRightDTO>> listGoodsRight(@RequestParam( value = "goodsId")String goodsId){
        GoodsRightDTO goodsRightDTO = new GoodsRightDTO();
        goodsRightDTO.setGoodsId(goodsId);
        List<GoodsRightDTO> goodsRightDTOList = goodsRightService.listByGoodsId(goodsRightDTO);
        return ResultVO.success(goodsRightDTOList);
    }
}
