package com.iwhalecloud.retail.goods.service.dubbo;


import com.iwhalecloud.retail.goods.dto.ProdTagsDTO;

import java.util.List;

public interface ProdTagsService{
    /**
     *查询标签列表
     * @param goodsId
     * @return
     */
    List<ProdTagsDTO> getTagsByGoodsId(String goodsId);

    /**
     * 查询标签列表 没有参数
     * @return
     */
    List<ProdTagsDTO> listProdTags();
}