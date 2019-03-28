package com.iwhalecloud.retail.goods2b.service.dubbo;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.GoodsRightDTO;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsRightAddReq;

import java.util.List;

/**
 * 商品-权益 服务
 * @author zwl
 */
public interface GoodsRightService{

    ResultVO<Boolean> batchInsertGoodsRight(GoodsRightAddReq req);

    int insertGoodsRight(GoodsRightAddReq req);

    int updateGoodsRight(GoodsRightDTO req);

    /**
     * 删除商品的所有 优惠券
     * @param goodsRightDTO
     * @return
     */
    int deleteGoodsRight(GoodsRightDTO goodsRightDTO);

    /**
     * 删除商品的一个优惠券
     * @param goodsRightDTO 记录ID
     * @return
     */
    int deleteOneGoodsRight(GoodsRightDTO goodsRightDTO);


    /**
     * 查询商品配置的优惠券列表
     * @param goodsRightDTO
     * @return
     */
    List<GoodsRightDTO> listByGoodsId(GoodsRightDTO goodsRightDTO);
}