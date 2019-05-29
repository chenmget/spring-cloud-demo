package com.iwhalecloud.retail.order2b.busiservice;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.OrderPayInfoResp;
import com.iwhalecloud.retail.order2b.dto.response.ToPayResp;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.AsynNotifyReq;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.OffLinePayReq;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.OrderPayInfoReq;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.UpdateOrdOrderReq;
import com.iwhalecloud.retail.order2b.model.SaveLogModel;

public interface BPEPPayLogService {

    /**
     * 去支付支付日志入库
     * @return
     */
    public int saveLog(SaveLogModel saveLogModel);

    /**
     * 接受支付通知日志入库
     * @param saveLogModel
     * @return
     */
    public int updateLog(SaveLogModel saveLogModel);

    /**
     * 封装去支付数据
     * @param orderId
     * @param orderAmount
     * @param orgLoginCode
     * @return
     */
    public ToPayResp handlePayData(String orderId, String orderAmount, String orgLoginCode,String operationType);

    /**
     * 线下支付
     * @param req
     * @return
     */
    public int offLinePay(OffLinePayReq req);
    
    /**
     * 翼支付预授权支付更新订单状态LWS
     * @param req
     * @return
     */
    public void UpdateOrdOrderStatus(UpdateOrdOrderReq req);
    
    /**
     * 翼支付预授权支付
     * @param req
     * @return
     */
    public ResultVO openToBookingPay(OffLinePayReq req);

    /**
     * 校验通知支付结果
     * @param req
     * @return
     */
    public boolean checkNotifyData(AsynNotifyReq req);

    /**
     * 同支付业务id查支付
     * @param orderId
     * @return
     */
    public OrderPayInfoResp qryOrderPayInfoById(String orderId);

    /**
     * 查支付日志
     * @param req
     * @return
     */
    public OrderPayInfoResp qryOrderPayInfo(OrderPayInfoReq req);

}
