package com.iwhalecloud.retail.oms.dto.resquest;

import lombok.Data;

import java.io.Serializable;

/**
 * @author mzl
 * @date 2018/10/17
 */
@Data
public class RandomLogQueryReq implements Serializable {

    private static final long serialVersionUID = -268502043075309536L;
    /**
     * 验证码
     */
    private String randomCode;

    /**
     * 信息接收账号
     */
    private String receviNo;
}
