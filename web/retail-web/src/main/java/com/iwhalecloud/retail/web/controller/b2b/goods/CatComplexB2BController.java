package com.iwhalecloud.retail.web.controller.b2b.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.GoodsConst;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.dto.resp.CatComplexQueryResp;
import com.iwhalecloud.retail.goods2b.dto.resp.CategoryRecommendQuery;
import com.iwhalecloud.retail.goods2b.dto.resp.GoodsForPageQueryResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.CatComplexService;
import com.iwhalecloud.retail.goods2b.service.dubbo.CatService;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsService;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.web.exception.UserNoMerchantException;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author mzl
 * @date 2018/12/28
 */
@RestController
@RequestMapping("/api/b2b/catComplex")
@Slf4j
public class CatComplexB2BController extends GoodsBaseController {

    @Reference
    private CatComplexService catComplexService;

    @Reference
    private GoodsService goodsService;


    @Reference
    private CatService catService;

    @ApiOperation(value = "查询分类推荐", notes = "查询分类推荐")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="/queryCategoryRecommend")
    ResultVO<List<CategoryRecommendQuery>> queryCategoryRecommend(@RequestParam(value = "catId") String catId) {
        log.info("CatComplexB2BController queryCategoryRecommend catId={} ", catId);
        CatComplexQueryReq catComplexQueryReq = new CatComplexQueryReq();
        List<String> catIdList = Lists.newArrayList();
        catIdList.add(catId);
        List<String> allCatIdList = Lists.newArrayList();
        allCatIdList = getAllCatId(catIdList, allCatIdList, catService);
        if (!CollectionUtils.isEmpty(allCatIdList)) {
            catComplexQueryReq.setCatIdList(allCatIdList);
        }
        if (UserContext.isUserLogin()) {
            catComplexQueryReq.setIsLogin(true);
        }
        return catComplexService.queryCategoryRecommend(catComplexQueryReq);
    }

    @ApiOperation(value = "查询分类销量排行", notes = "查询分类销量排行")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="/queryCategoryRanking")
    ResultVO<Page<GoodsForPageQueryResp>> queryCategoryRanking(@RequestParam(value = "catId") String catId)  throws UserNoMerchantException {
        log.info("CatComplexB2BController queryCategoryRanking catId={} ", catId);
        GoodsForPageQueryReq req = new GoodsForPageQueryReq();
        List<String> catIdList = Lists.newArrayList();
        catIdList.add(catId);
        req.setPageNo(1);
        req.setPageSize(10);
        req.setSortType(GoodsConst.SortTypeEnum.getValueByKey(5));

        // 查询商品分类的所有子分类
        List<String> allCatIdList = Lists.newArrayList();
        allCatIdList = getAllCatId(catIdList, allCatIdList, catService);
        if (!CollectionUtils.isEmpty(allCatIdList)) {
            req.setCatIdList(allCatIdList);
        }
        if (UserContext.isUserLogin()) {
            req.setIsLogin(true);
            req.setUserId(UserContext.getUser().getUserId());
            List<String> targetCodeList = Lists.newArrayList();
            Integer userFounder = UserContext.getUser().getUserFounder();
            setTargetCode(targetCodeList, userFounder);
            if (!CollectionUtils.isEmpty(targetCodeList)) {
                req.setTargetCodeList(targetCodeList);
            }
            log.info("CatComplexB2BController.queryCategoryRanking userFounder={},targetCodeList={}", userFounder, JSON.toJSONString(targetCodeList));
            userFounder = UserContext.getUser().getUserFounder();
            if (null == userFounder) {
                throw new UserNoMerchantException(ResultCodeEnum.ERROR.getCode(), "用户没有商家类型，请确认");
            }else if (userFounder == SystemConst.USER_FOUNDER_3) {
                // 如果用户是零售商，只能查到地包商品
                req.setMerchantType(PartnerConst.MerchantTypeEnum.SUPPLIER_GROUND.getType());
            } else if (userFounder == SystemConst.USER_FOUNDER_5) {
                // 如果用户是地包供应商，只能查到省包商品
                req.setMerchantType(PartnerConst.MerchantTypeEnum.SUPPLIER_PROVINCE.getType());
            } else {
                // 省包供应商、厂商查询不到任何商品
                req.setSourceFrom("-1");
            }
        }
        return goodsService.queryGoodsForPage(req);
    }

    @ApiOperation(value = "新增商品关联分类推荐", notes = "新增商品关联分类推荐")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/addCatComplex")
    public ResultVO<Boolean> addCatComplex(@RequestBody CatComplexAddReq catComplexAddReq){
        Boolean result = false;
        ResultVO<Boolean> resultVO = catComplexService.addCatComplex(catComplexAddReq);
        if(resultVO.isSuccess()){
            result = resultVO.getResultData();
        }
        return  ResultVO.success(result);
    }

    @ApiOperation(value = "修改商品关联分类推荐排序", notes = "修改商品关联分类推荐排序")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/updateCatComplexSortOrder")
    public ResultVO<Boolean> updateCatComplexSortOrder(@RequestBody CatComplexEditReq req){
        Boolean result = false;
        ResultVO<Boolean> resultVO = catComplexService.updateCatComplexSortOrder(req);
        if(resultVO.isSuccess()){
            result = resultVO.getResultData();
        }
        return  ResultVO.success(result);
    }

    @ApiOperation(value = "删除商品关联分类推荐", notes = "删除商品关联分类推荐")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/deleteCatComplex")
    public ResultVO<Boolean> deleteCatComplex(@RequestBody CatComplexDeleteReq req){
        Boolean result = false;
        ResultVO<Boolean> resultVO = catComplexService.deleteCatComplex(req);
        if(resultVO.isSuccess()){
            result = resultVO.getResultData();
        }
        return  ResultVO.success(result);
    }

    @ApiOperation(value = "查询商品关联分类推荐", notes = "查询商品关联分类推荐")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/queryCatComplexForPage")
    public ResultVO<Page<CatComplexQueryResp>> queryCatComplexForPage(@RequestBody CatComplexQueryReq req){
        Page<CatComplexQueryResp> result = new Page<CatComplexQueryResp>();
        ResultVO<Page<CatComplexQueryResp>> resultVO = catComplexService.queryCatComplexForPage(req);
        if(resultVO.isSuccess()){
            result = resultVO.getResultData();
        }
        return  ResultVO.success(result);
    }
}
