package com.iwhalecloud.retail.goods2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.iwhalecloud.retail.goods2b.entity.GoodsRegionRel;
import com.iwhalecloud.retail.goods2b.mapper.GoodsRegionRelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class GoodsRegionRelManager{
    @Resource
    private GoodsRegionRelMapper goodsRegionRelMapper;
    
    public int addGoodsRegionRel(String goodsId, String regionId, String regionName,String lanId) {
        GoodsRegionRel goodsRegionRel = new GoodsRegionRel();
        goodsRegionRel.setGoodsId(goodsId);
        goodsRegionRel.setRegionId(regionId);
        goodsRegionRel.setRegionName(regionName);
        goodsRegionRel.setLanId(lanId);
        return goodsRegionRelMapper.insert(goodsRegionRel);
    }

    public List<GoodsRegionRel> queryGoodsRegionRel(String goodsId){
        QueryWrapper<GoodsRegionRel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_id",goodsId);
        return goodsRegionRelMapper.selectList(queryWrapper);
    }

    public int delGoodsRegionRelByGoodsId(String goodsId) {
        UpdateWrapper<GoodsRegionRel> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("goods_id",goodsId);
        return goodsRegionRelMapper.delete(updateWrapper);
    }
}
