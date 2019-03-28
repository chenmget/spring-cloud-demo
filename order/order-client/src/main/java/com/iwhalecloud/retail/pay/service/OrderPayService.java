package com.iwhalecloud.retail.pay.service;


import com.iwhalecloud.retail.pay.dto.OrderPayDTO;
import com.iwhalecloud.retail.pay.dto.request.SaveOrderPayRequestDTO;
import com.iwhalecloud.retail.pay.dto.request.UpdateOrderPayRequestDTO;
import com.iwhalecloud.retail.pay.dto.response.SaveOrderPayResponseDTO;
import com.iwhalecloud.retail.pay.dto.response.UpdateOrderPayResponseDTO;

public interface OrderPayService{

    SaveOrderPayResponseDTO saveOrderPay(SaveOrderPayRequestDTO saveOrderPayRequestDTO);

    UpdateOrderPayResponseDTO updateOrderPay(UpdateOrderPayRequestDTO updateOrderPayRequestDTO);

    OrderPayDTO getOrderPay(String transactionId );

    OrderPayDTO getOrderPayByOrderId(String orderId );
}