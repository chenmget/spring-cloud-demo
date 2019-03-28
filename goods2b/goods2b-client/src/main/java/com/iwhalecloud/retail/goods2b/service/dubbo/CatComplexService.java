package com.iwhalecloud.retail.goods2b.service.dubbo;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.CatComplexAddReq;
import com.iwhalecloud.retail.goods2b.dto.req.CatComplexDeleteReq;
import com.iwhalecloud.retail.goods2b.dto.req.CatComplexEditReq;
import com.iwhalecloud.retail.goods2b.dto.req.CatComplexQueryReq;
import com.iwhalecloud.retail.goods2b.dto.resp.CatComplexQueryResp;
import com.iwhalecloud.retail.goods2b.dto.resp.CategoryRankingQuery;
import com.iwhalecloud.retail.goods2b.dto.resp.CategoryRecommendQuery;

import java.util.List;

public interface CatComplexService{

    ResultVO<List<CategoryRecommendQuery>> queryCategoryRecommend(CatComplexQueryReq catComplexQueryReq);

    ResultVO<List<CategoryRankingQuery>> queryCategoryRanking(CatComplexQueryReq catComplexQueryReq);

    /**
     * 商品分类关联推荐--新建
     * @param req
     * @return
     */
    ResultVO<Boolean> addCatComplex(CatComplexAddReq req);

    /**
     * 商品分类关联推荐--修改排序
     * @param req
     * @return
     */
    ResultVO<Boolean> updateCatComplexSortOrder(CatComplexEditReq req);

    /**
     * 商品分类关联推荐--删除
     * @param req
     * @return
     */
    ResultVO<Boolean> deleteCatComplex(CatComplexDeleteReq req);

    /**
     * 商品分类关联推荐--查询
     * @param req
     * @return
     */
    ResultVO<Page<CatComplexQueryResp>> queryCatComplexForPage(CatComplexQueryReq req);
}