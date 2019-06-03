package com.iwhalecloud.retail.goods2b.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.goods2b.dto.ActivityGoodsDTO;
import com.iwhalecloud.retail.goods2b.dto.GoodsDetailDTO;
import com.iwhalecloud.retail.goods2b.dto.req.ActivityGoodsReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProdFileReq;
import com.iwhalecloud.retail.goods2b.entity.GoodsProductRel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: GoodsProductRelMapper
 * @author autoCreate
 */
@Mapper
public interface GoodsProductRelMapper extends BaseMapper<GoodsProductRel>{

    Double getLowestPriceByGoodsId(String goodsId);

    /**
     * 根据产品ID查询商品
     * @param productId
     * @return
     */
    public List<GoodsDetailDTO> qryGoodsByProductId(@Param("productId") String productId);

    /**
     * 查询商品的提货价和商品名称给购物车用
     * @param productId
     * @param goodsId
     * @return
     */
    GoodsDetailDTO qryGoodsByProductIdAndGoodsId(@Param("productId") String productId, @Param("goodsId")String goodsId);

    public List<ActivityGoodsDTO> qryActivityGoodsId(@Param("req") ActivityGoodsReq req);

    List<String> listGoodsBySupplierId(@Param("supplierId") String supplierId, @Param("productId") String productId);
    
    public Integer insertProdFile(@Param("req") ProdFileReq req) ;
    
    public String selectProdFileId();
    
}