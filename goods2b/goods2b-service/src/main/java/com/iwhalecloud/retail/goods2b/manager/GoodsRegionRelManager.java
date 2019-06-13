package com.iwhalecloud.retail.goods2b.manager;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.iwhalecloud.retail.goods2b.entity.GoodsRegionRel;
import com.iwhalecloud.retail.goods2b.mapper.GoodsRegionRelMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
public class GoodsRegionRelManager {
    @Resource
    private GoodsRegionRelMapper goodsRegionRelMapper;

    public int addGoodsRegionRel(String goodsId, String regionId, String regionName, String lanId) {
        GoodsRegionRel goodsRegionRel = new GoodsRegionRel();
        goodsRegionRel.setGoodsId(goodsId);
        goodsRegionRel.setRegionId(regionId);
        goodsRegionRel.setRegionName(regionName);
        goodsRegionRel.setLanId(lanId);
        return goodsRegionRelMapper.insert(goodsRegionRel);
    }

    public int saveGoodsRegionRel(GoodsRegionRel goodsRegionRel) {
        log.info("GoodsRegionRelManager.saveGoodsRegionRel(), input：GoodsRegionRel={} ", JSON.toJSONString(goodsRegionRel));
        int result = goodsRegionRelMapper.insert(goodsRegionRel);
        log.info("GoodsRegionRelManager.saveGoodsRegionRel(), output：insert effect rows={} ", result);
        return result;
    }

    public List<GoodsRegionRel> queryGoodsRegionRel(String goodsId) {
        QueryWrapper<GoodsRegionRel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(GoodsRegionRel.FieldNames.goodsId.getTableFieldName(), goodsId);
        return goodsRegionRelMapper.selectList(queryWrapper);
    }

    public int delGoodsRegionRelByGoodsId(String goodsId) {
        UpdateWrapper<GoodsRegionRel> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(GoodsRegionRel.FieldNames.goodsId.getTableFieldName(), goodsId);
        return goodsRegionRelMapper.delete(updateWrapper);
    }
}
