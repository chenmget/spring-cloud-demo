package com.iwhalecloud.retail.goods2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.iwhalecloud.retail.goods2b.common.GoodsConst;
import com.iwhalecloud.retail.goods2b.dto.ActivityGoodsDTO;
import com.iwhalecloud.retail.goods2b.dto.GoodsDetailDTO;
import com.iwhalecloud.retail.goods2b.dto.req.ActivityGoodsReq;
import com.iwhalecloud.retail.goods2b.entity.GoodsProductRel;
import com.iwhalecloud.retail.goods2b.mapper.GoodsProductRelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Component
public class GoodsProductRelManager{
    @Resource
    private GoodsProductRelMapper goodsProductRelMapper;

    @Value("${fdfs.showUrl}")
    private String dfsShowIp;

    /**
     * 查询产商品关联表
     * @param goodsId
     * @return
     */
    public GoodsProductRel queryGoodsProductRel(String goodsId){

        QueryWrapper queryWrapper = new QueryWrapper();
//        queryWrapper.eq("is_deleted", GoodsConst.NO_DELETE);
        queryWrapper.eq("goods_id",goodsId);
        List<GoodsProductRel> goodsProductRels = goodsProductRelMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(goodsProductRels)){
            return null;
        }
        return goodsProductRels.get(0);
    }

    public int addGoodsProductRel(GoodsProductRel goodsProductRel) {
        return goodsProductRelMapper.insert(goodsProductRel);
    }

    public List<GoodsProductRel> listGoodsProductRel(String goodsId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq(GoodsProductRel.FieldNames.goodsId.getTableFieldName(), goodsId);
        return goodsProductRelMapper.selectList(wrapper);
    }

    public Double getLowestPriceByGoodsId(String goodsId){
        return goodsProductRelMapper.getLowestPriceByGoodsId(goodsId);
    }

    /**
     * 根据产品ID查询商品
     * @param productId
     * @return
     */
    public List<GoodsDetailDTO> qryGoodsByProductId(String productId){
        return goodsProductRelMapper.qryGoodsByProductId(productId);
    }

    public int delGoodsProductRelByGoodsId(String goodsId) {
        UpdateWrapper<GoodsProductRel> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("GOODS_ID",goodsId);
        return goodsProductRelMapper.delete(updateWrapper);
    }

    public int updateIsHaveStock(String goodsId, String productId, Boolean isHaveStock) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        if (goodsId != null) {
            updateWrapper.eq("GOODS_ID", goodsId);
        }
        if (productId != null) {
            updateWrapper.eq("PRODUCT_ID", productId);
        }
        GoodsProductRel goodsProductRel = new GoodsProductRel();
        if (isHaveStock) {
            goodsProductRel.setIsHaveStock(GoodsConst.HAVE_STOCK);
        } else {
            goodsProductRel.setIsHaveStock(GoodsConst.NOT_HAVE_STOCK);
        }
        return goodsProductRelMapper.update(goodsProductRel, updateWrapper);
    }

    public GoodsProductRel getGoodsProductRel(String goodsId, String productId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq(GoodsProductRel.FieldNames.goodsId.getTableFieldName(), goodsId);
        wrapper.eq(GoodsProductRel.FieldNames.productId.getTableFieldName(), productId);
        return goodsProductRelMapper.selectOne(wrapper);
    }

    public GoodsDetailDTO qryGoodsByProductIdAndGoodsId(String productId, String goodsId){
        return goodsProductRelMapper.qryGoodsByProductIdAndGoodsId(productId,goodsId);
    }

    public List<GoodsProductRel> queryRelByGoodsIds(List<String> goodsIds) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.in(GoodsProductRel.FieldNames.goodsId.getTableFieldName(), goodsIds);
        return goodsProductRelMapper.selectList(wrapper);
    }

    public int updateSupplyNumStock(String goodsId, String productId, Long supplyNum) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("GOODS_ID", goodsId);
        updateWrapper.eq("PRODUCT_ID", productId);
        GoodsProductRel goodsProductRel = new GoodsProductRel();
        goodsProductRel.setSupplyNum(supplyNum);
        return goodsProductRelMapper.update(goodsProductRel, updateWrapper);
    }

    public List<ActivityGoodsDTO> qryActivityGoodsId(ActivityGoodsReq req){
        List<ActivityGoodsDTO> activityGoodsDTOs = goodsProductRelMapper.qryActivityGoodsId(req);
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(activityGoodsDTOs)){
            for(ActivityGoodsDTO activityGoodsDTO:activityGoodsDTOs){
                String imageUrl = activityGoodsDTO.getImageUrl();
                if(StringUtils.isNotEmpty(imageUrl)){
                    activityGoodsDTO.setImageUrl(dfsShowIp+imageUrl);
                }
            }
        }
        return activityGoodsDTOs;
    }

    public List<String> listGoodsBySupplierId(String supplierId, String productId) {
        return goodsProductRelMapper.listGoodsBySupplierId(supplierId, productId);
    }

    public List<GoodsProductRel> queryGoodsByProductIds(List<String> productIds) {
        QueryWrapper<GoodsProductRel> wrapper = new QueryWrapper<>();
        wrapper.in(GoodsProductRel.FieldNames.productId.getTableFieldName(), productIds);
        return goodsProductRelMapper.selectList(wrapper);
    }
}
