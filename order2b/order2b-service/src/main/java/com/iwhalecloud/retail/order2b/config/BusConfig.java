package com.iwhalecloud.retail.order2b.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:properties/${spring.profiles.active}/bestpay.properties")
@ConfigurationProperties
public class BusConfig {
    private String ORGLOGINCODE;
    private String PLATCODE;
    private String SYNNOTICEURL;
    private String ASYNNOTICEURL;

    private String URL;

    public String getORGLOGINCODE() {
        return ORGLOGINCODE;
    }

    public void setORGLOGINCODE(String ORGLOGINCODE) {
        this.ORGLOGINCODE = ORGLOGINCODE;
    }

    public String getPLATCODE() {
        return PLATCODE;
    }

    public void setPLATCODE(String PLATCODE) {
        this.PLATCODE = PLATCODE;
    }

    public String getSYNNOTICEURL() {
        return SYNNOTICEURL;
    }

    public void setSYNNOTICEURL(String SYNNOTICEURL) {
        this.SYNNOTICEURL = SYNNOTICEURL;
    }

    public String getASYNNOTICEURL() {
        return ASYNNOTICEURL;
    }

    public void setASYNNOTICEURL(String ASYNNOTICEURL) {
        this.ASYNNOTICEURL = ASYNNOTICEURL;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
