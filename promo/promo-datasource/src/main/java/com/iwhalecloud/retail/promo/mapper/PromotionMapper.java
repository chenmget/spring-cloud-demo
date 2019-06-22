package com.iwhalecloud.retail.promo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.promo.entity.Promotion;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Class: PromotionMapper
 * @author autoCreate
 */
@Mapper
public interface PromotionMapper extends BaseMapper<Promotion>{
    /**
     * 根据活动Id删除优惠信息
     * @param marketingActivityId
     */
    void deletePromotion(String marketingActivityId);
}