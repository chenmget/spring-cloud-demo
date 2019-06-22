package com.iwhalecloud.retail.promo.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.promo.entity.ActivityGoods;
import com.iwhalecloud.retail.promo.mapper.ActivityGoodsMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class ActivityGoodsManager{
    @Resource
    private ActivityGoodsMapper activityGoodsMapper;


    /**
     * 根据商品ID查询商品
     * @param goodsId 商品ID
     * @return 商品
     */
    public List<ActivityGoods> queryMarketingActivityCode(String goodsId){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(ActivityGoods.FieldNames.goodsId.getTableFieldName(),goodsId);
        return activityGoodsMapper.selectList(queryWrapper);
    }

}
