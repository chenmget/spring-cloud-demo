package com.iwhalecloud.retail.pay.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class BusConfig
{
    @Value("${pay.ORGLOGINCODE}")
    private String ORGLOGINCODE;

    @Value("${pay.PLATCODE}")
    private String PLATCODE;

    @Value("${pay.SYNNOTICEURL}")
    private String SYNNOTICEURL;

    @Value("${pay.ASYNNOTICEURL}")
    private String ASYNNOTICEURL;

    @Value("${pay.URL}")
    private String URL;

}
