package com.iwhalecloud.retail.warehouse.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.iwhalecloud.retail.warehouse.entity.ResouceEvent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: ResouceEventMapper
 * @author autoCreate
 */
@Mapper
public interface ResouceEventMapper extends BaseMapper<ResouceEvent>{

    Integer updateResourceEventStatusCd(ResouceEvent event);


    List<ResouceEvent> selectMktResEventList(@Param("eventType")String[] eventType,
                                             @Param("startDate")String startDate,
                                             @Param("endDate")String endDate);

}