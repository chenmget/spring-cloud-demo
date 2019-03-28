package com.iwhalecloud.retail.goods.service.dubbo;


import com.iwhalecloud.retail.goods.common.GoodsConst;
import com.iwhalecloud.retail.goods.dto.ResultVO;

import java.util.List;

public interface ProdGoodsRelService{

    /**
     * 查询商品关联推荐或者合约计划关联套餐、终端
     * @param goodsId
     * @return
     */
    ResultVO<List<String>> getGoodsRelByZGoodsId(String goodsId, GoodsConst.GoodsRelType goodsRelType);

    /**
     * 查询关联合约计划
     * @param goodsId
     * @return
     */
    ResultVO<List<String>> getContractPlanByAGoodsId(String goodsId, GoodsConst.GoodsRelType goodsRelType);
}