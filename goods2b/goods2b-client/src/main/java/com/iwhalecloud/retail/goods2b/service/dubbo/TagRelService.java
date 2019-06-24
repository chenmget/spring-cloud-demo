package com.iwhalecloud.retail.goods2b.service.dubbo;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.TagRelDTO;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.dto.resp.TagRelListResp;

import java.util.List;

public interface TagRelService {

    /**
     * 根据ID查询标签商品关系
     * @param relGetByIdReq
     * @return
     */
    ResultVO<TagRelDTO> getTagRel(TagRelGetByIdReq relGetByIdReq);

    /**
     * 获取所有有效品牌列表
     * @return
     */
    ResultVO<List<TagRelDTO>> listAll();

    /**
     * 添加标签商品关系
     * @param req
     * @return
     */
    ResultVO<Integer> addTagRel(TagRelAddReq req);

    /**
     * 批量添加标签商品关系
     * @param relBatchAddReq
     * @return
     */
    ResultVO<Boolean> batchAddTagRel(TagRelBatchAddReq relBatchAddReq);

    /**
     * 修改标签商品关系
     * @param req
     * @return
     */
    ResultVO<Integer> updateTagRel(TagRelUpdateReq req);

    /**
     * 删除标签商品关系
     * @param relDeleteByIdReq
     * @return
     */
    ResultVO<Integer> deleteTagRel(TagRelDeleteByIdReq relDeleteByIdReq);


    /**
     * 根据商品ID删除标签商品关系
     * @param relDeleteByGoodsIdReq
     * @return
     */
    ResultVO<Boolean> deleteTagRelByGoodsId(TagRelDeleteByGoodsIdReq relDeleteByGoodsIdReq);

    /**
     * 根据商品ID查询标签
     * @return
     */
    ResultVO<List<TagRelDTO>> listTagByGoodsId(TagRelListByGoodsIdReq relListByGoodsIdReq);

    /**
     * 查询产商品标签关联集合
     * @param req
     * @return
     */
    ResultVO<List<TagRelListResp>> listTagRel(TagRelListReq req);

    /**
     * 查询产品标签关联集合(绿色权限标签)
     * @param req
     * @return
     */
    ResultVO<Boolean> batchAddTagRelProductId(TagRelBatchAddReq req);

    /**
     * 根据productId删除标签产品关系
     * @param req
     * @return
     */
    ResultVO<Boolean> deleteTagRelByProductId(TagRelDeleteByGoodsIdReq req);
}