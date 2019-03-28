package com.iwhalecloud.retail.order2b.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.order2b.dto.model.report.ReportOrderShopRankDTO;
import com.iwhalecloud.retail.order2b.dto.model.report.ReportOrderTimeIntervalDTO;
import com.iwhalecloud.retail.order2b.dto.resquest.report.ReportOrderOpeDeskReq;
import com.iwhalecloud.retail.order2b.manager.OrderOperationDeskManager;
import com.iwhalecloud.retail.order2b.service.OrderOperationDeskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
@Service
public class OrderOperationDeskServiceImpl implements OrderOperationDeskService {

    @Autowired
    private OrderOperationDeskManager orderOperationDeskManager;

    @Override
    public int getOrderCountByArea(ReportOrderOpeDeskReq req) {
        return orderOperationDeskManager.getOrderCountByArea(req);
    }
    @Override
    public double getOrderAmountByArea(ReportOrderOpeDeskReq req){
        return orderOperationDeskManager.getOrderAmountByArea(req);
    }
    @Override
    public int getSaleCountByArea(ReportOrderOpeDeskReq req){
        return orderOperationDeskManager.getSaleCountByArea(req);
    }
    @Override
    public List<ReportOrderShopRankDTO> getShopSaleAmountRankByArea(ReportOrderOpeDeskReq req){
        return orderOperationDeskManager.getShopSaleAmountRankByArea(req);
    }
    @Override
    public List<ReportOrderTimeIntervalDTO> getTimeIntervalAmountByArea(ReportOrderOpeDeskReq req) throws Exception {
        return orderOperationDeskManager.getTimeIntervalAmountByArea(req);
    }
}
