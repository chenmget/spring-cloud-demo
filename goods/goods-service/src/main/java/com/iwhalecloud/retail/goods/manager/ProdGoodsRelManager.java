package com.iwhalecloud.retail.goods.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.iwhalecloud.retail.goods.entity.ProdGoods;
import com.iwhalecloud.retail.goods.entity.ProdGoodsRel;
import com.iwhalecloud.retail.goods.mapper.ProdGoodsRelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Component
public class ProdGoodsRelManager{
    @Resource
    private ProdGoodsRelMapper prodGoodsRelMapper;

    public void insertProdGoodsRel(ProdGoods prodGoods, String[] recommendArr, String relType) {
        if (recommendArr != null && recommendArr.length > 0) {
            for (String recommend : recommendArr) {
                ProdGoodsRel prodGoodsRel = new ProdGoodsRel();
                prodGoodsRel.setAGoodsId(recommend);
                prodGoodsRel.setZGoodsId(prodGoods.getGoodsId());
                prodGoodsRel.setRelType(relType);
                prodGoodsRelMapper.insert(prodGoodsRel);
            }
        }
    }

    public List<ProdGoodsRel> getGoodsRelByZGoodsId(String goodsId) {
        Map map = Maps.newConcurrentMap();
        map.put("Z_GOODS_ID",goodsId);
        return prodGoodsRelMapper.selectByMap(map);
    }

    public List<ProdGoodsRel> getGoodsRelByZGoodsId(String goodsId, String goodsRelType) {
        Map map = Maps.newConcurrentMap();
        map.put("Z_GOODS_ID",goodsId);
        map.put("REL_TYPE", goodsRelType);
        return prodGoodsRelMapper.selectByMap(map);
    }

    public List<ProdGoodsRel> getGoodsRelByAGoodsId(String goodsId, String goodsRelType) {
        Map map = Maps.newConcurrentMap();
        map.put("A_GOODS_ID",goodsId);
        map.put("REL_TYPE", goodsRelType);
        return prodGoodsRelMapper.selectByMap(map);
    }

    public int deleteGoodsRelByZGoodsId(String goodsId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("Z_GOODS_ID", goodsId);
        return prodGoodsRelMapper.delete(wrapper);
    }
}
