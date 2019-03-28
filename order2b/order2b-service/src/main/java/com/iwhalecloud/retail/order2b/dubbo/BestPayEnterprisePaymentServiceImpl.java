package com.iwhalecloud.retail.order2b.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.OffLinePayResp;
import com.iwhalecloud.retail.order2b.dto.response.OrderPayInfoResp;
import com.iwhalecloud.retail.order2b.dto.response.ToPayResp;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.AsynNotifyReq;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.OffLinePayReq;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.OrderPayInfoReq;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.ToPayReq;
import com.iwhalecloud.retail.order2b.service.BestPayEnterprisePaymentService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class BestPayEnterprisePaymentServiceImpl implements BestPayEnterprisePaymentService {

    @Autowired
    private BestPayEnterprisePaymentService bestPayEnterprisePaymentService;

    @Override
    public ResultVO<ToPayResp> toPay(ToPayReq req) {
        return bestPayEnterprisePaymentService.toPay(req);
    }

    @Override
    public ResultVO asynNotify(AsynNotifyReq req) {
        return bestPayEnterprisePaymentService.asynNotify(req);
    }

    @Override
    public ResultVO<OrderPayInfoResp> qryOrderPayInfo(OrderPayInfoReq req) {
        return bestPayEnterprisePaymentService.qryOrderPayInfo(req);
    }

    @Override
    public ResultVO<OffLinePayResp> offLinePay(OffLinePayReq req) {
        return bestPayEnterprisePaymentService.offLinePay(req);
    }
}
