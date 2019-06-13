package com.iwhalecloud.retail.goods2b.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/4/15.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoodsSaleNumDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "销售数量")
    private Integer saleNum;

    @ApiModelProperty(value = "缩略图")
    private String imageUrl;

    @ApiModelProperty(value = "产品ID")
    private String productId;

    @ApiModelProperty(value = "产品BaseID")
    private String productBaseId;

    @ApiModelProperty(value = "产品名字")
    private String productName;
}
