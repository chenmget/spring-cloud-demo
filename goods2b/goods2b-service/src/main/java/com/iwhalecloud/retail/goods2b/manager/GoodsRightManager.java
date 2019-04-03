package com.iwhalecloud.retail.goods2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.goods2b.dto.GoodsRightDTO;
import com.iwhalecloud.retail.goods2b.entity.Goods;
import com.iwhalecloud.retail.goods2b.entity.GoodsRight;
import com.iwhalecloud.retail.goods2b.mapper.GoodsRightMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2019/2/23.
 */
@Component
public class GoodsRightManager {

    @Resource
    private GoodsRightMapper goodsRightMapper;

    public int addGoodsRight(GoodsRight goodsRight){
        return goodsRightMapper.insert(goodsRight);
    }

    public int deleteGoodsRightByGoodsId(String goodsId){
        QueryWrapper<GoodsRight> queryWrapper = new QueryWrapper();
        queryWrapper.eq("goods_id",goodsId);
        return goodsRightMapper.delete(queryWrapper);
    }

    public int deleteOneGoodsRight(String id){
        return goodsRightMapper.deleteById(id);
    }

    public List<GoodsRight> listAll(String goodsId){
        QueryWrapper<GoodsRight> queryWrapper = new QueryWrapper();
        queryWrapper.eq("goods_id",goodsId);
        return goodsRightMapper.selectList(queryWrapper);
    }

    public Boolean batchInsertGoodsRight(List<GoodsRight> goodsRightList){
        int i = goodsRightMapper.batchAddGoodsRight(goodsRightList);
        if(i>0) {
            return true;
        }
        return false;
    }

    public int updateGoodsRight(GoodsRightDTO goodsRightDTO){
        GoodsRight goodsRight = new GoodsRight();
        BeanUtils.copyProperties(goodsRightDTO, goodsRight);
        return goodsRightMapper.updateById(goodsRight);
    }
}
