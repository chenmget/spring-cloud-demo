package com.iwhalecloud.retail.goods2b.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/30 15:18
 */
@Data
public class ProductRebateReq implements Serializable {
    @ApiModelProperty(value = "产品名称")
    private String productName;
    @ApiModelProperty(value = "品牌")
    private String brandName;
    @ApiModelProperty(value = "产品型号")
    private String unitType;
    @ApiModelProperty(value = "产品类型")
    private String typeId;
    @ApiModelProperty(value = "品牌id")
    private String brandId;
    @ApiModelProperty(value = "归属厂家")
    private String manufacturerId;
}
