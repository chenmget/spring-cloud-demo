package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.CatComplexConst;
import com.iwhalecloud.retail.goods2b.dto.CatComplexDTO;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.dto.resp.CatComplexQueryResp;
import com.iwhalecloud.retail.goods2b.dto.resp.CategoryRankingQuery;
import com.iwhalecloud.retail.goods2b.dto.resp.CategoryRecommendQuery;
import com.iwhalecloud.retail.goods2b.dto.resp.GoodsForPageQueryResp;
import com.iwhalecloud.retail.goods2b.entity.CatComplex;
import com.iwhalecloud.retail.goods2b.manager.CatComplexManager;
import com.iwhalecloud.retail.goods2b.service.dubbo.CatComplexService;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Component("catComplexService")
@Service
public class CatComplexServiceImpl implements CatComplexService {

    @Autowired
    private CatComplexManager catComplexManager;

    @Autowired
    private GoodsService goodsService;

    @Override
    public ResultVO<List<CategoryRecommendQuery>> queryCategoryRecommend(CatComplexQueryReq catComplexQueryReq) {
        List<String> catIdList = catComplexQueryReq.getCatIdList();
        String targetType = CatComplexConst.TargetType.CAT_RECOMMEND_TARGET.getType();
        List<CatComplexDTO> catComplexDTOList = catComplexManager.queryCatComplexbyCatIds(catIdList, targetType);
        List<CategoryRecommendQuery> recommendList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(catComplexDTOList)) {
            return ResultVO.success(recommendList);
        }
        List<String> goodsIdList = catComplexDTOList.stream().map(CatComplexDTO::getTargetId).collect(Collectors.toList());
        GoodsForPageQueryReq req = new GoodsForPageQueryReq();
        req.setPageNo(1);
        req.setPageSize(10);
        req.setIds(goodsIdList);
        req.setIsLogin(catComplexQueryReq.getIsLogin());
        ResultVO<Page<GoodsForPageQueryResp>> resultVO = goodsService.queryGoodsForPage(req);
        if (!resultVO.isSuccess() || resultVO.getResultData() == null || resultVO.getResultData().getRecords() == null) {
            ResultVO result = new ResultVO();
            result.setResultCode(resultVO.getResultCode());
            result.setResultMsg(resultVO.getResultMsg());
            return result;
        }
        List<GoodsForPageQueryResp> goodsForPageQueryResps = resultVO.getResultData().getRecords();
        for (GoodsForPageQueryResp item : goodsForPageQueryResps) {
            CategoryRecommendQuery recommendQuery = new CategoryRecommendQuery();
            BeanUtils.copyProperties(item, recommendQuery);
            recommendQuery.setDefaultImage(item.getImageUrl());
            recommendList.add(recommendQuery);
        }
        return ResultVO.success(recommendList);
    }

    @Override
    public ResultVO<List<CategoryRankingQuery>> queryCategoryRanking(CatComplexQueryReq catComplexQueryReq) {
        List<CategoryRankingQuery> list = Lists.newArrayList();
        CategoryRankingQuery categoryRankingQuery = new CategoryRankingQuery();
        categoryRankingQuery.setGoodsId("1077375563869319169");
        categoryRankingQuery.setGoodsName("这是一个商品测试");
        categoryRankingQuery.setDeliveryPrice(5888.0);
        categoryRankingQuery.setMktprice(500.0);
        categoryRankingQuery.setSupplierId("1");
        categoryRankingQuery.setSupplierName("京东自营旗舰店");
        categoryRankingQuery.setOrderAmount("50");
        list.add(categoryRankingQuery);
        return ResultVO.success(list);
    }

    @Override
    public ResultVO<Boolean> addCatComplex(CatComplexAddReq req) {
        List<CatComplex> catComplexList = Lists.newArrayList();
        CatComplex catComplex = new CatComplex();
        BeanUtils.copyProperties(req, catComplex);
        try {
            catComplexList.add(catComplex);
            int rspNum = catComplexManager.addCatComplex(catComplexList);
            log.info("CatServiceImpl addCatComplex resultNum={}", JSON.toJSONString(rspNum));
            if(rspNum > 0){
                return ResultVO.success(true);
            }
        } catch (Exception e) {
            log.error("CatServiceImpl addCatComplex error={}", JSON.toJSONString(e.getMessage()));
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultVO.error(e.getMessage());
        }
        return ResultVO.success(false);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor={Exception.class})
    public ResultVO<Boolean> updateCatComplexSortOrder(CatComplexEditReq catComplexEditReq) {
        List<CatComplexAddReq> req = catComplexEditReq.getCatComplexEditReq();
        if(CollectionUtils.isEmpty(req)){
            log.warn("CatServiceImpl updateCatComplexSortOrder req is null");
            return ResultVO.success(false);
        }
        try {
            //删除该类别
            CatComplexAddReq catComplexAddReq = req.get(0);
            catComplexManager.delCatComplexByTargetId(catComplexAddReq.getCatId(), catComplexAddReq.getTargetType());
            //重新插入
            List<CatComplex> catComplexList = Lists.newArrayList();
            req.forEach(item -> {
                CatComplex catComplex = new CatComplex();
                BeanUtils.copyProperties(item, catComplex);
                catComplexList.add(catComplex);
            });
            catComplexManager.addCatComplex(catComplexList);
        } catch (Exception e) {
            log.error("CatServiceImpl updateCatComplexSortOrder error={}", JSON.toJSONString(e.getMessage()));
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultVO.error(e.getMessage());
        }
        return ResultVO.success(true);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor={Exception.class})
    public ResultVO<Boolean> deleteCatComplex(CatComplexDeleteReq req) {
        List<String> ids = req.getIds();
        try {
            if(CollectionUtils.isEmpty(ids)){
                log.warn("CatServiceImpl deleteCatComplex ids is null");
                return ResultVO.success(false);
            }
            catComplexManager.deleteCatComplexById(ids);
        } catch (Exception e) {
            log.error("CatServiceImpl deleteCatComplex error={}", JSON.toJSONString(e.getMessage()));
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultVO.error(e.getMessage());
        }
        return ResultVO.success(true);
    }

    @Override
    public ResultVO<Page<CatComplexQueryResp>> queryCatComplexForPage(CatComplexQueryReq req) {
        Page<CatComplexQueryResp> catComplexQueryRespPage = catComplexManager.queryCatComplexForPage(req);
        return ResultVO.success(catComplexQueryRespPage);
    }
}