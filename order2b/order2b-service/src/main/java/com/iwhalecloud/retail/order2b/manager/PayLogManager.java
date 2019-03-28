package com.iwhalecloud.retail.order2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.order2b.dto.model.pay.PayLogDTO;
import com.iwhalecloud.retail.order2b.dto.response.OrderPayInfoResp;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.OrderPayInfoReq;
import com.iwhalecloud.retail.order2b.entity.PayLog;
import com.iwhalecloud.retail.order2b.mapper.PayLogMapper;
import com.iwhalecloud.retail.order2b.util.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;


@Component
public class PayLogManager{
    @Resource
    private PayLogMapper payLogMapper;


    @Value("${fdfs.show.url}")
    private String showUrl;
    
    public int savaPayLog(PayLogDTO payLogDTO){
        PayLog payLog = new PayLog();
        BeanUtils.copyProperties(payLogDTO, payLog);
        return payLogMapper.insert(payLog);
    }

    public int updatePayLog(PayLogDTO payLogDTO){
        PayLog payLog = new PayLog();
        BeanUtils.copyProperties(payLogDTO, payLog);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(PayLog.FieldNames.orderId.getTableFieldName(), payLogDTO.getOrderId());
        BeanUtils.copyProperties(payLogDTO, payLog);
        return payLogMapper.update(payLog, queryWrapper);
    }

    public int updatePayLogId(PayLogDTO payLogDTO){
        PayLog payLog = new PayLog();
        BeanUtils.copyProperties(payLogDTO, payLog);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(PayLog.FieldNames.id.getTableFieldName(), payLogDTO.getId());
        BeanUtils.copyProperties(payLogDTO, payLog);
        return payLogMapper.update(payLog, queryWrapper);
    }

    public OrderPayInfoResp qryOrderPayInfo(OrderPayInfoReq req){
        QueryWrapper<PayLog> queryWrapper = new QueryWrapper<>();
        //queryWrapper.eq(PayLog.FieldNames.orderId.getTableFieldName(), req.getOrderId());
        queryWrapper.eq(PayLog.FieldNames.id.getTableFieldName(), req.getOrderId());
        PayLog payLog = this.payLogMapper.selectOne(queryWrapper);
        if (null == payLog) {
            return null;
        }

        OrderPayInfoResp resp = new OrderPayInfoResp();
        BeanUtils.copyProperties(payLog, resp);
        return resp;
    }

    public List<OrderPayInfoResp> qryOrderPayInfoList(OrderPayInfoReq req){
        List<OrderPayInfoResp> orderPayInfoResps = Lists.newArrayList();
        QueryWrapper<PayLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(PayLog.FieldNames.orderId.getTableFieldName(), req.getOrderId());
        List<PayLog> payLogs = this.payLogMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(payLogs)) {
            return null;
        }
        for (PayLog payLog : payLogs) {
            OrderPayInfoResp resp = new OrderPayInfoResp();
            BeanUtils.copyProperties(payLog, resp);
            resp.setPayDataMd(Utils.attacheUrlPrefix(showUrl,resp.getPayDataMd()));
            orderPayInfoResps.add(resp);
        }
        return orderPayInfoResps;
    }

    public OrderPayInfoResp qryOrderPayInfoById(String id){
        PayLog payLog = this.payLogMapper.selectById(id);
        if (null == payLog) {
            return null;
        } else {
            OrderPayInfoResp resp = new OrderPayInfoResp();
            BeanUtils.copyProperties(payLog, resp);
            return resp;
        }
    }

}
