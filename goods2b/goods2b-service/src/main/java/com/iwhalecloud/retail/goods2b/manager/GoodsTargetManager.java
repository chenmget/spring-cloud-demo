package com.iwhalecloud.retail.goods2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.iwhalecloud.retail.goods2b.entity.GoodsTargetRel;
import com.iwhalecloud.retail.goods2b.mapper.GoodsTargetRelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2019/3/11.
 */
@Component
public class GoodsTargetManager {
    @Resource
    private GoodsTargetRelMapper goodsTargetRelMapper;

    public int addGoodsTargerRel(String goodsId,String targerId){
        GoodsTargetRel goodsTargetRel = new GoodsTargetRel();
        goodsTargetRel.setGoodsId(goodsId);
        goodsTargetRel.setTargetId(targerId);
        return goodsTargetRelMapper.insert(goodsTargetRel);
    }

    public int deleteGoodsTargerRel(String goodsId){
        UpdateWrapper<GoodsTargetRel> goodsTargetRelUpdateWrapper = new UpdateWrapper<GoodsTargetRel>();
        goodsTargetRelUpdateWrapper.eq("goods_id", goodsId);
        return goodsTargetRelMapper.delete(goodsTargetRelUpdateWrapper);
    }

    public List<GoodsTargetRel> queryGoodsTargerRel(String goodsId){
        QueryWrapper<GoodsTargetRel> goodsTargetRelQueryWrapper = new QueryWrapper<GoodsTargetRel>();
        goodsTargetRelQueryWrapper.eq("goods_id",goodsId);
        return goodsTargetRelMapper.selectList(goodsTargetRelQueryWrapper);
    }
}
