package com.iwhalecloud.retail.goods.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.goods.dto.ProdTagsDTO;
import com.iwhalecloud.retail.goods.dto.SelectTagListDTO;
import com.iwhalecloud.retail.goods.entity.ProdTags;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: ProdTagsMapper
 * @author autoCreate
 */
@Mapper
public interface ProdTagsMapper extends BaseMapper<ProdTags>{
    /**
     * 查询标签列表 通过goodsId
     * @param goodsId
     * @return
     */
    List<ProdTagsDTO> getTagsByGoodsId(String goodsId);

    /**
     * 查询标签列表
     * @return
     */
    List<ProdTagsDTO> listProdTags();
}