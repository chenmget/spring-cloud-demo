package com.iwhalecloud.retail.goods2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.goods2b.dto.GoodsGroupRelDTO;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsGroupRelAddReq;
import com.iwhalecloud.retail.goods2b.entity.GoodsGroupRel;
import com.iwhalecloud.retail.goods2b.mapper.GoodsGroupRelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * 删除商品关联组根据商品组ID
     * @param groupId
     * @return
     */
    public int deleteGoodsGroupRelByGroupId(String groupId){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("group_id",groupId);
        return goodsGroupRelMapper.delete(queryWrapper);
    }

    /**
     * 删除商品关联组根据主键
     * @param groupRelId
     * @return
     */
    public int deleteOneGoodsGroupRel(String groupRelId){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("group_rel_id",groupRelId);
        return goodsGroupRelMapper.delete(queryWrapper);
    }


    public int updateGoodsGroupRel(GoodsGroupRelDTO req){
        GoodsGroupRel goodsGroupRel = new GoodsGroupRel();
        BeanUtils.copyProperties(req, goodsGroupRel);
        return goodsGroupRelMapper.updateById(goodsGroupRel);

    }

    public List<GoodsGroupRel> queryGoodsGroupRelByGoodsId(String goodsId){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("goods_id",goodsId);
        return goodsGroupRelMapper.selectList(queryWrapper);
    }

    public List<GoodsGroupRel> queryGoodsGroupRelByGroupId(String groupId){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("group_id",groupId);
        return goodsGroupRelMapper.selectList(queryWrapper);
    }
}
