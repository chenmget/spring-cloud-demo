package com.iwhalecloud.retail.goods2b.service.dubbo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.GoodsDTO;
import com.iwhalecloud.retail.goods2b.dto.MerchantDTO;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.dto.resp.*;
import com.iwhalecloud.retail.goods2b.exception.GoodsRulesException;

import java.util.List;

public interface GoodsService{

    /**
     * 新增商品
     * @param req
     * @return
     */
    ResultVO<GoodsAddResp> addGoods(GoodsAddReq req) throws GoodsRulesException;

    /**
     * 新增商品--中台
     * @param req
     * @return
     */
    ResultVO<GoodsAddResp> addGoodsByZT(GoodsAddByZTReq req);

    /**
     * 修改商品
     * @param req
     * @return
     */
    ResultVO<GoodsOperateResp> editGoods(GoodsEditReq req) throws GoodsRulesException ;

    /**
     * 修改商品--中台
     * @param req
     * @return
     */
    ResultVO<GoodsOperateResp> editGoodsByZT(GoodsEditByZTReq req);

    /**
     * 删除商品
     * @param req
     * @return
     */
    ResultVO<GoodsOperateResp> deleteGoods(GoodsDeleteReq req);

    /**
     * 商品分页查询
     * @param req
     * @return
     */
    ResultVO<Page<GoodsForPageQueryResp>> queryGoodsForPage(GoodsForPageQueryReq req);

    /**
     * 根据商品ID修改上下架状态
     * @param goodsMarketEnableReq
     * @return
     */
    ResultVO<GoodsOperateResp> updateMarketEnable(GoodsMarketEnableReq goodsMarketEnableReq);

    /**
     * 根据商品ID修改审核状态
     * @param goodsAuditStateReq
     * @return
     */
    ResultVO<GoodsOperateResp> updateAuditState(GoodsAuditStateReq goodsAuditStateReq);

    /**
     * 根据条件进行商品分页查询（管理端）
     * @param req
     * @return
     */
    ResultVO<Page<GoodsPageResp>> queryPageByConditionAdmin(GoodsPageReq req);


    /**
     * 查询商品详情
     * @param req
     * @return
     */
    ResultVO<GoodsDetailResp> queryGoodsDetail(GoodsQueryReq req);

    /**
     * 查询商品列表
     * @param goodsIdListReq
     * @return
     */
    ResultVO<List<GoodsDTO>> listGoods(GoodsIdListReq goodsIdListReq);

    /**
     * 根据商品id修改购买数量
     * @param req
     * @return
     */
    ResultVO<Boolean> updateBuyCountById(GoodsBuyCountByIdReq req);

    /**
     * 根据商品id查询供应商信息
     * @param goodsSupplierIDGetReq
     * @return
     */
    ResultVO<MerchantDTO> querySupplierIdByGoodsId(GoodsSupplierIDGetReq goodsSupplierIDGetReq);

    /**
     * 更新商品预售、前置补贴状态
     *
     * @param req 更新参数
     * @return 更新结果
     */
    ResultVO<Boolean> updateGoodsActTypeByGoodsIdList(GoodsUpdateActTypeByGoodsIdsReq req);
}