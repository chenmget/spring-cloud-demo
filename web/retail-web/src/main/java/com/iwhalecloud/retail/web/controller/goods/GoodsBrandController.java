package com.iwhalecloud.retail.web.controller.goods;


import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.goods.common.ResultCodeEnum;
import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.dto.req.ProdBrandAddReq;
import com.iwhalecloud.retail.goods.dto.req.ProdBrandDeleteReq;
import com.iwhalecloud.retail.goods.dto.req.ProdBrandGetReq;
import com.iwhalecloud.retail.goods.dto.req.ProdBrandUpdateReq;
import com.iwhalecloud.retail.goods.dto.resp.ProdBrandGetResp;
import com.iwhalecloud.retail.goods.service.dubbo.ProdBrandService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author he.sw
 * @date 2018/12/01
 */
@RestController
@RequestMapping("/api/goodsBrand")
@Slf4j
public class GoodsBrandController {

	@Reference
    private ProdBrandService goodsBrandService;

	@ApiOperation(value = "品牌查询", notes = "传入品牌ID，商品ID进行查询操作")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "brandId", value = "brandId", paramType = "query", required = true, dataType = "String"),
        @ApiImplicitParam(name = "goodsId", value = "goodsId", paramType = "query", required = false, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="getBrand")
    public ResultVO<ProdBrandGetResp> getBrand(@RequestParam(value = "brandId", required = false) String brandId,
                             @RequestParam(value = "goodsId", required = false) String goodsId) {
        log.info("GoodsBrandController.getBrand brandId={}, goodsId={}", brandId, goodsId);
        if (StringUtils.isEmpty(brandId) && StringUtils.isEmpty(goodsId)) {
            ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        ProdBrandGetReq req = new ProdBrandGetReq();
        req.setBrandId(brandId);
        req.setGoodsId(goodsId);
        return goodsBrandService.getBrand(req);
    }

	@ApiOperation(value = "品牌查询", notes = "查询所有操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="listBrand")
    public ResultVO<List<ProdBrandGetResp>> listBrand() {
    	return goodsBrandService.listBrand();
    }

	@ApiOperation(value = "添加品牌", notes = "查询所有操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="addBrand")
    public ResultVO<String> addBrand(@RequestBody ProdBrandAddReq req) {
        return goodsBrandService.addBrand(req);
        
    }

	@ApiOperation(value = "更新品牌", notes = "更新操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PutMapping(value="updateBrand")
    public ResultVO<Integer> updateBrand(@RequestBody ProdBrandUpdateReq req) {
        return goodsBrandService.updateBrand(req);
        
    }

	@ApiOperation(value = "删除品牌", notes = "删除操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @DeleteMapping(value="deleteBrand")
    public ResultVO<Integer> deleteBrand(@RequestBody ProdBrandDeleteReq req) {
        return goodsBrandService.deleteBrand(req);
    }
}
