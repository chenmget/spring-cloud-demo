package com.iwhalecloud.retail.order.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class MemberAddrDTO implements Serializable {


    private String provinceId;
    private String province;
    private String cityId;
    private String city;
    private String regionId;
    private String region;

    private String shipAddr;
    private String shipZip;
    private String shipTel;
    private String shipMobile;
    private String shipEmail;

}
