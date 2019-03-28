package com.iwhalecloud.retail.goods2b.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mzl
 * @date 2019/2/26
 */
@Data
@ApiModel(value = "添加商品返回结果")
public class ExchangeObjectGetResp implements Serializable {

    private static final long serialVersionUID = -8937915699388098094L;

    /**
     * 换货对象
     */
    @ApiModelProperty(value = "换货对象")
    private String exchangeObject;


    /**
     * 归属厂家
     */
    @ApiModelProperty(value = "归属厂家")
    private String manufacturerId;
}
