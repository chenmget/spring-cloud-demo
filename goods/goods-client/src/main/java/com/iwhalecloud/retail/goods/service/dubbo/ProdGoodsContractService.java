package com.iwhalecloud.retail.goods.service.dubbo;


import com.iwhalecloud.retail.goods.dto.ResultVO;

import java.util.List;

public interface ProdGoodsContractService{

    /**
     * 获取商品合约信息
     * @param goodsIds
     * @return
     */
    ResultVO listGoodsContractByGoodsIds(List<String> goodsIds);

}