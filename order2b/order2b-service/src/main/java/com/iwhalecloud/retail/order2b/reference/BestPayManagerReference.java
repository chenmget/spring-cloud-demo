package com.iwhalecloud.retail.order2b.reference;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.order2b.dto.response.ToPayResp;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.ToBestPayReq;
import com.iwhalecloud.retail.order2b.service.BestPayService;
import org.springframework.stereotype.Component;

@Component
public class BestPayManagerReference {

    @Reference
    private BestPayService bestPayService;

    public ToPayResp handlePayData(ToBestPayReq req) {
        return bestPayService.handlePayData(req);
    }
}
