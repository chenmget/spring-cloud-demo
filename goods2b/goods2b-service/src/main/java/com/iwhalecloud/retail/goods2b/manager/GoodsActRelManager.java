package com.iwhalecloud.retail.goods2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.goods2b.common.GoodsConst;
import com.iwhalecloud.retail.goods2b.dto.GoodsActRelDTO;
import com.iwhalecloud.retail.goods2b.entity.GoodsActRel;
import com.iwhalecloud.retail.goods2b.mapper.GoodsActRelMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class GoodsActRelManager{
    @Resource
    private GoodsActRelMapper goodsActRelMapper;
    
    public List<String> queryActNameByGoodsId(String goodsId){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("is_deleted", GoodsConst.NO_DELETE);
        queryWrapper.in("goods_id",goodsId);
        List<GoodsActRel> actList = goodsActRelMapper.selectList(queryWrapper);
        List<String> actNames = actList.stream().map(GoodsActRel::getActName).collect(Collectors.toList());
        return actNames;
    }

    /**
     * 查询营销活动列表
     * @param goodsId
     * @return
     */
    public List<GoodsActRel> queryGoodActRel(String goodsId){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("is_deleted", GoodsConst.NO_DELETE);
        queryWrapper.eq("goods_id",goodsId);
        List<GoodsActRel> goodsActRels = goodsActRelMapper.selectList(queryWrapper);
        return goodsActRels;
    }

    /**
     * 根据商品ID和活动类型查询有效的活动
     * @param goodsId
     * @param activityType
     * @return
     */
    public List<GoodsActRel> queryGoodsActRel(String goodsId,String activityType) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("is_deleted", GoodsConst.NO_DELETE);
        queryWrapper.eq("goods_id",goodsId);
        queryWrapper.eq("act_type",activityType);
        List<GoodsActRel> goodsActRels = goodsActRelMapper.selectList(queryWrapper);
        return goodsActRels;
    }

    /**
     * 根据商品ID删除活动关联关系
     * @param goodsId
     */
    public void deleteGoodsActRel(String goodsId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("goods_id",goodsId);

        goodsActRelMapper.delete(queryWrapper);
    }

    /**
     * 修改商品与活动的关联关系
     * @param goodsId
     * @param goodsActRelDTOs
     */
    public void editGoodsActRel(String goodsId,List<GoodsActRelDTO> goodsActRelDTOs) {
        this.deleteGoodsActRel(goodsId);
        this.addGoodsActRel(goodsId,goodsActRelDTOs);
    }

    /**
     * 添加商品与活动的关联关系
     * @param goodsActs
     */
    public void addGoodsActRel(String goodsId,List<GoodsActRelDTO> goodsActs) {
        if (!CollectionUtils.isEmpty(goodsActs)) {
            for (GoodsActRelDTO relDto : goodsActs) {
                GoodsActRel goodsActRel = new GoodsActRel();
                BeanUtils.copyProperties(relDto,goodsActRel);
                goodsActRel.setGoodsId(goodsId);
                goodsActRel.setIsDeleted(GoodsConst.NO_DELETE.toString());

                goodsActRelMapper.insert(goodsActRel);
            }
        }
    }
    
}
