package com.iwhalecloud.retail.order2b.reference;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.order2b.dto.response.ToPayResp;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.ToBestPayReq;
import com.iwhalecloud.retail.order2b.service.BestPayEnterprisePaymentService;

public class BestPayManagerReference {

    @Reference
    private BestPayEnterprisePaymentService bestPayEnterprisePaymentService;

    public ToPayResp handlePayData(ToBestPayReq req) {
        return bestPayEnterprisePaymentService.handlePayData(req);
    }
}
