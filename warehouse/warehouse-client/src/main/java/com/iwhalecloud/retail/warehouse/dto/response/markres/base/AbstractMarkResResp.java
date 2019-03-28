package com.iwhalecloud.retail.warehouse.dto.response.markres.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 营销资源返回信息
 * @author 吴良勇
 * @date 2019/3/2 9:42
 */
@Data
public abstract class AbstractMarkResResp implements Serializable {

    private String Message;
    private String ExchangeId;
    private String Code;

}
