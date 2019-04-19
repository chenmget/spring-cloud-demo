package com.iwhalecloud.retail.goods2b.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/4/15.
 */
@Data
public class GoodsSaleNumDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品ID")
    private String goodsId;

    @ApiModelProperty(value = "商品名字")
    private String goodsName;

    @ApiModelProperty(value = "销售数量")
    private Integer saleNum;

    @ApiModelProperty(value = "缩略图")
    private String imageUrl;
}
