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

}