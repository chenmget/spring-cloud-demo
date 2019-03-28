package com.iwhalecloud.retail.goods.service.dubbo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.goods.dto.ProdGoodsDTO;
import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.dto.req.*;
import com.iwhalecloud.retail.goods.dto.resp.*;

import java.util.List;

public interface ProdGoodsService {

    /**
     * 新增商品
     * @param req
     * @return
     */
    ResultVO<ProdGoodsAddResp> addGoods(ProdGoodsAddReq req);

    /**
     * 修改商品
     * @param req
     * @return
     */
    ResultVO<ProdGoodsEditResp> editGoods(ProdGoodsEditReq req);

    /**
     * 删除商品
     * @param req
     * @return
     */
    ResultVO<ProdGoodsDeleteResp> deleteGoods(ProdGoodsDeleteReq req);

    /**
     * 商品列表查询
     * @param req
     * @return
     */
    ResultVO<Page<ProdGoodsDTO>> queryGoodsForPage(ProdGoodsQueryReq req);

    /**
     * 商品列表查询
     * @param req
     * @return
     */
    ResultVO<List<ProdGoodsDTO>> listGoods(ProdGoodsListReq req);

    /**
     * 商品详情查询
     * @param goodsId
     * @return
     */
    ResultVO<ProdGoodsDetailResp> queryGoodsDetail(String goodsId);

    /**
     * 根据产品ID查询商品
     * @param productId
     * @return
     */
    ResultVO<QryGoodsByProductIdResp> qryGoodsByProductId(String productId);

    /**
     * 根据商品ID修改上下架状态
     * @param goodsId
     * @param marketEnable
     * @return
     */
    ResultVO<Boolean> updateMarketEnableByPrimaryKey(String goodsId, Integer marketEnable);

    /**
     * 商品查询
     * @param goodsId
     * @return
     */
    ResultVO<ProdGoodsQueryByIdReq> queryGoods(String goodsId);

    /**
     * 修改商品
     * @param req
     * @return
     */
    ResultVO<Boolean> updateBuyCountById(List<UpdateBuyCountByIdReq> req);
}
