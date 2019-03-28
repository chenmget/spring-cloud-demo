package com.iwhalecloud.retail.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.oms.dto.ReportEventShopRankDTO;
import com.iwhalecloud.retail.oms.dto.ReportEventTimeIntervalDTO;
import com.iwhalecloud.retail.oms.dto.resquest.report.ReportOperationDeskReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface EventOperationDeskMapper extends BaseMapper {

    int getTodayEventCountByPartnerId(@Param("partnerId") String partnerId, @Param("eventCode") String eventCode);

    int getEventCountByArea(ReportOperationDeskReq req);

    List<ReportEventShopRankDTO> getShopVisitVolumeRankByArea(ReportOperationDeskReq req);

    List<ReportEventShopRankDTO> getShopWorkTimeRankByArea(ReportOperationDeskReq req);

    List<ReportEventTimeIntervalDTO> getTimeIntervalVisitByArea(ReportOperationDeskReq req);

    List<Map<String, Object>> getOnOffLineTimeAboutDevice(ReportOperationDeskReq req);
}
