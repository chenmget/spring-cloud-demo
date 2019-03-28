package com.iwhalecloud.retail.goods2b.service.dubbo;

import com.iwhalecloud.retail.goods2b.dto.GoodsGroupRelDTO;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsGroupRelAddReq;

import java.util.List;

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

    int deleteGoodsGroupRel(GoodsGroupRelDTO goodsGroupRelDTO);

    int deleteGoodsGroupRelByGroupId(GoodsGroupRelDTO goodsGroupRelDTO);

    int deleteOneGoodsGroupRel(GoodsGroupRelDTO goodsGroupRelDTO);

    int updateGoodsGroupRel(GoodsGroupRelDTO req);

    List<GoodsGroupRelDTO> queryGoodsGroupRelByGoodsId(GoodsGroupRelDTO goodsGroupRelDTO);

    List<GoodsGroupRelDTO> queryGoodsGroupRelByGroupId(GoodsGroupRelDTO goodsGroupRelDTO);
}
