package com.iwhalecloud.retail.pay.service;


import com.iwhalecloud.retail.pay.dto.SmsSendDTO;

public interface SmsSendService{

    public int saveSmsSend(SmsSendDTO smsSendDTO);

}