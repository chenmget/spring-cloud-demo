package com.iwhalecloud.retail.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.pay.dto.SmsSendDTO;
import com.iwhalecloud.retail.pay.manager.SmsSendManager;
import com.iwhalecloud.retail.pay.service.SmsSendService;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class SmsSendServiceImpl implements SmsSendService {

    @Autowired
    private SmsSendManager smsSendManager;

    @Override
    public int saveSmsSend(SmsSendDTO smsSendDTO) {
        smsSendManager.saveSms(smsSendDTO);
        return 0;
    }
}