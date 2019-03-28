package com.iwhalecloud.retail.goods2b.service.dubbo;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.GoodsActRelDTO;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsActRelListReq;

import java.util.List;

/**
 * 商品活动关联表
 * @author z
 */
public interface GoodsActRelService {


    /**
     * 查询商品活动关联信息
     * @param req
     * @return
     */
    ResultVO<List<GoodsActRelDTO>> queryGoodsActRel(GoodsActRelListReq req);
}