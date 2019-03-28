package com.iwhalecloud.retail.goods.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.goods.dto.ProdGoodsDTO;
import com.iwhalecloud.retail.goods.dto.req.ProdGoodsListReq;
import com.iwhalecloud.retail.goods.dto.req.ProdGoodsQueryReq;
import com.iwhalecloud.retail.goods.entity.ProdGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: ProdGoodsMapper
 * @author autoCreate
 */
@Mapper
public interface ProdGoodsMapper extends BaseMapper<ProdGoods>{

	/**
     * 根据分类获取关联商品信息
     * @param catId
     * @return
     */
    public List<ProdGoods> getComplexGoodsByCatId(String catId);

    /**
     * 商品列表查询（分页）
     * @param req
     * @return
     */
    Page<ProdGoodsDTO> queryGoodsForPage(Page<ProdGoodsDTO> page, @Param("req") ProdGoodsQueryReq req);

    /**
     * 商品列表查询
     * @param req
     * @return
     */
    List<ProdGoodsDTO> listGoods(@Param("req") ProdGoodsListReq req);

}