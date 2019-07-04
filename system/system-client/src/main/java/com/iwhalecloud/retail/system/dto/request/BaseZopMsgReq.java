package com.iwhalecloud.retail.system.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseZopMsgReq implements Serializable {

    /**
     * 地市Id
     */
    private String landId;
    /**
     * 电话号
     */
    private String phoneNo;
}
