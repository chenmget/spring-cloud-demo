package com.iwhalecloud.retail.order.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.iwhalecloud.retail.order.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.model.OrderModel;
import com.iwhalecloud.retail.order.manager.OrderManager;
import com.iwhalecloud.retail.pay.dto.OrderPayDTO;
import com.iwhalecloud.retail.pay.dto.PayParamsDTO;
import com.iwhalecloud.retail.pay.dto.request.NotifyRequestDTO;
import com.iwhalecloud.retail.pay.dto.request.PayParamsRequest;
import com.iwhalecloud.retail.pay.dto.request.SaveOrderPayRequestDTO;
import com.iwhalecloud.retail.pay.dto.request.UpdateOrderPayRequestDTO;
import com.iwhalecloud.retail.pay.dto.response.SaveOrderPayResponseDTO;
import com.iwhalecloud.retail.pay.dto.response.UpdateOrderPayResponseDTO;
import com.iwhalecloud.retail.pay.handler.OrderPayHandler;
import com.iwhalecloud.retail.pay.service.OrderPayService;
import com.iwhalecloud.retail.pay.service.PayManagerOpenService;
import com.iwhalecloud.retail.pay.util.PayClient;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class PayManagerOpenServiceImpl implements PayManagerOpenService {


    @Autowired
    private OrderPayHandler orderPayHandler;

    @Autowired
    private OrderPayService orderPayService;

    @Autowired
    private OrderManager orderManager;

    @Override
    public CommonResultResp toPay(PayParamsRequest toPayRequest) {
        CommonResultResp resultVO=new CommonResultResp();
        OrderModel order = orderManager.selectOrder(toPayRequest.getOrderId());

        PayParamsDTO paramsDTO=orderPayHandler.builderPayParams(toPayRequest,order);

        JSONObject jsonObject=new JSONObject();
        SaveOrderPayRequestDTO saveOrderPayRequestDTO = orderPayHandler.covertOrderPayByJson(jsonObject);
        SaveOrderPayResponseDTO responseDTO = orderPayService.saveOrderPay(saveOrderPayRequestDTO);
        if(responseDTO.isFlag()) {
            //调用网管，发起支付
            resultVO= PayClient.toPay(paramsDTO);
        }else{
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg("入库失败");
        }
        return resultVO;
    }


    @Override
    public CommonResultResp notify(NotifyRequestDTO notifyRequest) {
        CommonResultResp resp=new CommonResultResp();

        OrderPayDTO orderPayDTO = this.orderPayService.getOrderPayByOrderId(notifyRequest.getOrder_id());
//        String s = createSign(jsonObject, "");
        String s="";
        UpdateOrderPayRequestDTO updateOrderPayRequestDTO = new UpdateOrderPayRequestDTO();
        updateOrderPayRequestDTO.setTransactionId(orderPayDTO.getTransactionId());
        if (s.equals(notifyRequest.getSign())) {
            //支付成功
            updateOrderPayRequestDTO.setStatus("1100");
            //订单支付成功
        } else {
            updateOrderPayRequestDTO.setStatus("1200");
        }
        updateOrderPayRequestDTO.setPayId(notifyRequest.getPay_id());
        updateOrderPayRequestDTO.setTransSeq(notifyRequest.getTrans_seq());
        updateOrderPayRequestDTO.setUpTransSeq(notifyRequest.getUp_trans_seq());
        updateOrderPayRequestDTO.setPayChanlReqTransSeq(notifyRequest.getPay_chanl_req_trans_seq());
        updateOrderPayRequestDTO.setBankTransSeq(notifyRequest.getBank_trans_seq());
        UpdateOrderPayResponseDTO ret = orderPayService.updateOrderPay(updateOrderPayRequestDTO);

        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

}
