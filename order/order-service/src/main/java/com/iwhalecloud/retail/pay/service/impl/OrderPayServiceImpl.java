package com.iwhalecloud.retail.pay.service.impl;

import com.iwhalecloud.retail.pay.dto.OrderPayDTO;
import com.iwhalecloud.retail.pay.dto.request.SaveOrderPayRequestDTO;
import com.iwhalecloud.retail.pay.dto.request.UpdateOrderPayRequestDTO;
import com.iwhalecloud.retail.pay.dto.response.SaveOrderPayResponseDTO;
import com.iwhalecloud.retail.pay.dto.response.UpdateOrderPayResponseDTO;
import com.iwhalecloud.retail.pay.entity.OrderPay;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.iwhalecloud.retail.pay.manager.OrderPayManager;
import com.iwhalecloud.retail.pay.service.OrderPayService;
import org.springframework.stereotype.Service;


@Service
public class OrderPayServiceImpl implements OrderPayService {

    @Autowired
    private OrderPayManager orderPayManager;

    @Override
    public SaveOrderPayResponseDTO saveOrderPay(SaveOrderPayRequestDTO saveOrderPayRequestDTO) {
        OrderPay orderPay = new OrderPay();
        BeanUtils.copyProperties(saveOrderPayRequestDTO, orderPay);
        int ret = orderPayManager.saveOrderPay(orderPay);
        SaveOrderPayResponseDTO saveOrderPayResponseDTO = new SaveOrderPayResponseDTO();
        if(ret > 0){
            saveOrderPayResponseDTO.setFlag(true);
        }else{
            saveOrderPayResponseDTO.setFlag(false);
        }
        return saveOrderPayResponseDTO;
    }

    @Override
    public UpdateOrderPayResponseDTO updateOrderPay(UpdateOrderPayRequestDTO updateOrderPayRequestDTO) {
        OrderPay orderPay = new OrderPay();
        BeanUtils.copyProperties(updateOrderPayRequestDTO, orderPay);
        UpdateOrderPayResponseDTO updateOrderPayResponseDTO = new UpdateOrderPayResponseDTO();
        int ret = orderPayManager.updateOrderPay(orderPay);
        if(ret > 0){
            updateOrderPayResponseDTO.setFlag(true);
        }else{
            updateOrderPayResponseDTO.setFlag(false);
        }
        return updateOrderPayResponseDTO;
    }

    @Override
    public OrderPayDTO getOrderPay(String transactionId) {
        OrderPay orderPay = orderPayManager.getOrderPay(transactionId);
        OrderPayDTO orderPayDTO = new OrderPayDTO();
        BeanUtils.copyProperties(orderPay, orderPayDTO);
        return orderPayDTO;
    }

    @Override
    public OrderPayDTO getOrderPayByOrderId(String orderId) {
        OrderPay orderPay = orderPayManager.getOrderPayByOrderId(orderId);
        OrderPayDTO orderPayDTO = new OrderPayDTO();
        BeanUtils.copyProperties(orderPay, orderPayDTO);
        return orderPayDTO;
    }
}