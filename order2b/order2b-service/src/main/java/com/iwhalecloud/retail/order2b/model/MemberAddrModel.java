package com.iwhalecloud.retail.order2b.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class MemberAddrModel implements Serializable {

    private String provinceId;
    private String province;
    private String cityId;
    private String city;
    private String regionId;
    private String region;

    private String receiveName;
    private String receiveAddr;
    private String receiveZip;
    private String receiveEmail;
    private String receiveMobile;

}
