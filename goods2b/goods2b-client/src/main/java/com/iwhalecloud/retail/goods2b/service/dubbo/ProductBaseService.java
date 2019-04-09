package com.iwhalecloud.retail.goods2b.service.dubbo;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.dto.resp.ExchangeObjectGetResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductBaseGetResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductDetailResp;

import java.util.List;

public interface ProductBaseService {

    /**
     * 根据产品基本信息ID获取产品产品基本信息对象
     * @param req 产品ID
     * @return 产品对象
     */
    ResultVO<ProductBaseGetResp> getProductBase(ProductBaseGetByIdReq req);

    /**
     * 获取产品产品基本信息列表
     * @param req
     * @return
     */
    ResultVO<Page<ProductBaseGetResp>> getProductBaseList(ProductBaseListReq req);

    /**
     * 添加产品基本信息列表
     * @param req
     * @return
     */
    public ResultVO<String> addProductBase (ProductBaseAddReq req);

    /**
     * 更新
     * @param req
     * @return
     */
    public ResultVO<Integer> updateProductBase(ProductBaseUpdateReq req);

    /**
     * 删除
     * @param req
     * @return
     */
    public ResultVO<Integer> deleteProdProductBase(ProdProductBaseDeleteReq req);

    /**
     * 软删除
     * @param req
     * @return
     */
    public ResultVO<Integer> softDelProdProductBase(ProdProductBaseSoftDelReq req);
    /**
     * 通用查询
     * @param req
     * @return
     */
    public ResultVO<List<ProductBaseGetResp>> selectProductBase(ProductBaseGetReq req);

    /**
     * 获取产品详情
     * @param req
     * @return
     */
    public ResultVO<ProductDetailResp> getProductDetail(ProductDetailGetByBaseIdReq req);

    /**
     * 查询换货对象
     * @param req
     * @return
     */
    public ResultVO<ExchangeObjectGetResp> getExchangeObject(ProductExchangeObjectGetReq req);

    /**
     * 更新
     * @param req
     * @return
     */
    ResultVO<Boolean> updateAvgApplyPrice(ProductBaseUpdateReq req);
}