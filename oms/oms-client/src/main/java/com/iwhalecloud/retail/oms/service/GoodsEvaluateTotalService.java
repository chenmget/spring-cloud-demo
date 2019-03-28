package com.iwhalecloud.retail.oms.service;

import com.iwhalecloud.retail.oms.dto.resquest.TGoodsEvaluateTotalDTO;

import java.util.List;

public interface GoodsEvaluateTotalService {

    /**
     * 添加
     */
    int addGoodsEvaluate(TGoodsEvaluateTotalDTO t);

    /**
     *
     */
    int modifyGoodsEvaluate(TGoodsEvaluateTotalDTO t);

    /**
     *
     */
    List<TGoodsEvaluateTotalDTO> selectGoodsEvaluate(TGoodsEvaluateTotalDTO t);

}
