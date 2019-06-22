package com.iwhalecloud.retail.pay.manager;

import com.iwhalecloud.retail.order.mapper.SmsSendMapper;
import com.iwhalecloud.retail.pay.dto.SmsSendDTO;
import com.iwhalecloud.retail.pay.entity.SmsSend;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class SmsSendManager{
    @Resource
    private SmsSendMapper smsSendMapper;
    
    public int saveSms(SmsSendDTO smsSendDTO){
        SmsSend smsSend = new SmsSend();
        BeanUtils.copyProperties(smsSendDTO, smsSend);
        int ret = smsSendMapper.insert(smsSend);
        return ret;
    }
    
}
