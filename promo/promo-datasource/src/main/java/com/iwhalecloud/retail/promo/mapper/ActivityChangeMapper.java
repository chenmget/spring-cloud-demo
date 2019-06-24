package com.iwhalecloud.retail.promo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.promo.entity.ActivityChange;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Class: ActivityChangeMapper
 * @author autoCreate
 */
@Mapper
public interface ActivityChangeMapper extends BaseMapper<ActivityChange>{
    /**
     * 获取主键
     *
     * @return
     */
    String getPrimaryKey();

    /**
     * 根据营销活动id查找最新的一次活动修改记录
     * @param activityId
     * @return
     */
    ActivityChange queryLatestActivityChangeByActivityId(String activityId);
}