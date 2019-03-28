package com.iwhalecloud.retail.goods.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.goods.dto.req.GoodsGroupRelAddReq;
import com.iwhalecloud.retail.goods.entity.GoodsGroupRel;
import com.iwhalecloud.retail.goods.mapper.GoodsGroupRelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author My
 * @Date 2018/11/5
 **/
@Component
public class GoodsGroupRelManager {
    @Resource
    private GoodsGroupRelMapper goodsGroupRelMapper;

    /**
     * 添加商品关联组
     * @param req
     * @return
     */
    public int addGoodsGroupRel(GoodsGroupRelAddReq req){
        GoodsGroupRel goodsGroupRel = new GoodsGroupRel();
        BeanUtils.copyProperties(req, goodsGroupRel);
        return goodsGroupRelMapper.insert(goodsGroupRel);
    }

    /**
     * 删除商品关联组根据商品ID
     * @param goodsId
     * @return
     */
    public int deleteGoodsGroupRel(String goodsId){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("goods_id",goodsId);
        return goodsGroupRelMapper.delete(queryWrapper);
    }
}
