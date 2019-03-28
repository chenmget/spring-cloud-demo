package com.iwhalecloud.retail.order.dbservice.impl;

import com.iwhalecloud.retail.order.OrderServiceApplication;
import com.iwhalecloud.retail.order.dto.response.OrderAmountCategoryRespDTO;
import com.iwhalecloud.retail.order.dto.response.PastWeekOrderAmountRespDTO;
import com.iwhalecloud.retail.order.dto.resquest.*;
import com.iwhalecloud.retail.order.service.OrderSummaryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.List;

@SpringBootTest(classes = OrderServiceApplication.class)
@Slf4j
@RunWith(SpringRunner.class)
public class OrderSummaryTest {

    @Autowired
    private OrderSummaryService orderSummaryService;

    @Test
    public void getTodayTodoOrderCount(){
        TodayTodoOrderCountReq req = new TodayTodoOrderCountReq();
//        req.setSupplierId("10010");
        req.setPartnerId("4301211024068");

        int result = orderSummaryService.getTodayTodoOrderCount(req);
        System.out.println("供货商ID："+req.getSupplierId()
                + "  分销商ID：" + req.getPartnerId()
                + "  今日待办订单数量：" + result
        );
    }

    @Test
    public void getTodayOrderCount(){
        TodayOrderCountReq req = new TodayOrderCountReq();
//        req.setSupplierId("10010");
//        req.setPartnerId("4301211024068");

        int result = orderSummaryService.getTodayOrderCount(req);
        System.out.println("供货商ID："+req.getSupplierId()
                + "  分销商ID：" + req.getPartnerId()
                + "  今日订单数量：" + result
        );
    }

    @Test
    public void getTodayOrderAmount(){
        TodayOrderAmountReq req = new TodayOrderAmountReq();
//        req.setSupplierId("10010");
//        req.setPartnerId("4301211024068");

        float result = orderSummaryService.getTodayOrderAmount(req);
        System.out.println("供货商ID："+req.getSupplierId()
                + "  分销商ID：" + req.getPartnerId()
                + "  今日订单销售额：" + result
        );
    }

    @Test
    public void getPastWeekOrderAmount(){

        PastWeekOrderAmountReq req = new PastWeekOrderAmountReq();
//        req.setSupplierId("10010");
//        req.setPartnerId("4301211024068");

        List<PastWeekOrderAmountRespDTO> result = orderSummaryService.getPastWeekOrderAmount(req);

        System.out.println("供货商ID："+req.getSupplierId()
                + "  分销商ID：" + req.getPartnerId()
                + "  今日订单销售额：" + result.toString()
        );
    }


    @Test
    public void getOrderAmountCategory(){
        OrderAmountCategoryReq req = new OrderAmountCategoryReq();
//        req.setSupplierId("10010");
//        req.setPartnerId("4301211024068");
        req.setQueryType(1);
        Calendar c = Calendar.getInstance();
        if(req.getQueryType() == 1){
            // 设置为当月 1号
            c.set(Calendar.DAY_OF_MONTH, 1);
        }else if (req.getQueryType() == 2){
            // 设置为当年 1月1号
            c.set(Calendar.DAY_OF_MONTH, 1);
            c.set(Calendar.MONTH, 0); //月份从0开始
        }
        req.setStartDate(c.getTime());

        List<OrderAmountCategoryRespDTO> result = orderSummaryService.getOrderAmountCategory(req);

        System.out.println("req.toString："+req.toString()
                + "  订单销售额类别：" + result.toString()
        );
    }
}
