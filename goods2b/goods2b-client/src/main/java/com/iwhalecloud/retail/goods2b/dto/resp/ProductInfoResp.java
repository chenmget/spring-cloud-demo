package com.iwhalecloud.retail.goods2b.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "对应模型prod_product, 对应实体ProdProduct类")
public class ProductInfoResp implements Serializable {

    @ApiModelProperty(value = "productId")
    private String productId;

    @ApiModelProperty(value = "产品名称")
    private String unitName;

    @ApiModelProperty(value = "政企价格")
    private double corporationPrice;
    
}
