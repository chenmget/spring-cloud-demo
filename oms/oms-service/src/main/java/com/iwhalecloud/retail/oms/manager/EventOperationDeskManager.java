package com.iwhalecloud.retail.oms.manager;

import com.iwhalecloud.retail.oms.dto.ReportEventShopRankDTO;
import com.iwhalecloud.retail.oms.dto.ReportEventTimeIntervalDTO;
import com.iwhalecloud.retail.oms.dto.resquest.report.ReportOperationDeskReq;
import com.iwhalecloud.retail.oms.entity.CloudDeviceLog;
import com.iwhalecloud.retail.oms.mapper.EventOperationDeskMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class EventOperationDeskManager {

    private static final DateFormat DATE_FORMAT_TRUNC = new SimpleDateFormat("yyyy/MM/dd 00:00:00");

    @Resource
    private EventOperationDeskMapper eventOperationDeskMapper;

    public int getTodayEventCountByPartnerId(String partnerId, String eventCode){
        return eventOperationDeskMapper.getTodayEventCountByPartnerId(partnerId, eventCode);
    }

    public int getEventCountByArea(ReportOperationDeskReq req) {
        return eventOperationDeskMapper.getEventCountByArea(req);
    }

    public List<ReportEventShopRankDTO> getShopVisitVolumeRankByArea(ReportOperationDeskReq req){
        return eventOperationDeskMapper.getShopVisitVolumeRankByArea(req);
    }

    public List<ReportEventShopRankDTO> getShopWorkTimeRankByArea(ReportOperationDeskReq req){
        return eventOperationDeskMapper.getShopWorkTimeRankByArea(req);
    }

    public List<ReportEventTimeIntervalDTO> getTimeIntervalVisitByArea(ReportOperationDeskReq req) throws Exception {
        // 取出计算数据
        List<ReportEventTimeIntervalDTO> storeVisitList = eventOperationDeskMapper.getTimeIntervalVisitByArea(req);
        // 取整
        long beginTimeL = DATE_FORMAT_TRUNC.parse(DATE_FORMAT_TRUNC.format(req.getBeginTime())).getTime();
        long endTimeL = DATE_FORMAT_TRUNC.parse(DATE_FORMAT_TRUNC.format(req.getEndTime())).getTime();
        Map<Long,Double> timeIntervalMap = new LinkedHashMap<>();
        do{
            timeIntervalMap.put(beginTimeL,0.0);
            // 增加一天
            beginTimeL = beginTimeL + 24*60*60*1000;
        }
        while (beginTimeL <= endTimeL);
        // 拆箱
        for (ReportEventTimeIntervalDTO dto : storeVisitList){
            Long timeStamp = dto.getTimeStamp();
            Double reportAmount = dto.getReportAmount();
            for (Map.Entry<Long,Double> entry : timeIntervalMap.entrySet()){
                Long beginTimeStamp = entry.getKey();
                Long endTimeStamp = entry.getKey() + 24*60*60*1000;
                if (timeStamp >= beginTimeStamp && timeStamp < endTimeStamp){
                    entry.setValue(entry.getValue() + reportAmount);
                    break;
                }
            }
        }
        List<ReportEventTimeIntervalDTO> dtoList = convertToJSONList(timeIntervalMap);
        return dtoList;
    }

    public List<ReportEventTimeIntervalDTO> getOnOffLineTimeAboutDevice(ReportOperationDeskReq req) throws Exception{
        List<Map<String, Object>> onOffLineTimeList = eventOperationDeskMapper.getOnOffLineTimeAboutDevice(req);
        // 取整
        long beginTimeL = DATE_FORMAT_TRUNC.parse(DATE_FORMAT_TRUNC.format(req.getBeginTime())).getTime();
        long endTimeL = DATE_FORMAT_TRUNC.parse(DATE_FORMAT_TRUNC.format(req.getEndTime())).getTime();
        Map<Long,Double> timeIntervalMap = new HashMap<>();
        do{
            timeIntervalMap.put(beginTimeL,0.0);
            // 增加一天
            beginTimeL = beginTimeL + 24*60*60*1000;
        }
        while (beginTimeL <= endTimeL);
        //拆箱
        for (Map<String,Object> timeMap : onOffLineTimeList){
            long onlineTimeL = ((Timestamp)timeMap.get(CloudDeviceLog.FieldNames.onlineTime.getTableFieldName())).getTime();
            long offlineTimeL = ((Timestamp)timeMap.get(CloudDeviceLog.FieldNames.offlineTime.getTableFieldName())).getTime();
            for (Map.Entry entry : timeIntervalMap.entrySet()){
                handleTimeData(entry,onlineTimeL,offlineTimeL);
            }
        }
        List<ReportEventTimeIntervalDTO> dtoList = convertToJSONList(timeIntervalMap);
        return dtoList;
    }

    /**
     * 转化为出参DTO数据结构
     * @param timeIntervalMap
     * @return
     */
    private List<ReportEventTimeIntervalDTO> convertToJSONList(Map<Long,Double> timeIntervalMap){
        List<ReportEventTimeIntervalDTO> dtoList = new ArrayList<>();
        for(Map.Entry entry : timeIntervalMap.entrySet()){
            ReportEventTimeIntervalDTO dto = new ReportEventTimeIntervalDTO();
            dto.setTimeStamp((Long) entry.getKey());
            dto.setReportAmount((Double)entry.getValue());
            dtoList.add(dto);
        }
        return dtoList;
    }

    /**
     * 计算各时段工作时长叠加总值
     * @param entry
     * @param onlineTimeL
     * @param offlineTimeL
     */
    private void handleTimeData(Map.Entry<Long,Double> entry, long onlineTimeL, long offlineTimeL){
        long beginTimeL = entry.getKey();
        long endTimeL = beginTimeL + 24*60*60*1000;
        long less = 0;
        //double total = entry.getValue();
        if (onlineTimeL <= beginTimeL && beginTimeL <= offlineTimeL && offlineTimeL <= endTimeL){
            less = offlineTimeL - beginTimeL;
        } else if (beginTimeL <= onlineTimeL && onlineTimeL <= endTimeL && endTimeL <= offlineTimeL){
            less = endTimeL - onlineTimeL;
        } else if (beginTimeL <= onlineTimeL && onlineTimeL <= offlineTimeL && offlineTimeL <= endTimeL){
            less = offlineTimeL - onlineTimeL;
        } else if (onlineTimeL <= beginTimeL && beginTimeL <= endTimeL && endTimeL <= offlineTimeL){
            less = endTimeL - beginTimeL;
        }
        BigDecimal lessBd = new BigDecimal(less);
        BigDecimal hoursBd = lessBd.divide(new BigDecimal(60*60*1000),1,BigDecimal.ROUND_HALF_UP);
        entry.setValue(entry.getValue()+ hoursBd.doubleValue());
    }
}
