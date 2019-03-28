package com.iwhalecloud.retail.web.controller.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.goods.common.ResultCodeEnum;
import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.dto.req.ProdCatComplexAddReq;
import com.iwhalecloud.retail.goods.dto.req.ProdGoodsCatsListByIdReq;
import com.iwhalecloud.retail.goods.dto.resp.ComplexGoodsGetResp;
import com.iwhalecloud.retail.goods.dto.resp.ProdGoodsCatListResp;
import com.iwhalecloud.retail.goods.service.dubbo.ProdGoodsCatService;
import com.iwhalecloud.retail.web.controller.goods.request.ProdCatComplexBatchAddReq;
import io.swagger.annotations.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author he.sw
 * @date 2018/12/01
 */ 
@RestController
@RequestMapping("/api/goodsCats")
public class GoodsCatsController {

	@Reference
    private ProdGoodsCatService goodsCatsService;

	@ApiOperation(value = "购物车查询", notes = "查询所有操作tt")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "catId", value = "catId", paramType = "query", required = false, dataType = "String"),
        @ApiImplicitParam(name = "parentCatId", value = "parentCatId", paramType = "query", required = false, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
	@GetMapping(value="listCats")
    public ResultVO<List<ProdGoodsCatListResp>> listCats(String catId, String parentCatId) {
		ProdGoodsCatsListByIdReq req = new ProdGoodsCatsListByIdReq();
		req.setCatId(catId);
		req.setParentCatId(parentCatId);
		return goodsCatsService.listCats(req);
        
    }


	@ApiOperation(value = "购物车查询", notes = "传入购物车ID进行查询操作")
    @ApiImplicitParam(name = "catId", value = "catId", paramType = "query", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="getComplexGoods")
    public  ResultVO<List<ComplexGoodsGetResp>> getComplexGoods(@RequestParam(value = "catId") String catId) {
    	return goodsCatsService.getComplexGoods(catId);
        
    }


	@ApiOperation(value = "购物车添加", notes = "传入购物车集合进行添加操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="addCatComplex")
    public ResultVO<Integer> addCatComplex(@RequestBody ProdCatComplexBatchAddReq req) {
        if (StringUtils.isEmpty(req) || CollectionUtils.isEmpty(req.getCatComplexList())) {
            return ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        List<ProdCatComplexAddReq> catComplexAddReqs = req.getCatComplexList();
        return goodsCatsService.addCatComplex(catComplexAddReqs);
      
    }
}
