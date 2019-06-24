package com.iwhalecloud.retail.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.order.dto.ReportOrderShopRankDTO;
import com.iwhalecloud.retail.order.dto.ReportOrderTimeIntervalDTO;
import com.iwhalecloud.retail.order.dto.resquest.ReportOrderOpeDeskReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderOperationDeskMapper extends BaseMapper {

    int getOrderCountByArea(ReportOrderOpeDeskReq req);

    double getOrderAmountByArea(ReportOrderOpeDeskReq req);

    int getSaleCountByArea(ReportOrderOpeDeskReq req);

    List<ReportOrderShopRankDTO> getShopSaleAmountRankByArea(ReportOrderOpeDeskReq req);

    List<ReportOrderTimeIntervalDTO> getTimeIntervalAmountByArea(ReportOrderOpeDeskReq req);
}
