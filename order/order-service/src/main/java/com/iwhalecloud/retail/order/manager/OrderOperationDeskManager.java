package com.iwhalecloud.retail.order.manager;

import com.iwhalecloud.retail.order.dto.ReportOrderShopRankDTO;
import com.iwhalecloud.retail.order.dto.ReportOrderTimeIntervalDTO;
import com.iwhalecloud.retail.order.dto.resquest.ReportOrderOpeDeskReq;
import com.iwhalecloud.retail.order.mapper.OrderOperationDeskMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderOperationDeskManager {

    private static final DateFormat DATE_FORMAT_TRUNC = new SimpleDateFormat("yyyy/MM/dd 00:00:00");

    @Resource
    private OrderOperationDeskMapper orderOperationDeskMapper;

    public int getOrderCountByArea(ReportOrderOpeDeskReq req){
        return orderOperationDeskMapper.getOrderCountByArea(req);
    }

    public double getOrderAmountByArea(ReportOrderOpeDeskReq req){
        return orderOperationDeskMapper.getOrderAmountByArea(req);
    }

    public int getSaleCountByArea(ReportOrderOpeDeskReq req){
        return orderOperationDeskMapper.getSaleCountByArea(req);
    }

    public List<ReportOrderShopRankDTO> getShopSaleAmountRankByArea(ReportOrderOpeDeskReq req){
        return orderOperationDeskMapper.getShopSaleAmountRankByArea(req);
    }

    public List<ReportOrderTimeIntervalDTO> getTimeIntervalAmountByArea(ReportOrderOpeDeskReq req) throws Exception {
        // 取出计算数据
        List<ReportOrderTimeIntervalDTO> storeVisitList = orderOperationDeskMapper.getTimeIntervalAmountByArea(req);
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
        for (ReportOrderTimeIntervalDTO dto : storeVisitList){
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
        List<ReportOrderTimeIntervalDTO> dtoList = convertToJSONList(timeIntervalMap);
        return dtoList;
    }

    /**
     * 转化为出参DTO数据结构
     * @param timeIntervalMap
     * @return
     */
    private List<ReportOrderTimeIntervalDTO> convertToJSONList(Map<Long,Double> timeIntervalMap){
        List<ReportOrderTimeIntervalDTO> dtoList = new ArrayList<>();
        for(Map.Entry entry : timeIntervalMap.entrySet()){
            ReportOrderTimeIntervalDTO dto = new ReportOrderTimeIntervalDTO();
            dto.setTimeStamp((Long) entry.getKey());
            dto.setReportAmount((Double)entry.getValue());
            dtoList.add(dto);
        }
        return dtoList;
    }

}
