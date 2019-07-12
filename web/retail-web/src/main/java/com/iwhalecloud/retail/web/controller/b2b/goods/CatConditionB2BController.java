package com.iwhalecloud.retail.web.controller.b2b.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.CatConditionDTO;
import com.iwhalecloud.retail.goods2b.dto.req.CatConditionListReq;
import com.iwhalecloud.retail.goods2b.dto.req.CatConditionSaveReq;
import com.iwhalecloud.retail.goods2b.dto.resp.CatConditionDetailResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.CatConditionService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wenlong.zhong
 * @date 2019/6/3
 */
@RestController
@Slf4j
@RequestMapping("/api/b2b/catCondition")
@Api(value = "商品类型条件控制器", tags = {"商品类型条件控制器"})
public class CatConditionB2BController {

    @Reference
    private CatConditionService catConditionService;

    /**
     * 添加一个 商品类型条件
     * @param req
     * @return
     */
    @ApiOperation(value = "添加一个 商品类型条件 记录", notes = "添加一个 商品类型条件 记录")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/save")
    public ResultVO<Integer> saveCatCondition(@RequestBody @ApiParam(value = "添加一个 商品类型条件 记录的入参数", required = true) CatConditionSaveReq req) {
        log.info("CatConditionB2BController.saveCatCondition() input:req={} ", JSON.toJSONString(req));
        ResultVO resultVO = catConditionService.saveCatCondition(req);
        log.info("CatConditionB2BController.saveCatCondition() out:resultVO={} ", JSON.toJSONString(resultVO));
        return resultVO;
    }

    /**
     * 商品类型条件 列表查询
     * @param req
     * @return
     */
    @ApiOperation(value = "商品类型条件 列表查询", notes = "商品类型条件 列表查询")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/list")
    public ResultVO<List<CatConditionDTO>> listCatCondition(@RequestBody @ApiParam(value = "商品类型条件 列表查询的入参数", required = true) CatConditionListReq req) {
        log.info("CatConditionB2BController.listCatCondition() input:req={} ", JSON.toJSONString(req));
        ResultVO resultVO = catConditionService.listCatCondition(req);
        log.info("CatConditionB2BController.listCatCondition() out:resultVO={} ", JSON.toJSONString(resultVO));
        return resultVO;
    }

    /**
     * 根据商品分类ID获取 商品分类条件 详情
     * @param catId
     * @return
     */
    @ApiOperation(value = "根据商品分类ID获取 商品分类条件 详情", notes = "根据商品分类ID获取 商品分类条件 详情")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "/getDetailByCatId")
    public ResultVO<CatConditionDetailResp> listCatConditionDetail(@RequestParam(value = "catId", required = true) String catId) {
        log.info("CatConditionB2BController.listCatConditionDetail() input: catId = {} ", JSON.toJSONString(catId));
        ResultVO<CatConditionDetailResp> resultVO = catConditionService.getCatConditionDetail(catId);
        log.info("CatConditionB2BController.listCatConditionDetail() out:resultVO={} ", JSON.toJSONString(resultVO));
        return resultVO;
    }

}
