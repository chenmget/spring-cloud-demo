package com.iwhalecloud.retail.order2b.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DeliveryGoodsResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "串码")
    private String nbr;

    @ApiModelProperty(value = "商品名称")
    private String goodName;

    @ApiModelProperty(value = "颜色")
    private String color;

    @ApiModelProperty(value = "内存版本")
    private String memory;

    @ApiModelProperty(value = "发货结果 0成功；1异常")
    private String result;

    @ApiModelProperty(value = "发货结果说明")
    private String resultDesc;
}
