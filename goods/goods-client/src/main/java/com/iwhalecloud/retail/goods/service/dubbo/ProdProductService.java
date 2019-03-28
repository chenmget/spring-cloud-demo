package com.iwhalecloud.retail.goods.service.dubbo;


import com.iwhalecloud.retail.goods.dto.ProdProductDTO;
import com.iwhalecloud.retail.goods.dto.ResultVO;

import java.util.List;

public interface ProdProductService{

    /**
     * 根据产品ID获取产品对象
     * @param productId 产品ID
     * @return 产品对象
     */
    ProdProductDTO getProduct(String productId);

    /**
     * 根据商品ID查询产品
     * @param goodsId
     * @return
     */
    ResultVO<List<ProdProductDTO>> queryProductByGoodsId(String goodsId);
}