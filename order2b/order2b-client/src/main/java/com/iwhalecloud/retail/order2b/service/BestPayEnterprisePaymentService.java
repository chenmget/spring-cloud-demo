package com.iwhalecloud.retail.order2b.service;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.OffLinePayResp;
import com.iwhalecloud.retail.order2b.dto.response.OrderPayInfoResp;
import com.iwhalecloud.retail.order2b.dto.response.ToPayResp;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.*;

public interface BestPayEnterprisePaymentService {

    /**
     * 去支付
     * @param req
     * @return
     */
    public ResultVO<ToPayResp> toPay(ToPayReq req);

    /**
     * 翼支付支付结果回调并记录支付支付结果
     * @param req
     * @return
     */
    public ResultVO asynNotify(AsynNotifyReq req);

    /**
     * 订单支付状态查询即展示
     * @param req
     * @return
     */
    public ResultVO<OrderPayInfoResp> qryOrderPayInfo(OrderPayInfoReq req);

    /**
     * 线下支付
     * @param req
     * @return
     */
    public ResultVO<OffLinePayResp> offLinePay(OffLinePayReq req);

    /**
     * 构造去翼支付的参数，这是要调用翼支付的证书、支付平台号、公约
     * @param req
     * @return
     */
    public ToPayResp handlePayData(ToBestPayReq req);

}
