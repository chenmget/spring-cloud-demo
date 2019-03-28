package com.iwhalecloud.retail.goods2b.dto.resp;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/2/27.
 */
public class ComplexInfoResp  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "complexInfoId")
    private String complexInfoId;

    @ApiModelProperty(value = "aGoodsId")
    private String aGoodsId;

    @ApiModelProperty(value = "zGoodsId")
    private String zGoodsId;

    @ApiModelProperty(value = "complexInfo")
    private String complexInfo;
}
