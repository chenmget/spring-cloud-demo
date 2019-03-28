package com.iwhalecloud.retail.goods2b.service.dubbo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.goods2b.dto.GoodsDTO;
import com.iwhalecloud.retail.goods2b.dto.GoodsGroupDTO;
import com.iwhalecloud.retail.goods2b.dto.req.GoodGroupQueryReq;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsGroupAddReq;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsGroupEditReq;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsGroupUpdateReq;

import java.util.List;

/**
 * @Author My
 * @Date 2018/11/5
 **/
public interface GoodsGroupService {
    /**
     * 添加商品组
     * @param req
     * @return
     */
    int insertGoodsGroup(GoodsGroupAddReq req);

    /**
     * 修改商品组
     * @param req
     * @return
     */
    int updateGoodsGroup(GoodsGroupUpdateReq req);

    /**
     * 删除商品组
     * @param goodsGroupEditReq
     * @return
     */
    int deleteGoodsGroup(GoodsGroupEditReq goodsGroupEditReq);
    /**
     * 通过商品ID查询商品组
     * @param goodsGroupEditReq
     * @return
     */
    GoodsGroupDTO listGoodsGroupByGroupId(GoodsGroupEditReq goodsGroupEditReq);

    /**
     * 查询商品组
     * @param req
     * @return
     */
    Page<GoodsGroupDTO> listGoodsGroup(GoodGroupQueryReq req);

    /**
     * 通过商品ID 查询商品列表
     * @param goodsGroupEditReq
     * @return
     */
    List<GoodsDTO> listGoodsByGoodsId(GoodsGroupEditReq goodsGroupEditReq);

    /**
     *
     * @param goodsGroupEditReq
     * @return
     */
    Boolean queryGoodsGroupNameIsContains(GoodsGroupEditReq goodsGroupEditReq);
}
