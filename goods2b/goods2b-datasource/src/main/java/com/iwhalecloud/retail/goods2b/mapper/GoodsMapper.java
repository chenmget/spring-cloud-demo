package com.iwhalecloud.retail.goods2b.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.goods2b.dto.SupplierGoodsDTO;
import com.iwhalecloud.retail.goods2b.dto.SupplierGroundGoodsDTO;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsForPageQueryReq;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsPageReq;
import com.iwhalecloud.retail.goods2b.dto.resp.GoodsForPageQueryResp;
import com.iwhalecloud.retail.goods2b.dto.resp.GoodsPageResp;
import com.iwhalecloud.retail.goods2b.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: GoodsMapper
 * @author autoCreate
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods>{

    /**
     * 商品列表查询（分页）
     * @param req
     * @return
     */
    Page<GoodsForPageQueryResp> queryGoodsForPage(Page<GoodsForPageQueryResp> page, @Param("req") GoodsForPageQueryReq req);

    /**
     * 根据条件商品分页查询（管理端）
     * @param page
     * @param req
     * @return
     */
    Page<GoodsPageResp> queryPageByConditionAdmin(Page<GoodsPageResp> page, @Param("req") GoodsPageReq req);

    /**
     * 根据产品基本信息id查询地包商品提货价
     * @param productBaseId
     * @return
     */
    List<SupplierGroundGoodsDTO> listSupplierGroundRelative(@Param("productBaseId") String productBaseId);

    /**
     * 根据产品基本信息id查询地包商上架数量
     * @param productBaseId
     * @return
     */
    Double listSupplierGroundSupplyNum(@Param("productBaseId") String productBaseId);

    /**
     * 根据产品基本信息id查询地包商品提货价
     * @param productId
     * @param merchantType
     * @return
     */
    List<SupplierGoodsDTO> listSupplierGoodsByType(@Param("productId") String productId,@Param("merchantType")String merchantType);

}