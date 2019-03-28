package com.iwhalecloud.retail.order.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.order.consts.order.OrderAllStatus;
import com.iwhalecloud.retail.order.dto.response.OrderAmountCategoryRespDTO;
import com.iwhalecloud.retail.order.dto.response.PastWeekOrderAmountRespDTO;
import com.iwhalecloud.retail.order.dto.resquest.*;
import com.iwhalecloud.retail.order.dto.resquest.order.OrderCountOrAmountReq;
import com.iwhalecloud.retail.order.manager.OrderSummaryManager;
import com.iwhalecloud.retail.order.service.OrderSummaryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component("orderSummaryService")
@Service
public class OrderSummaryServiceImpl implements OrderSummaryService {

    @Autowired
    private OrderSummaryManager orderSummaryManager;

    @Override
    public int getTodayTodoOrderCount(TodayTodoOrderCountReq req) {
        OrderCountOrAmountReq orderCountOrAmountReq = new OrderCountOrAmountReq();
        BeanUtils.copyProperties(req, orderCountOrAmountReq);

        //设置状态 待付款2、待发货4、 确认收货5、 待评价7、 已完成6
        List<Integer> statusList = new ArrayList<>();
        statusList.add(Integer.parseInt(OrderAllStatus.ORDER_STATUS_4.getCode()));
        orderCountOrAmountReq.setStatusList(statusList);

        return orderSummaryManager.getOrderCount(orderCountOrAmountReq);
//        return orderSummaryManager.getTodayTodoOrderCount(req);
    }

    @Override
    public int getTodayOrderCount(TodayOrderCountReq req) {
        OrderCountOrAmountReq orderCountOrAmountReq = new OrderCountOrAmountReq();
        BeanUtils.copyProperties(req, orderCountOrAmountReq);

        //设置状态 待付款2、待发货4、 确认收货5、 待评价7、 已完成6
        List<Integer> statusList = new ArrayList<>();
        statusList.add(Integer.parseInt(OrderAllStatus.ORDER_STATUS_2.getCode()));
        statusList.add(Integer.parseInt(OrderAllStatus.ORDER_STATUS_4.getCode()));
        statusList.add(Integer.parseInt(OrderAllStatus.ORDER_STATUS_5.getCode()));
        statusList.add(Integer.parseInt(OrderAllStatus.ORDER_STATUS_6.getCode()));
        statusList.add(Integer.parseInt(OrderAllStatus.ORDER_STATUS_7.getCode()));
        orderCountOrAmountReq.setStatusList(statusList);

        return orderSummaryManager.getOrderCount(orderCountOrAmountReq);
//        return orderSummaryManager.getTodayOrderCount(req);
    }

    @Override
    public float getTodayOrderAmount(TodayOrderAmountReq req) {
        OrderCountOrAmountReq orderCountOrAmountReq = new OrderCountOrAmountReq();
        BeanUtils.copyProperties(req, orderCountOrAmountReq);

//        orderCountOrAmountReq.setStartDate(orderCountOrAmountReq.getDateWithInterval(-10));
        //设置状态 待付款2、待发货4、 确认收货5、 待评价7、 已完成6
        List<Integer> statusList = new ArrayList<>();
        statusList.add(Integer.parseInt(OrderAllStatus.ORDER_STATUS_4.getCode()));
        statusList.add(Integer.parseInt(OrderAllStatus.ORDER_STATUS_5.getCode()));
        statusList.add(Integer.parseInt(OrderAllStatus.ORDER_STATUS_6.getCode()));
        statusList.add(Integer.parseInt(OrderAllStatus.ORDER_STATUS_7.getCode()));
        orderCountOrAmountReq.setStatusList(statusList);

        return orderSummaryManager.getOrderAmount(orderCountOrAmountReq);
//        return orderSummaryManager.getTodayOrderAmount(req);
    }

    @Override
    public List<PastWeekOrderAmountRespDTO> getPastWeekOrderAmount(PastWeekOrderAmountReq req) {

        //先获取最近9天的订单金额
        OrderCountOrAmountReq orderCountOrAmountReq = new OrderCountOrAmountReq();
        BeanUtils.copyProperties(req, orderCountOrAmountReq);
        List<Integer> statusList = new ArrayList<>();
        statusList.add(Integer.parseInt(OrderAllStatus.ORDER_STATUS_4.getCode()));
        statusList.add(Integer.parseInt(OrderAllStatus.ORDER_STATUS_5.getCode()));
        statusList.add(Integer.parseInt(OrderAllStatus.ORDER_STATUS_6.getCode()));
        statusList.add(Integer.parseInt(OrderAllStatus.ORDER_STATUS_7.getCode()));
        orderCountOrAmountReq.setStatusList(statusList);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 结果集
        List<PastWeekOrderAmountRespDTO> result = new ArrayList<>();
        for (int i=0; i<9; i++){
            // 设置日期
            orderCountOrAmountReq.setStartDate(orderCountOrAmountReq.getDateWithInterval(-i));
            orderCountOrAmountReq.setEndDate(orderCountOrAmountReq.getDateWithInterval(-i+1));
            PastWeekOrderAmountRespDTO respDTO = new PastWeekOrderAmountRespDTO();
            respDTO.setOrderDate(sdf.format(orderCountOrAmountReq.getStartDate()));
            respDTO.setTimeStamp(orderCountOrAmountReq.getStartDate().getTime());
            respDTO.setOrderAmount(orderSummaryManager.getOrderAmount(orderCountOrAmountReq));
            result.add(respDTO);
        }



        //处理数据 返回数据是最近9天的  日期是递减的
//        List<PastWeekOrderAmountRespDTO> result = orderSummaryManager.getPastWeekOrderAmount(req);
        // 添加时间戳
        for (int i= 0; i< result.size()-1; i++){
            PastWeekOrderAmountRespDTO lastOne=result.get(i+1);
            PastWeekOrderAmountRespDTO current=result.get(i);
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            try {
//                current.setTimeStamp(sdf.parse(current.getOrderDate()).getTime());
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
            String desc = "比" + lastOne.getOrderDate();
            String proportion = "";
            int upOrDown = 0; // 1:上升 -1：下降  0：没变化
            DecimalFormat df = new DecimalFormat("0.00%");
            if(lastOne.getOrderAmount() == 0){
                if(current.getOrderAmount() == 0){
                    // 增长
                    desc = desc +"增长";
                    proportion = "0%";
                    upOrDown = 0;
                } else {
                    // 下降
                    desc = desc +"增长";
                    proportion = "100%";
                    upOrDown = 1;
                }
            } else {
                if(current.getOrderAmount() > lastOne.getOrderAmount()){
                    // 增长
                    desc = desc +"增长";
                    proportion = df.format((current.getOrderAmount()-lastOne.getOrderAmount())/lastOne.getOrderAmount());;
                    upOrDown = 1;
                } else if(current.getOrderAmount() < lastOne.getOrderAmount()) {
                    // 下降
                    desc = desc +"下降";
                    proportion =  df.format((lastOne.getOrderAmount()-current.getOrderAmount())/lastOne.getOrderAmount());
                    upOrDown = -1;
                } else {
                    // 增长
                    desc = desc +"增长";
                    proportion = "0%";;
                    upOrDown = 0; // 相等
                }
            }
            desc = desc + proportion;
            current.setUpOrDown(upOrDown);
            current.setProportion(proportion);
            current.setDesc(desc);
        }
        // 移除头尾两个数据
        result.remove(result.size()-1);
        result.remove(0);
        return result;
    }

    @Override
    public List<OrderAmountCategoryRespDTO> getOrderAmountCategory(OrderAmountCategoryReq req) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        if(req.getQueryType() == 1){
            // 设置为当月 1号
            c.set(Calendar.DAY_OF_MONTH, 1);
        }else if (req.getQueryType() == 2){
            // 设置为当年 1月1号
            c.set(Calendar.DAY_OF_MONTH, 1);
            c.set(Calendar.MONTH, 0); //月份从0开始
        }
        // 只设置开始时间就可以啦 结束时间默认为现在
        req.setStartDate(c.getTime());
        //设置状态 待付款2、待发货4、 确认收货5、 待评价7、 已完成6
        List<Integer> statusList = new ArrayList<>();
        statusList.add(Integer.parseInt(OrderAllStatus.ORDER_STATUS_4.getCode()));
        statusList.add(Integer.parseInt(OrderAllStatus.ORDER_STATUS_5.getCode()));
        statusList.add(Integer.parseInt(OrderAllStatus.ORDER_STATUS_6.getCode()));
        statusList.add(Integer.parseInt(OrderAllStatus.ORDER_STATUS_7.getCode()));
        req.setStatusList(statusList);

        List<OrderAmountCategoryRespDTO> result = orderSummaryManager.getOrderAmountCategory(req);
        float total = 0;
        for(OrderAmountCategoryRespDTO item: result){
            total += item.getOrderAmount();
        }
        DecimalFormat df = new DecimalFormat("0.00%");
        for(OrderAmountCategoryRespDTO item: result){
            if(total == 0){
                item.setProportion("0%");
            } else {
                item.setProportion(df.format(item.getOrderAmount()/total));
            }
        }
        return result;
    }
}
