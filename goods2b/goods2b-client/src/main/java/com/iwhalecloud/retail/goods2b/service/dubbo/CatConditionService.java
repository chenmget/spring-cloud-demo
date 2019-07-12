package com.iwhalecloud.retail.goods2b.service.dubbo;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.CatConditionDTO;
import com.iwhalecloud.retail.goods2b.dto.req.CatConditionDeleteReq;
import com.iwhalecloud.retail.goods2b.dto.req.CatConditionListReq;
import com.iwhalecloud.retail.goods2b.dto.req.CatConditionSaveReq;
import com.iwhalecloud.retail.goods2b.dto.resp.CatConditionDetailResp;

import java.util.List;

public interface CatConditionService {

    /**
     * 添加一个 商品分类条件
     * @param req
     * @return
     */
    ResultVO<Integer> saveCatCondition(CatConditionSaveReq req);

    /**
     * 删除 商品分类条件
     * @param req
     * @return
     */
    ResultVO<Integer> deleteCatCondition(CatConditionDeleteReq req);


    /**
     * 商品分类条件 列表查询
     * @param req
     * @return
     */
    ResultVO<List<CatConditionDTO>> listCatCondition(CatConditionListReq req);

    /**
     * 根据商品分类ID获取 商品分类条件 详情
     * @param catId
     * @return
     */
    ResultVO<CatConditionDetailResp> getCatConditionDetail(String catId);

}