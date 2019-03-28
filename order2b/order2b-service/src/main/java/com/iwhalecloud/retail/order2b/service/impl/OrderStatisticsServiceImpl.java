package com.iwhalecloud.retail.order2b.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.consts.order.OrderAllStatus;
import com.iwhalecloud.retail.order2b.dto.response.OrderWorkPlatformShowResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.OrderStatisticsReq;
import com.iwhalecloud.retail.order2b.dto.resquest.report.OrderStatisticsMatureReq;
import com.iwhalecloud.retail.order2b.dto.resquest.report.OrderStatisticsRawReq;
import com.iwhalecloud.retail.order2b.manager.AfterSaleManager;
import com.iwhalecloud.retail.order2b.manager.OrderManager;
import com.iwhalecloud.retail.order2b.manager.OrderOperatingDayStatisticsManager;
import com.iwhalecloud.retail.order2b.service.OrderStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class OrderStatisticsServiceImpl implements OrderStatisticsService {

    @Autowired
    private OrderManager orderManager;

    @Autowired
    private AfterSaleManager afterSaleManager;

    @Autowired
    private OrderOperatingDayStatisticsManager orderOperatingDayStatisticsManager;

    @Override
    public ResultVO<OrderWorkPlatformShowResp> getOrderWorkPlatformShowTotal( OrderStatisticsReq req) {
        String merchantId=req.getMerchantId();
        boolean isSupplier=req.getIsSupplier();
        log.info("ResourceInstStoreServiceImpl.getOrderDataRawTotal(), 入参merchantId ", merchantId);
        OrderWorkPlatformShowResp resp = new OrderWorkPlatformShowResp();
        resp.setBuyerConfirmCount(queryOrderCount(merchantId,true,false,new String[]{OrderAllStatus.ORDER_STATUS_12.getCode()}));
        resp.setBuyerPayCount(queryOrderCount(merchantId,true,false,new String[]{OrderAllStatus.ORDER_STATUS_2.getCode()}));
        resp.setBuyerSendCount(queryOrderCount(merchantId,true,false,new String[]{OrderAllStatus.ORDER_STATUS_4.getCode()}));
        resp.setBuyerReceiveCount(queryOrderCount(merchantId,true,false,new String[]{OrderAllStatus.ORDER_STATUS_5.getCode()}));
        resp.setBuyerCommentCount(queryOrderCount(merchantId,true,false,new String[]{OrderAllStatus.ORDER_STATUS_6.getCode()}));
        // 退换货
        List<String> applyStatusList = getApplyStatusList();
        resp.setBuyerReturnCount(queryApplyOrderCount(merchantId,true,false,applyStatusList.toArray(new String[applyStatusList.size()])));
        resp.setSellerConfirmCount(queryOrderCount(merchantId,false,false,new String[]{OrderAllStatus.ORDER_STATUS_12.getCode()}));
        resp.setSellerPayCount(queryOrderCount(merchantId,false,false,new String[]{OrderAllStatus.ORDER_STATUS_2.getCode()}));
        resp.setSellerSendCount(queryOrderCount(merchantId,false,false,new String[]{OrderAllStatus.ORDER_STATUS_4.getCode()}));
        resp.setSellerReceiveCount(queryOrderCount(merchantId,false,false,new String[]{OrderAllStatus.ORDER_STATUS_5.getCode()}));
        // 售后
        resp.setSellerDoneCount(queryApplyOrderCount(merchantId,false,false,applyStatusList.toArray(new String[applyStatusList.size()])));

        String[] usefulOrderStatusArr = {OrderAllStatus.ORDER_STATUS_2.getCode(),OrderAllStatus.ORDER_STATUS_3.getCode(),OrderAllStatus.ORDER_STATUS_4.getCode(),
                OrderAllStatus.ORDER_STATUS_5.getCode(),OrderAllStatus.ORDER_STATUS_6.getCode(),OrderAllStatus.ORDER_STATUS_10.getCode(),
                OrderAllStatus.ORDER_STATUS_11.getCode(),OrderAllStatus.ORDER_STATUS_12.getCode()};
        resp.setBuyerOrderCountToday(queryOrderCount(merchantId,true,true,usefulOrderStatusArr));
        resp.setSellerOrderCountToday(queryOrderCount(merchantId,false,true,usefulOrderStatusArr));
        resp.setBuyerOrderAmountToday(queryOrderAmount(merchantId,true,true,usefulOrderStatusArr));
        resp.setSellerOrderAmountToday(queryOrderAmount(merchantId,false,true,usefulOrderStatusArr));

        resp.setBuyerOrderCountThisMonth(queryOrderHistoryCount(merchantId,isSupplier,true,true,false));
        resp.setBuyerOrderCountThisYear(queryOrderHistoryCount(merchantId,isSupplier,true,false,true));
        resp.setSellerOrderCountThisMonth(queryOrderHistoryCount(merchantId,isSupplier,false,true,false));
        resp.setSellerOrderCountThisYear(queryOrderHistoryCount(merchantId,isSupplier,false,false,true));
        resp.setBuyerOrderAmountThisMonth(queryOrderHistoryAmount(merchantId,isSupplier,true,true,false));
        resp.setBuyerOrderAmountThisYear(queryOrderHistoryAmount(merchantId,isSupplier,true,false,true));
        resp.setSellerOrderAmountThisMonth(queryOrderHistoryAmount(merchantId,isSupplier,false,true,false));
        resp.setSellerOrderAmountThisYear(queryOrderHistoryAmount(merchantId,isSupplier,false,false,true));
        resp.setSellerOrderCountTotal(queryOrderHistoryCount(merchantId,isSupplier,false,false,false));
        resp.setSellerOrderAmountTotal(queryOrderHistoryAmount(merchantId,isSupplier,false,false,false));
        log.info("ResourceInstStoreServiceImpl.getOrderDataRawTotal(), 出参OrderStatisticsRawResp={} ", merchantId);
        return ResultVO.success(resp);
    }

    private int queryOrderCount(String merchantId,boolean isBuyer,boolean isToday,String[] statuses){
        OrderStatisticsRawReq req = new OrderStatisticsRawReq();
        req.setMerchantId(merchantId);
        req.setIsBuyer(isBuyer);
        req.setIsToday(isToday);
        req.setStatusList(Arrays.asList(statuses));
        return orderManager.getOrderCountByCondition(req);
    }

    private int queryApplyOrderCount(String merchantId,boolean isBuyer,boolean isToday,String[] statuses){
        OrderStatisticsRawReq req = new OrderStatisticsRawReq();
        req.setMerchantId(merchantId);
        req.setIsBuyer(isBuyer);
        req.setIsToday(isToday);
        req.setStatusList(Arrays.asList(statuses));
        return afterSaleManager.getApplyOrderCountByCondition(req);
    }

    private double queryOrderAmount(String merchantId,boolean isBuyer,boolean isToday,String[] statuses){
        OrderStatisticsRawReq req = new OrderStatisticsRawReq();
        req.setMerchantId(merchantId);
        req.setIsBuyer(isBuyer);
        req.setIsToday(isToday);
        req.setStatusList(Arrays.asList(statuses));
        return orderManager.getOrderAmountByCondition(req);
    }

    private int queryOrderHistoryCount(String merchantId,boolean isSupplier,boolean isBuyer,boolean isThisMonth,boolean isThisYear){
        OrderStatisticsMatureReq req = new OrderStatisticsMatureReq();
        req.setMerchantId(merchantId);
        req.setIsThisMonth(isThisMonth);
        req.setIsThisYear(isThisYear);
        req.setIsBuyer(isBuyer);
        req.setIsSupplier(isSupplier);
        return orderOperatingDayStatisticsManager.getOrderCountByTimeAndRole(req);
    }

    private double queryOrderHistoryAmount(String merchantId,boolean isSupplier,boolean isBuyer,boolean isThisMonth,boolean isThisYear){
        OrderStatisticsMatureReq req = new OrderStatisticsMatureReq();
        req.setMerchantId(merchantId);
        req.setIsThisMonth(isThisMonth);
        req.setIsThisYear(isThisYear);
        req.setIsBuyer(isBuyer);
        req.setIsSupplier(isSupplier);
        return orderOperatingDayStatisticsManager.getOrderAmountByTimeAndRole(req);
    }

    /**
     * 获取订单状态
     * @return
     */
    private List<String> getApplyStatusList() {
        List<String> statusList = new ArrayList<>();
        statusList.add(OrderAllStatus.ORDER_STATUS_11.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_11_.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_12.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_12_.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_2.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_13.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_14.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_3.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_4.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_5.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_6.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_10.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_8_.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_9_.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_99.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_21.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_22.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_23.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_24.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_25.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_28.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_29.getCode());
        return statusList;
    }
}
