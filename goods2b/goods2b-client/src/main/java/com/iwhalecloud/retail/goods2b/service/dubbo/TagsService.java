package com.iwhalecloud.retail.goods2b.service.dubbo;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.GoodsDetailDTO;
import com.iwhalecloud.retail.goods2b.dto.TagsDTO;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.dto.resp.GoodsForPageQueryResp;

import java.util.List;

public interface TagsService {
    /**
     *查询标签列表
     * @param tagGetByGoodsIdReq
     * @return
     */
    ResultVO<List<TagsDTO>> getTagsByGoodsId(TagGetByGoodsIdReq tagGetByGoodsIdReq);

    /**
     * 查询标签列表 没有参数
     * @return
     */
    ResultVO<List<TagsDTO>> listProdTags();

    /**
     * 查询标签列表 没有参数
     * @return
     */
    ResultVO<List<TagsDTO>> listProdTagsChannel();
    
    /**
     * 添加标签
     * @return
     */
    ResultVO<String> addProdTags(TagsDTO tagsDTO);

    /**
     * 编辑标签
     * @return
     */
    ResultVO<Boolean> editProdTags(TagsDTO tagsDTO);

    /**
     * 删除标签
     * @return
     */
    ResultVO<Boolean> deleteProdTags(ProdTagDeleteReq prodTagDeleteReq);

    /**
     * 根据标签id查询标签
     * @return
     */
    ResultVO<TagsDTO> getTagById(TagGetByIdReq req);

    /**
     * 根据标签id查询标签
     * @return
     */
    ResultVO<List<TagsDTO>> getTags(TagGetByTagReq req);

    /**
     * 根据产品标签查询商品列表
     * @param req
     * @return
     */
    ResultVO<List<GoodsDetailDTO>> queryGoodsListByTag(GoodsQueryByTagsReq req);
}