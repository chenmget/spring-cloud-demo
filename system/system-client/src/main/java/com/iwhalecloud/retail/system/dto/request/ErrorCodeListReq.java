package com.iwhalecloud.retail.system.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 异常编码列表查询
 * @author: Z
 * @date: 2019/7/1 21:59
 */
@Data
public class ErrorCodeListReq implements Serializable {

    @ApiModelProperty(value = "公共模块以：-1" +
            "system模块：system" +
            "order模块：order" +
            "goods模块：goods" +
            "partner模块：partner" +
            "rights模块：rights" +
            "promo模块：promo" +
            "payment模块：payment" +
            "warehouse模块：warehouse" +
            "workflow模块：workflow" +
            "web模块：web" +
            "job模块：job" +
            "report模块：report" +
            "oms模块：oms")
    private java.lang.String systemCode;
}
