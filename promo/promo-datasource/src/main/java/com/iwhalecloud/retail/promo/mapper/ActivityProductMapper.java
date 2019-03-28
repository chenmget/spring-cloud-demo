package com.iwhalecloud.retail.promo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.iwhalecloud.retail.promo.entity.ActivityProduct;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Class: ActivityProductMapper
 * @author autoCreate
 */
@Mapper
public interface ActivityProductMapper extends BaseMapper<ActivityProduct>{

    /**
     * 批量新增参与活动产品
     * @param activityProducts
     * @return
     */
//    public Integer batchInsert(List<ActivityProduct> activityProducts);

    /**
     * 根据活动Id删除活动产品
     * @param marketingActivityId
     */
    void deleteActivityProduct(String marketingActivityId);

    /**
     * 查询活动产品的参入总数
     * @param marketingActivityId
     * @return
     */
    Long queryActProductSum (String marketingActivityId);
}