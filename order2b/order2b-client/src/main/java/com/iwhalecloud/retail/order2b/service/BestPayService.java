package com.iwhalecloud.retail.order2b.service;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.OffLinePayResp;
import com.iwhalecloud.retail.order2b.dto.response.OrderPayInfoResp;
import com.iwhalecloud.retail.order2b.dto.response.ToPayResp;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.*;

public interface BestPayService {
    /**
     * 构造去翼支付的参数，这是要调用翼支付的证书、支付平台号、公约
     * @param req
     * @return
     */
    public ToPayResp handlePayData(ToBestPayReq req);

}
