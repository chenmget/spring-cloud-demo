package com.iwhalecloud.retail.oms.dto.resquest;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/11/13
 **/
@Data
public class TempCartReq implements Serializable {
    /**
     * key值
     */
    private String key;
    /**
     * json字符串
     */
    private String value;
}
