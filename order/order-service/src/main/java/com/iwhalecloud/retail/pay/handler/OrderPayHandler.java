package com.iwhalecloud.retail.pay.handler;

import com.alibaba.fastjson.JSONObject;
import com.iwhalecloud.retail.order.dto.model.OrderModel;
import com.iwhalecloud.retail.pay.dto.PayAttachInfoDTO;
import com.iwhalecloud.retail.pay.dto.PayOrderInfoDTO;
import com.iwhalecloud.retail.pay.dto.PayParamsDTO;
import com.iwhalecloud.retail.pay.dto.request.PayParamsRequest;
import com.iwhalecloud.retail.pay.dto.request.SaveOrderPayRequestDTO;
import com.iwhalecloud.retail.pay.util.PayClient;
import com.iwhalecloud.retail.pay.util.PayUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OrderPayHandler {


    public PayParamsDTO builderPayParams(PayParamsRequest p, OrderModel order) {

        PayParamsDTO paramsDTO = new PayParamsDTO();
        // 业务渠道编码:业务渠道编码 说明：需要向电渠支付网关管理负责人申请。
        paramsDTO.setAccount_id(PayClient.busiChannel);
        // 支付主帐号:业务渠道帐号    说明：需要向电渠支付网关管理负责人申请。
        paramsDTO.setAccount_id(PayClient.accountId);
        // 支付渠道编码:
        // 支付渠道编码(支付平台分配）
        // BEST：翼支付
        // ALI：支付宝
        // WX：微信
        // TEN: 财付通
        // JT ： 集团电渠
        // 说明：如需使用该字段，需向电渠支付网关管理负责人申请开通。
        paramsDTO.setPay_channel(null);
        // 业务渠道支付请求流水
        // 由业务渠道提供，建议：yyyyMMddhhmmss + 6位随机数
        paramsDTO.setRequest_seq(PayUtils.CreateSeq());
//        分账明细 分账填写,不分账为空。格式：account1:800|account2:100|account3:100

        // 支付类型
        //支付类型编码（支付平台定义）WEB：web网关
        // WAP：wap网关
        // NATIVE：扫码
        // JSAPI : 微信公众号
        // MWEB：微信H5
        // NST：分期支付
        paramsDTO.setPay_type(p.getPayType()); //JSAPI

        // 请求时间:yyyyMMddhhmmss
        paramsDTO.setRequest_time(PayUtils.dateToStr(new Date(), "yyyyMMddhhmmss"));

        // 支付金额 单位：分
        paramsDTO.setPay_amount(String.valueOf(order.getOrderAmount()));

        // 后台返回地址 用于接收交易返回的后台url
        paramsDTO.setNotify_url(PayClient.notifyUrl);

        //  银行编码 此参数对应银行编码表
        paramsDTO.setBank_id(p.getBankId());

        // 客户端IP 用户IP
        paramsDTO.setClient_ip("127.0.0.1");

        paramsDTO.setAttach_info(builderAttachInfo(p));

        paramsDTO.setOrder_info(builderOrderInfo(order));

        return paramsDTO;


    }

    private PayAttachInfoDTO builderAttachInfo(PayParamsRequest p) {
        PayAttachInfoDTO attachInfoDTO = new PayAttachInfoDTO();
        attachInfoDTO.setAttach("");
        attachInfoDTO.setCust_id(p.getOpenId());
        attachInfoDTO.setProduct_id("");

        attachInfoDTO.setProduct_desc("");
        attachInfoDTO.setBusi_code("");
        attachInfoDTO.setTmnum("");
        return attachInfoDTO;
    }

    private PayOrderInfoDTO builderOrderInfo(OrderModel order) {
        PayOrderInfoDTO orderInfoDTO = new PayOrderInfoDTO();
        orderInfoDTO.setOrder_date( PayUtils.dateToStr(new Date(order.getCreateTime()), "yyyyMMddhhmmss"));
        orderInfoDTO.setOrder_desc("网购");
        orderInfoDTO.setOrder_id(order.getOrderId());
        orderInfoDTO.setOver_time(PayUtils.dateToStr(new Date(), "yyyyMMddhhmmss"));
        return orderInfoDTO;
    }


    public SaveOrderPayRequestDTO covertOrderPayByJson(JSONObject payObj) {
        SaveOrderPayRequestDTO saveOrderPayRequestDTO = new SaveOrderPayRequestDTO();
        saveOrderPayRequestDTO.setBankId(payObj.getString("bank_id"));
        saveOrderPayRequestDTO.setPayChannel(payObj.getString("pay_channel"));
        saveOrderPayRequestDTO.setPayType(payObj.getString("pay_type"));
        saveOrderPayRequestDTO.setStatus("1000");
        saveOrderPayRequestDTO.setAttachInfo(payObj.getString("attach_info"));
        saveOrderPayRequestDTO.setClientIp(payObj.getString("client_ip"));
        saveOrderPayRequestDTO.setDivDetails(payObj.getString("div_details"));
        saveOrderPayRequestDTO.setLimitPay(payObj.getString("limit_pay"));
        saveOrderPayRequestDTO.setNotifyUrl(payObj.getString("notify_url"));
        saveOrderPayRequestDTO.setOrderInfo(payObj.getString("order_info"));
        saveOrderPayRequestDTO.setPayAmount(payObj.getLong("pay_amount"));
        saveOrderPayRequestDTO.setPedCnt(payObj.getString("ped_cnt"));
        saveOrderPayRequestDTO.setRequestSeq(payObj.getString("request_seq"));
        saveOrderPayRequestDTO.setOrderId((payObj.getJSONObject("order_info")).getString("order_id"));
        saveOrderPayRequestDTO.setRequestTime(PayUtils.strToUtilDate(payObj.getString("request_time"), "yyyyMMddhhmmss"));
        saveOrderPayRequestDTO.setReturnUrl(payObj.getString("return_url"));
        return saveOrderPayRequestDTO;
    }
}
