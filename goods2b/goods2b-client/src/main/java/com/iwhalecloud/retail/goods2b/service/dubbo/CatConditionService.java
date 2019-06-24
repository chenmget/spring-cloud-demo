package com.iwhalecloud.retail.goods2b.service.dubbo;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.CatConditionDTO;
import com.iwhalecloud.retail.goods2b.dto.req.CatConditionListReq;
import com.iwhalecloud.retail.goods2b.dto.req.CatConditionSaveReq;

import java.util.List;

public interface CatConditionService {

    /**
     * 添加一个 商品类型条件
     * @param req
     * @return
     */
    ResultVO<Integer> saveCatCondition(CatConditionSaveReq req);

    /**
     * 商品类型条件 列表查询
     * @param req
     * @return
     */
    ResultVO<List<CatConditionDTO>> listCatCondition(CatConditionListReq req);

    /**
     * 商品类型条件 列表查询
     * @param req
     * @return
     */
//    ResultVO<List<CatConditionDTO>> listCatConditionDetail(CatConditionListReq req);

}