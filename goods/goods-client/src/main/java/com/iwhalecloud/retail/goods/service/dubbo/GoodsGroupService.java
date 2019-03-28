package com.iwhalecloud.retail.goods.service.dubbo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.goods.dto.GoodsDTO;
import com.iwhalecloud.retail.goods.dto.GoodsGroupDTO;
import com.iwhalecloud.retail.goods.dto.req.GoodGroupQueryReq;
import com.iwhalecloud.retail.goods.dto.req.GoodsGroupAddReq;
import com.iwhalecloud.retail.goods.dto.req.GoodsGroupUpdateReq;

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
     * @param groupId
     * @return
     */
    int deleteGoodsGroup(String groupId);
    /**
     * 通过商品ID查询商品组
     * @param groupId
     * @return
     */
    GoodsGroupDTO listGoodsGroupByGroupId(String groupId);

    /**
     * 查询商品组
     * @param req
     * @return
     */
    Page<GoodsGroupDTO> listGoodsGroup(GoodGroupQueryReq req);

    /**
     * 通过商品ID 查询商品列表
     * @param goodsId
     * @return
     */
    List<GoodsDTO> listGoodsByGoodsId(String goodsId);

    /**
     *
     * @param name
     * @return
     */
    Boolean queryGoodsGroupNameIsContains(String name);
}
