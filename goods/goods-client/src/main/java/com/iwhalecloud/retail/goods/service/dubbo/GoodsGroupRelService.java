package com.iwhalecloud.retail.goods.service.dubbo;

import com.iwhalecloud.retail.goods.dto.req.GoodsGroupRelAddReq;

/**
 * @Author My
 * @Date 2018/11/5
 **/
public interface GoodsGroupRelService {
    /**
     * 新增商品组与商品关联表
     * @param req
     * @return
     */
    int insertGoodsGroupRel(GoodsGroupRelAddReq req);


}
