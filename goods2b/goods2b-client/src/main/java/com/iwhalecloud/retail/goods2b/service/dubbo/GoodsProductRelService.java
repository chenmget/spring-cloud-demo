package com.iwhalecloud.retail.goods2b.service.dubbo;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.ActivityGoodsDTO;
import com.iwhalecloud.retail.goods2b.dto.BuyCountCheckDTO;
import com.iwhalecloud.retail.goods2b.dto.GoodsDetailDTO;
import com.iwhalecloud.retail.goods2b.dto.GoodsProductRelDTO;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsProductRelEditReq;

import java.util.List;

public interface GoodsProductRelService{

    /**
     * 根据产品ID查询商品
     * @param goodsProductRelEditReq
     * @return
     */
    ResultVO<List<GoodsDetailDTO>> qryGoodsByProductId(GoodsProductRelEditReq goodsProductRelEditReq);
    /**
     * 更新是否有库存
     * @param  goodsProductRelEditReq
     * @return
     */
    ResultVO<Boolean> updateIsHaveStock(GoodsProductRelEditReq goodsProductRelEditReq);

    /**
     * 根据产品ID和商品ID查询查询最大最小购买量
     * @param goodsProductRelEditReq
     * @return
     */
    ResultVO<GoodsProductRelDTO> qryMinAndMaxNum(GoodsProductRelEditReq goodsProductRelEditReq);

    /**
     * 根据产品ID和商品ID查询查询最大最小购买量
     * @param goodsProductRelEditReq
     * @return
     */
    ResultVO<Boolean> checkBuyCount(GoodsProductRelEditReq goodsProductRelEditReq);
    /**
     * 查询商品的提货价
     * @param goodsProductRelEditReq
     * @return
     */
    ResultVO<GoodsDetailDTO> qryGoodsByProductIdAndGoodsId(GoodsProductRelEditReq goodsProductRelEditReq);

    /**
     * 根据产品ID查询商品信息
     * @param productIdList
     * @return
     */
    ResultVO<List<ActivityGoodsDTO>> qryActivityGoodsId(List<String> productIdList,String regionId,String lanId,String merchantId);
}