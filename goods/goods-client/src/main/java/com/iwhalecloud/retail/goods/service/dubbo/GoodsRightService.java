package com.iwhalecloud.retail.goods.service.dubbo;


import com.iwhalecloud.retail.goods.dto.GoodsRightDTO;
import com.iwhalecloud.retail.goods.dto.req.GoodsRightAddReq;

import java.util.List;

/**
 * 商品-权益 服务
 * @author zwl
 */
public interface GoodsRightService{

    int insertGoodsRight(GoodsRightAddReq req);

    int updateGoodsRight(GoodsRightAddReq req);

    /**
     * 删除商品的所有 优惠券
     * @param goodsId 商品ID
     * @return
     */
    int deleteGoodsRight(String goodsId);

    /**
     * 删除商品的一个优惠券
     * @param goodsRightId 记录ID
     * @return
     */
    int deleteOneGoodsRight(String goodsRightId);


    /**
     * 查询商品配置的优惠券列表
     * @param goodsId
     * @return
     */
    List<GoodsRightDTO> listByGoodsId(String goodsId);
}