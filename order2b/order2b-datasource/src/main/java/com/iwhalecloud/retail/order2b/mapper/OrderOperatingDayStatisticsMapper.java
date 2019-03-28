package com.iwhalecloud.retail.order2b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.order2b.dto.resquest.report.OrderStatisticsMatureReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderOperatingDayStatisticsMapper extends BaseMapper {

     Integer getOrderCountByTimeAndRole(@Param("req") OrderStatisticsMatureReq req);

    Double getOrderAmountByTimeAndRole(@Param("req") OrderStatisticsMatureReq req);
}
