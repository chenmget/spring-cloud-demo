package com.iwhalecloud.retail.order2b.manager;

import com.iwhalecloud.retail.order2b.dto.model.pay.PayOperationLogDTO;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.UpdateOrdOrderReq;
import com.iwhalecloud.retail.order2b.entity.PayOperationLog;
import com.iwhalecloud.retail.order2b.mapper.PayOperationLogMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class PayOperationLogManager{
    @Resource
    private PayOperationLogMapper payOperationLogMapper;

    public int savaPayOperationLog(PayOperationLogDTO payLogDTO){
        PayOperationLog payLog = new PayOperationLog();
        BeanUtils.copyProperties(payLogDTO, payLog);
        return payOperationLogMapper.insert(payLog);
    }

    public void updateOrdOrderStatus(UpdateOrdOrderReq req){
    	payOperationLogMapper.updateOrdOrderStatus(req);
    }
}
